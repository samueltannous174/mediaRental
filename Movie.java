package project;

public class Movie extends Media {
	private String rating;

	public Movie(String title, int numberOfCopies, String rating) {
		super(title, numberOfCopies);
		this.rating = rating;
	}

	public Movie() {
		super();
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public boolean search(String searchedTitle, String searchedRating) {

		if (searchedRating != null && this.title.equals(searchedTitle)
				&& rating.equals(searchedTitle)) {
			return true;
		}
		if (this.title.equals(searchedTitle)) {
			return true;
		}
		return rating.equals(searchedRating);
	}

	@Override
	public String toString() {
		return "Movie{" +
				"title='" + title + '\'' +
				", numberOfCopies=" + numberOfCopies +
				", rating='" + rating + '\'' +
				'}';
	}
}
