use actix_web::{get, Responder, web};

#[get("/")]
pub(crate) async fn home() -> impl Responder {
    "Hello World!"
}

pub(crate) fn init_routes(cfg: &mut web::ServiceConfig) {
    cfg.service(home);
}