use actix_web::{get, App, HttpServer, Responder};

#[get("/")]
async fn home() -> impl Responder {
    "Hello World!"
}

#[actix_web::main]
async fn main() -> std::io::Result<()> {
    let server =
        HttpServer::new(||
            App::new()
                .service(home)
        )
            .bind(("0.0.0.0", 8080))?
            .run();

    println!("Server is running!");

    server.await
}