USE RestaurantApplication;
SELECT * FROM Restaurants;
SELECT * FROM Users;
SELECT * FROM Reviews;
SELECT * FROM Reservations;
SELECT * FROM Creditcardinfo;
SELECT * FROM Recommendations;

# 1. What are the top 10 restaurants, sorted by the average of their review ratings?
# Include the restaurant ID, restaurant name, and average rating in the result set.
SELECT Restaurants.RestaurantId,
       Restaurants.Name,
       AVG(Reviews.Rating) AS AVG_RATING
FROM Restaurants
INNER JOIN Reviews
  ON Restaurants.RestaurantId = Reviews.RestaurantId
GROUP BY Restaurants.RestaurantId,
         Restaurants.Name
ORDER BY AVG_RATING DESC
LIMIT 10;


# 2. How many users have created more than one review?
SELECT COUNT(*) AS USER_CNT
FROM (
  SELECT UserName
  FROM Reviews
  GROUP BY UserName
  HAVING COUNT(*) > 1
) AS REVIEW_USERS;
/**
# 2. UserNames who have created more than one review.
SELECT UserName, COUNT(*) AS CNT
FROM Reviews
GROUP BY UserName
HAVING COUNT(*) > 1
*/


# 3. What day of the week is most popular for making a reservation?
# Use the DAYOFWEEK() function on the ‘Start’ column.
SELECT DAYOFWEEK(StartTime) AS DAY_NUM,
       COUNT(*) AS RESERVATION_CNT
FROM Reservations
GROUP BY DAY_NUM
ORDER BY RESERVATION_CNT DESC
LIMIT 1;


# 4. What are the distinct UserNames for users that have made multiple reservations at any given SitDownRestaurant?
SELECT DISTINCT UserName
FROM Reservations
# Reservations already reference SitDownRestaurants, no additional join is needed
GROUP BY UserName, RestaurantId
HAVING COUNT(*) > 1
ORDER BY UserName;


# 5. Identify the number of credit cards per provider.
# The credit card provider is determined by the leading digit(s) of the CardNumber.
# Use this mapping for card providers:
# Provider         CardNumber Starts With
# American Express 34, 37
# Discover         6011, 644, 645, 646, 647, 648, 649, 65
# MasterCard       51, 52, 53, 54, 55
# Visa             4
# NA               (Everything else)
SELECT
  CASE
    WHEN CardNumber LIKE '34%' OR CardNumber LIKE '37%'
      THEN 'American Express'

    WHEN CardNumber LIKE '6011%'
      OR CardNumber LIKE '644%'
      OR CardNumber LIKE '645%'
      OR CardNumber LIKE '646%'
      OR CardNumber LIKE '647%'
      OR CardNumber LIKE '648%'
      OR CardNumber LIKE '649%'
      OR CardNumber LIKE '65%'
      THEN 'Discover'

    WHEN CardNumber LIKE '51%'
      OR CardNumber LIKE '52%'
      OR CardNumber LIKE '53%'
      OR CardNumber LIKE '54%'
      OR CardNumber LIKE '55%'
      THEN 'MasterCard'

    WHEN CardNumber LIKE '4%'
      THEN 'Visa'

    ELSE 'NA'
  END AS PROVIDER,

  COUNT(*) AS CARD_CNT

FROM CreditCardInfo
GROUP BY PROVIDER
ORDER BY PROVIDER;


# 6. What is the total number of active restaurants for each type of restaurant
# (SitDownRestaurant, TakeOutRestaurant, FoodCartRestaurant)?
SELECT 'SitDownRestaurant' AS RESTAURANT_TYPE,
       COUNT(*) AS CNT
FROM Restaurants
INNER JOIN SitDownRestaurants
  ON Restaurants.RestaurantId = SitDownRestaurants.RestaurantId
WHERE Restaurants.IsActive = TRUE

UNION

SELECT 'TakeOutRestaurant',
       COUNT(*)
FROM Restaurants
INNER JOIN TakeOutRestaurants
  ON Restaurants.RestaurantId = TakeOutRestaurants.RestaurantId
WHERE Restaurants.IsActive = TRUE

UNION

SELECT 'FoodCartRestaurant',
       COUNT(*)
FROM Restaurants
INNER JOIN FoodCartRestaurants
  ON Restaurants.RestaurantId = FoodCartRestaurants.RestaurantId
WHERE Restaurants.IsActive = TRUE;


# 7. Which UserNames have not created a review, nor created a recommendation, nor created a reservation?
# In other words, users that have not created any of the following: reviews, recommendations, reservations.
SELECT Users.UserName
FROM Users
LEFT OUTER JOIN Reviews
  ON Users.UserName = Reviews.UserName
LEFT OUTER JOIN Recommendations
  ON Users.UserName = Recommendations.UserName
LEFT OUTER JOIN Reservations
  ON Users.UserName = Reservations.UserName
WHERE Reviews.UserName IS NULL
  AND Recommendations.UserName IS NULL
  AND Reservations.UserName IS NULL
ORDER BY Users.UserName;


# 8. What is the ratio of the total number of recommendations to the total number of reviews?
SELECT
  (SELECT COUNT(*) FROM Recommendations) /
  (SELECT COUNT(*) FROM Reviews) AS REC_TO_REVIEW_RATIO;


# 9. Of all the users that have created a reservation at a SitDownRestaurant,
# what is the percentage that the same user has recommended the same SitDownRestaurant?
# To calculate this percentage, consider {UserName, RestaurantId} reservation tuples as the denominator
# and their intersecting {UserName, RestaurantId} recommendation tuples at the numerator.
SELECT
  100.0 * COUNT(*) /
  (SELECT COUNT(*)
   FROM Reservations) AS PERCENT_MATCH
FROM Reservations
INNER JOIN Recommendations
  ON Reservations.UserName = Recommendations.UserName
 AND Reservations.RestaurantId = Recommendations.RestaurantId;


# 10. Which UserNames have created more than twice the number of recommendations than number of reviews?
# Also take into account users that have not created recommendations or reviews
# -- if a user has created one (or more) recommendation but zero reviews, then that user should be included in the result set.

SELECT Users.UserName,
  IF(RECS.REC_CNT IS NULL, 0, RECS.REC_CNT) AS REC_CNT,
  IF(REVS.REVIEW_CNT IS NULL, 0, REVS.REVIEW_CNT) AS REVIEW_CNT
FROM Users

# Handle NULLs: users with zero reviews or zero recommendations must still be considered.
LEFT OUTER JOIN (
  SELECT UserName,
         COUNT(*) AS REC_CNT
  FROM Recommendations
  GROUP BY UserName
) AS RECS
  ON Users.UserName = RECS.UserName

LEFT OUTER JOIN (
  SELECT UserName,
         COUNT(*) AS REVIEW_CNT
  FROM Reviews
  GROUP BY UserName
) AS REVS
  ON Users.UserName = REVS.UserName

WHERE
  IF(RECS.REC_CNT IS NULL, 0, RECS.REC_CNT)
  >
  2 * IF(REVS.REVIEW_CNT IS NULL, 0, REVS.REVIEW_CNT);

# Note: if you want to go above and beyond on the data analysis, then consider if we flipped the comparison
# so that we instead asked: “created more than twice the number of reviews than number of recommendations?”
# What would change in the query?
/**
WHERE
  IF(REVS.REVIEW_CNT IS NULL, 0, REVS.REVIEW_CNT)
  >
  2 * IF(RECS.REC_CNT IS NULL, 0, RECS.REC_CNT);
*/