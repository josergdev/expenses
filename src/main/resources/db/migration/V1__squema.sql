CREATE TABLE IF NOT EXISTS friend
(
    id   VARCHAR(36)  NOT NULL UNIQUE PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS expense
(
    id          BIGINT       NOT NULL AUTO_INCREMENT UNIQUE PRIMARY KEY,
    payer_id    VARCHAR(36)  NOT NULL,
    amount      BIGINT       NOT NULL,
    description VARCHAR(255) NOT NULL,
    created_at  DATETIME     NOT NULL
);
