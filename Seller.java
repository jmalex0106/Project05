import java.util.ArrayList;
import java.io.*;

/**
 * The seller class represents the Tutor user of this program, which is seen as an array list of
 * stores bound by a name.
 *
 * @author Moxiao Li, Junmo Kim, Aidan Davis Lab 03 Group 08
 * @version 8/2/2022
 */

public class Seller implements Serializable {
    private String name;
    private ArrayList<Store> stores;

    public Seller(String name) {
        this.name = name;
        stores = new ArrayList<>();
    }

    public synchronized String getName() {
        return name;
    }

    public synchronized void setName(String name) {
        this.name = name;
    }

    public synchronized ArrayList<Store> getStores() {
        return stores;
    }

    public synchronized void makeFileFromSeller() {
        try {
            File file = new File(name + ".txt");
            file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this);
            objectOutputStream.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public synchronized void remakeSellerFromFile() {
        try {
            File file = new File(name + ".txt");
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Object sellerObject = objectInputStream.readObject();
            if (sellerObject instanceof Seller) {
                Seller seller = (Seller) sellerObject;
                System.out.println("SELLER OBJECT");
                this.name = seller.getName();
                this.stores = seller.getStores();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Returns a string arraylist that contains the names of all the stores that a certain tutor owns.
     * This method reads from AllStores.txt. It assumes that AllStores.txt exists.
     */
    public synchronized ArrayList<String> sellerOwnedStores(Seller seller) {
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
        } catch (Exception exception) {
            //Exception handling
        }
        return output;
    }

    public synchronized String[] createStoreStatisticsToStringArray() {
        String[] output = new String[stores.size()];
        for (int i = 0; i < output.length; i++) {
            output[i] = stores.get(i).getMostPopularDaysOfWeekToString();
        }
        return output;
    }

    public synchronized String createUnsortedSellerStatisticsToString() {
        String output = "";
        String[] storeStatisticsArray = createStoreStatisticsToStringArray();
        for (int i = 0; i < storeStatisticsArray.length; i++) {
            output += storeStatisticsArray[i];
        }
        return output;
    }

    public synchronized String createSortedSellerStatisticsToString() {
        String output = "";
        //Sets int maxCustomers to the maximum number of unique customers at a store
        //that this seller owns
        int maxCustomers = 0;
        for (int i = 0; i < stores.size(); i++) {
            if (maxCustomers < stores.get(i).getUniqueCustomers().size()) {
                maxCustomers = stores.get(i).getUniqueCustomers().size();
            }
        }
        for (int i = maxCustomers; i <= 0; i--) {
            for (int j = 0; j < stores.size(); j++) {
                if (stores.get(j).getUniqueCustomers().size() == i) {
                    output += stores.get(j).createStatisticsToString();
                }
            }
        }
        return output;
    }

    public synchronized void setStores(ArrayList<Store> stores) {
        this.stores = stores;
    }

}

