/**
 * Used to send a customer and a string together between client and server
 *
 * @author Aidan Davis
 * @version 8/2/2022
 */
public class CustomerStringPacket {
    private Customer customer;
    private String[] strings;

    public CustomerStringPacket(Customer customer, String[] strings) {
        this.customer = customer;
        this.strings = strings;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String[] getStrings() {
        return strings;
    }
}
