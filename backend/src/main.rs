#[macro_use]
extern crate diesel;
#[macro_use]
extern crate diesel_migrations;
#[macro_use]
extern crate log;

use actix_web::{App, HttpServer, web};

use crate::db::init_db;
use crate::search_engine::init_engine;
use crate::users::routes::init_routes;

mod schema;
mod users;
mod db;
mod search_engine;

#[actix_web::main]
async fn main() -> std::io::Result<()> {
    dotenv::dotenv().ok();
    env_logger::init();

    let pool = init_db();
    let client = init_engine();

    let server =
        HttpServer::new(move ||
            App::new()
                .app_data(web::Data::new(client.clone()))
                .app_data(web::Data::new(pool.clone()))
                .configure(init_routes)
        )
            .bind(("0.0.0.0", 8080))?
            .run();

    info!("Server is running!");

    server.await
}