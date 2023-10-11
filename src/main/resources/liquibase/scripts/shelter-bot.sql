-- liquibase formatted sql

--changeset elysanov:1
CREATE TABLE shelterbot.animal (
    id        SERIAL PRIMARY KEY,
    name      TEXT,
    age       INTEGER,
    gender    TEXT,
    type      TEXT
);

----changeset elysanov:2
--CREATE TABLE shelterbot.parent (
--    id        SERIAL PRIMARY KEY,
--    name      TEXT,
--    age       INTEGER CHECK (age >= 18),
--    username  TEXT,
--    gender    TEXT,
--    animal    TEXT ??????
--);
