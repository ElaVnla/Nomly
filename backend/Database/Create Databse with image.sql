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
    FOREIGN KEY (ProfilePic) REFERENCES Images(imageId) ON DELETE CASCADE,
    unique(Email)
);

CREATE TABLE NomlyDB.Groupings (
	GroupId int NOT NULL auto_increment,
	GroupName varchar(50) NOT NULL,
    -- GroupPic varchar(255),
    GroupPic  int,
    createdAt datetime NOT NULL,
    groupCode char(5),
    PRIMARY KEY (GroupId),
    FOREIGN KEY (GroupPic) REFERENCES Images(imageId) ON DELETE CASCADE
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
    LatLong varchar(50) not null,
    MeetingDateTime datetime not null,
    CreatedAt datetime not null,
    Completed boolean,
    primary key (SessionId),
    foreign key (GroupId) references Groupings(GroupId) ON DELETE CASCADE

);

create table NomlyDB.Eateries(
	EateryId int NOT NULL auto_increment,
    Image int,
	Location varchar(255) not null,
    OperationHours varchar(50) not null,
    PriceRange varchar(10) not null,
    Cuisine varchar(50) not null,
    Rating decimal(1,1) ,
    primary key (EateryId),
    FOREIGN KEY (Image) REFERENCES Images(imageId) ON DELETE CASCADE
);

create table NomlyDB.SessionsEateries(
	SessionId int NOT NULL,
	EateryId int NOT NULL,
    foreign key (SessionId) references Sessions(SessionId) ON DELETE CASCADE,
    foreign key (EateryId) references Eateries(EateryId) ON DELETE CASCADE

);

create table NomlyDB.UsersSessionsEateries(
	UserId int NOT NULL,
	SessionId int NOT NULL,
	EateryId int NOT NULL,
    Liked bool not null,
    foreign key (UserId) references Users(UserId) ON DELETE CASCADE,
    foreign key (SessionId) references Sessions(SessionId) ON DELETE CASCADE,
    foreign key (EateryId) references Eateries(EateryId) ON DELETE CASCADE


);
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 