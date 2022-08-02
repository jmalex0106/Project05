# Project05

**Instructions on how to compile and run your project**

Take the all of the classes within our compositor and all of the .txt files to run and compile the program. If you decide to make a new .txt file to upload, you have to make sure it is in the specific format that our program takes. 

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


**Class Description**

Customer.java 
The class helped us create customer objects and remake Customer file, which includes customer data like the approved list and the waiting list of appointments. It also can let the customer make an appointment request and cancel the request, which removes the customer from the waiting list or approved list. Besides, there is a function named approveAppointmentAtTime, which is not for the customer but for the seller.

CustomerCancelAppointmentGUI.java

The class is a graphical user interface that helps customers cancel appointments that exist on approved lists or waiting lists. It will show a JOptionPane message if canceled successfully. CustomerMenuGUI.java The class is a graphical user interface after the customer logs in. Customers can export appointments to the CSV file, view sorted and unsorted statistics data shown on a JOptionPane, and cancel an appointment. Request a new appointment, return to the main menu, and exit from the program.

CustomerSelectStoreGUI.java

The class is a graphical user interface helping customers request a new appointment. The customer will choose a store and then jump to NewAppointmentRequest.java (A GUI file). Customers can also export appointment data as a CSV file, giving a JOptionPane to message customers if the export is successfully executed. Customers also can cancel existing appointments (on the waiting list or approved list) by jumping to cancel appointments CustomerCancelAppointmentGUI. It also implements sorted statistics and unsorted statistics

 MainMenuGUI.java
 
The class is a graphical user interface letting users log in or sign in. If the user has existed, the user needs to enter his or her username and password and click log in. The program will automatically identify if a user is a student or a tutor. Then, the user will go to the responding menu GUI(customer/seller menu GUI). If the user wants to sign in, he or she needs to enter the email and click sign in; then, there is a JOptionPane letting the select user status if he or she wants to create a student account or a tutor account. The “Mystery Option” is a fun bonus, connecting a YouTube video link.

NewAppointmentRequest.java

Customers will operate on the GUI when they request a new appointment. When customers choose new appointment requests and select the store they want to request, they will jump to the GUI. In the GUI, they will enter a year, month, day, and hours.

Seller.java

The class file helps create seller (tutor) objects. The file can also make a tutor file named after the tutor's name. If the tutor.file exists, the class also can remake it. A seller object will store stores owned by the tutor in a Store array, which sellerOwnedStores can show. createStoreStatisticsToStringArray can return the most popular days of the week by store. createStortedSellerStatisticsToString method can generate statistics that are sorted. 

SellerCancelAppointmentGUI.java

The class is a graphical user interface helping sellers (tutors) cancel appointments. There is a dropbox including existing appointments. Tutors can cancel appointment by selecting a appointment and clicking confirm. The seller can go back to the previous graphical user interface by clicking the back button.

SellerMenuGUI.java

The class is a graphical user interface after sellers (tutors) login. In the GUI, tutors can view and select stores in a dropdown, then jump to SellerStoreGUI.java. In the GUI, tutors can also view statistics data (sorted and unsorted). Tutors can open a new store via the “Open new Store” button.

SellerStoreGUI.java

When tutors select store, they will jump to the GUI. In the GUI, tutors can select an appointment in a dropdown, including the waiting list and approved list. After clicking confirm, the tutor will jump to a Session GUI. Tutors also can go back to SellerMenuGUI via the “Back to Tutor” button and exit via the “Exit” button.

Session.java

The class helped create Session objects with hour, day, month, year, store, enrolledCustomers, waitingCustomers as field variables. Sessions are items in Store, which are appointments.

SessionGUI.java

This GUI opens from SellerStoreGUI after the user has selected a session(appointment). In the GUI, a tutor (a seller) can view the waiting list and approved list. If the tutor wants to move a student (a customer) from the waiting list to the approved list, he or she can select the customer(if the customer in the waiting list) and click the “Approve” button. If the tutor wants to deny the student, he or she can select the student and click the “Deny” button. Also, the “Reschedule” button can open RescheduleGUI. The “Back” button can return to SellerStoreGUI.


Store.java

The class helps create store objects. Sellers (tutors) manage stores that are like different folders/directories. Every store has a name, it can be based on majors (like mathematics, computer science), classes (like calculus, linear algebra, CS 180), or others. Store has seller, isOpen, openingTimes, closingTimes, capacities, locations, uniqueCustomers, approvedRequest, and waitingRequest as field variables. importFromCsv in the file can read data from store files. makeCsvFromTxt method can read a txt file and make corresponding CSV file. There are other methods for file making, import, and export. mostPopularAp is to calculate the most popular appointment(Session) in the Store.


TestServer.java

This is the server class file. This class represents the methods that need to be done on various object to extract useful data to show to the client. searchForValidLogin method is to match .txt files named after username. createNewAccount assist to create new account. allExistingStores will read AllStores.txt and return a data array. RequestAppointment method is called on the server side when the user, a customer (student), presses the confirm button on the request appointment GUI. After the confirm button has been pressed, a JOptionPane with the appropriate error/success message will appear. Also, declineAppointmentAtTime, approveAppointmentAtTime, andcancelAppointmentAtTime can deny, approve, andcancel appointments, respectively. customerCancelAppointmentAtIndex is another method to cancel at the index apoointmentIndex for a certain customer. checkIfDateIsFuture methods will return true if the date is future. checkIfDateIsValid method will check the input format of the date input. Other methods also help us to check format, return true if there is an expected input.



