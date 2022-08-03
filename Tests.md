Note: These tests are to be performed in order, starting with 
Test 1 being done on a fresh app with no accounts made yet. 
Test 1:
1. Login GUI appears
2. Grader presses mystery option
3. Entertaining video plays from Youtube (assuming user's laptop
can run Youtube) and convinces grader to give us an A. (Please)
4. Process finishes and Login GUI closes when grader goes back to 
the login GUI

Test 2:
1. Login GUI appears
2. Grader enters "bobStore" into the username box and "123456" into 
the password box and "bob@gmail.com" into the email box. 
3. Grader presses create account button
4. JOption appears with buttons "Student" and "Tutor". Grader presses
either button.
5. JOption appears stating that the entered credentials are
invalid
6. Grader exits this JOption pane.
7. A fresh Login GUI appears. 

Test 3:
1. Login GUI appears
2. Grader enters "bob" into the username box and "123" into
   the password box and "bob@gmail.com" into the email box.
3. Grader presses create account button
4. JOption appears with buttons "Student" and "Tutor". Grader presses
5. JOption appears stating that the entered credentials are
   invalid
6. Grader exits this JOption pane.
7. A fresh Login GUI appears.

Test 4:
1. Login GUI appears
2. Grader enters "bob" into the username box and "123456" into
   the password box and "bobgmailcom" into the email box.
3. Grader presses create account button
4. JOption appears with buttons "Student" and "Tutor". Grader presses
   either button
5. JOption appears stating that the entered credentials are
   invalid
6. Grader exits this JOption pane.
7. A fresh Login GUI appears.

Test 5:
1. Login GUI appears
2. Grader enters "bob" into the username box and "123456" into
   the password box and "bob@gmail.com" into the email box.
3. Grader presses create account button
4. JOption appears with buttons "Student" and "Tutor". Grader presses
   "Tutor"
5. JOption appears stating that a new account has been created for
bob. 
6. Grader exits this JOption pane.
7. A fresh Login GUI appears.

Test 6:
1. Login GUI appears
2. Grader enters "bob" into the username box and "123456" into
   the password box and "bob@gmail.com" into the email box.
3. Grader presses create account button
4. JOption appears with buttons "Student" and "Tutor". Grader presses
   "Tutor"
5. JOption appears stating that the entered credentials are
   invalid
6. Grader exits this JOption pane.
7. A fresh Login GUI appears.

Test 6:
1. Login GUI appears
2. Grader enters "bob" into the username box and "123456" into
   the password box and "bob@gmail.com" into the email box.
3. Grader presses login button. 
4. The login GUI disappears
5. A seller GUI with a welcome message for bob at the top appears. 
6. Grader presses the "Main Menu" button
7. Seller GUI for bob disappears
8. A fresh login GUI appears
9. Grader logs back into bob's account with the credentials "bob" and
"123456". Email is only used for creating new accounts, not to log in, 
so this field can be left blank. 
10. Login GUI disappears
11. Seller GUI for bob appears with welcome message for bob at the top. 
12. Grader exits the program.

Test 7:
1. Grader logs in to bob's account with credentials "bob" and "123456"
from the login GUI
2. Seller GUI for bob appears
3. The grader presses open new store
4. The grader puts "bobMath" into the store name text field
5. The grader puts the path to a csv file containing the contents below
into the CSV file path text field:
   sun,true,11,15,2,lawson
   mon,true,11,16,2,lawson
   tues,false,false
   wedn,true,10,14,3,walc
   thur,false,false
   fri,false,false
6. Grader presses confirm button, and a JOption pane appears to tell
the grader that the store bobMath has been opened successfully
7. Grader exits the program. 

Test 8:
1. Grader repeats test 7 exactly. This time, the JOption pane should
tell the grader that this store name is taken. 

Test 9:
1. Grader puts "Naveen" , "nav123" , "naveen@gmail.com" into the 
username , password , and email fields of the login GUI and presses
the create account button.
2. JOption pane appears to prompt the grader to select tutor or 
student. 
3. Grader selects student
4. JOption pane appears to notify the grader that the account Naveen
has been created successfully. 
5. A fresh login GUI appears
6. The grader logs in to Naveen's account using the credentials
"Naveen" and "nav123". These are case sensitive. Email is not needed. 
7. Grader presses request new appointment button
8. Grader selects the store bobMath from the dropdown and 
presses confirm 
9. grader enters a tomorrow's date and the number 12 in the hour field
into the year , month , day, and hour text fields. 
10. Grader presses confirm
11. A JOPtion pane appears to tell the grader that they have been 
placed on the waitlist sucessfully. 
12. Grader exits the program

Test 10:
1. Grader logs in to Bob's account. 
2. Grader selects the store bob math from the dropdown and presses 
the confirm button in the top right.
3. A new GUI appears. There should be a dropdown full of session 
times in the top left corner. Since only one appointment has been 
requested, there will only be one session to choose. The grader 
selects this session. 
4. Grader presses confirm
5. Grader selects Naveen from the dropdown by "view waiting list". 
This is the list of people waiting to get into the session.
6. Grader presses approve button.
   (We have been stuck on this button for a while. Currently ,
it seems to be opening a brand new store object with the same name
as the seller. Then this will be the next store that opens. Despite
Junmo and I's best debugging efforts , we cannot figure out why it
does this. )