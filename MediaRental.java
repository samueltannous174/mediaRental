package project;

import java.util.ArrayList;
import java.util.Collections;

public class MediaRental implements MediaRentalInt {
	public ArrayList<Customer> customersList = new ArrayList<Customer>();
	public ArrayList<Media> mediaList = new ArrayList<Media>();
	private int limitedPlanLimit=2;

	public MediaRental() {
	}

	@Override
	public void addCustomer(String name, String address, String plan) {
		Customer c = new Customer(name, address, plan);
		customersList.add(c);
	}

	@Override
	public void addMovie(String title, int copiesAvailable, String rating) {
		Movie m = new Movie(title, copiesAvailable, rating);
		mediaList.add(m);
	}

	@Override
	public void addGame(String title, int copiesAvailable, double weight) {
		Game g = new Game(title, copiesAvailable, weight);
		mediaList.add(g);
	}

	@Override
	public void addAlbum(String title, int copiesAvailable, String artist, String songs) {
		Album a = new Album(title, copiesAvailable, artist, songs);
		mediaList.add(a);
	}

	@Override
	public void setLimitedPlanLimit(int value) {
		limitedPlanLimit = value;
	}

	private int getLimitedPlanLimit() {
		return limitedPlanLimit;
	}

	@Override
	public String getAllCustomersInfo() {
		String result = " ";
		Collections.sort(customersList);
		for (int i = 0; i < customersList.size(); i++) {
			result += customersList.get(i);

		}
		return result;
	}

	@Override
	public String getAllMediaInfo() {
		String result = " ";
		Collections.sort(mediaList);
		for (int i = 0; i < mediaList.size(); i++) {
			result += mediaList.get(i);

		}
		return result;
	}

	@Override
	public boolean addToCart(String customerName, String mediaTitle) {
		Customer c = null;

		for (int j = 0; j < customersList.size(); j++) {
			if (customersList.get(j).getName().equals(customerName)) {
				c = customersList.get(j);
				break;
			}
		}

		if (!c.getMediaInterestedIn().contains(mediaTitle)) {
			c.addToMediaIntrestedIn(mediaTitle);
			return true;
		}
		return false;
	}

	@Override
	public boolean removeFromCart(String customerName, String mediaTitle) {
		Customer c = new Customer();

		for (int j = 0; j < customersList.size(); j++) {
			if (customersList.get(j).getName().equals(customerName)) {
				c = customersList.get(j);
				break;
			}
		}

		if (c.getMediaInterestedIn().contains(mediaTitle)) {
			c.removeFromMediaIntrestedIn(mediaTitle);
			return true;
		}
		return false;
	}

	private Media getMediaByTitle(String title) {
		Media m;
		for (int i = 0; i < mediaList.size(); i++) {
			m = mediaList.get(i);
			if (m.getTitle().equals(title)) {
				return m;
			}
		}
		return null;
	}

	@Override
	public String processRequests() {
		Collections.sort(customersList);
		Customer customer;
		String mediaTitle;
		Media media;

		for (int i = 0; i < customersList.size(); i++) {
			customer = customersList.get(i);
			ArrayList<String>mediaTitleToRemove = new ArrayList<>(); 
			for (int j = 0; j < customer.getMediaInterestedIn().size(); j++) {
				mediaTitle = customer.getMediaInterestedIn().get(j);
				media = getMediaByTitle(mediaTitle);
				if (media!= null && media.isAvailable() && (customer.checkPlanIfUnlimited()
						|| getLimitedPlanLimit() > customer.getMediaRented().size())) {
					customer.addToMediaRented(mediaTitle);
					System.out.println("Sending "+mediaTitle+" to "+ customer.getName());
					media.decreaseNumberOfCopies();
					mediaTitleToRemove.add(mediaTitle);
				}

			}
			for (int k = 0; k < mediaTitleToRemove.size(); k++) {
				customer.removeFromMediaIntrestedIn(mediaTitleToRemove.get(k));

			}
		}
		
		return null;
	}

	@Override
	public boolean returnMedia(String customerName, String mediaTitle) {
		Customer customer;
		Media media;

		for (int i = 0; i < customersList.size(); i++) {
			customer = customersList.get(i);
			if (customer.getName().equals(customerName)) {
					media = getMediaByTitle(mediaTitle);
					if (media != null) {
						customer.removeFromMediaRented(mediaTitle);
						media.increaseNumberOfCopies();
				}
			}
		}
		return false;
	}

	@Override
	public ArrayList<String> searchMedia(String title, String rating, String artist, String songs) {
		ArrayList<String> mediaTitles = new ArrayList<>();

		if (title == null && rating == null && artist == null && songs == null) {
			for (int i = 0; i < mediaList.size(); i++) {
				String MediaTitle = mediaList.get(i).getTitle();
				mediaTitles.add(MediaTitle);
			}
			Collections.sort(mediaTitles);
			return mediaTitles;
		}

		for (int i = 0; i < mediaList.size(); i++) {
			Media media = mediaList.get(i);
			if (media instanceof Album && ((Album) media).search(title, artist, songs)) {
				mediaTitles.add(media.getTitle());
			}
			if (media instanceof Movie && ((Movie) media).search(title, rating)) {
				mediaTitles.add(media.getTitle());
			}
			if (media instanceof Game && ((Game) media).search(title)) {
				mediaTitles.add(media.getTitle());
			}

		}
		Collections.sort(mediaTitles);
		return mediaTitles;
	}
}
