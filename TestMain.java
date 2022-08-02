import java.util.Arrays;

/**
 * TEST CLASS DO NOT TURN IN
 */
public class TestMain {
    public static void main(String[] args) {
        ServerMethods serverMethods = new ServerMethods();
        Seller aidan = new Seller("Aidan");
        String csv = "C:\\Users\\home computer\\Downloads\\Sample Store Input CSV - Sheet1 (1).csv";
        Store store = new Store("aidanMath" , "Aidan");
        for (int i = 0; i < serverMethods.csvPathToString(csv).length; i++) {
            for (int j = 0; j < serverMethods.csvPathToString(csv)[i].length; j++) {
                System.out.print(serverMethods.csvPathToString(csv)[i][j] + " ");
            }
            System.out.print("\n");
        }
        System.out.println("MAKE IS OPEN");
        for (int i = 0; i < serverMethods.makeIsOpenFromCSV(csv).length; i++) {
            System.out.println(serverMethods.makeIsOpenFromCSV(csv)[i]);
        }
        serverMethods.setupNewStore(aidan , "aidanMath" , csv);
    }
}
