import java.io.Serializable;
/**
 * This class represents the methods that need to be done on various object to extract useful data
 * to show to the client.
 *
 * @author Moxiao Li, Junmo Kim, Aidan Davis Lab 03 Group 08
 * @version 8/2/2022
 */
public class SellerStorePacket implements Serializable {
    private final Seller seller;
    private final Store store;

    public SellerStorePacket(Seller seller, Store store) {
        this.seller = seller;
        this.store = store;
    }

    public synchronized Seller getSeller() {
        return seller;
    }

    public synchronized Store getStore() {
        return store;
    }
}
