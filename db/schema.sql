CREATE TABLE post (
    id SERIAL PRIMARY KEY,
    name TEXT,
    description TEXT
);

CREATE TABLE candidate (
    id SERIAL PRIMARY KEY,
    name TEXT,
    photo TEXT
);

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name TEXT,
    email TEXT not null unique,
    password TEXT
);

CREATE TABLE city (
    id SERIAL PRIMARY KEY,
    name TEXT
);

insert into city(name) values ('Moscow');
insert into city(name) values ('Spb');