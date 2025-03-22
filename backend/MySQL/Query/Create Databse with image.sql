-- Query to create database and all its content



-- Checks if database already exist and deletes them if so
DROP DATABASE IF EXISTS NomlyDB;

-- Create new database with the name nomlyDB 
CREATE DATABASE NomlyDB;

create table NomlyDB.Images(
	ImageId int NOT NULL auto_increment,
    Image blob not null,
    primary key(imageId)
);


CREATE TABLE NomlyDB.Users (
	UserId int NOT NULL auto_increment,
    Username varchar(50) NOT NULL,
    Email varchar(255) NOT NULL,
    Password varchar(50) NOT NULL,
    ProfilePic int,
    Preferences varchar(255),
    createdAt datetime NOT NULL,
    PRIMARY KEY (UserId),
    FOREIGN KEY (ProfilePic) REFERENCES Images(imageId)
);

CREATE TABLE NomlyDB.Groupings (
	GroupId int NOT NULL auto_increment,
	GroupName varchar(50) NOT NULL,
    -- GroupPic varchar(255),
    GroupPic  int,
    createdAt datetime NOT NULL,
    PRIMARY KEY (GroupId),
    FOREIGN KEY (GroupPic) REFERENCES Images(imageId)
);

CREATE TABLE NomlyDB.Users_Groupings (
	GroupId int NOT NULL,
	UserId int NOT NULL,
    JoinedAt datetime NOT NULL,
    FOREIGN KEY (GroupId) REFERENCES Groupings(GroupId),
    FOREIGN KEY (UserId) REFERENCES Users(UserId)
);

CREATE TABLE NomlyDB.Sessions(
	SessionId int NOT NULL auto_increment,
	GroupId int NOT NULL,
    Location varchar(255) not null,
    MeetingDateTime datetime not null,
    CreatedAt datetime not null,
    Completed boolean,
    primary key (SessionId),
    foreign key (GroupId) references Groupings(GroupId)

);

create table NomlyDB.Eateries(
	EateryId int NOT NULL auto_increment,
    primary key (EateryId)
);

create table NomlyDB.Sessions_Eaateries(
	SessionId int NOT NULL,
	EateryId int NOT NULL,
    foreign key (SessionId) references Sessions(SessionId),
    foreign key (EateryId) references Eateries(EateryId)


);

create table NomlyDB.Users_Sessions_Eaateries(
	UserId int NOT NULL,
	SessionId int NOT NULL,
	EateryId int NOT NULL,
    Liked bool not null,
    foreign key (UserId) references Users(UserId),
    foreign key (SessionId) references Sessions(SessionId),
    foreign key (EateryId) references Eateries(EateryId)


);
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 