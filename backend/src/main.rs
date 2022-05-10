use actix_web::{web, App, HttpServer};

#[actix_web::main]
async fn main() -> std::io::Result<()> {
    let server =
        HttpServer::new(|| App::new().route("/hello", web::get().to(|| async { "Hello World!" })))
            .bind(("127.0.0.1", 8080))?
            .run();

    println!("Server is running!");

    server.await
}