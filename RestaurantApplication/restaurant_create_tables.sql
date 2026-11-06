CREATE SCHEMA IF NOT EXISTS RestaurantApplication;
USE RestaurantApplication;

-- =====================================================
-- DROP TABLES
-- Child tables first
-- =====================================================

DROP TABLE IF EXISTS Recommendations;
DROP TABLE IF EXISTS Reservations;
DROP TABLE IF EXISTS Reviews;
DROP TABLE IF EXISTS CreditCardInfo;

DROP TABLE IF EXISTS SitDownRestaurants;
DROP TABLE IF EXISTS TakeOutRestaurants;
DROP TABLE IF EXISTS FoodCartRestaurants;
DROP TABLE IF EXISTS Restaurants;

DROP TABLE IF EXISTS Users;
DROP TABLE IF EXISTS Companies;
DROP TABLE IF EXISTS UsersProfile;

-- =====================================================
-- USERS PROFILE
-- =====================================================

CREATE TABLE UsersProfile (
    UserName VARCHAR(255),
    Password VARCHAR(255) NOT NULL,
    FirstName VARCHAR(255) NOT NULL,
    LastName VARCHAR(255) NOT NULL,
    Email VARCHAR(255),
    PhoneNumber VARCHAR(255),

    CONSTRAINT pk_UsersProfile
        PRIMARY KEY (UserName)
);

-- =====================================================
-- USERS (inherits UsersProfile)
-- =====================================================

CREATE TABLE Users (
    UserName VARCHAR(255),
    DoB TIMESTAMP NOT NULL,

    CONSTRAINT pk_Users
        PRIMARY KEY (UserName),

    CONSTRAINT fk_Users_UserName
        FOREIGN KEY (UserName)
        REFERENCES UsersProfile(UserName)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

-- =====================================================
-- CREDIT CARDS
-- =====================================================

CREATE TABLE CreditCardInfo (
    CardNumber VARCHAR(32),
    ExpirationDate DATE NOT NULL,
    UserName VARCHAR(255) NOT NULL,

    CONSTRAINT pk_CreditCardInfo
        PRIMARY KEY (CardNumber),

    CONSTRAINT fk_CreditCardInfo_UserName
        FOREIGN KEY (UserName)
        REFERENCES UsersProfile(UserName)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

-- =====================================================
-- COMPANIES
-- =====================================================

CREATE TABLE Companies (
    CompanyName VARCHAR(255),
    Description VARCHAR(1000),

    CONSTRAINT pk_Companies
        PRIMARY KEY (CompanyName)
);

-- =====================================================
-- RESTAURANTS
-- Parent Table
-- =====================================================

CREATE TABLE Restaurants (
    RestaurantId INT AUTO_INCREMENT,

    Name VARCHAR(255) NOT NULL,
    Description VARCHAR(1000),
    Menu VARCHAR(2000),
    ListedHours VARCHAR(255),

    IsActive BOOLEAN DEFAULT TRUE,

    Street1 VARCHAR(255),
    Street2 VARCHAR(255),
    City VARCHAR(255),
    State VARCHAR(255),
    Zip VARCHAR(20),

    CuisineType ENUM(
        'African',
        'American',
        'Asian',
        'European',
        'Hispanic'
    ) NOT NULL,

    CompanyName VARCHAR(255),

    CONSTRAINT pk_Restaurants
        PRIMARY KEY (RestaurantId),

    CONSTRAINT fk_Restaurants_CompanyName
        FOREIGN KEY (CompanyName)
        REFERENCES Companies(CompanyName)
        ON UPDATE CASCADE
        ON DELETE SET NULL
);

-- =====================================================
-- SIT DOWN RESTAURANTS
-- =====================================================

CREATE TABLE SitDownRestaurants (
    RestaurantId INT,
    Capacity INT NOT NULL,

    CONSTRAINT pk_SitDownRestaurants
        PRIMARY KEY (RestaurantId),

    CONSTRAINT fk_SitDownRestaurants_RestaurantId
        FOREIGN KEY (RestaurantId)
        REFERENCES Restaurants(RestaurantId)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

-- =====================================================
-- TAKE OUT RESTAURANTS
-- =====================================================

CREATE TABLE TakeOutRestaurants (
    RestaurantId INT,
    MaxWaitTime INT NOT NULL,

    CONSTRAINT pk_TakeOutRestaurants
        PRIMARY KEY (RestaurantId),

    CONSTRAINT fk_TakeOutRestaurants_RestaurantId
        FOREIGN KEY (RestaurantId)
        REFERENCES Restaurants(RestaurantId)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

-- =====================================================
-- FOOD CART RESTAURANTS
-- =====================================================

CREATE TABLE FoodCartRestaurants (
    RestaurantId INT,
    IsLicensed BOOLEAN NOT NULL,

    CONSTRAINT pk_FoodCartRestaurants
        PRIMARY KEY (RestaurantId),

    CONSTRAINT fk_FoodCartRestaurants_RestaurantId
        FOREIGN KEY (RestaurantId)
        REFERENCES Restaurants(RestaurantId)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

-- =====================================================
-- REVIEWS
-- =====================================================

CREATE TABLE Reviews (
    ReviewId INT AUTO_INCREMENT,

    Rating DOUBLE NOT NULL,
    WrittenReview VARCHAR(2000),

    Created TIMESTAMP
        DEFAULT CURRENT_TIMESTAMP,

    UserName VARCHAR(255),
    RestaurantId INT,

    CONSTRAINT pk_Reviews
        PRIMARY KEY (ReviewId),

    -- a user can write only one review for a given restaurant
    CONSTRAINT uq_Reviews_User_Restaurant
        UNIQUE (UserName, RestaurantId),

    CONSTRAINT fk_Reviews_UserName
        FOREIGN KEY (UserName)
        REFERENCES Users(UserName)
        ON UPDATE CASCADE
        ON DELETE SET NULL,

    CONSTRAINT fk_Reviews_RestaurantId
        FOREIGN KEY (RestaurantId)
        REFERENCES Restaurants(RestaurantId)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

-- =====================================================
-- RESERVATIONS
-- =====================================================

CREATE TABLE Reservations (
    ReservationId INT AUTO_INCREMENT,

    StartTime TIMESTAMP NOT NULL,
    EndTime TIMESTAMP NOT NULL,
    PartySize INT NOT NULL,

    UserName VARCHAR(255),
    RestaurantId INT,

    CONSTRAINT pk_Reservations
        PRIMARY KEY (ReservationId),

    CONSTRAINT fk_Reservations_UserName
        FOREIGN KEY (UserName)
        REFERENCES Users(UserName)
        ON UPDATE CASCADE
        ON DELETE CASCADE,

    CONSTRAINT fk_Reservations_RestaurantId
        FOREIGN KEY (RestaurantId)
        REFERENCES SitDownRestaurants(RestaurantId)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

-- =====================================================
-- RECOMMENDATIONS
-- =====================================================

CREATE TABLE Recommendations (
    RecommendationId INT AUTO_INCREMENT,

    IsRecommended BOOLEAN NOT NULL,

    UserName VARCHAR(255),
    RestaurantId INT,

    CONSTRAINT pk_Recommendations
        PRIMARY KEY (RecommendationId),

    CONSTRAINT fk_Recommendations_UserName
        FOREIGN KEY (UserName)
        REFERENCES Users(UserName)
        ON UPDATE CASCADE
        ON DELETE SET NULL,

    CONSTRAINT fk_Recommendations_RestaurantId
        FOREIGN KEY (RestaurantId)
        REFERENCES Restaurants(RestaurantId)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);