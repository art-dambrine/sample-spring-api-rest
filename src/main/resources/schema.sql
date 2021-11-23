CREATE TABLE partners
(
    id           INT AUTO_INCREMENT PRIMARY KEY,
    company_name VARCHAR(250) NOT NULL,
    ref          VARCHAR(250) NOT NULL,
    locale       VARCHAR(250) NOT NULL,
    expires      DATETIME     NOT NULL
);