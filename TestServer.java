import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

/**
 * This class is the server, takes object packets in from the client, calls the correct method
 * in ServerMethods, and sends back a packet.
 *
 * @author Junmo Kim, Aidan Davis, Moxiao Li
 *
 * @version 8/2/2022
 */
public class TestServer {
    ServerSocket serverSocket;
    Socket socket;
    List<Thread> threads;
    ServerMethods serverMethods;

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

    public static void main(String[] args) {
        TestServer testServer = new TestServer();
        testServer.openServer();
    }


    class ServerThread extends Thread {
        TestServer myServer;


        public ServerThread(TestServer testServer, Socket socket) {
            myServer = testServer;
            System.out.println(socket.getInetAddress() + "connected..."); //client IP address
        }

        public void run() {
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                while (true) {
                    Object object = objectInputStream.readObject();
                    if (object instanceof String[]) {
                        String[] array = (String[]) object;
                        System.out.println("TAG" + array[0]);
                        if (array.length == 3 && array[0].equals("loginCredentials")) {
                            objectOutputStream.writeObject(serverMethods.searchForValidLogin(array[1], array[2]));
                            System.out.println("SENDING");
                        } else if (array.length == 2 && array[0].equals("requestSeller")) {
                            Seller seller = new Seller(array[1]);
                            System.out.println("TEST1");
                            seller.remakeSellerFromFile();
                            System.out.println("TEST2");
                            objectOutputStream.writeObject(seller);
                            objectOutputStream.flush();
                        } else if (array.length == 2 && array[0].equals("requestCustomer")) {
                            Customer customer = new Customer(array[1]);
                            customer.remakeCustomerFromFile();
                            System.out.println("T 0");
                            objectOutputStream.writeObject(customer);
                            System.out.println("T 1");
                            objectOutputStream.flush();
                            System.out.println("T 2");
                        } else if (array.length == 3 && array[0].equals("sellerRequestStatistics")) {
                            if (array[1].equals("View sorted statistics")) {
                                Seller seller = new Seller(array[2]);
                                seller.remakeSellerFromFile();
                                String statistics = serverMethods.showSellerSortedStatistics(seller);
                                objectOutputStream.writeObject(statistics);
                                objectOutputStream.flush();
                            } else if (array[1].equals("View unsorted statistics")) {
                                Seller seller = new Seller(array[2]);
                                seller.remakeSellerFromFile();
                                String statistics = serverMethods.showSellerUnsortedStatistics(seller);
                                objectOutputStream.writeObject(statistics);
                                objectOutputStream.flush();
                            } else {
                                String statistics = "an error has occurred";
                                objectOutputStream.writeObject(statistics);
                                objectOutputStream.flush();
                            }
                        } else if (array.length == 5 && array[0].equals("newAccountCreation")) {
                            String[] newAccount = (String[]) object;
                            boolean isTutor = Boolean.valueOf(newAccount[1]);
                            Boolean newAccountBool = serverMethods.createNewAccount(
                                            isTutor,
                                            newAccount[2],
                                            newAccount[3],
                                            newAccount[4]);
                            objectOutputStream.writeObject(newAccountBool);
                        } else if (array.length == 1 &&
                                array[0].equals("customerRequestSortedStats")) {
                            String sortedStats = serverMethods.showSortedStatisticsToCustomer();
                            objectOutputStream.writeObject(sortedStats);
                            objectOutputStream.flush();
                        } else if (array.length == 1 &&
                                array[0].equals("customerRequestUnsortedStats")) {
                            String unsortedStats = serverMethods.showUnsortedStatisticsToCustomer();
                            objectOutputStream.writeObject(unsortedStats);
                            objectOutputStream.flush();
                        } else if (array.length == 1 && array[0].equals("requestAllStoreNames")) {
                            String[] allStoreNames = serverMethods.allStoreNames();
                            objectOutputStream.writeObject(allStoreNames);
                            objectOutputStream.flush();
                        } else if (array.length == 2 && array[0].equals("customerRequestStore")) {
                            Store store = serverMethods.getStoreWithName(array[1]);
                            objectOutputStream.writeObject(store);
                            objectOutputStream.flush();
                        }
                    } else if (object instanceof FileStringPacket) {
                        FileStringPacket fileStringPacket = (FileStringPacket) object;
                        File file = fileStringPacket.getFile();
                        Seller seller = fileStringPacket.getSeller();
                        String[] tags = fileStringPacket.getStrings();
                        seller.makeFileFromSeller();
                        Store store = new Store(tags[0], seller.getName());
                        int openedNewStore =
                                serverMethods.setupNewStoreFromFile(seller, tags[0], file);
                        if (openedNewStore == 0) {
                            store.remakeStoreFromFile();
                            store.makeFileFromStore();
                        }
                        SellerIntegerPacket sellerIntegerPacket =
                                new SellerIntegerPacket(seller, openedNewStore);
                        objectOutputStream.writeObject(sellerIntegerPacket);
                        objectOutputStream.flush();
                    } else if (object instanceof CustomerStringPacket) {
                        //giving customer their exported csv file
                        CustomerStringPacket customerStringPacket =
                                (CustomerStringPacket) object;
                        if (customerStringPacket.getStrings()[0].equals("customerRequestCSV")) {
                            Customer customer = customerStringPacket.getCustomer();
                            File file = serverMethods.exportCustomerAppointmentsToCSVFile(customer);
                            objectOutputStream.writeObject(file);
                            objectOutputStream.flush();
                        } else if (customerStringPacket.getStrings()[0].equals(
                                "customerCancelAppointment")) {
                            int index = Integer.parseInt(customerStringPacket.getStrings()[1]);
                            serverMethods.customerCancelAppointmentAtIndex(
                                    customerStringPacket.getCustomer(), index);
                        } else if (customerStringPacket.getStrings()[0].equals(
                                "customerRequestAppointment")) {
                            Integer success = serverMethods.requestAppointment(
                                    customerStringPacket.getCustomer(),
                                    customerStringPacket.getStrings()[5],
                                    customerStringPacket.getStrings()[1],
                                    customerStringPacket.getStrings()[2],
                                    customerStringPacket.getStrings()[3],
                                    customerStringPacket.getStrings()[4]);
                            objectOutputStream.writeObject(success);
                            objectOutputStream.flush();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}