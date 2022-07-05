#[macro_use]
extern crate diesel;
#[macro_use]
extern crate diesel_migrations;
#[macro_use]
extern crate log;

use actix_web::{App, HttpServer, web};
use diesel::PgConnection;
use diesel::r2d2::ConnectionManager;
use meilisearch_sdk::client::Client;
use r2d2::Pool;

use crate::db::init_db_pool_and_migrate;
use crate::search_engine::init_search_engine;
use crate::users::routes::init_routes;

mod schema;
mod users;
mod db;
mod search_engine;

struct AppState {
    db_pool: Pool<ConnectionManager<PgConnection>>,
    search_client: Client,
}

#[actix_web::main]
async fn main() -> std::io::Result<()> {
    dotenv::dotenv().ok();
    env_logger::init();

    let app_state = web::Data::new(AppState {
        db_pool: init_db_pool_and_migrate(),
        search_client: init_search_engine(),
    });

    let server =
        HttpServer::new(move ||
            App::new()
                .app_data(app_state.clone())
                .configure(init_routes)
        )
            .bind(("0.0.0.0", 8080))?
            .run();

    info!("Server is running!");

    server.await
}