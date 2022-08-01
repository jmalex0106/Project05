# Project05

Instructions on how to compile and run your project
Take the all of the classes within our compositor and all of the .txt files to run and compile the program. If you decide to make a new .txt file to upload, you have to make sure it is in the specific format that our program takes. 

Users.txt
Each line of users.txt corresponds to a unique user, either a tutor or a seller. 
Each line has format status,username,email,password 
Status can either be “Tutor” or “Student”. It is case sensitive. 
An example Users.txt file is:

Tutor,MoxiaoLi,moxiao@gmail.com,mo12345
Student,JunmoKim,junmo@gmail.com,junmo12345

AllStores.txt
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



