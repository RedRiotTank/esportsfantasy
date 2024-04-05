CREATE TABLE `user` (
    uuid BINARY(16) PRIMARY KEY,
    mail VARCHAR(75) NOT NULL,
    pass VARCHAR(255),
    username VARCHAR(25) NOT NULL,
    admin BOOLEAN DEFAULT FALSE NOT NULL
);

CREATE TABLE `realleague` (
    uuid BINARY(16) PRIMARY KEY,
    event VARCHAR(75) NOT NULL,
    overviewpage VARCHAR(255) NOT NULL,
    game varchar(10) NOT NULL
);