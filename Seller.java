import java.util.ArrayList;
import java.io.*;

public class Seller extends User {
    private ArrayList<Store> stores;

    public Seller(String name, String email) {
        super(name, email);
        stores = new ArrayList<>();
    }

    public ArrayList<Store> getStores() {
        return stores;
    }


    public void makeFileFromSeller() {
        try {
            File file = new File(getName() + ".txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            PrintWriter printWriter = new PrintWriter(fileOutputStream);
            String add = "";
            for (int i = 0; i < stores.size(); i++) {
                add += stores.get(i).getName();
                add += "\n";
            }
            add = add.trim();
            printWriter.println(add);
            printWriter.close();
        } catch (Exception exception) {
            System.out.println("An error has occurred");
        }
    }

    public void remakeSellerFromFile() {
        File f = new File(getName() + ".txt");
        try {
            FileReader fr = new FileReader(f);
            BufferedReader bfr = new BufferedReader(fr);
            ArrayList<String> list = new ArrayList<String>();
            String line = bfr.readLine();
            while (line != null) {
                list.add(line);
                line = bfr.readLine();
            }
            bfr.close();

            for (int i = 0; i < list.size(); i++) {
                Store store = new Store(list.get(i), getName());
                store.remakeStoreFromFile();
                stores.add(store);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns a string arraylist that contains the names of all the stores that a certain tutor owns.
     * This method reads from AllStores.txt. It assumes that AllStores.txt exists.
     */
    public ArrayList<String> sellerOwnedStores(Seller seller) {
        ArrayList<String> output = new ArrayList<>();
        try {
            File file = new File("AllStores.txt");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                if (line.split(",")[1].equals(seller.getName())) {    //checks seller's name
                    output.add(line.split(",")[0]);   //adds the store name.
                }
                line = bufferedReader.readLine();
            }
        } catch (Exception exception){
            //Exception handling
        }
        return output;
    }
}

