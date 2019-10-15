DROP TABLE IF EXISTS children CASCADE;
DROP TABLE IF EXISTS parents CASCADE;

CREATE TABLE parents
(
    id           IDENTITY AUTO_INCREMENT PRIMARY KEY,
    title        VARCHAR(250) NOT NULL,
    firstName    VARCHAR(250) NOT NULL,
    lastName     VARCHAR(250) NOT NULL,
    emailAddress VARCHAR(250) NOT NULL,
    dateOfBirth  DATE         NOT NULL,
    gender       VARCHAR(250) NOT NULL,
    secondName   VARCHAR(250) DEFAULT NULL
);

CREATE TABLE children
(
    id           IDENTITY AUTO_INCREMENT PRIMARY KEY,
    firstName    VARCHAR(250) NOT NULL,
    lastName     VARCHAR(250) NOT NULL,
    emailAddress VARCHAR(250) NOT NULL,
    dateOfBirth  DATE         NOT NULL,
    gender       VARCHAR(250) NOT NULL,
    secondName   VARCHAR(250) DEFAULT NULL,
    parentId     BIGINT       NOT NULL,
    FOREIGN KEY (parentId) REFERENCES parents(id)
);
