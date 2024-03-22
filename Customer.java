package project;

import java.util.ArrayList;

public class Customer implements Comparable<Customer> {
	protected String name;
	protected String address;
	protected String plan;
	
	private ArrayList<String> mediaInterestedIn = new ArrayList<>();
	private ArrayList<String> mediaRented = new ArrayList<>();

	public Customer(String name, String address, String plan){
		this.name = name;
		this.address = address;
		this.plan = plan;
		
	}
	public Customer( ){
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPlan() {
		return plan;
	}

	public void setPlan(String plan) {
		if (plan.equals("limited") || plan.equals("unlimited")) {
			this.plan = plan;
		}
		else {
			System.out.println("Unsupported plan");
		}
	}

	public ArrayList<String> getMediaRented() {
		return mediaRented;
	}

	public void addToMediaIntrestedIn(String m) {
		mediaInterestedIn.add(m);
	}

	public void removeFromMediaIntrestedIn(String m) {
		mediaInterestedIn.remove(m);
	}
	public void removeFromMediaRented(String m) {
		mediaRented.remove(m);
	}
	
	public void addToMediaRented(String m) {
		mediaRented.add(m);
	}

	public ArrayList<String> getMediaInterestedIn() {
		return mediaInterestedIn;
	}
	
	public boolean checkPlanIfUnlimited() {

		if (this.getPlan().equals("limited")) {
			return false;
		}
		
		return true;
	}

	@Override
	public String toString() {
		return "Customer{" +
				"name='" + name + '\'' +
				", address='" + address + '\'' +
				", plan='" + plan + '\'' +
				", mediaInterestedIn=" + mediaInterestedIn +
				", mediaRented=" + mediaRented +
				'}';
	}

	@Override
	public int compareTo(Customer o) {
		return this.getName().compareTo(o.getName());

	}
}
