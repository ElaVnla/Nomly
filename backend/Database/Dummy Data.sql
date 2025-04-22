
-- STEP 2: Insert users (no spaces in usernames)
INSERT INTO NomlyDB.Users (Username, Email, Password, Preferences, CreatedAt)
VALUES 
("JohnDoe", "johndoe@abc.com", "Testing123", "No Pork", CURRENT_TIMESTAMP),
("MaryJane", "maryjane@xyz.com", "Testing123", NULL, CURRENT_TIMESTAMP),
("AliceWong", "alice@xyz.com", "Testing123", "Vegetarian", CURRENT_TIMESTAMP),
("BobTan", "bob@abc.com", "Testing123", NULL, CURRENT_TIMESTAMP),
("EthanLim", "ethan@abc.com", "Testing123", "No Beef", CURRENT_TIMESTAMP);

-- STEP 3: Insert groups
INSERT INTO NomlyDB.Groupings (GroupName, CreatedAt)
VALUES 
("nom nom time", CURRENT_TIMESTAMP),       -- Shared
("supper crew", CURRENT_TIMESTAMP),        -- JohnDoe only
("vegan vibes", CURRENT_TIMESTAMP),        -- MaryJane, AliceWong
("weekend munchies", CURRENT_TIMESTAMP),   -- Shared
("spicy squad", CURRENT_TIMESTAMP),        -- JohnDoe, EthanLim
("budget bites", CURRENT_TIMESTAMP),       -- BobTan only
("salad society", CURRENT_TIMESTAMP),      -- AliceWong only
("meat feast", CURRENT_TIMESTAMP);         -- JohnDoe, BobTan

-- STEP 4: Link users to groups
-- JohnDoe
INSERT INTO NomlyDB.UsersGroupings (UserId, GroupId, JoinedAt)
SELECT u.UserId, g.GroupId, CURRENT_TIMESTAMP
FROM NomlyDB.Users u, NomlyDB.Groupings g
WHERE u.Email = 'johndoe@abc.com' 
  AND g.GroupName IN ('nom nom time', 'supper crew', 'weekend munchies', 'spicy squad', 'meat feast');

-- MaryJane
INSERT INTO NomlyDB.UsersGroupings (UserId, GroupId, JoinedAt)
SELECT u.UserId, g.GroupId, CURRENT_TIMESTAMP
FROM NomlyDB.Users u, NomlyDB.Groupings g
WHERE u.Email = 'maryjane@xyz.com' 
  AND g.GroupName IN ('nom nom time', 'vegan vibes', 'weekend munchies');

-- AliceWong
INSERT INTO NomlyDB.UsersGroupings (UserId, GroupId, JoinedAt)
SELECT u.UserId, g.GroupId, CURRENT_TIMESTAMP
FROM NomlyDB.Users u, NomlyDB.Groupings g
WHERE u.Email = 'alice@xyz.com' 
  AND g.GroupName IN ('vegan vibes', 'salad society');

-- BobTan
INSERT INTO NomlyDB.UsersGroupings (UserId, GroupId, JoinedAt)
SELECT u.UserId, g.GroupId, CURRENT_TIMESTAMP
FROM NomlyDB.Users u, NomlyDB.Groupings g
WHERE u.Email = 'bob@abc.com' 
  AND g.GroupName IN ('budget bites', 'meat feast');

-- EthanLim
INSERT INTO NomlyDB.UsersGroupings (UserId, GroupId, JoinedAt)
SELECT u.UserId, g.GroupId, CURRENT_TIMESTAMP
FROM NomlyDB.Users u, NomlyDB.Groupings g
WHERE u.Email = 'ethan@abc.com' 
  AND g.GroupName IN ('spicy squad', 'weekend munchies');

-- STEP 5: Insert sessions (with Latitude and Longitude)
INSERT INTO NomlyDB.Sessions (GroupId, SessionName, Location, Latitude, Longitude, MeetingDateTime, CreatedAt, Completed)
SELECT g.GroupId, s.SessionName, s.Location, s.Latitude, s.Longitude, s.MeetingDateTime, CURRENT_TIMESTAMP, false
FROM NomlyDB.Groupings g
JOIN (
    SELECT 'nom nom time', 'yum yum time', 'Tampines Mall', 1.35256362, 103.94479657, '2025-04-20 12:00:00'
    UNION ALL
    SELECT 'nom nom time', 'noodle night', 'Marina Square', 1.2912, 103.8571, '2025-04-25 19:00:00'
    UNION ALL
    SELECT 'supper crew', 'late supper', 'Jewel Changi', 1.3644, 103.9915, '2025-04-21 22:00:00'
    UNION ALL
    SELECT 'vegan vibes', 'green feast', 'The Vegan Place', 1.2966, 103.7764, '2025-04-22 13:00:00'
    UNION ALL
    SELECT 'vegan vibes', 'plant power', 'SaladStop!', 1.2801, 103.8500, '2025-04-26 14:00:00'
    UNION ALL
    SELECT 'weekend munchies', 'snack attack', 'Bugis Junction', 1.2992, 103.8558, '2025-04-27 17:00:00'
    UNION ALL
    SELECT 'spicy squad', 'curry blast', 'Little India', 1.3065, 103.8511, '2025-04-23 20:00:00'
    UNION ALL
    SELECT 'spicy squad', 'spice night', 'Chomp Chomp', 1.3585, 103.8700, '2025-04-28 20:30:00'
    UNION ALL
    SELECT 'budget bites', 'hawker run', 'Hawker Heaven', 1.3151, 103.8567, '2025-04-19 11:00:00'
    UNION ALL
    SELECT 'salad society', 'salad party', 'Greendot', 1.3000, 103.8000, '2025-04-24 12:00:00'
    UNION ALL
    SELECT 'meat feast', 'bbq bonanza', 'BBQ Express', 1.3322, 103.9433, '2025-04-29 19:00:00'
    UNION ALL
    SELECT 'meat feast', 'meaty madness', 'Smokey\'s BBQ', 1.3234, 103.9271, '2025-05-01 20:00:00'
) AS s(GroupName, SessionName, Location, Latitude, Longitude, MeetingDateTime)
ON g.GroupName = s.GroupName;

-- STEP 6: View populated data
SELECT * FROM NomlyDB.Users;
SELECT * FROM NomlyDB.Groupings;
SELECT * FROM NomlyDB.UsersGroupings;
SELECT * FROM NomlyDB.Sessions;

INSERT INTO NomlyDB.Eateries (EateryId, DisplayName, Latitude, Longitude, PriceLevel, Cuisine, Rating, OperationHours, Location) VALUES
('eatery_1', 'Tasty Noodles', 1.300000, 103.800000, 'PRICE_LEVEL_MODERATE', 'Chinese', 4.2, '9AM-9PM', 'Downtown'),
('eatery_2', 'Burger Blast', 1.301000, 103.801000, 'PRICE_LEVEL_INEXPENSIVE', 'Western', 4.0, '10AM-10PM', 'Central Mall'),
('eatery_3', 'Sushi Zen', 1.302000, 103.802000, 'PRICE_LEVEL_EXPENSIVE', 'Japanese', 4.5, '11AM-9PM', 'Uptown'),
('eatery_4', 'Curry Kingdom', 1.303000, 103.803000, 'PRICE_LEVEL_MODERATE', 'Indian', 4.1, '10AM-10PM', 'Little India');

INSERT INTO NomlyDB.Sessions (
    SessionId, GroupId, SessionName, Location,
    Latitude, Longitude, MeetingDateTime, CreatedAt, Completed
) VALUES (
    19, 1, 'Food Hunt #1', 'Bugis Junction',
    1.300123456789000, 103.853123456789000,
    NOW(), NOW(), FALSE
);

INSERT INTO NomlyDB.SessionsEateries (SessionId, EateryId) VALUES
(19, 'eatery_1'),
(19, 'eatery_2'),
(19, 'eatery_3'),
(19, 'eatery_4');

INSERT INTO NomlyDB.UsersSessionsEateries (UserId, SessionId, EateryId, Liked) VALUES
(1, 19, 'eatery_1', TRUE),
(1, 19, 'eatery_2', TRUE),
(1, 19, 'eatery_3', TRUE),
(1, 19, 'eatery_4', TRUE);