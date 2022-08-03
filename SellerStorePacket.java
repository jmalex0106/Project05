import java.io.Serializable;

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
