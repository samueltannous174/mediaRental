package project;

import java.util.Objects;
import java.lang.Comparable;

public class Media implements Comparable<Media>

{
	protected String title;
	protected int numberOfCopies;
	
	public Media(String title, int numberOfCopies) {
		this.title = title;
		this.numberOfCopies = numberOfCopies;
	}
	public Media() {
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getNumberOfCopies() {
		return numberOfCopies;
	}
	public void setNumberOfCopies(int numberOfCopies) {
		this.numberOfCopies = numberOfCopies;
	}
		@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Media other = (Media) obj;
		return numberOfCopies == other.numberOfCopies && Objects.equals(title, other.title);
	}
		public int compareTo(Media m) {
			return this.getTitle().compareTo(m.getTitle());
		
		}

	public boolean isAvailable() {
			return this.numberOfCopies > 0;
		}
		
		public void increaseNumberOfCopies() {
			numberOfCopies++;
		}
		
		public void decreaseNumberOfCopies() {
			numberOfCopies--;
		}
	}
	


