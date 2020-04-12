import java.sql.*;
import java.io.*;
import java.util.*;

/**
 * 
 * Acknowledgments: This example is a modification of code provided by Dimitri
 * Rakitine. Further modified by Shrikanth N C for MySql(MariaDB) support.
 * Replace all $USER$ with your unity id and $PASSWORD$ with your 9 digit
 * student id or updated password (if changed)
 * 
**/

public class Project {
	static final String jdbcURL = "jdbc:mariadb://classdb2.csc.ncsu.edu:3306/jmodi3";
	// Put your oracle ID and password here

	static final String createFile = "create_java.sql";
	static final String insertFile = "insert_java.sql";

	private static Connection connection = null;
	private static Statement statement = null;
	private static ResultSet result = null;

	public static void main(String[] args) {

		initialize();

		while(true)
		{
			System.out.println("\nMAIN MENU");
			System.out.println("1. Editing and Publishing");
			System.out.println("2. Production of a book edition or of an issue of a publication");
			System.out.println("3. Distribution");
			System.out.println("4. Reports");
			System.out.println("0. Exit");
			System.out.println("Enter your choice: ");

			Scanner intScanner = new Scanner(System.in);
			Scanner floatScanner = new Scanner(System.in);
			Scanner lineScanner = new Scanner(System.in);

			int main_choice = intScanner.nextInt();

			switch(main_choice)
			{
				case 1: System.out.println("In case 1"); // editing and publishing section
					break;

				case 2: System.out.println("In case 2"); // production section
					
					execute_task2();

					// call instance methods of class
					// prod.getName("chintan");
					
					break;

				case 3: System.out.println("In case 3"); // distribution section
					break;

				case 4: System.out.println("In case 4"); // reports section

					execute_task4();
					
					break;

				case 0: // exit program
					close();
					System.exit(0);

				default:
					System.out.println("Invalid choice! Please enter correct choice");
			}
		}
		
		/**
		try {
			
			
			boolean canAfford = checkAbilityToStudy("Todd");
			// ************************************************************************

			// --------- MODIFIED CODE START ---------

			// MOD - try catch block to catch any SQLException occurring in modifyALittleBit2().
			try
			{
				// MOD - set auto commit mode to false - this means that the changes made by SQL queries won't be committed to the database unless we call the commit() function manually.
				connection.setAutoCommit(false);

				// modifyALittleBit1();
				modifyALittleBit2();

				// MOD - call commit() manually to permanently commit changes to database as a single transaction.
				// this statement will not be reached if one of the update statements in modifyALittleBit2() is not executed.
				connection.commit();
			}
			// MOD - Catching a SQLException would mean that one of the update queries is not executed properly and hence rollback the database to its previous state.
			catch(SQLException se) 
			{
				connection.rollback();
			}
			// MOD - good practice to again enable the default auto-commit mode to prevent any other issues.
			finally 
			{
				connection.setAutoCommit(true);
			}
			// --------- MODIFIED CODE END ---------

			boolean canAfford1 = checkAbilityToStudy("Angela");

			if (canAfford == canAfford1) {
				System.out.println("Success");
			} else {
				System.out.println("Failure");
			}
			

		} catch (SQLException e) {
			e.printStackTrace();
		}
		**/
		// ***********************************************************************
		// close();
	}

	public static void execute_task2() 
	{
		// pass connection so the class can work on SQL queries
		Production prod = new Production(connection);

		Scanner intScanner = new Scanner(System.in);
		Scanner floatScanner = new Scanner(System.in);
		Scanner lineScanner = new Scanner(System.in);

		System.out.println("\nTASK 2: Production");
		System.out.println("1. Enter a new book edition or new issue of a publication"); 
		System.out.println("2. Update, delete a book edition or publication issue");
		System.out.println("3. Enter/update an article or chapter: title, author's name, topic, and date");
		System.out.println("4. Enter/update text of an article");
		System.out.println("5. Find books and articles by topic, date, author's name");
		System.out.println("6. Enter payment for author or editor, and keep track of when each payment was claimed by its addressee");
		System.out.println("0. Exit this menu");
		System.out.println("Enter your choice: ");
		
		int task2_choice = intScanner.nextInt();

		switch(task2_choice)
		{
			case 1: //operation 1
				int pubId, orderItemId, val;
				String pubDate;
				float price;

				boolean isEdition;
				System.out.println("Enter 0 for edition and 1 for a periodic issue: ");
				val = intScanner.nextInt();

				if(val == 0)
					isEdition = true;
				else
					isEdition = false;

				System.out.println("Enter publication ID: ");
				pubId = intScanner.nextInt();

				if(isEdition)
				{
					System.out.println("Enter edition no: ");
					orderItemId = intScanner.nextInt();
				}
				else
				{
					System.out.println("Enter issue no: ");
					orderItemId = intScanner.nextInt();
				}
				
				System.out.println("Enter price: ");
				price = floatScanner.nextFloat();

				System.out.println("Enter publication date: ");
				pubDate = lineScanner.nextLine();

				if(isEdition)
				{
					System.out.println("Enter ISBN: ");
					String isbn = lineScanner.nextLine();

					try
					{
						prod.op1_edition(orderItemId, pubId, price, pubDate, isbn);
						System.out.println("New Book Edition inserted successfully!");
					} catch(SQLException e)
					{
						e.printStackTrace();
						// System.out.println("Operation Failed. Try Again!");
					}
				}
				else
				{
					try
					{
						prod.op1_issue(orderItemId, pubId, price, pubDate, orderItemId);
						System.out.println("New Periodic Issue inserted successfully!");
					} catch(SQLException e)
					{
						e.printStackTrace();
						// System.out.println("Operation Failed. Try Again!");
					}
				}

				break;

			case 2: //operation 2

				// boolean isEdition;
				System.out.println("Enter 0 for edition and 1 for a periodic issue: ");
				val = intScanner.nextInt();

				if(val == 0)
					isEdition = true;
				else
					isEdition = false;

				System.out.println("\nSUB-MENU");
				System.out.println("1. Update price");
				System.out.println("2. Update publication date");
				
				if(isEdition)
				{
					System.out.println("3. Update ISBN");
					System.out.println("4. Delete Book Edition");
				}
				else
				{
					System.out.println("3. Delete Issue");
				}

				System.out.println("0. Exit this menu");

				System.out.println("Enter choice:");
				val = intScanner.nextInt();

				switch(val)
				{
					case 1: 

						System.out.println("Enter edition no./issue no. for which update is to be done: ");
						orderItemId = intScanner.nextInt();

						System.out.println("Enter publication id for which update is to be done: ");
						pubId = intScanner.nextInt();

						System.out.println("Enter new price: ");
						price = floatScanner.nextFloat();
						
						try
						{
							prod.op2_update_price(price, orderItemId, pubId);
							System.out.println("Price updated!");
						} catch(SQLException e)
						{
							e.printStackTrace();
							// System.out.println("Operation Failed. Try Again!");
						}

						break;

					case 2: 
						System.out.println("Enter publication date: ");
						pubDate = lineScanner.nextLine();

						System.out.println("Enter edition no./issue no. for which update is to be done: ");
						orderItemId = intScanner.nextInt();

						System.out.println("Enter publication id for which update is to be done: ");
						pubId = intScanner.nextInt();
						
						try
						{
							prod.op2_update_pubDate(pubDate, orderItemId, pubId);
							System.out.println("Publication Date updated!");
						} catch(SQLException e)
						{
							e.printStackTrace();
							// System.out.println("Operation Failed. Try Again!");
						}

						break;

					case 3: 
						if(isEdition)
						{
							System.out.println("Enter edition no./issue no. for which update is to be done: ");
							orderItemId = intScanner.nextInt();

							System.out.println("Enter publication id for which update is to be done: ");
							pubId = intScanner.nextInt();

							System.out.println("Enter ISBN: ");
							String isbn = lineScanner.nextLine();
							
							try
							{
								prod.op2_update_isbn(isbn, orderItemId, pubId);
								System.out.println("ISBN updated!");
							} catch(SQLException e)
							{
								e.printStackTrace();
								// System.out.println("Operation Failed. Try Again!");
							}
						}
						else
						{
							System.out.println("Enter issue no. to be deleted: ");
							orderItemId = intScanner.nextInt();

							System.out.println("Enter publication id whose issue is to be deleted: ");
							pubId = intScanner.nextInt();

							try
							{
								prod.op2_delete_edition_issue(orderItemId, pubId);
								System.out.println("Issue deleted successfully!");
							} catch(SQLException e)
							{
								e.printStackTrace();
								// System.out.println("Operation Failed. Try Again!");
							}
						}

						break;

					case 4: 
						System.out.println("Enter edition no. to be deleted: ");
						orderItemId = intScanner.nextInt();

						System.out.println("Enter publication id whose edition is to be deleted: ");
						pubId = intScanner.nextInt();

						try
						{
							prod.op2_delete_edition_issue(orderItemId, pubId);
							System.out.println("Book Edition deleted successfully!");
						} catch(SQLException e)
						{
							e.printStackTrace();
							// System.out.println("Operation Failed. Try Again!");
						}
						break;

					case 0: break;

					default:
						System.out.println("Invalid choice! Please enter correct choice");
				}
				
				break;

			case 3: //operation 3
				System.out.println("\nSUB-MENU");
				System.out.println("1. Enter a chapter");
				System.out.println("2. Enter an article");
				System.out.println("3. Update a chapter");
				System.out.println("4. Update an article");
				System.out.println("0. Exit this menu");
				System.out.println("Enter your choice: ");

				val = intScanner.nextInt();

				switch(val)
				{
					case 1: 
						System.out.println("Enter publication id for this chapter: ");
						pubId = intScanner.nextInt();

						System.out.println("Enter edition no. for this chapter: ");
						orderItemId = intScanner.nextInt();

						System.out.println("Enter title: ");
						String title = lineScanner.nextLine();

						System.out.println("Enter chapter text: ");
						String chapterText = lineScanner.nextLine();

						System.out.println("Enter chapter date: ");
						String chapterDate = lineScanner.nextLine();

						System.out.println("Enter topic(s) associated with chapter (separated by commas only): ");
						String topics = lineScanner.nextLine();

						System.out.println("Enter author(s) id associated with chapter (separated by commas only): ");
						String authorIds = lineScanner.nextLine();

						String[] topicList = topics.split(",");
						String[] authorList = authorIds.split(",");

						try
						{
							prod.op3_enter_chapter(orderItemId, pubId, title, chapterText, chapterDate, topicList, authorList);
							System.out.println("New Chapter entered successfully!");
						} catch(SQLException e)
						{
							e.printStackTrace();
							// System.out.println("Operation Failed. Try Again!");
						}

						break;

					case 2:
						break;

					case 3:
						break;

					case 4:
						break;

					case 0:
						break;

					default:
						System.out.println("Invalid choice! Please enter correct choice");
				}

				break;

			case 4: //operation 4
				System.out.println("Enter issue no. in which article was published: ");
				orderItemId = intScanner.nextInt();

				System.out.println("Enter publication id for the issue: ");
				pubId = intScanner.nextInt();

				System.out.println("Enter title of the article: ");
				String title = lineScanner.nextLine();

				System.out.println("Enter new text for the article: ");
				String articleText = lineScanner.nextLine();

				try
				{
					prod.op4_update_text_article(title, orderItemId, pubId, articleText);
					System.out.println("Article's text updated!");
				} catch(SQLException e)
				{
					e.printStackTrace();
					// System.out.println("Operation Failed. Try Again!");
				}	

				break;

			case 5: //operation 5
				System.out.println("\nSUB-MENU");
				System.out.println("1. Find books and articles by topic");
				System.out.println("2. Find books and articles by date");
				System.out.println("3. Find books and articles by author's name");
				System.out.println("0. Exit this menu");
				System.out.println("Enter your choice: ");

				int op5_choice = intScanner.nextInt();

				switch(op5_choice)
				{
					case 1: 
						System.out.println("Enter topic name: ");
						String topicName = lineScanner.nextLine();

						try
						{
							prod.op5_find_topic(topicName);
						} catch(SQLException e)
						{
							e.printStackTrace();
							// System.out.println("Operation Failed. Try Again!");
						}

						break;

					case 2:
						System.out.println("Enter publication date: ");
						pubDate = lineScanner.nextLine();

						try
						{
							prod.op5_find_pubDate(pubDate);
						} catch(SQLException e)
						{
							e.printStackTrace();
							// System.out.println("Operation Failed. Try Again!");
						}

						break;

					case 3:
						System.out.println("Enter author's name: ");
						String authorName = lineScanner.nextLine();

						try
						{
							prod.op5_find_authorName(authorName);
						} catch(SQLException e)
						{
							e.printStackTrace();
							// System.out.println("Operation Failed. Try Again!");
						}
						break;

					case 0:
						break;

					default:
						System.out.println("Invalid choice! Please enter correct choice");
					
				}
				break;

			case 6: //operation 6
				System.out.println("\nSUB-MENU");
				System.out.println("1. Enter payment record for a employee");
				System.out.println("2. Claim payment");
				System.out.println("0. Exit this menu");
				System.out.println("Enter your choice: ");
		
				val = intScanner.nextInt();

				switch(val)
				{
					case 1:
						System.out.println("Enter payment id: ");
						int payId = intScanner.nextInt();

						System.out.println("Enter employee id for which payment record is to be entered: ");
						int cmId = intScanner.nextInt();

						System.out.println("Enter amount: ");
						float amount = floatScanner.nextFloat();

						System.out.println("Enter payment date: ");
						String payDate = lineScanner.nextLine();

						try
						{
							prod.op6_insert_payment(payId, cmId, amount, payDate);
							System.out.println("Payment record entered!");
						} catch(SQLException e)
						{
							e.printStackTrace();
							// System.out.println("Operation Failed. Try Again!");
						}

						break;

					case 2: 
						// System.out.println("Enter employee id who is claiming the payment: ");
						// cmId = intScanner.nextInt();

						System.out.println("Enter payment record id which is to be claimed: ");
						payId = intScanner.nextInt();

						System.out.println("Enter claim date: ");
						String claimDate = lineScanner.nextLine();

						try
						{
							prod.op6_claim_payment(payId, claimDate);
							System.out.println("Payment record updated!");
						} catch(SQLException e)
						{
							e.printStackTrace();
							// System.out.println("Operation Failed. Try Again!");
						}

						break;

					case 0:
						break;

					default:
						System.out.println("Invalid choice! Please enter correct choice");
				}
				break;

			case 0: 
				break;

			default: 
				System.out.println("Invalid choice! Please enter correct choice");
		}
	}

	public static void execute_task4() {
		// pass connection so the class can work on SQL queries
		Reports report = new Reports(connection);

		Scanner intScanner = new Scanner(System.in);
		

		System.out.println("\nTASK 4: Reports");
		System.out.println("1. Generate monthly reports.");
		System.out.println("2. Calculate the total current number of distributors.");
		System.out.println("3. calculate total revenue (since inception) per city, per distributor, and per location.");
		System.out.println("4. Calculate total payments to the editors and authors, per time period and per work type (book authorship, article authorship, or editorial work)."); 
		System.out.println("0. Exit this menu");
		System.out.println("Enter your choice: ");
		
		int task4_choice = intScanner.nextInt();

		switch(task4_choice)
		{
			case 1: // task 4 choice 1
				try
				{
					report.op1_get_monthly_reports();
				} catch(SQLException e)
				{
					e.printStackTrace();
					// System.out.println("Operation Failed. Try Again!");
				}
				break;
			case 2: // task 4 choice 2
				try
				{
					report.op2_get_total_distributors();
				} catch(SQLException e)
				{
					e.printStackTrace();
					// System.out.println("Operation Failed. Try Again!");
				}
				break;
			case 3: // task 4 choice 3
				try
				{
					report.op3_calculate_total_revenue();
				} catch(SQLException e)
				{
					e.printStackTrace();
					// System.out.println("Operation Failed. Try Again!");
				}
				break;
			case 4: // task 4 choice 4
				Scanner lineScanner = new Scanner(System.in);
				String beginDate,endDate;
				System.out.println("Enter begin date: ");
				beginDate = lineScanner.nextLine();
				System.out.println("Enter end date: ");
				endDate = lineScanner.nextLine();
				
				try
				{
					report.op4_calculate_total_payments(beginDate, endDate);
				} catch(SQLException e)
				{
					e.printStackTrace();
					// System.out.println("Operation Failed. Try Again!");
				}
				break;
			case 0: 
				break;

			default: 
				System.out.println("Invalid choice! Please enter correct choice");
		}

		
	}

	public static void resetDatabase() {
		try {
			// statement.executeUpdate("DROP TABLE Students");
			// statement.executeUpdate("DROP TABLE Schools");
			statement.executeUpdate("drop table OrderContains,OrderBillMappings,Orders,Bills,Locations,Distributors,ArticleWrittenBy,ArticleTopicMappings,Articles,ChapterWrittenBy,ChapterTopicMappings,Chapters,Issues,Editions,ItemEditedBy,OrderItems,Payrolls,Journalists,Authors,Editors,ContentManagers,PeriodicPublications,BookTopicMappings,Topics,Books,Publications");
		} 
		catch (SQLException e) { }

		createTables();
		populateTables();
	}

	private static void initialize() {
		try {
			connectToDatabase();
			resetDatabase();
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

	private static void createTables() {
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(createFile));

			String line;

			while((line = br.readLine()) != null) {
				// process the line.
				if(!line.isEmpty())
				{
					statement.executeUpdate(line);
				}
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	private static void populateTables() {
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(insertFile));

			String line;

			while((line = br.readLine()) != null) {
				// process the line.
				if(!line.isEmpty())
				{
					statement.executeUpdate(line);
				}
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	private static void connectToDatabase() throws ClassNotFoundException, SQLException {
		Class.forName("org.mariadb.jdbc.Driver");

		String user = "jmodi3";
		String password = "200315622";

		connection = DriverManager.getConnection(jdbcURL, user, password);
		statement = connection.createStatement();
	}

	private static void close() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (result != null) {
			try {
				result.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}