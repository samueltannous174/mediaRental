package project;

public class Game extends Media {
	private double weight;

	public Game(String title, int numberOfCopies, double weight) {
		super(title, numberOfCopies);
		this.weight = weight;
	}

	public Game() {
		super();
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public boolean search(String searchedTitle) {
		return title != null && this.title.equals(searchedTitle);

	}

	@Override
	public String toString() {
		return "Game{" +
				"weight=" + weight +
				", title='" + title + '\'' +
				", numberOfCopies=" + numberOfCopies +
				'}';
	}
}
