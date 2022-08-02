public class SellerIntegerPacket {
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
