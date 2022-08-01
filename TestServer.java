import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class TestServer {
    ServerSocket serverSocket;
    Socket socket;
    List<Thread> threads;
    ServerMethods serverMethods;
    BufferedReader messageIn;
    BufferedWriter messageOut;

    public TestServer() {
        threads = new ArrayList<Thread>();
        System.out.println("Server Started...");
        System.out.println("Awaiting clients to connect...");
        serverMethods = new ServerMethods();
    }

    public void openServer() {
        try {
            serverSocket = new ServerSocket(1234);
            serverSocket.setReuseAddress(true);
            while (true) {
                socket = serverSocket.accept();
                ServerThread thread = new ServerThread(this, socket);
                thread.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private synchronized void connect(ServerThread thread) {
        threads.add(thread);
        System.out.println("1 client connected...");
        System.out.println("Total Clients: " + threads.size());
    }

    private synchronized void disconnect(ServerThread thread) {
        threads.remove(thread);
        System.out.println("1 client disconnected...");
        System.out.println("Total Clients: " + threads.size());
    }

    public static void send(Socket socket, Object obj) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(obj);
        oos.flush();
    }

    public static String receive(Socket socket) throws IOException {
        BufferedReader bfr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String receivedMsg = bfr.readLine();
        return receivedMsg;
    }



    class ServerThread extends Thread {
        TestServer myServer;


        public ServerThread(TestServer testServer, Socket socket) {
            myServer = testServer;
            System.out.println(socket.getInetAddress() + "connected..."); //client IP address
        }

        public void run() {
            try {
                messageIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                messageOut = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                String input = messageIn.readLine();

                /** TODO
                 * true needs to change when create button clicked
                 */
                if (true) {
                    messageOut.write("Student or Tutor?");
                    if (input.equalsIgnoreCase("student")) {
                        String userName = messageIn.readLine();
                        String email = messageIn.readLine();
                        String password = messageIn.readLine();
                        serverMethods.createNewAccount(false, userName, email, password);
                    } else if (input.equalsIgnoreCase("tutor")) {
                        String userName = messageIn.readLine();
                        String email = messageIn.readLine();
                        String password = messageIn.readLine();
                        serverMethods.createNewAccount(true, userName, email, password);
                    }

                }

                /** TODO
                 * checking password
                 * need to change the input, input when mixed with GUI
                 * messageOut needs to change to JOptionPane Error
                 */

                if (serverMethods.searchForValidLogin(input, input) == 1) {
                    // TODO: go to Tutor GUI
                    ArrayList<String> allStores = serverMethods.allExistingStores();
                    ArrayList<String> tutorStoreList = new ArrayList<>();
                    // TODO: get tutor name
                    String tutorName = "asdf";

                    for (int i = 0; i < allStores.size(); i++) {
                        if(allStores.get(i).substring(0, tutorName.length()).equals(tutorName)) {
                            tutorStoreList.add(allStores.get(i));
                        }
                    }
                    String[] tutorStoreArr = tutorStoreList.toArray(new String[tutorStoreList.size()]);
                    // TODO: add tutorStoreArr to comboBox
                    // ex) viewStore = new JComboBox(tutorStoreArr);
                    if (true) { // TODO: when Confirmed was clicked for viewStore
                        // TODO: get confirmed data
                        // ex) String storeName = viewStore.getSelectedItem().toString();
                        // TODO: go to Tutoring Service GUI
                        String storeName = "sadf";
                        Store store = new Store(storeName, tutorName);
                        store.remakeStoreFromFile();
                        ArrayList<Session> sessions = store.getSessions();
                        ArrayList<Session> mySessions = new ArrayList<Session>();
                        LocalDate currentDate = LocalDate.now();
                        int currentYear = currentDate.getYear();
                        int currentMonth = currentDate.getMonthValue();
                        int currentDay = currentDate.getDayOfMonth();
                        //TODO: need to tweak this if statement a bit
                        for (int i = 0; i < sessions.size(); i++) {
                            if (sessions.get(i).getYear() == currentYear &&
                                    sessions.get(i).getMonth() == currentMonth &&
                                    sessions.get(i).getDay() >= currentDay &&
                                    sessions.get(i).getEnrolledCustomers().size() != 0) {
                                mySessions.add(sessions.get(i));
                            }
                        }
                        ArrayList<String> appointment = new ArrayList<>();
                        for (int i = 0; i < mySessions.size(); i++) {
                            String year = String.valueOf(mySessions.get(i).getYear());
                            String month = String.valueOf(mySessions.get(i).getMonth());
                            String day = String.valueOf(mySessions.get(i).getDay());
                            String hour = String.valueOf(mySessions.get(i).getHour());
                            String location = store.getLocations()[i];
                            appointment.add(year + "." + month + "." + day + "." + hour + location);
                        }
                        String[] appointmentArr = appointment.toArray(new String[appointment.size()]);
                        // TODO: add appointment to comboBox
                        // ex) selectAppointment = new JComboBox(appointmentArr);
                        // TODO: when confirmed is clicked, go to Session GUI

                        ArrayList<ArrayList<String>> waitList = new ArrayList<>();
                        for (int i = 0; i < mySessions.size(); i++) {
                            waitList.add(mySessions.get(i).getWaitingCustomers());
                        }

                        String[] waitStr = waitList.toArray(new String[waitList.size()]);
                        // TODO: add waitStr to comboBox
                        // ex) viewWaitList = new JComboBox(waitStr);

                        ArrayList<ArrayList<String>> approveList = new ArrayList<>();
                        for (int i = 0; i < mySessions.size(); i++) {
                            approveList.add(mySessions.get(i).getEnrolledCustomers());
                        }
                        String[] approveStr = waitList.toArray(new String[approveList.size()]);
                        // TODO: if confirmed (approve)
                        if(true) {
                            //serverMethods.approveAppointmentAtTime();
                        }
                        // TODO: add approveStr to comboBox
                        // ex) viewWaitApprovedList = new JComboBox(approveStr);
                        // TODO: if confirmed (decline)
                        if(true) {
                            //serverMethods.cancelAppointmentAtTime();
                        }
                        // TODO: message disproved


                    } else if (true) { // TODO: when Confirmed was clicked for viewSeats
                        // TODO: go to Tutoring Service GUI
                    } else if (true) { // TODO: when Confirmed was clicked for openNewStore
                        // TODO: go to csv import
                    }

                } else  if (serverMethods.searchForValidLogin(input, input) == 2) {
                    // TODO: go to Student GUI
                } else {
                    // TODO: JOPTION Error
                    messageOut.write("Invalid Username or Password!");
                }

            } catch (IOException e) {
                myServer.disconnect(this);
                System.out.println(super.getName() + "disconnected...");
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}