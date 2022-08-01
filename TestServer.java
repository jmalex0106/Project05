import java.net.ServerSocket;
import java.net.Socket;
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
                            objectOutputStream.writeObject(new ServerMethods().searchForValidLogin(array[1], array[2]));
                            System.out.println("SENDING");
                        } else if (array.length == 2 && array[0].equals("requestSeller")) {
                            Seller seller = new Seller(array[1]);
                            objectOutputStream.writeObject(seller);
                            objectOutputStream.flush();
                        } else if (array.length == 2 && array[0].equals("requestCustomer")) {
                            Customer customer = new Customer(array[1]);
                            System.out.println("T 0");
                            objectOutputStream.writeObject(customer);
                            System.out.println("T 1");
                            objectOutputStream.flush();
                            System.out.println("T 2");
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            }
        }
    }
}