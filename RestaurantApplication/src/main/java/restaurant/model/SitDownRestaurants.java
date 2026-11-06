package restaurant.model;

public class SitDownRestaurants extends Restaurants {
	protected int capacity;

	public SitDownRestaurants(int restaurantId, String name, String description, String menu, String listedHours, boolean isActive, 
			String street1, String street2, String city, String state, String zip, CuisineType cuisineType, Companies company, int capacity) {
		super(restaurantId, name, description, menu, listedHours, isActive, street1, street2, city, state, zip, cuisineType, company);
		this.capacity = capacity;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
}