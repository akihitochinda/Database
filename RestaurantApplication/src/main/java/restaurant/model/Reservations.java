package restaurant.model;

import java.sql.Timestamp;


public class Reservations {
	protected int reservationId;
	protected Timestamp startTime;
	protected Timestamp endTime;
	protected int partySize;
	protected Users user;
	protected SitDownRestaurants restaurant;

	public Reservations(int reservationId, Timestamp startTime, Timestamp endTime, int partySize, Users user, SitDownRestaurants restaurant) {
		this.reservationId = reservationId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.partySize = partySize;
		this.user = user;
		this.restaurant = restaurant;
	}

	public Reservations(int reservationId) {
		this.reservationId = reservationId;
	}

	public Reservations(Timestamp startTime, Timestamp endTime, int partySize, Users user, SitDownRestaurants restaurant) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.partySize = partySize;
		this.user = user;
		this.restaurant = restaurant;
	}

	public int getReservationId() {
		return reservationId;
	}

	public void setReservationId(int reservationId) {
		this.reservationId = reservationId;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}
	
	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public int getPartySize() {
		return partySize;
	}

	public void setPartySize(int partySize) {
		this.partySize = partySize;
	}
	
	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}
	
	public SitDownRestaurants getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(SitDownRestaurants restaurant) {
		this.restaurant = restaurant;
	}
}