#[macro_use]
extern crate diesel;
#[macro_use]
extern crate diesel_migrations;

use actix_web::{App, HttpServer, web};

use crate::db::init_db;
use crate::users::routes::home;

mod schema;
mod users;
mod db;

#[actix_web::main]
async fn main() -> std::io::Result<()> {
    dotenv::dotenv().ok();

    let pool = init_db();

    let server =
        HttpServer::new(move ||
            App::new()
                .app_data(web::Data::new(pool.clone()))
                .service(home)
        )
            .bind(("0.0.0.0", 8080))?
            .run();

    println!("Server is running!");

    server.await
}