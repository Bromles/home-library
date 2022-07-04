CREATE TABLE "user"
(
    id         BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL PRIMARY KEY,
    login      TEXT                             NOT NULL,
    email      TEXT                             NOT NULL,
    created_at TIMESTAMP                        NOT NULL
);