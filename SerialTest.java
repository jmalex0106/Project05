public class SerialTest {
    public static void main(String[] args) {
        Store a = new Store("aidanMath" , "Aidan");
        Seller aidan = new Seller("Aidan");
        a.remakeStoreFromFile();
        System.out.println(a.getSessions().size());
        System.out.println(a.getSessions().get(0).getWaitingCustomers().size());
        System.out.println(a.getSessions().get(0).getWaitingCustomers().get(0));
        System.out.println(a.getSessions().get(0).getHour());
        a.makeFileFromStore();
        aidan.getStores().add(a);
        aidan.makeFileFromSeller();
        aidan.remakeSellerFromFile();
        System.out.println("RUNN");
        System.out.println(aidan.getStores().size());
    }
}
