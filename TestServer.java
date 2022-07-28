import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class TestServer {
    ServerSocket serverSocket;
    Socket socket;
    List<Thread> threads;

    public TestServer() {
        threads = new ArrayList<Thread>();
        System.out.println("Server Started...");
        System.out.println("Awaiting clients to connect...");
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


    class ServerThread extends Thread {
        TestServer myServer;


        public ServerThread(TestServer testServer, Socket socket) {
            myServer = testServer;
            System.out.println(socket.getInetAddress() + "connected..."); //client IP address
        }

        public void run() {
            try {
               // methods to add
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
