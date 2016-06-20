CREATE SCHEMA TASKS AUTHORIZATION SA;

CREATE TABLE IF NOT EXISTS TASKS.USERS (
  ID    INT IDENTITY PRIMARY KEY,
  LOGIN VARCHAR,
  NAME  VARCHAR
);

CREATE TABLE IF NOT EXISTS TASKS.LISTS (
  ID      INT IDENTITY PRIMARY KEY,
  SUMMARY VARCHAR
)
