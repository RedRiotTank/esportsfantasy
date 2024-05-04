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

CREATE TABLE `league` (
    uuid BINARY(16) PRIMARY KEY,
    name VARCHAR(75) NOT NULL,
    activeclause tinyint(1),
    startingtype int(11) NOT NULL,
    realleague BINARY(16),
    publicleague tinyint(1),
    FOREIGN KEY (realleague) REFERENCES realleague(uuid)
);

CREATE TABLE `userxleague` (
	useruuid BINARY(16),
    leagueuuid BINARY(16),
    isAdmin boolean,
    money integer,
	primary key(useruuid,leagueuuid),
    FOREIGN KEY (useruuid) REFERENCES user(uuid),
    FOREIGN KEY (leagueuuid) REFERENCES league(uuid)
);

CREATE TABLE `market` (
	playeruuid BINARY(16),
    leagueuuid BINARY(16),
    owneruuid BINARY(16),
    clause integer not null,
    insell tinyint(1) NOT null,
    maxbid integer,
    biduseruuid binary(16),
	primary key(playeruuid,leagueuuid),
    FOREIGN KEY (playeruuid) REFERENCES player(uuid),
    FOREIGN KEY (leagueuuid) REFERENCES league(uuid),
    FOREIGN KEY (owneruuid) REFERENCES user(uuid),
    FOREIGN KEY (biduseruuid) REFERENCES user(uuid)
);