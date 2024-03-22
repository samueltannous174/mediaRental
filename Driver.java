package project;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Driver {
	private MediaRental mediaRental = new MediaRental();
	private String DATA_SEPARATOR = "/";

	public Driver() {
	}

	public void testAddingCustomers() {
		printLineSeparator();
		String customerName, customerAddress, customerPlan;
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter Customer Name: ");
		customerName = scanner.nextLine();
		System.out.println("Enter Customer Address: ");
		customerAddress = scanner.nextLine();
		System.out.println("Enter Customer Plan: ");
		customerPlan = scanner.nextLine();
		mediaRental.addCustomer(customerName, customerAddress, customerPlan);
	}

	public void testAddingMedia() {
		printLineSeparator();
		int mediaTypeChoice;
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter Media Type: ");
		System.out.println("1) Game");
		System.out.println("2) Album");
		System.out.println("3) Movie");
		printLineSeparator();
		mediaTypeChoice = scanner.nextInt();
		String title;
		int numberOfCopies;
		scanner.nextLine();
		if (mediaTypeChoice == 1) {
			System.out.println("Enter Game Title: ");
			title = scanner.nextLine();
			System.out.println("Enter Number of copies: ");
			numberOfCopies = scanner.nextInt();
			scanner.nextLine();
			System.out.println("Enter Weight: ");
			double weight = scanner.nextDouble();
			mediaRental.addGame(title, numberOfCopies, weight);
		} else if (mediaTypeChoice == 2) {
			System.out.println("Enter Album Title: ");
			title = scanner.nextLine();
			System.out.println("Enter Number of copies: ");
			numberOfCopies = scanner.nextInt();
			scanner.nextLine();
			System.out.println("Enter Artist Name: ");
			String artistName = scanner.nextLine();
			System.out.println("Enter Song names (comma seperated): ");
			String songs = scanner.nextLine();
			mediaRental.addAlbum(title, numberOfCopies, artistName, songs);
		} else if (mediaTypeChoice == 3) {
			System.out.println("Enter Movie Title: ");
			title = scanner.nextLine();
			System.out.println("Enter Number of copies: ");
			numberOfCopies = scanner.nextInt();
			scanner.nextLine();
			System.out.println("Enter Movie rating ");
			String rating = scanner.nextLine();
			mediaRental.addMovie(title, numberOfCopies, rating);
		} else {
			System.out.println("wrong choice");
		}

	}

	public void testingAddingToCart() {
		if (mediaRental.customersList.size() == 0) {
			System.out.println("No Customers found");
			return;
		}
		printAllCustomers();
		Scanner scanner = new Scanner(System.in);
		int choice;
		System.out.println("Enter Customer Number: ");
		choice = scanner.nextInt();
		String customerName = mediaRental.customersList.get(choice - 1).getName();
		printAllMedia();
		System.out.println("Enter Media Number to add to cart: ");
		choice = scanner.nextInt();
		String mediaTitle = mediaRental.mediaList.get(choice - 1).getTitle();
		mediaRental.addToCart(customerName, mediaTitle);
		printLineSeparator();
	}

	public void testingRemovingFromCart() {
		if (mediaRental.customersList.size() == 0) {
			System.out.println("No Customers found");
			return;
		}
		Scanner scanner = new Scanner(System.in);
		int choice;
		printAllCustomers();
		System.out.println("Enter Customer Number: ");
		choice = scanner.nextInt();
		String customerName = mediaRental.customersList.get(choice - 1).getName();
		Customer customer = getCustomer(customerName);
		if (customer == null) {
			System.out.println("Customer not found");
			return;
		}
		printMediaTitles(customer.getMediaInterestedIn());
		if (customer.getMediaInterestedIn().size() == 0) {
			return;
		}
		System.out.println("Enter Media Number to remove from cart: ");
		choice = scanner.nextInt();
		String mediaTitle = customer.getMediaInterestedIn().get(choice - 1);
		mediaRental.removeFromCart(customerName, mediaTitle);
		printLineSeparator();
	}

	public void testProcessingRequestsOne() {
		mediaRental.processRequests();
	}

	public void testReturnMedia() {
		if (mediaRental.customersList.size() == 0) {
			System.out.println("No Customers found");
			return;
		}
		Scanner scanner = new Scanner(System.in);
		int choice;
		printAllCustomers();
		System.out.println("Enter Customer Number: ");
		choice = scanner.nextInt();
		String customerName = mediaRental.customersList.get(choice - 1).getName();
		Customer customer = getCustomer(customerName);
		if (customer == null) {
			System.out.println("Customer not found");
			return;
		}
		printMediaTitles(customer.getMediaRented());
		if (customer.getMediaRented().size() == 0) {
			return;
		}
		System.out.println("Enter Media Number to return: ");
		choice = scanner.nextInt();
		String mediaTitle = customer.getMediaRented().get(choice - 1);
		mediaRental.returnMedia(customerName, mediaTitle);
		printLineSeparator();
	}

	private String getSearchValue(String searchTitle) {
		Scanner scanner = new Scanner(System.in);
		System.out.println(searchTitle);
		String value = scanner.nextLine();
		printLineSeparator();
		if (value.isBlank() || value.isEmpty()) {
			value = null;
		}
		return value;
	}

	public void testSearchMedia() {
		String title, rating, artist, songs;
		title = getSearchValue("Enter Title To Search (press enter to skip): ");
		rating = getSearchValue("Enter Rating To Search (press enter to skip): ");
		artist = getSearchValue("Enter Artist To Search (press enter to skip): ");
		songs = getSearchValue("Enter Songs To Search (comma separated), (press enter to skip): ");
		ArrayList<String> searchResults = mediaRental.searchMedia(title, rating, artist, songs);
		printMediaTitles(searchResults);
	}

	private Customer getCustomer(String customerName) {
		Customer customer;
		for (int i = 0; i < mediaRental.customersList.size(); i++) {
			customer = mediaRental.customersList.get(i);
			if (customer.getName().equals(customerName)) {
				return customer;
			}
		}
		return null;
	}

	private void initializeCustomerMediaInterestedIn() {
		try {
			File file = new File("customers_media_interested_in.txt");
			Scanner myReader = new Scanner(file);
			String customerName, mediaTitle;
			Customer currentCustomer;
			while (myReader.hasNextLine()) {
				String[] arr = myReader.nextLine().trim().split(DATA_SEPARATOR);
				customerName = arr[0];
				mediaTitle = arr[1];
				currentCustomer = getCustomer(customerName);
				if (currentCustomer != null) {
					currentCustomer.addToMediaIntrestedIn(mediaTitle);
				}
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("no customer media interested file found");
		}
	}

	private void initializeCustomerMediaRented() {
		try {
			File file = new File("customers_media_rented.txt");
			Scanner myReader = new Scanner(file);
			String customerName, mediaTitle;
			Customer currentCustomer;
			while (myReader.hasNextLine()) {
				String[] arr = myReader.nextLine().trim().split(DATA_SEPARATOR);
				customerName = arr[0];
				mediaTitle = arr[1];
				currentCustomer = getCustomer(customerName);
				if (currentCustomer != null) {
					currentCustomer.addToMediaRented(mediaTitle);
				}
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("no customer media interested file found");
		}
	}

	private void initializeCustomers() {
		try {
			File file = new File("customers.txt");
			Scanner myReader = new Scanner(file);
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				String[] array = data.split(DATA_SEPARATOR);
				Customer customer = new Customer(array[0], array[1], array[2]);
				mediaRental.customersList.add(customer);
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("no customers file found");
		}
	}

	private void initializeMediaList() {
		try {
			File file = new File("media_inventory.txt");
			Scanner myReader = new Scanner(file);
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				if (data.isEmpty()) {
					continue;
				}
				String[] array = data.split(DATA_SEPARATOR);
				Media media = null;
				if (array[0].equals("album")) {
					media = new Album(array[1], Integer.parseInt(array[2]), array[3], array[4]);

				}
				if (array[0].equals("game")) {
					media = new Game(array[1], Integer.parseInt(array[2]), Double.parseDouble(array[3]));
				}
				if (array[0].equals("movie")) {
					media = new Movie(array[1], Integer.parseInt(array[2]), array[3]);
				}
				mediaRental.mediaList.add(media);
			}

			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("no media inventory file found");
		}
	}

	private PrintWriter getPrinterWriter(String fileName) throws IOException {
		FileWriter fileWriter = new FileWriter(fileName, false);
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		return new PrintWriter(bufferedWriter);
	}

	private void saveCustomersToFile() throws IOException {
		Customer customer;
		PrintWriter printWriter = getPrinterWriter("customers.txt");
		for (int i = 0; i < mediaRental.customersList.size(); i++) {
			customer = mediaRental.customersList.get(i);
			String address = customer.getAddress();
			if (address == null) {
				address = "";
			}
			String line = customer.getName() + DATA_SEPARATOR + address + DATA_SEPARATOR + customer.getPlan();
			printWriter.println(line);
		}
		printWriter.close();
	}

	private void saveMediaToFileInventory() throws IOException {
		String mediaType, line;
		PrintWriter printWriter = getPrinterWriter("media_inventory.txt");
		Media media;
		for (int i = 0; i < mediaRental.mediaList.size(); i++) {
			media = mediaRental.mediaList.get(i);
			line = "";
			if (media instanceof Game) {
				mediaType = "game";
				line += mediaType + DATA_SEPARATOR + media.getTitle() + DATA_SEPARATOR + media.getNumberOfCopies()
						+ DATA_SEPARATOR + ((Game) media).getWeight();
			} else if (media instanceof Movie) {
				mediaType = "movie";
				line += mediaType + DATA_SEPARATOR + media.getTitle() + DATA_SEPARATOR + media.getNumberOfCopies()
						+ DATA_SEPARATOR + ((Movie) media).getRating();
			} else if (media instanceof Album) {
				mediaType = "album";
				line += mediaType + DATA_SEPARATOR + media.getTitle() + DATA_SEPARATOR + media.getNumberOfCopies()
						+ DATA_SEPARATOR + ((Album) media).getArtist() + DATA_SEPARATOR + ((Album) media).getSongs();
			}
			printWriter.println(line);
		}
		printWriter.close();
	}

	private void saveCustomerMediaInterestedInToFile() throws IOException {
		Customer customer;
		PrintWriter printWriter = getPrinterWriter("customers_media_interested_in.txt");
		for (int i = 0; i < mediaRental.customersList.size(); i++) {
			customer = mediaRental.customersList.get(i);
			for (int j = 0; j < customer.getMediaInterestedIn().size(); j++) {
				String line = customer.getName() + DATA_SEPARATOR + customer.getMediaInterestedIn().get(j);
				printWriter.println(line);
			}
		}
		printWriter.close();
	}

	private void saveCustomerMediaRentedToFile() throws IOException {
		Customer customer;
		PrintWriter printWriter = getPrinterWriter("customers_media_rented.txt");
		for (int i = 0; i < mediaRental.customersList.size(); i++) {
			customer = mediaRental.customersList.get(i);
			for (int j = 0; j < customer.getMediaRented().size(); j++) {
				String line = customer.getName() + DATA_SEPARATOR + customer.getMediaRented().get(j);
				printWriter.println(line);
			}
		}
		printWriter.close();
	}

	public void initialize() {
		initializeCustomers();
		initializeMediaList();
		initializeCustomerMediaInterestedIn();
		initializeCustomerMediaRented();
	}

	private void saveAll() {
		try {
			saveCustomerMediaInterestedInToFile();
			saveCustomerMediaRentedToFile();
			saveMediaToFileInventory();
			saveCustomersToFile();
		} catch (IOException e) {
			System.out.println("Failed to Save");
			e.printStackTrace();
		}
	}

	private void printLineSeparator() {
		System.out.println("***************************************************************");
	}

	private void printAllMedia() {
		Collections.sort(mediaRental.mediaList);
		printLineSeparator();
		if (mediaRental.mediaList.size() > 0) {
			System.out.println("All Media: ");
			for (int i = 0; i < mediaRental.mediaList.size(); i++) {
				System.out.println((i + 1) + ") " + mediaRental.mediaList.get(i));
			}
		} else {
			System.out.println("No Media found");
		}
		printLineSeparator();
	}

	private void printMediaTitles(ArrayList<String> mediaTitles) {
		Collections.sort(mediaTitles);
		printLineSeparator();
		if (mediaTitles.size() > 0) {
			System.out.println("Media: ");
			for (int i = 0; i < mediaTitles.size(); i++) {
				System.out.println((i + 1) + ") " + mediaTitles.get(i));
			}
		} else {
			System.out.println("No Media");
		}
		printLineSeparator();
	}

	private void printAllCustomers() {
		Collections.sort(mediaRental.customersList);
		printLineSeparator();
		if (mediaRental.customersList.size() > 0) {
			System.out.println("All Customers: ");
			for (int i = 0; i < mediaRental.customersList.size(); i++) {
				System.out.println((i + 1) + ") " + mediaRental.customersList.get(i));
			}
		} else {
			System.out.println("No Customers found.");
		}
		printLineSeparator();
	}

	private void enterLimitedPlanLimit() {
		System.out.println("Enter limited plan limit: ");
		Scanner scanner = new Scanner(System.in);
		int limit = scanner.nextInt();
		mediaRental.setLimitedPlanLimit(limit);
	}

	private int printAndChooseFromMainMenu() {
		Scanner scanner = new Scanner(System.in);
		printLineSeparator();
		System.out.println("Main Menu");
		System.out.println("1) Add a new Customer");
		System.out.println("2) Add a new Media");
		System.out.println("3) Add Media To Cart");
		System.out.println("4) Remove Media From Cart");
		System.out.println("5) Process Requests");
		System.out.println("6) Return Media");
		System.out.println("7) Search Media");
		System.out.println("8) Print All Media");
		System.out.println("9) Print All Customers");
		System.out.println("10) Set Limited Plan limit");
		System.out.println("11) Load All Data from files");
		System.out.println("12) Save All Date To Files");
		System.out.println("13) Exit Menu");
		System.out.println("Choose a Choice from menu: ");
		printLineSeparator();
		return scanner.nextInt();
	}

	public void start() {
		int menuChoice = printAndChooseFromMainMenu();

		while (menuChoice != 13) {
			if (menuChoice == 1) {
				testAddingCustomers();
			} else if (menuChoice == 2) {
				testAddingMedia();
			} else if (menuChoice == 3) {
				testingAddingToCart();
			} else if (menuChoice == 4) {
				testingRemovingFromCart();
			} else if (menuChoice == 5) {
				testProcessingRequestsOne();
			} else if (menuChoice == 6) {
				testReturnMedia();
			} else if (menuChoice == 7) {
				testSearchMedia();
			} else if (menuChoice == 8) {
				printAllMedia();
			} else if (menuChoice == 9) {
				printAllCustomers();
			} else if (menuChoice == 10) {
				enterLimitedPlanLimit();
			} else if (menuChoice == 11) {
				initialize();
			} else if (menuChoice == 12) {
				saveAll();
			}
			menuChoice = printAndChooseFromMainMenu();
		}
	}
}
