public class TestMain {
    public static void main(String[] args) {
        Customer customer = new Customer("bob");
        Session session = new Session(1,1,1,1,"STORE");
        customer.getWaitingRequest().add(session);
        customer.remakeFileFromCustomer();
    }
}