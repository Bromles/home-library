use diesel::{Insertable, Queryable};
use serde::{Deserialize, Serialize};

use crate::schema::*;

#[derive(Serialize, Deserialize, Queryable, Debug)]
pub(crate) struct User {
    pub(crate) id: i32,
    pub(crate) login: String,
    pub(crate) email: String,
    pub(crate) created_at: chrono::NaiveDateTime,
}

#[derive(Insertable, Debug)]
#[table_name = "user"]
pub(crate) struct NewUser<'a> {
    pub(crate) login: &'a str,
    pub(crate) email: &'a str,
    pub(crate) created_at: chrono::NaiveDateTime,
}