use actix_web::{get, Responder};

#[get("/")]
pub(crate) async fn home() -> impl Responder {
    "Hello World!"
}