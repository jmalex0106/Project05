import com.sun.source.doctree.SeeTree;

public class TestMain {
    public static void main(String[] args) {
        Seller a = new Seller("Aidan");
        a.makeFileFromSeller();
        Store s = new Store("aidanMath" , "Aidan");
        a.getStores().add(s);
        a.makeFileFromSeller();
        s.makeFileFromStore();
    }
}