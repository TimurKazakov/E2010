import java.io.File;

public class Action implements Runnable {
File inputFile;
    public Action(File inputFile ) {
        this.inputFile = inputFile;
        run();
    }

//    public static void main(String[] args) {
//
//Graph myGrap = new Graph(new FileDataToIntHex());
////myGrap.ShowGrap();
//myGrap.run();
//
//    }


    @Override
    public void run() {
        Graph myGrap = new Graph(new FileDataToIntHex(this.inputFile));
//myGrap.ShowGrap();
        myGrap.run();
    }
}
