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

CREATE TABLE `team` (
    uuid BINARY(16) PRIMARY KEY,
    name VARCHAR(75) NOT NULL,
    image VARCHAR(100),
    shortname VARCHAR(75) NOT NULL,
    overviewpage VARCHAR(255),
    game varchar(10) NOT NULL
);

CREATE TABLE `teamxrleague` (
    teamuuid BINARY(16),
    leagueuuid BINARY(16),
    primary key (teamuuid,leagueuuid),
    FOREIGN KEY (teamuuid) REFERENCES team(uuid),
    FOREIGN KEY (leagueuuid) REFERENCES realleague(uuid)
);