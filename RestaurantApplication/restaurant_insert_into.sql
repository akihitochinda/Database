USE RestaurantApplication;

-- =====================================================
-- UsersProfile
-- =====================================================

INSERT INTO UsersProfile
(UserName, Password, FirstName, LastName, Email, PhoneNumber)
VALUES
('alice1', 'password1', 'Alice', 'Smith',
 '[email protected]', '2061111111'),

('bob1', 'password2', 'Bob', 'Johnson',
 '[email protected]', '2062222222');

-- =====================================================
-- Users
-- =====================================================

INSERT INTO Users
(UserName, DoB)
VALUES
('alice1', '1999-05-12 00:00:00'),
('bob1', '2000-09-30 00:00:00');

-- =====================================================
-- Companies
-- =====================================================

INSERT INTO Companies
(CompanyName, Description)
VALUES
('Seattle Eats',
 'Restaurant management company'),

('Northwest Dining',
 'Regional restaurant operator');

-- =====================================================
-- Restaurants
-- =====================================================

INSERT INTO Restaurants
(
Name,
Description,
Menu,
ListedHours,
IsActive,
Street1,
Street2,
City,
State,
Zip,
CuisineType,
CompanyName
)
VALUES

(
'Amani Grill',
'Authentic African cuisine',
'Grill Specials',
'10AM-9PM',
TRUE,
'101 Main St',
NULL,
'Seattle',
'WA',
'98101',
'African',
'Seattle Eats'
),

(
'Classic Burger',
'American comfort food',
'Burgers and Fries',
'11AM-10PM',
TRUE,
'102 Pine St',
NULL,
'Seattle',
'WA',
'98102',
'American',
'Seattle Eats'
),

(
'Tokyo Bowl',
'Quick Asian meals',
'Rice Bowls',
'10AM-8PM',
TRUE,
'103 Pike St',
NULL,
'Seattle',
'WA',
'98103',
'Asian',
'Northwest Dining'
),

(
'Roma Kitchen',
'Traditional European dishes',
'Italian Entrees',
'12PM-10PM',
TRUE,
'104 Union St',
NULL,
'Seattle',
'WA',
'98104',
'European',
'Northwest Dining'
),

(
'Taco Express',
'Fresh Hispanic food',
'Tacos and Burritos',
'9AM-9PM',
TRUE,
'105 Olive Way',
NULL,
'Seattle',
'WA',
'98105',
'Hispanic',
'Seattle Eats'
),

(
'Street Bites',
'Mobile American food cart',
'Hot Dogs and Snacks',
'8AM-6PM',
TRUE,
'106 Market St',
NULL,
'Seattle',
'WA',
'98106',
'American',
'Northwest Dining'
);

-- =====================================================
-- SitDownRestaurants
-- Restaurant IDs: 1,2
-- =====================================================

INSERT INTO SitDownRestaurants
(RestaurantId, Capacity)
VALUES
(1, 120),
(2, 80);

-- =====================================================
-- TakeOutRestaurants
-- Restaurant IDs: 3,4
-- =====================================================

INSERT INTO TakeOutRestaurants
(RestaurantId, MaxWaitTime)
VALUES
(3, 15),
(4, 20);

-- =====================================================
-- FoodCartRestaurants
-- Restaurant IDs: 5,6
-- =====================================================

INSERT INTO FoodCartRestaurants
(RestaurantId, IsLicensed)
VALUES
(5, TRUE),
(6, TRUE);

-- =====================================================
-- Reviews
-- =====================================================

INSERT INTO Reviews
(
Rating,
WrittenReview,
UserName,
RestaurantId
)
VALUES

(
4.5,
'Great food and friendly service.',
'alice1',
1
),

(
5.0,
'Fast service and excellent quality.',
'bob1',
3
);

-- =====================================================
-- Reservations
-- Must reference SitDownRestaurants
-- =====================================================

INSERT INTO Reservations
(
StartTime,
EndTime,
PartySize,
UserName,
RestaurantId
)
VALUES

(
'2026-10-15 18:00:00',
'2026-10-15 20:00:00',
4,
'alice1',
1
),

(
'2026-10-16 19:00:00',
'2026-10-16 21:00:00',
2,
'bob1',
2
);

-- =====================================================
-- Recommendations
-- =====================================================

INSERT INTO Recommendations
(
IsRecommended,
UserName,
RestaurantId
)
VALUES

(
TRUE,
'alice1',
3
);

-- Place creditcards.csv in secure_file_priv directory
LOAD DATA INFILE
'C:/ProgramData/MySQL/MySQL Server 26.7/Uploads/creditcards.csv'
INTO TABLE CreditCardInfo
FIELDS TERMINATED BY ','
LINES TERMINATED BY '\n'
IGNORE 1 LINES;

/**
Contents of /creditcards.csv:
CardNumber,ExpirationDate,UserName
"card1","date1","user1"
"card2","date2","user2"
"card3","date3","user3"
*/