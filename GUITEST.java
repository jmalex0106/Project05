public class GUITEST {
    public GUITEST() {
    }

    public void SellerMenuTest() {
        Seller seller = new Seller("Bob");
        Store store = new Store("bobMath" , "Bob");
        Store store1 = new Store("bobReading" ,"Bob");
        store.makeFileFromStore();
        store1.makeFileFromStore();
        seller.makeFileFromSeller();
        seller.getStores().add(store);
        seller.getStores().add(store1);
        Session session = new Session(12 , 15 , 8 , 2022, store.getName());
        store.getSessions().add(session);
        SellerMenuGUI sellerMenuGUI = new SellerMenuGUI(seller);
        sellerMenuGUI.playGUI();
    }

    public void CustomerMenuGUITest() {
        Customer customer = new Customer("Tom");
        new CustomerMenuGUI(customer).playGUI();
    }

    public void NewAppointmentRequestTest() {
        Seller seller = new Seller("Bob");
        Store store = new Store("bobMath" , "Bob");
        Store store1 = new Store("bobReading" ,"Bob");
        store.makeFileFromStore();
        store1.makeFileFromStore();
        seller.makeFileFromSeller();
        seller.getStores().add(store);
        seller.getStores().add(store1);
        Customer customer = new Customer("Tom");
        new NewAppointmentRequest(customer , store).playGUI();
    }

    public void CustomerSelectStoreTest() {
        Customer customer = new Customer("Tom");
        new CustomerSelectStoreGUI(customer).playGUI();
    }

    public void MainMenuTest() {
        new MainMenuGUI().playGUI();
    }
    public static void main(String[] args) {
        new GUITEST().MainMenuTest();
    }
}
