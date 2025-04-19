-- Query to create database and all its content



-- Checks if database already exist and deletes them if so
DROP DATABASE IF EXISTS NomlyDB;

-- Create new database with the name nomlyDB 
CREATE DATABASE NomlyDB;

create table NomlyDB.Images(
	ImageId int NOT NULL auto_increment,
    Image longblob not null,
    primary key(imageId)
);


CREATE TABLE NomlyDB.Users (
	UserId int NOT NULL auto_increment,
    Username varchar(50) NOT NULL,
    Email varchar(255) NOT NULL,
    Password varchar(50) NOT NULL,
    ImageId int,
    Preferences varchar(255),
    createdAt datetime NOT NULL,
    PRIMARY KEY (UserId),
    FOREIGN KEY (ImageId) REFERENCES Images(ImageId) ON DELETE CASCADE,
    unique(Email)
);



CREATE TABLE NomlyDB.Groupings (
	GroupId int NOT NULL auto_increment,
	GroupName varchar(50) NOT NULL,
    -- GroupPic varchar(255),
    ImageId  int,
    createdAt datetime NOT NULL,
    groupCode char(10),
    PRIMARY KEY (GroupId),
    FOREIGN KEY (ImageId) REFERENCES Images(ImageId) ON DELETE CASCADE
);

CREATE TABLE NomlyDB.UsersGroupings (
	UserGroupId int NOT NULL auto_increment,
	GroupId int NOT NULL,
	UserId int NOT NULL,
    JoinedAt datetime NOT NULL,
    PRIMARY KEY (UserGroupId),
    FOREIGN KEY (GroupId) REFERENCES Groupings(GroupId) ON DELETE CASCADE,
    FOREIGN KEY (UserId) REFERENCES Users(UserId) ON DELETE CASCADE
);

CREATE TABLE NomlyDB.Sessions(
	SessionId int NOT NULL auto_increment,
	GroupId int NOT NULL,
    SessionName varchar(50),
    Location varchar(255) not null,
    -- LatLong varchar(50) not null,
    Latitude decimal(18,15) not null,
    Longitude decimal(18,15) not null,
    MeetingDateTime datetime not null,
    CreatedAt datetime not null,
    Completed boolean,
    primary key (SessionId),
    foreign key (GroupId) references Groupings(GroupId) ON DELETE CASCADE

);

create table NomlyDB.Eateries(
	EateryId varchar(255) NOT NULL,
	DisplayName varchar(255) not null,
    Latitude decimal(18,15) not null,
    Longitude decimal(18,15) not null,
    PriceLevel ENUM("PRICE_LEVEL_UNSPECIFIED",
    "PRICE_LEVEL_FREE",
    "PRICE_LEVEL_INEXPENSIVE",
    "PRICE_LEVEL_MODERATE",
    "PRICE_LEVEL_EXPENSIVE",
    "PRICE_LEVEL_VERY_EXPENSIVE"),
    Cuisine varchar(255) not null,
    Rating decimal(2,1),
    OperationHours varchar(50) not null,
    Location varchar(255),
    primary key (EateryId)
);

create table NomlyDB.SessionsEateries(
	SessionEateryId int NOT NULL auto_increment,
	SessionId int NOT NULL,
	EateryId varchar(255) NOT NULL,
    primary key (SessionEateryId),
	-- Ranking int NOT NULL,
    foreign key (SessionId) references Sessions(SessionId) ON DELETE CASCADE,
    foreign key (EateryId) references Eateries(EateryId) ON DELETE CASCADE

);

create table NomlyDB.UsersSessionsEateries(
	UserSessionEateryId int NOT NULL auto_increment,
	UserId int NOT NULL,
	SessionId int NOT NULL,
	EateryId varchar(255) NOT NULL,
    Liked bool not null,
    primary key (UserSessionEateryId),
    foreign key (UserId) references Users(UserId) ON DELETE CASCADE,
    foreign key (SessionId) references Sessions(SessionId) ON DELETE CASCADE,
    foreign key (EateryId) references Eateries(EateryId) ON DELETE CASCADE
);

create table NomlyDB.EateriesPhotos(
	PhotoId int NOT NULL auto_increment,
    PhotoName varchar(1000) NOT NULL,
    EateryId varchar(255) NOT NULL,
    primary key (PhotoId),
    foreign key (EateryId) references Eateries(EateryId) ON DELETE CASCADE
);