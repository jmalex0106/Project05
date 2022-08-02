public class SerialTest {
    public static void main(String[] args) {
        Customer bob = new Customer("bob");
        bob.remakeCustomerFromFile();
        int i = bob.getWaitingRequest().get(0).getHour();
        System.out.println(i);
    }
}
