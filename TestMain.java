import com.sun.source.doctree.SeeTree;

public class TestMain {
    public static void main(String[] args) {
        Seller a = new Seller("Aidan");
        a.remakeSellerFromFile();
        System.out.println(a.getStores().size());
    }
}