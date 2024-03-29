# Project05

**Instructions on how to compile and run your project**

Take the all of the classes within our compositor and all of the .txt files to run and compile the program. If you decide to make a new .txt file to upload, you have to make sure it is in the specific format that our program takes. 
**Run Server.java firstly, then run StartClientGui.java.**

**Users.txt**

Each line of users.txt corresponds to a unique user, either a tutor or a seller. 
Each line has format status,username,email,password 
Status can either be “Tutor” or “Student”. It is case sensitive. 
An example Users.txt file is:

Tutor,MoxiaoLi,moxiao@gmail.com,mo12345

Student,JunmoKim,junmo@gmail.com,junmo12345

**AllStores.txt**

Each line of AllStores.txt corresponds to a store that exists.
Each line has format storeName,sellerName

Each user has a file associated with them. The file path is username + “.txt”. So a user with the name “bob” has the file bob.txt.

For tutors, the file is format is:
Each line of the file corresponds to the name of a store that this tutor owns.
Each line has format storeName

For customers, each line of the file corresponds to a session that this customer is either waitlisted or approved at. 
Each line has format isApproved,hour,day,month,year,storeName
isApproved is either “waiting” or “approved”. It is case sensitive. 

Each store has a file called storeName + “Store.txt”. So the store called bobmath has a file called 
bobmathStore.txt. 

The first line is the username of the store owner

The next seven lines each correspond to a day of the week (0 = Sunday, etc) and contain opening and closing information about the store for that day, separated by commas, in this order: isOpen,openHour,closeHour,capacity,location. 

isOpen is “true” if the store open that day of the week, closed if not

openHour is the hour that the store will open

closeHour is the hour that the store will close

capacity is the capacity of the store that day

Location is the location of the store that day

NOTE: if isOpen is false on a certain line, the entire line should just be “false”.

The following lines each contain a name of the customer, thus listing out the elements of uniqueCustomers. 

The next line is just the string “BREAK” in all caps.

The following times each correspond to a session. They have format:
isApproved,hour,day,month,year,customer username

isApproved can be “approved” or “waiting”.

**Changes of Project 4**

Most of the functions were turned into synchronized functions. We had to tweak some of the functions such as remakeSellerFromFile in Seller.java because it was not operating the way that we wanted to.

**Class Description**

*Customer.java*

The class helped us create customer objects and remake Customer file, which includes customer data like the approved list and the waiting list of appointments. It also can let the customer make an appointment request and cancel the request, which are adding student into waiting list and removing the customer from the waiting list or approved list. Besides, there is a function named approveAppointmentAtTime, which is not for the customer but for the seller.

*CustomerStringPacket.java*

Used to send a customer and a string together between client and server

*FileStringPacket.java*

Used to send a File and a String between the client and the server.

*MainMenuGUI.java*

This class runs all the GUIs that this application needs and handles all client side communication with the server. The class is a graphical user interface letting users log in or sign in. If the user has existed, the user needs to enter his or her username and password and click log in. The program will automatically identify if a user is a student or a tutor. Then, the user will go to the responding menu GUI(customer/seller menu GUI). If the user wants to sign in, he or she needs to enter the email and click sign in; then, there is a JOptionPane letting the select user status if he or she wants to create a student account or a tutor account. The “Mystery Option” is a fun bonus, connecting a YouTube video link. The mainMenuGUI has some subGUI methods including playSellerMenuGUI, playCustomerMenuGUI, playCustomerCancelAppointmentGUI, playCustomerSelectStoreGUI, playNewAppointmentRequestGUI, playSellerStoreGUI, playSessionGUI, playSellerCancelAppointmentGUI, playOpenNewStoreGUI.

-playCustomerCancelAppointmentGUI method:

The method is a graphical user interface that helps customers cancel appointments that exist on approved list which is shown in a dropdown box. It will show a JOptionPane message if canceled successfully. If there is no appointment, there is a JOptionPane to message user no appointment to cancel. If user cancel the appointment successfully, there is JOptionPane to message user that he or she cancels the appointment successfully.

-playCustomerMenuGUI method:

The method is a graphical user interface after the customer logs in. Customers can export appointments to the CSV file which will show a JOptionPane to message user that he or she exports successfully and the path of the CSV file, view sorted and unsorted statistics data shown on a JOptionPane, and cancel an appointment which will call  playCustomerCancelAppointmentGUI method which is a GUI, request a new appointment which will call playCustomerSelectStoreGUI method, return to the main menu where user can log in or sign in, and exit from the program.

-playCustomerSelectStoreGUI method:

The method is a graphical user interface helping customers request a new appointment. The customer will choose a store and then jump to playNewAppointmentRequestGUI (A GUI method). The customer can return to playCustomerMenuGUI via the “Back” button.

-playNewAppointmentRequestGUI method:

Customers will operate on the GUI when they request a new appointment. When customers choose new appointment requests and select the store they want to request, they will jump to the GUI. In the GUI, they will enter a year, month, day, and hours and confirm new appointment request. Customer can return to previous GUI via the “Back” button

-playSellerCancelAppointmentGUI method:

The method is a graphical user interface helping sellers (tutors) cancel appointments. There is a dropbox including existing appointments. Tutors can cancel appointment by selecting a appointment and clicking confirm. There is a label to show open time of the store. The seller can go back to the previous graphical user interface by clicking the “Back” button.

-playSellerMenuGUI method

The method is a graphical user interface after sellers (tutors) login. In the GUI, tutors can view and select stores in a dropdown, then jump to playSellerStoreGUI method. In the GUI, tutors can also view statistics data (sorted and unsorted). If there is no store, there will be a JOptionPen to message user that there is no stores. Tutors can open a new store via the “Open new Store” button which will call playOpenNewStore method.

-playSellerStoreGUI method:

When tutors select a store, they will jump to the GUI. In the GUI, tutors can select an appointment in a dropdown, including the waiting list and approved list. After clicking confirm, the tutor will jump to a playSession GUI. Tutors also can go back to playSellerMenuGUI via the “Back to Tutor” button and exit via the “Exit” button.

-playSessionGUI method:

This GUI opens from playSellerStoreGUI after the user has selected a session(appointment). In the GUI, a tutor (a seller) can view the waiting list and approved list. If the tutor wants to move a student (a customer) from the waiting list to the approved list, he or she can select the customer(if the customer in the waiting list) and click the “Approve” button. If the tutor wants to deny the student, he or she can select the student and click the “Deny” button. Also, the “
There is a modification later
Reschedule” button can open RescheduleGUI. The “Back” button can return to SellerStoreGUI.

-playOpenNewStoreGUI method:

In the GUI method, play can enter a store name and open the store. If the name of the store already exits, there will be a JOptionPane message that the name already exits which also cover other errors. If the file path is not valid, there will be a JOptionPane that file path is invalid and try again. The seller(tutor) can return back to playSellerMenuGUI method via the “Back” button.

*Store.java*

The class helps create store objects. Sellers (tutors) manage stores that are like different folders/directories. Every store has a name, it can be based on majors (like mathematics, computer science), classes (like calculus, linear algebra, CS 180), or others. Store has seller, isOpen, openingTimes, closingTimes, capacities, locations, uniqueCustomers, approvedRequest, and waitingRequest as field variables. importFromCsv in the file can read data from store files. makeCsvFromTxt method can read a txt file and make corresponding CSV file. There are other methods for file making, import, and export. mostPopularAp is to calculate the most popular appointment(Session) in the Store.

*Seller.java*

The class file helps create seller (tutor) objects. The file can also make a tutor file named after the tutor's name. If the tutor.file exists, the class also can remake it. A seller object will store stores owned by the tutor in a Store array, which sellerOwnedStores can show. createStoreStatisticsToStringArray can return the most popular days of the week by store. createStortedSellerStatisticsToString method can generate statistics that are sorted.

*Session.java*

The class helped create Session objects with hour, day, month, year, store, enrolledCustomers, waitingCustomers as field variables. Sessions are items in Store, which are appointments.

*SellerIntegerPacket.java*

Used to send a seller object and an Integer between the client and server.

*ServerMethods.java*

This class represents the methods that need to be done on various object to extract useful data to show to the client. searchForValidLogin method is to match .txt files named after username. createNewAccount assist to create new account. allExistingStores will read AllStores.txt and return a data array. RequestAppointment method is called on the server side when the user, a customer (student), presses the confirm button on the request appointment GUI. After the confirm button has been pressed, a JOptionPane with the appropriate error/success message will appear. Also, declineAppointmentAtTime, approveAppointmentAtTime, cancelAppointmentAtTime can deny, approve, andcancel appointments, respectively. customerCancelAppointmentAtIndex is another method to cancel at the index apoointmentIndex for a certain customer. checkIfDateIsFuture method will return true if the date is future. checkIfDateIsValid method will check the input format of the date input. Other methods also help us to check format, for example, return true if there is an expected input. The file is to support Server.java.

*Server.java*

This class is the server, takes object packets in from the client, calls the correct method in ServerMethods, and sends back a packet. Server class will user ServerMethods.

*SellerStorePacket.java*

Used to send a seller object and an Store between the client and server.

*StartClientGui.java*

Users will start program from here after they launched Server.java
