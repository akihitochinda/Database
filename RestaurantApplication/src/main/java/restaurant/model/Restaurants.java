package restaurant.model;

public class Restaurants {
    protected int restaurantId;
    protected String name;
    protected String description;
    protected String menu;
    protected String listedHours;
    protected boolean isActive;
    protected String street1;
    protected String street2;
    protected String city;
    protected String state;
    protected String zip;
    protected CuisineType cuisineType;
    
    public enum CuisineType {
        African, American, Asian, European, Hispanic
    }

    protected Companies company;

    public Restaurants(int restaurantId, String name, String description, String menu, String listedHours, boolean isActive,
    		String street1, String street2, String city, String state, String zip, CuisineType cuisineType, Companies company) {
        this.restaurantId = restaurantId;
        this.name = name;
        this.description = description;
        this.menu = menu;
        this.listedHours = listedHours;
        this.isActive = isActive;
        this.street1 = street1;
        this.street2 = street2;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.cuisineType = cuisineType;
        this.company = company;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getListedHours() {
        return listedHours;
    }

    public void setListedHours(String listedHours) {
        this.listedHours = listedHours;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public String getStreet1() {
        return street1;
    }

    public void setStreet1(String street1) {
        this.street1 = street1;
    }

    public String getStreet2() {
        return street2;
    }

    public void setStreet2(String street2) {
        this.street2 = street2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public CuisineType getCuisineType() {
        return cuisineType;
    }

    public void setCuisineType(CuisineType cuisineType) {
        this.cuisineType = cuisineType;
    }

    public Companies getCompany() {
        return company;
    }

    public void setCompany(Companies company) {
        this.company = company;
    }
}