package restaurant.model;

public class FoodCartRestaurants extends Restaurants {
	protected boolean isLicensed;

	public FoodCartRestaurants(int restaurantId, String name, String description, String menu, String listedHours, boolean isActive, 
			String street1, String street2, String city, String state, String zip, CuisineType cuisineType, Companies company, boolean isLicensed) {
		super(restaurantId, name, description, menu, listedHours, isActive, street1, street2, city, state, zip, cuisineType, company);
		this.isLicensed = isLicensed;
	}

    public boolean getIsLicensed() {
        return isLicensed;
    }

    public void setIsLicensed(boolean isLicensed) {
        this.isLicensed = isLicensed;
    }
}