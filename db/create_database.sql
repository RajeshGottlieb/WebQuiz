-- discard old version of db
DROP DATABASE IF EXISTS web_quiz;

-- create the database
CREATE DATABASE web_quiz;

-- select for use
USE web_quiz;

-- user
CREATE TABLE user
(
    id          INT NOT NULL AUTO_INCREMENT,
    username    VARCHAR(40),   -- "bob"
    password    VARCHAR(40),   -- "password123"
    PRIMARY KEY(id)
);

-- subject
CREATE TABLE subject
(
    id          INT NOT NULL AUTO_INCREMENT,
    name        VARCHAR(40),   -- "math", "history"
    PRIMARY KEY(id)
);

-- category
CREATE TABLE category
(
    id          INT NOT NULL AUTO_INCREMENT,
    subject_id  INT,
    name        VARCHAR(40),   -- "addition", "subtraction"
    PRIMARY KEY(id)
);

-- module
CREATE TABLE module
(
    id          INT NOT NULL AUTO_INCREMENT,
    category_id INT,
    name        VARCHAR(40),   -- "single digit", "mutiple digits"
    PRIMARY KEY(id)
);

-- question
CREATE TABLE question
(
    id          INT NOT NULL AUTO_INCREMENT,
    module_id   INT,
    type        ENUM('fill-in-the-blank', 'multiple-choice', 'true-false'),
    text        TEXT,                     -- "(6 x 7) - 5"
    -- fill_in_the_blank_prefix CHAR(40),    -- "$"
    -- fill_in_the_blank_suffix CHAR(40),    -- "ounces"
    PRIMARY KEY(id)
);

-- answer
CREATE TABLE answer
(
    id          INT NOT NULL AUTO_INCREMENT,
    question_id INT,
    correct     TINYINT,    -- 1 for correct answer, 0 for incorrect answer
    value       TEXT,       -- "37", "true", "false"
    PRIMARY KEY(id)
);

