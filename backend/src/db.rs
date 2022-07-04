use std::ops::Deref;

use diesel::PgConnection;
use diesel::r2d2::ConnectionManager;
use diesel_migrations::embed_migrations;
use r2d2::Pool;

embed_migrations!();

pub(crate) fn init_db() -> Pool<ConnectionManager<PgConnection>> {
    let database_url = std::env::var("DATABASE_URL").expect("DATABASE_URL must be set");

    let manager = ConnectionManager::<PgConnection>::new(database_url);
    let pool = Pool::builder()
        .max_size(num_cpus::get_physical() as u32)
        .build(manager)
        .expect("Failed to create pool");

    embedded_migrations::run(pool.get().expect("Failed to get connection from pool").deref())
        .expect("Failed to run migrations");

    info!("Migrations are applied");

    pool
}