import java.io.File;

public class Action implements Runnable {
File inputFile;
String fileChoser;
    public Action(File inputFile, String fileChoser ) {
        this.inputFile = inputFile;
        this.fileChoser = fileChoser;
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
        Graph myGrap = null;
        if (this.fileChoser.equals("hex")){
         myGrap = new Graph(new FileDataToIntHex(this.inputFile));
        }
        else if (this.fileChoser.equals("integ")){
         myGrap = new Graph(new FileDataFromStringToInt(this.inputFile));
        }

//myGrap.ShowGrap();
        myGrap.run();
    }
}
