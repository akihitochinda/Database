package restaurant.tools;

import restaurant.dal.*;
import restaurant.model.*;

import java.sql.SQLException;
import java.sql.Date;
import java.sql.Timestamp;
// import java.util.Date;
import java.util.List;


/**
 * main() runner, used for the app demo.
 * 
 * Instructions:
 * 1. Create a new MySQL schema and then run the CREATE TABLE statements from restaurant_create_tables:
 * 2. Update ConnectionManager with the correct user, password, and schema.
 */
public class Inserter {

	public static void main(String[] args) throws SQLException {
		// DAO instances.
		UsersProfileDao usersProfileDao = UsersProfileDao.getInstance();
		UsersDao usersDao = UsersDao.getInstance();
		CompaniesDao companiesDao = CompaniesDao.getInstance();
		RestaurantsDao restaurantsDao = RestaurantsDao.getInstance();
		SitDownRestaurantsDao sitDownRestaurantsDao = SitDownRestaurantsDao.getInstance();
		TakeOutRestaurantsDao takeOutRestaurantsDao = TakeOutRestaurantsDao.getInstance();
		FoodCartRestaurantsDao foodCartRestaurantsDao = FoodCartRestaurantsDao.getInstance();
		CreditCardsDao creditCardsDao = CreditCardsDao.getInstance();
		ReviewsDao reviewsDao = ReviewsDao.getInstance();
		RecommendationsDao recommendationsDao = RecommendationsDao.getInstance();
		ReservationsDao reservationsDao = ReservationsDao.getInstance();
		
		// INSERT objects from our model.
		// UsersProfile profile = new UsersProfile("alice1", "password1", "Alice", "Smith", "[email protected]", "2061111111");
		// profile = usersProfileDao.create(profile);
		// UsersProfile profile1 = new UsersProfile("bob1", "password2", "Bob", "Johnson", "[email protected]", "2062222222");
		// profile1 = usersProfileDao.create(profile1);
		Users user = new Users("alice1", "password1", "Alice", "Smith", "[email protected]", "2061111111", Timestamp.valueOf("1999-05-12 00:00:00"));
		user = usersDao.create(user);
		Users user1 = new Users("bob1", "password2", "Bob", "Johnson", "[email protected]", "2062222222", Timestamp.valueOf("2000-09-30 00:00:00"));
		user1 = usersDao.create(user1);
		Companies company = new Companies("Seattle Eats", "Restaurant management company");
		company = companiesDao.create(company);
		Companies company1 = new Companies("Northwest Dining", "Regional restaurant operator");
		company1 = companiesDao.create(company1);
		// Restaurants restaurant = new Restaurants(0, "Amani Grill", "Authentic African cuisine", "Grill Specials", "10AM-9PM", true, "101 Main St", null, "Seattle", "WA", "98101", Restaurants.CuisineType.African, company);
		// restaurant = restaurantsDao.create(restaurant);
		// Restaurants restaurant1 = new Restaurants(0, "Classic Burger", "American comfort food", "Burgers and Fries", "11AM-10PM", true, "102 Pine St", null, "Seattle", "WA", "98102", Restaurants.CuisineType.American, company);
		// restaurant1 = restaurantsDao.create(restaurant1);
		// Restaurants restaurant2 = new Restaurants(0, "Tokyo Bowl", "Quick Asian meals", "Rice Bowls", "10AM-8PM", true, "103 Pike St", null, "Seattle", "WA", "98103", Restaurants.CuisineType.Asian, company1);
		// restaurant2 = restaurantsDao.create(restaurant2);
		// Restaurants restaurant3 = new Restaurants(0, "Roma Kitchen", "Traditional European dishes", "Italian Entrees", "12PM-10PM", true, "104 Union St", null, "Seattle", "WA", "98104", Restaurants.CuisineType.European, company1);
		// restaurant3 = restaurantsDao.create(restaurant3);
		// Restaurants restaurant4 = new Restaurants(0, "Taco Express", "Fresh Hispanic food", "Tacos and Burritos", "9AM-9PM", true, "105 Olive Way", null, "Seattle", "WA", "98105", Restaurants.CuisineType.Hispanic, company);
		// restaurant4 = restaurantsDao.create(restaurant4);
		// Restaurants restaurant5 = new Restaurants(0, "Street Bites", "Mobile American food cart", "Hot Dogs and Snacks", "8AM-6PM", true, "106 Market St", null, "Seattle", "WA", "98106", Restaurants.CuisineType.American, company1);
		// restaurant5 = restaurantsDao.create(restaurant5);
		SitDownRestaurants sitDownRestaurant = new SitDownRestaurants(0, "Amani Grill", "Authentic African cuisine", "Grill Specials", "10AM-9PM", true, "101 Main St", null, "Seattle", "WA", "98101", Restaurants.CuisineType.African, company, 120);
		sitDownRestaurant = sitDownRestaurantsDao.create(sitDownRestaurant);
		SitDownRestaurants sitDownRestaurant1 = new SitDownRestaurants(0, "Classic Burger", "American comfort food", "Burgers and Fries", "11AM-10PM", true, "102 Pine St", null, "Seattle", "WA", "98102", Restaurants.CuisineType.American, company, 80);
		sitDownRestaurant1 = sitDownRestaurantsDao.create(sitDownRestaurant1);
		TakeOutRestaurants takeOutRestaurant = new TakeOutRestaurants(0, "Tokyo Bowl", "Quick Asian meals", "Rice Bowls", "10AM-8PM", true, "103 Pike St", null, "Seattle", "WA", "98103", Restaurants.CuisineType.Asian, company1, 15);
		takeOutRestaurant = takeOutRestaurantsDao.create(takeOutRestaurant);
		TakeOutRestaurants takeOutRestaurant1 = new TakeOutRestaurants(0, "Roma Kitchen", "Traditional European dishes", "Italian Entrees", "12PM-10PM", true, "104 Union St", null, "Seattle", "WA", "98104", Restaurants.CuisineType.European, company1, 20);
		takeOutRestaurant1 = takeOutRestaurantsDao.create(takeOutRestaurant1);
		FoodCartRestaurants foodCartRestaurant = new FoodCartRestaurants(0, "Taco Express", "Fresh Hispanic food", "Tacos and Burritos", "9AM-9PM", true, "105 Olive Way", null, "Seattle", "WA", "98105", Restaurants.CuisineType.Hispanic, company, true);
		foodCartRestaurant = foodCartRestaurantsDao.create(foodCartRestaurant);
		FoodCartRestaurants foodCartRestaurant1 = new FoodCartRestaurants(0, "Street Bites", "Mobile American food cart", "Hot Dogs and Snacks", "8AM-6PM", true, "106 Market St", null, "Seattle", "WA", "98106", Restaurants.CuisineType.American, company1, true);
		foodCartRestaurant1 = foodCartRestaurantsDao.create(foodCartRestaurant1);
		Reviews review = new Reviews(4.5, "Great food and friendly service.", new Timestamp(System.currentTimeMillis()), user, sitDownRestaurant);
		review = reviewsDao.create(review);
		Reviews review1 = new Reviews(5.0, "Fast service and excellent quality.", new Timestamp(System.currentTimeMillis()), user1, takeOutRestaurant);
		review1 = reviewsDao.create(review1);
		Recommendations recommendation = new Recommendations(true, user, takeOutRestaurant);
		recommendation = recommendationsDao.create(recommendation);
		Reservations reservation = new Reservations(Timestamp.valueOf("2026-10-15 18:00:00"), Timestamp.valueOf("2026-10-15 20:00:00"), 4, user, sitDownRestaurant);
		reservation = reservationsDao.create(reservation);
		Reservations reservation1 = new Reservations(Timestamp.valueOf("2026-10-16 19:00:00"), Timestamp.valueOf("2026-10-16 21:00:00"), 2, user1, sitDownRestaurant1);
		reservation1 = reservationsDao.create(reservation1);
		CreditCards card = new CreditCards(4111111111111111L, Date.valueOf("2028-12-31"), user);
		card = creditCardsDao.create(card);
		CreditCards card1 = new CreditCards(5555555555554444L, Date.valueOf("2029-06-30"), user);
		card1 = creditCardsDao.create(card1);
		
		// READ.
		UsersProfile p1 = usersProfileDao.getUserFromUserName("alice1");
		List<UsersProfile> pList1 = usersProfileDao.getUsersFromFirstName("Alice");
		System.out.format("Reading profile: u:%s f:%s l:%s \n",
			p1.getUserName(), p1.getFirstName(), p1.getLastName());
		for(UsersProfile p : pList1) {
			System.out.format("Looping profiles: u:%s f:%s l:%s \n",
			    p.getUserName(), p.getFirstName(), p.getLastName());
		}
		Users u1 = usersDao.getUserByUserName("alice1");
		List<Users> uList1 = usersDao.getUsersByFirstName("Alice");
		System.out.format("Reading user: u:%s d:%s \n",
			u1.getUserName(), u1.getDob());
		for(Users u : uList1) {
			System.out.format("Looping users: u:%s d:%s \n",
			    u.getUserName(), u.getDob());
		}
		Companies c1 = companiesDao.getCompanyByCompanyName("Seattle Eats");
		System.out.format("Reading company: n:%s d:%s \n",
			c1.getCompanyName(), c1.getDescription());
		c1 = companiesDao.updateAbout(c1, "Updated description");
		System.out.format("Updated company: n:%s d:%s \n",
			c1.getCompanyName(), c1.getDescription());
		List<Restaurants> rList1 = restaurantsDao.getRestaurantsByCompanyName("Seattle Eats");
		for(Restaurants r : rList1) {
			System.out.format("Looping restaurants: i:%s n:%s \n",
			    r.getRestaurantId(), r.getName());
		}
		List<SitDownRestaurants> sdList1 = sitDownRestaurantsDao.getSitDownRestaurantsByCompanyName("Seattle Eats");
		for(SitDownRestaurants sd : sdList1) {
			System.out.format("Looping sitdowns: i:%s c:%s \n",
			    sd.getRestaurantId(), sd.getCapacity());
		}
		List<TakeOutRestaurants> tList1 = takeOutRestaurantsDao.getTakeOutRestaurantsByCompanyName("Northwest Dining");
		for(TakeOutRestaurants t : tList1) {
			System.out.format("Looping takeouts: i:%s w:%s \n",
			    t.getRestaurantId(), t.getMaxWaitTime());
		}
		List<FoodCartRestaurants> fcList1 = foodCartRestaurantsDao.getFoodCartRestaurantsByCompanyName("Seattle Eats");
		for(FoodCartRestaurants fc : fcList1) {
			System.out.format("Looping foodcarts: i:%s l:%s \n",
			    fc.getRestaurantId(), fc.getIsLicensed());
		}
		List<Reviews> rvList1 = reviewsDao.getReviewsByUserName("alice1");
		for(Reviews rv : rvList1) {
			System.out.format("Looping reviews: i:%s t:%s \n",
			    rv.getReviewId(), rv.getWrittenReview());
		}
		review = reviewsDao.updateReview(review, "Updated review");
		System.out.format("Updated review: i:%s t:%s \n",
			review.getReviewId(), review.getWrittenReview());
		List<Recommendations> recList1 = recommendationsDao.getRecommendationsByUserName("alice1");
		for(Recommendations rec : recList1) {
			System.out.format("Looping recommendations: i:%s b:%s \n",
			    rec.getRecommendationId(), rec.getIsRecommended());
		}
		List<Reservations> rsList1 = reservationsDao.getReservationsByUserName("alice1");
		for(Reservations rs : rsList1) {
			System.out.format("Looping reservations: i:%s p:%s \n",
			    rs.getReservationId(), rs.getPartySize());
		}
		List<CreditCards> ccList1 = creditCardsDao.getCreditCardsByUserName("alice1");
		for(CreditCards cc : ccList1) {
			System.out.format("Looping cards: c:%s e:%s u:%s \n",
			    cc.getCardNumber(), cc.getExpirationDate(), cc.getUser().getUserName());
		}
		card = creditCardsDao.updateExpiration(card,Date.valueOf("2030-12-31"));
		System.out.format("Updated card: c:%s e:%s \n",
		    card.getCardNumber(), card.getExpirationDate());
		
		// DELETE.
		user = usersDao.delete(user);
		user1 = usersDao.delete(user1);
		company = companiesDao.delete(company);
		company1 = companiesDao.delete(company1);
		sitDownRestaurant = sitDownRestaurantsDao.delete(sitDownRestaurant);
		sitDownRestaurant1 = sitDownRestaurantsDao.delete(sitDownRestaurant1);
		takeOutRestaurant = takeOutRestaurantsDao.delete(takeOutRestaurant);
		takeOutRestaurant1 = takeOutRestaurantsDao.delete(takeOutRestaurant1);
		foodCartRestaurant = foodCartRestaurantsDao.delete(foodCartRestaurant);
		foodCartRestaurant1 = foodCartRestaurantsDao.delete(foodCartRestaurant1);
		review = reviewsDao.delete(review);
		review1 = reviewsDao.delete(review1);
		recommendation = recommendationsDao.delete(recommendation);
		reservation = reservationsDao.delete(reservation);
		reservation1 = reservationsDao.delete(reservation1);
		card = creditCardsDao.delete(card);
		card1 = creditCardsDao.delete(card1);
	}
}
