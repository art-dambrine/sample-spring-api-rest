CREATE TABLE partners
(
    id           INT AUTO_INCREMENT PRIMARY KEY,
    company_name VARCHAR(250)             NOT NULL,
    ref          VARCHAR(250) UNIQUE      NOT NULL,
    locale       VARCHAR(250)             NOT NULL,
    expires      TIMESTAMP WITH TIME ZONE NOT NULL
);