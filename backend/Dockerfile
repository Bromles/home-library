FROM rust:1.62.0-slim-bullseye as builder

# create a new empty shell project
RUN USER=root cargo new --bin app
WORKDIR /app

RUN apt-get update
RUN apt-get install -y pkg-config libssl-dev libpq-dev

# copy over your manifests
COPY ./Cargo.lock .
COPY ./Cargo.toml .

# this build step will cache your dependencies
RUN cargo build --release
RUN rm src/*.rs

# copy your source tree
COPY . .

# build for release
RUN rm ./target/release/deps/backend*
RUN cargo build --release

# our final base
FROM debian:bullseye-slim

RUN apt-get update
RUN apt-get install -y libpq5

# copy the build artifact from the build stage
COPY --from=builder /app/target/release/backend .

EXPOSE 8080
# set the startup command to run your binary
CMD ["./backend"]