import java.io.*;
import java.util.ArrayList;
import java.util.Locale;

public class Customer extends User {
    ArrayList<Session> waitingRequest;
    ArrayList<Session> approvedRequest;

    public ArrayList<Session> getWaitingRequest() {
        return waitingRequest;
    }

    public ArrayList<Session> getApprovedRequest() {
        return approvedRequest;
    }

    public void remakeCustomerFromFile() {
        try {
            File file = new File(getName() + ".txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            String name = file.getName();
            name = name.replace(".txt", "");
            line = bufferedReader.readLine();
            while (line != null) {
                if (line.split(",").length == 6) {
                    Session session = new Session(Integer.parseInt(line.split(",")[1]),
                            Integer.parseInt(line.split(",")[2]),
                            Integer.parseInt(line.split(",")[3]),
                            Integer.parseInt(line.split(",")[4]));
                    if (line.split(",")[0].equals("approved")) {
                        approvedRequest.add(session);
                    } else if (line.split(",")[0].equals("waiting")) {
                        waitingRequest.add(session);
                    }
                }
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
        } catch (Exception exception) {
            System.out.println("An error has occurred");
        }
    }

    public void makeFileIfNotFound() {
        try {
            File file = new File(getName() + ".txt");
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception exception) {
            System.out.println("An errror has occurred");
        }
    }

    public Customer(String name, String email) {
        super(name, email);
        waitingRequest = new ArrayList<>();
        approvedRequest = new ArrayList<>();
    }

    // Customers can make or cancel appointment requests.
    public void requestAppointment(Session session) {
        waitingRequest.add(session);
        session.addToWaitingList(this);
        System.out.println("Appointment requested!");
    }

    public void cancelAppointment(Session session) {
        if (waitingRequest.contains(session)) {
            waitingRequest.remove(session);
            session.removeFromWaitingList(this);
        } else if (approvedRequest.contains(session)) {
            approvedRequest.remove(session);
            session.removeFromWaitingList(this);
        } else {
            System.out.println("The session does not exist");
        }
    }

    public void remakeFileFromCustomer() {
        try {
            File file = new File(getName() + ".txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            PrintWriter printWriter = new PrintWriter(fileOutputStream);
            String add = "";
            for (int i = 0; i < waitingRequest.size(); i++) {
                add += "waiting,";
                add += waitingRequest.get(i).getHour();
                add += ",";
                add += waitingRequest.get(i).getDay();
                add += ",";
                add += waitingRequest.get(i).getMonth();
                add += ",";
                add += waitingRequest.get(i).getYear();
                add += ",";
                add += waitingRequest.get(i).getStore();
                add += "\n";
            }
            for (int i = 0; i < approvedRequest.size(); i++) {
                add += "approved,";
                add += approvedRequest.get(i).getHour();
                add += ",";
                add += approvedRequest.get(i).getDay();
                add += ",";
                add += approvedRequest.get(i).getMonth();
                add += ",";
                add += approvedRequest.get(i).getYear();
                add += ",";
                add += approvedRequest.get(i).getStore();
                add += "\n";
            }
            add = add.trim();
            printWriter.println(add);
            printWriter.close();
        } catch (Exception exception) {
            System.out.println("An error has occurred.");
        }
    }
}
