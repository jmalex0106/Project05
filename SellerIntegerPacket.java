import java.io.Serializable;

/**
 * Used to send a seller object and an Integer between the client and server.
 *
 * @author Aidan Davis, lab 03, group 08
 *
 * @version 8/2/2022
 */
public class SellerIntegerPacket implements Serializable {
    private Seller seller;
    private int integer;
    public SellerIntegerPacket (Seller seller , int integer) {
        this.seller = seller;
        this.integer = integer;
    }

    public int getInteger() {
        return integer;
    }

    public Seller getSeller() {
        return seller;
    }
}
