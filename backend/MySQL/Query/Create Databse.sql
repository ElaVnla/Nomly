-- Query to create database and all its content



-- Checks if database already exist and deletes them if so
DROP DATABASE IF EXISTS NomlyDB;

-- Create new database with the name nomlyDB 
CREATE DATABASE NomlyDB;

CREATE TABLE NomlyDB.Users (
	UserId int NOT NULL auto_increment,
    Username varchar(50) NOT NULL,
    Email varchar(255) NOT NULL,
    Password varchar(50) NOT NULL,
    -- ProfilePic varchar(255),
--     ProfilePic blob,
    Preferences varchar(255),
    createdAt datetime NOT NULL,
    PRIMARY KEY (UserId)
);

CREATE TABLE NomlyDB.Groupings (
	GroupId int NOT NULL auto_increment,
	GroupName varchar(50) NOT NULL,
    -- GroupPic varchar(255),
    GroupPic blob,
    createdAt datetime NOT NULL,
    PRIMARY KEY (GroupId)
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

create table NomlyDB.Restaurants(
    RestaurantId int not null auto_increment,
    Location varchar(255) not null,
    OperationHours varchar(255),
    -- Images varchar(255),
    PriceRange char not null,
    Cuisine varchar(255) not null,
    Rating float not null,
    primary key (RestaurantId)
);

create table NomlyDB.Users_Sessions_Restaurants(
	UserId int NOT NULL,
	SessionId int NOT NULL,
	RestaurantId int NOT NULL,
    foreign key (UserId) references Users(UserId),
    foreign key (SessionId) references Sessions(SessionId),
    foreign key (RestaurantId) references Restaurants(RestaurantId)


);
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 