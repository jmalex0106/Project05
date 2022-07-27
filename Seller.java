import java.util.ArrayList;
import java.io.*;

public class Seller {
    private String name;
    private ArrayList<Store> stores;

    public Seller(String name) {
        this.name = name;
        stores = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}

