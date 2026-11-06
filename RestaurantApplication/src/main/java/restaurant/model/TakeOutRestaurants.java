package restaurant.model;

public class TakeOutRestaurants extends Restaurants {
	protected int maxWaitTime;

	public TakeOutRestaurants(int restaurantId, String name, String description, String menu, String listedHours, boolean isActive, 
			String street1, String street2, String city, String state, String zip, CuisineType cuisineType, Companies company, int maxWaitTime) {
		super(restaurantId, name, description, menu, listedHours, isActive, street1, street2, city, state, zip, cuisineType, company);
		this.maxWaitTime = maxWaitTime;
	}

	public int getMaxWaitTime() {
		return maxWaitTime;
	}

	public void setMaxWaitTime(int maxWaitTime) {
		this.maxWaitTime = maxWaitTime;
	}
}