use meilisearch_sdk::client::Client;

pub(crate) fn init_search_engine_client() -> Client {
    let engine_url = std::env::var("SEARCH_ENGINE_URL").expect("SEARCH_ENGINE_URL must be set");
    let engine_key = std::env::var("SEARCH_ENGINE_KEY").expect("SEARCH_ENGINE_KEY must be set");

    Client::new(engine_url, engine_key)
}