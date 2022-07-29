import java.io.*;
import java.util.ArrayList;
import java.util.Locale;

/**
 * TODO ADD DESCRIPTIVE JAVADOCS
 *
 * @author Moxiao Li, Junmo Kim, Aidan Davis Lab 03 Group 08
 * @version date
 */

public class Customer {
    private String name;
    ArrayList<Session> waitingRequest;
    ArrayList<Session> approvedRequest;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Session> getWaitingRequest() {
        return waitingRequest;
    }

    public ArrayList<Session> getApprovedRequest() {
        return approvedRequest;
    }

    public boolean remakeCustomerFromFile() {
        try {
            File file = new File(getName() + ".txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            String remakeName = file.getName();
            this.name = remakeName.replace(".txt", "");
            line = bufferedReader.readLine();
            while (line != null) {
                if (line.split(",").length == 6) {
                    Session session = new Session(Integer.parseInt(line.split(",")[1]),
                            Integer.parseInt(line.split(",")[2]),
                            Integer.parseInt(line.split(",")[3]),
                            Integer.parseInt(line.split(",")[4]),
                            line.split(",")[5].trim());
                    if (line.split(",")[0].equals("approved")) {
                        approvedRequest.add(session);
                    } else if (line.split(",")[0].equals("waiting")) {
                        waitingRequest.add(session);
                    }
                }
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public Customer(String name) {
        this.name = name;
        waitingRequest = new ArrayList<>();
        approvedRequest = new ArrayList<>();
    }

    // Customers can make or cancel appointment requests.
    public void requestAppointment(Session session) {
        waitingRequest.add(session);
    }

    public void cancelAppointment(Session session) {
        if (waitingRequest.contains(session)) {
            waitingRequest.remove(session);
        } else if (approvedRequest.contains(session)) {
            approvedRequest.remove(session);
        } else {
        }
    }

    public boolean remakeFileFromCustomer() {
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
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public void removeFromWaitlistAtTime(int year, int month, int day, int hour, String storeName) {
        for (Session session : waitingRequest) {
            if (session.getYear() == year &&
                    session.getMonth() == month &&
                    session.getDay() == day &&
                    session.getHour() == hour &&
                    session.getStore().equals(storeName)) {
                waitingRequest.remove(session);
            }
        }
    }

    public void removeFromApprovedlistAtTime(int year, int month, int day, int hour, String storeName) {
        for (Session session : approvedRequest) {
            if (session.getYear() == year &&
                    session.getMonth() == month &&
                    session.getDay() == day &&
                    session.getHour() == hour &&
                    session.getStore().equals(storeName)) {
                approvedRequest.remove(session);
            }
        }
    }

    public void approveAppointmentAtTime(int year, int month, int day, int hour,
                                         String storeName) {
        try {
            for (Session session : waitingRequest) {
                if (session.getYear() == year &&
                        session.getMonth() == month &&
                        session.getDay() == day &&
                        session.getHour() == hour &&
                        session.getStore().equals(storeName)) {
                    waitingRequest.remove(session);
                    approvedRequest.add(session);
                }
            }
        } catch (Exception exception) {
            return;
        }
    }
}
