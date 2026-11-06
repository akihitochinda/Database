package restaurant.model;

public class Recommendations {
	protected int recommendationId;
	protected boolean isRecommended;
	protected Users user;
	protected Restaurants restaurant;

	public Recommendations(int recommendationId, boolean isRecommended, Users user, Restaurants restaurant) {
		this.recommendationId = recommendationId;
		this.isRecommended = isRecommended;
		this.user = user;
		this.restaurant = restaurant;
	}

	public Recommendations(int recommendationId) {
		this.recommendationId = recommendationId;
	}

	public Recommendations(boolean isRecommended, Users user, Restaurants restaurant) {
		this.isRecommended = isRecommended;
		this.user = user;
		this.restaurant = restaurant;
	}

	public int getRecommendationId() {
		return recommendationId;
	}

	public void setRecommendationId(int recommendationId) {
		this.recommendationId = recommendationId;
	}

	public boolean getIsRecommended() {
		return isRecommended;
	}

	public void setIsRecommended(boolean isRecommended) {
		this.isRecommended = isRecommended;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Restaurants getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurants restaurant) {
		this.restaurant = restaurant;
	}
}