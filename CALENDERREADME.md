# tmpCal
CS180 summer 22
	Our group’s project focused on creating a calender application where tutors could create tutoring services and students could request appointments at a certain
  tutoring service. 
  
  The main menu, which displays upon running the program, has four options: sign in, create account, exit, and mystery option. 
  
  Exit closes displays a goodbye message and closes the program. Mystery option plays a video and re-displays the main menu. 
  
	Data for the program is stored in .txt files. It is assumed that, when the program is run, that Users.txt and AllStores.txt exist and are in the correct directory.
  It is also assumed that the user does not have manual access to these files. Manually changing any of the .txt data storage files can cause issues with the program. 
  
	Statistics display functionality is incomplete. Proper error messages have been added in the places where there is not working statistics menu. 
  
	The sign in option allows a user to enter their username and password to sign in to their account. The sign in details are kept in Users.txt, 
  and this file is read when the user signs in to check if their details are valid. If they enter invalid sign-in details, the main menu will appear again 
  so that they can sign-in again with proper details or create an account.
  
  Each valid account is classified as either a tutor or a customer. When the user successfully signs in, the appropriate menu will appear and request a selection.
  Each of tutor and customer menu are discussed further below. 
	
  The create account option allows the user to enter account information in order to make an account. They specify if they are a tutor or seller, their username,
  email, and password. The username entered is checked against the list of existing usernames. If the username entered is already taken, an error message appears
  and accepts input for a different username. Thus all users have a unique username; this is their unique identifier. The email entered is checked to see if it is 
  “valid” (that it contains an “@” and only one “.” after the “@”). The email need not be unique, because some users may want multiple accounts 
  (such as a parent that schedules tutoring sessions for each of their children). The password is checked for a minimum length of six characters, for 
  security purposes. Once an account is successfully created, the system displays an exit message and the program closes.Since each user has a file associated 
  with them, the program needs to close for a new file to be created. Also, since the user’s file name is associated with their username, using spaces or special
  characters in a username can cause unexpected errors. 
  
	When a student signs in, the customer menu appears and accepts input. The options are request appointment, list appointments, export appointments to CSV, 
  view personal statistics, and back to main menu. Request appointment allows the user to enter details of a new appointment and request an appointment at that time. When the customer requests an appointment, it is added to the appropriate store’s waitlist. The waitlist for a store is an arraylist of session objects with no size limit, thus allowing each store to have a waiting log of customers. 
The list appointments displays that customer’s existing appointments. Export appointments to CSV exports the appointments to a .txt file, because the group 
member tasked with CSV file work did not do so. View personal statistics has not been implemented fully. 
	
  When a tutor logs in, the tutor menu appears and accepts input.The options are view list of stores, set up store from CSV, view unsorted statistics, view 
  sorted statistics, and back to main menu. View list of stores shows all of the stores that that tutor owns and allows them to select a store to view. Setup store
  from CSV allows the tutor to import a CSV file and creates a new store object. Then, the system displays an exit message and shuts down to write the .txt file
  for that store. Back to main menu displays the main menu and requests input. 
  
	When the tutor selects a store, a menu for that store appears with three options: approve appointment, decline appoinment,
  and back to seller menu. The approve and decline options display a the store’s waitlisted sessions and prompts the user to select a session to approve or decline. 
  The .txt files are then updated automatically. 


	








