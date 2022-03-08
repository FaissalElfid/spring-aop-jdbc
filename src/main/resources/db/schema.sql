CREATE TABLE language
(
    id            varchar(11) NOT NULL,
    name          varchar(255) NOT NULL,
    author        varchar(255) NOT NULL,
    fileExtension varchar(255) NOT NULL,
    CONSTRAINT pk_language_id PRIMARY KEY (id)
);