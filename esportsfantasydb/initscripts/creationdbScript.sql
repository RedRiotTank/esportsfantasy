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
    apiID VARCHAR(255) ,
    shortname varchar(10) NOT NULL,
    game varchar(10) NOT NULL,
    currentjour INT
);

CREATE TABLE `team` (
    uuid BINARY(16) PRIMARY KEY,
    name VARCHAR(75) NOT NULL,
    image VARCHAR(100),
    shortname VARCHAR(75) NOT NULL,
    overviewpage VARCHAR(255),
    game varchar(10) NOT NULL
);

CREATE TABLE `games` (
    game varchar(5) PRIMARY KEY
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

CREATE TABLE `player` (
	uuid BINARY(16),
    username VARCHAR(75) NOT NULL,
    fullname VARCHAR(100),
    image VARCHAR(25),
    role VARCHAR(25) NOT NULL,
    value int NOT NULL,
	primary key(uuid)
);

CREATE TABLE `teamxplayer` (
	playeruuid BINARY(16),
    teamuuid BINARY(16),
	primary key(playeruuid,teamuuid),
    FOREIGN KEY (playeruuid) REFERENCES player(uuid),
    FOREIGN KEY (teamuuid) REFERENCES team(uuid)
);

CREATE TABLE `userxleaguexplayer` (
	useruuid BINARY(16),
    leagueuuid BINARY(16),
	playeruuid BINARY(16),
    jour int,
    aligned tinyint(7),
	primary key(useruuid,leagueuuid,playeruuid, jour),
    FOREIGN KEY (useruuid) REFERENCES user(uuid),
    FOREIGN KEY (leagueuuid) REFERENCES league(uuid),
    FOREIGN KEY (playeruuid) REFERENCES player(uuid)
);

CREATE TABLE `market` (
	playeruuid BINARY(16),
    leagueuuid BINARY(16),
    owneruuid BINARY(16),
    clause integer not null,
    insell tinyint(1) NOT null,
    marketValue int,
	primary key(playeruuid,leagueuuid),
    FOREIGN KEY (playeruuid) REFERENCES player(uuid),
    FOREIGN KEY (leagueuuid) REFERENCES league(uuid),
    FOREIGN KEY (owneruuid) REFERENCES user(uuid)
);

CREATE TABLE `bidup` (
	playeruuid BINARY(16),
    leagueuuid BINARY(16),
    biduseruuid BINARY(16),
    date DATETIME,
    state tinyint(1) NOT null,
    bid int,
	primary key(playeruuid,leagueuuid, biduseruuid, date),
    FOREIGN KEY (playeruuid) REFERENCES player(uuid),
    FOREIGN KEY (leagueuuid) REFERENCES league(uuid),
    FOREIGN KEY (biduseruuid) REFERENCES user(uuid)
);

CREATE TABLE `event` (
	realleagueuuid BINARY(16),
    team1uuid BINARY(16),
    team2uuid BINARY(16),
    date DATETIME,
    jour int,
    team1Score varchar(6),
    team2Score varchar(6),
    matchid varchar(100),
    mvp varchar(100),
    primary key(realleagueuuid,team1uuid,team2uuid,jour),
    FOREIGN KEY (realleagueuuid) REFERENCES realleague(uuid),
    FOREIGN KEY (team1uuid) REFERENCES team(uuid),
	FOREIGN KEY (team2uuid) REFERENCES team(uuid),
    UNIQUE(matchid)
);

CREATE TABLE `playerpoints` (
    matchid varchar(100),
    playeruuid BINARY(16),
    points int,
    PRIMARY KEY (matchid, playeruuid),
    FOREIGN KEY (matchid) REFERENCES event(matchid),
    FOREIGN KEY (playeruuid) REFERENCES player(uuid)
);

insert into games(game) values ("LOL");
insert into games(game) values ("CSGO");