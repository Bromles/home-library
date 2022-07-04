use diesel::{Insertable, Queryable};
use serde::{Deserialize, Serialize};

use crate::schema::*;

#[derive(Serialize, Deserialize, Queryable, Debug)]
pub struct User {
    pub id: i64,
    pub login: String,
    pub email: String,
    pub created_at: chrono::NaiveDateTime,
}

#[derive(Insertable, Debug)]
#[table_name = "user"]
pub struct NewUser<'a> {
    pub login: &'a str,
    pub email: &'a str,
    pub created_at: chrono::NaiveDateTime,
}