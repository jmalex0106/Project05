import java.io.File;
import java.io.Serializable;

public class FileStringPacket implements Serializable {
    private File file;
    private String[] strings;
    private Seller seller;
    public FileStringPacket(File file , String[] strings , Seller seller) {
        this.file = file;
        this.strings = strings;
        this.seller = seller;
    }

    public File getFile() {
        return file;
    }

    public String[] getStrings() {
        return strings;
    }

    public Seller getSeller() {
        return seller;
    }
}
