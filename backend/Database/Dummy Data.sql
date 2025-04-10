-- insert into NomlyDB.Users (Username , Email, Password, CreatedAt)
-- values ("John Doe", "johndoe@abc.com", "Testing123", current_timestamp);
insert into NomlyDB.Users (Username, Email, Password, Preferences, CreatedAt)
values ("John Doe", "johndoe@abc.com", "Testing123", "No Pork", current_timestamp);
-- insert into NomlyDB.Users (Username, Email, Password, Preferences)
-- values ("John Doe", "johndoe@abc.com", "Testing123", "No Pork");

insert into NomlyDB.Users (Username, Email, Password , CreatedAt)
values ("Mary Jane", "maryjane@xyz.com", "Testing123", current_timestamp);
-- insert into NomlyDB.Users (Username, Email, Password)
-- values ("Mary Jane", "maryjane@xyz.com", "Testing123");

select * from NomlyDB.Users;

insert into NomlyDB.Groupings (GroupName, createdAt)
values ("nom nom time", current_timestamp);

select * from NomlyDB.Groupings;

insert into NomlyDB.UsersGroupings(UserId, GroupId, JoinedAt)
select Users.UserId , Groupings.GroupId, current_timestamp
from NomlyDB.Users Users
cross join  NomlyDB.Groupings Groupings 
where Users.Email = "johndoe@abc.com" and Groupings.GroupName = "nom nom time";


insert into NomlyDB.UsersGroupings( UserId, GroupId,JoinedAt)
select Users.UserId , Groupings.GroupId, current_timestamp
from NomlyDB.Users Users
cross join  NomlyDB.Groupings Groupings 
where Users.Email = "maryjane@xyz.com" and Groupings.GroupName = "nom nom time";

select * from NomlyDB.UsersGroupings;

insert into NomlyDB.Sessions(GroupId,SessionName,Location, Latitude, longitude ,MeetingDateTime,CreatedAt,Completed)
values(1,"yum yum time","Tampines Mall", 1.3525636200333846, 103.94479657820084, "2025-04-20 12:00:00", current_timestamp, false);


select * from NomlyDB.Sessions;



