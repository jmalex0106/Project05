import java.io.*;
import java.util.ArrayList;
import java.util.Locale;

/**
 * The customer class represents each customer user of this application.
 *
 * @author Moxiao Li, Junmo Kim, Aidan Davis Lab 03 Group 08
 * @version date
 */

public class Customer implements Serializable {
    private String name;
    ArrayList<Session> waitingRequest;
    ArrayList<Session> approvedRequest;

    public synchronized String getName() {
        return name;
    }

    public synchronized void setName(String name) {
        this.name = name;
    }

    public synchronized ArrayList<Session> getWaitingRequest() {
        return waitingRequest;
    }

    public synchronized ArrayList<Session> getApprovedRequest() {
        return approvedRequest;
    }

    public synchronized boolean remakeCustomerFromFile() {
        try {
            File file = new File(name + ".txt");
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Object customerObject = objectInputStream.readObject();
            if (customerObject instanceof Customer) {
                Customer customer = (Customer) customerObject;
                this.name = customer.getName();
                this.approvedRequest = customer.getApprovedRequest();
                this.waitingRequest = customer.getWaitingRequest();
            }
            objectInputStream.close();
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }

    public Customer(String name) {
        this.name = name;
        waitingRequest = new ArrayList<>();
        approvedRequest = new ArrayList<>();
    }

    // Customers can make or cancel appointment requests.
    public synchronized void requestAppointment(Session session) {
        waitingRequest.add(session);
    }

    public synchronized void cancelAppointment(Session session) {
        if (waitingRequest.contains(session)) {
            waitingRequest.remove(session);
        } else if (approvedRequest.contains(session)) {
            approvedRequest.remove(session);
        } else {
        }
    }

    public synchronized boolean remakeFileFromCustomer() {
        try {
            File file = new File(name + ".txt");
            file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this);
            objectOutputStream.close();
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            return true;
        }
    }

    public synchronized void removeFromWaitlistAtTime(int year, int month, int day, int hour, String storeName) {
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

    public synchronized void removeFromApprovedlistAtTime(int year, int month, int day, int hour, String storeName) {
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

    public synchronized void approveAppointmentAtTime(int year, int month, int day, int hour,
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
