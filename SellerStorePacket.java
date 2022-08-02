import java.io.Serializable;

public class SellerStorePacket implements Serializable {
    private Seller seller;
    private Store store;
    public SellerStorePacket(Seller seller , Store store) {
        this.seller = seller;
        this.store = store;
    }

    public Seller getSeller() {
        return seller;
    }

    public Store getStore() {
        return store;
    }
}
