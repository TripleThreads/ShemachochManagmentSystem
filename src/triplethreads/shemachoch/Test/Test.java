package triplethreads.shemachoch.Test;

public class Test {
    public static void main(String[] args){
        Controller.createTempFile("login.tmp","logged in as segni");
        System.out.println(Controller.readTempFiles("login.tmp"));
    }
}
