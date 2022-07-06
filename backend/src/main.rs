#[macro_use]
extern crate diesel;
#[macro_use]
extern crate diesel_migrations;
#[macro_use]
extern crate log;

use actix_web::{web, App, HttpServer};
use actix_web_lab::middleware::RedirectHttps;
use diesel::r2d2::ConnectionManager;
use diesel::PgConnection;
use meilisearch_sdk::client::Client;
use r2d2::Pool;
use rustls::{Certificate, PrivateKey, ServerConfig};
use rustls_pemfile::{certs, pkcs8_private_keys};
use std::fs::File;
use std::io::BufReader;

use crate::db::init_db_pool_and_migrate;
use crate::search_engine::init_search_engine_client;
use crate::users::routes::init_routes;

mod db;
mod schema;
mod search_engine;
mod users;

struct AppState {
    db_pool: Pool<ConnectionManager<PgConnection>>,
    search_client: Client,
}

#[actix_web::main]
async fn main() -> std::io::Result<()> {
    dotenv::dotenv().ok();
    env_logger::init();

    let http_port = std::env::var("HTTP_PORT")
        .unwrap_or_else(|_| 8080.to_string())
        .parse::<u16>()
        .expect("HTTP_PORT must be a number");
    let https_port = std::env::var("HTTPS_PORT")
        .unwrap_or_else(|_| 8081.to_string())
        .parse::<u16>()
        .expect("HTTPS_PORT must be a number");

    let app_state = web::Data::new(AppState {
        db_pool: init_db_pool_and_migrate(),
        search_client: init_search_engine_client(),
    });

    let server = HttpServer::new(move || {
        App::new()
            .wrap(RedirectHttps::default().to_port(https_port))
            .app_data(app_state.clone())
            .configure(init_routes)
    })
    .bind(("0.0.0.0", http_port))?
    .bind_rustls(("0.0.0.0", https_port), init_rustls_config())?
    .run();

    info!(
        "Server is running on ports http:{} and https:{}",
        http_port, https_port
    );

    server.await
}

fn init_rustls_config() -> ServerConfig {
    // init server config builder with safe defaults
    let config = ServerConfig::builder()
        .with_safe_defaults()
        .with_no_client_auth();

    // load TLS key/cert files
    let cert_file = &mut BufReader::new(File::open("localhost-cert.pem").unwrap());
    let key_file = &mut BufReader::new(File::open("localhost-key.pem").unwrap());

    // convert files to key/cert objects
    let cert_chain = certs(cert_file)
        .unwrap()
        .into_iter()
        .map(Certificate)
        .collect();
    let mut keys: Vec<PrivateKey> = pkcs8_private_keys(key_file)
        .unwrap()
        .into_iter()
        .map(PrivateKey)
        .collect();

    // exit if no keys could be parsed
    if keys.is_empty() {
        error!("Could not locate PKCS 8 private keys.");
        std::process::exit(1);
    }

    config.with_single_cert(cert_chain, keys.remove(0)).unwrap()
}