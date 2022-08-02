public class TestMain {
    public static void main(String[] args) {
        Seller aidan = new Seller("Aidan");
        String csv = "C:\\Users\\home computer\\Downloads\\Sample Store Input CSV - Sheet1 (1).csv";
        Store store = new Store("aidanMath" , "Aidan");
        store.remakeStoreFromFile();
    }
}
