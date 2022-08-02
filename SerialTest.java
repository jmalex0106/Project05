public class SerialTest {
    public static void main(String[] args) {
        Store a = new Store("aidanMath" , "Aidan");
        a.remakeStoreFromFile();
        System.out.println(a.getSessions().size());
        System.out.println(a.getSessions().get(0).getWaitingCustomers().size());
        System.out.println(a.getSessions().get(0).getWaitingCustomers().get(0));
    }
}
