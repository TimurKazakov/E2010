import java.io.*;
import java.util.ArrayList;


public class FileDataToIntHex extends  FileDataToInt {


    public FileDataToIntHex( File fileToInt) {
        super(fileToInt);
        this.integers =new ArrayList<>();
        try
        {
//            File testFile = new File("C:\\Users\\Тимур\\Desktop\\Проект MyProjects\\Проектный практикум\\Homework\\E2010\\src\\Test2.txt");
            File testFile = fileToInt;
            FileInputStream fis = new FileInputStream(testFile);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fis));
            while (bufferedReader.ready()) {
                String line = bufferedReader.readLine();
                String[] num = line.split(" ");
                for (String s : num) {
                    integers.add(fromHexToInt(s));
                }
            }

//            for (Integer num : integers) {
//                System.out.println(num);
//            }

        } catch(FileNotFoundException e)

        {
            e.printStackTrace();
        } catch(IOException e)

        {
            e.printStackTrace();
        }

    }



    public static Integer fromHexToInt(String number){
        Integer num = Integer.parseInt(number, 16);

        return num;


    }
}