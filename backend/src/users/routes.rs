use actix_web::{get, Responder};

#[get("/")]
pub async fn home() -> impl Responder {
    "Hello World!"
}