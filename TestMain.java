public class TestMain {
    public static void main(String[] args) {
        System.out.println("Running");
        System.out.println(new ServerMethods().createNewAccount(false , "jim" ,
                "a@b.c" , "123456"));
    }
}
