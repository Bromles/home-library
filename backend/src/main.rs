mod models;
mod schema;

#[macro_use]
extern crate diesel;
#[macro_use]
extern crate diesel_migrations;

use std::ops::Deref;
use actix_web::{get, web, App, HttpServer, Responder};
use diesel::PgConnection;
use diesel::r2d2::ConnectionManager;
use diesel_migrations::embed_migrations;
use r2d2::Pool;

embed_migrations!();

#[get("/")]
async fn home() -> impl Responder {
    "Hello World!"
}

#[actix_web::main]
async fn main() -> std::io::Result<()> {
    dotenv::dotenv().ok();
    let database_url = std::env::var("DATABASE_URL").expect("DATABASE_URL must be set");

    let manager = ConnectionManager::<PgConnection>::new(database_url);
    let pool = Pool::builder()
        .build(manager)
        .expect("Failed to create pool");

    embedded_migrations::run(pool.get().expect("Failed to get connection from pool").deref())
        .expect("Failed to run migrations");

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