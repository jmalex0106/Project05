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
 * @version 8/2/2022
 */
public class Server {
    ServerSocket serverSocket;
    Socket socket;
    List<Thread> threads;
    ServerMethods serverMethods;

    public Server() {
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
        oos.reset();
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.openServer();
    }
    /**
     * This Class starts the serverThread
     *
     * @author Junmo Kim, Aidan Davis, Moxiao Li
     * @version 8/2/2022
     */
    class ServerThread extends Thread {
        Server myServer;


        public ServerThread(Server server, Socket socket) {
            myServer = server;
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
                        if (array.length == 3 && array[0].equals("loginCredentials")) {
                            objectOutputStream.writeObject(ServerMethods.searchForValidLogin(array[1], array[2]));
                        } else if (array.length == 2 && array[0].equals("requestSeller")) {
                            Seller seller = new Seller(array[1]);
                            seller.remakeSellerFromFile();
                            objectOutputStream.writeObject(seller);
                            objectOutputStream.reset();
                        } else if (array.length == 2 && array[0].equals("requestCustomer")) {
                            Customer customer = new Customer(array[1]);
                            customer.remakeCustomerFromFile();
                            objectOutputStream.writeObject(customer);
                            objectOutputStream.reset();
                        } else if (array.length == 3 && array[0].equals("sellerRequestStatistics")) {
                            if (array[1].equals("View sorted statistics")) {
                                Seller seller = new Seller(array[2]);
                                seller.remakeSellerFromFile();
                                String statistics = ServerMethods.showSellerSortedStatistics(seller);
                                objectOutputStream.writeObject(statistics);
                                objectOutputStream.reset();
                            } else if (array[1].equals("View unsorted statistics")) {
                                Seller seller = new Seller(array[2]);
                                seller.remakeSellerFromFile();
                                String statistics = ServerMethods.showSellerUnsortedStatistics(seller);
                                objectOutputStream.writeObject(statistics);
                                objectOutputStream.reset();
                            } else {
                                String statistics = "an error has occurred";
                                objectOutputStream.writeObject(statistics);
                                objectOutputStream.reset();
                            }
                        } else if (array.length == 5 && array[0].equals("newAccountCreation")) {
                            String[] newAccount = (String[]) object;
                            boolean isTutor = Boolean.valueOf(newAccount[1]);
                            Boolean newAccountBool = ServerMethods.createNewAccount(
                                    isTutor,
                                    newAccount[2],
                                    newAccount[3],
                                    newAccount[4]);
                            objectOutputStream.writeObject(newAccountBool);
                        } else if (array.length == 1 &&
                                array[0].equals("customerRequestSortedStats")) {
                            String sortedStats = ServerMethods.showSortedStatisticsToCustomer();
                            objectOutputStream.writeObject(sortedStats);
                            objectOutputStream.reset();
                        } else if (array.length == 1 &&
                                array[0].equals("customerRequestUnsortedStats")) {
                            String unsortedStats = ServerMethods.showUnsortedStatisticsToCustomer();
                            objectOutputStream.writeObject(unsortedStats);
                            objectOutputStream.reset();
                        } else if (array.length == 1 && array[0].equals("requestAllStoreNames")) {
                            String[] allStoreNames = ServerMethods.allStoreNames();
                            objectOutputStream.writeObject(allStoreNames);
                            objectOutputStream.reset();
                        } else if (array.length == 2 && array[0].equals("customerRequestStore")) {
                            Store store = ServerMethods.getStoreWithName(array[1]);
                            objectOutputStream.writeObject(store);
                            objectOutputStream.reset();
                        } else if (array.length == 3 && array[0].equals("sellerStorePacket")) {
                            Store store = new Store(array[1], array[2]);
                            Seller seller = new Seller(array[2]);
                            store.remakeStoreFromFile();
                            seller.remakeSellerFromFile();
                            SellerStorePacket sellerStorePacket = new SellerStorePacket(seller, store);
                            objectOutputStream.writeObject(sellerStorePacket);
                            objectOutputStream.reset();
                        }
                    } else if (object instanceof FileStringPacket) {
                        //SELLER OPENS NEW STORE
                        FileStringPacket fileStringPacket = (FileStringPacket) object;
                        File file = fileStringPacket.getFile();
                        Seller seller = fileStringPacket.getSeller();
                        String[] tags = fileStringPacket.getStrings();
                        seller.makeFileFromSeller();
                        Store store = new Store(tags[0], seller.getName());
                        int openedNewStore =
                                ServerMethods.setupNewStoreFromFile(seller, tags[0], file);
                        if (openedNewStore == 0) {
                            store.remakeStoreFromFile();
                            store.makeFileFromStore();
                        }
                        seller.getStores().add(store);
                        SellerIntegerPacket sellerIntegerPacket =
                                new SellerIntegerPacket(seller, openedNewStore);
                        sellerIntegerPacket.getSeller().makeFileFromSeller();
                        objectOutputStream.writeObject(sellerIntegerPacket);
                        objectOutputStream.reset();
                    } else if (object instanceof CustomerStringPacket) {
                        //giving customer their exported csv file
                        CustomerStringPacket customerStringPacket =
                                (CustomerStringPacket) object;
                        customerStringPacket.getCustomer().remakeFileFromCustomer();
                        if (customerStringPacket.getStrings()[0].equals("customerRequestCSV")) {
                            Customer customer = customerStringPacket.getCustomer();
                            File file = ServerMethods.exportCustomerAppointmentsToCSVFile(customer);
                            objectOutputStream.writeObject(file);
                            objectOutputStream.reset();
                        } else if (customerStringPacket.getStrings()[0].equals(
                                "customerCancelAppointment")) {
                            int index = Integer.parseInt(customerStringPacket.getStrings()[1]);
                            ServerMethods.customerCancelAppointmentAtIndex(
                                    customerStringPacket.getCustomer(), index);
                        } else if (customerStringPacket.getStrings()[0].equals(
                                "customerRequestAppointment")) {
                            Store store =
                                    ServerMethods.getStoreWithName(
                                            customerStringPacket.getStrings()[5]);
                            store.remakeStoreFromFile();
                            Integer success = ServerMethods.requestAppointment(
                                    customerStringPacket.getCustomer(),
                                    store,
                                    customerStringPacket.getStrings()[4],
                                    customerStringPacket.getStrings()[3],
                                    customerStringPacket.getStrings()[2],
                                    customerStringPacket.getStrings()[1]);
                            int year = Integer.parseInt(customerStringPacket.getStrings()[1]);
                            int month = Integer.parseInt(customerStringPacket.getStrings()[2]);
                            int day = Integer.parseInt(customerStringPacket.getStrings()[3]);
                            int hour = Integer.parseInt(customerStringPacket.getStrings()[4]);
                            objectOutputStream.writeObject(success);
                            objectOutputStream.reset();
                            if (success == 0 || success == 1) {
                                store.requestAppointmentAtTime(year, month, day, hour,
                                        customerStringPacket.getCustomer().getName());
                                store.makeFileFromStore();
                            }
                        }
                    } else if (object instanceof SellerIntegerPacket) {
                        SellerIntegerPacket sellerIntegerPacket = (SellerIntegerPacket) object;
                        if (sellerIntegerPacket.getInteger() == 50) {
                            sellerIntegerPacket.getSeller().makeFileFromSeller();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

