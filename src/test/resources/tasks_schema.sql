CREATE SCHEMA TASKS
  AUTHORIZATION SA;

CREATE TABLE IF NOT EXISTS TASKS.USERS (
  ID    BIGINT IDENTITY PRIMARY KEY,
  LOGIN VARCHAR NOT NULL,
  NAME  VARCHAR NOT NULL
);
ALTER TABLE TASKS.USERS ADD CONSTRAINT LOGIN_UNIQUE UNIQUE (LOGIN);

CREATE TABLE IF NOT EXISTS TASKS.LISTS (
  ID      BIGINT IDENTITY PRIMARY KEY,
  SUMMARY VARCHAR
);

CREATE TABLE IF NOT EXISTS TASKS.PACKAGES (
  ID          BIGINT IDENTITY PRIMARY KEY,
  SUMMARY     VARCHAR        NOT NULL,
  LISTS_LIMIT SMALLINT       NOT NULL,
  PRICE       DECIMAL(10, 4) NOT NULL
);
