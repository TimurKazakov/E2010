import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class FileDataFromStringToInt extends FileDataToInt {
    public FileDataFromStringToInt(File file) {
        super(file);
        this.integers =new ArrayList<>();
        try
        {
//            File testFile = new File("C:\\Users\\Тимур\\Desktop\\Проект MyProjects\\Проектный практикум\\Homework\\E2010\\src\\Test2.txt");
            File testFile = file;
            FileInputStream fis = new FileInputStream(testFile);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fis));
            int counter = 0;
            while (bufferedReader.ready()) {
                try {
                    integers.add(Integer.parseInt(bufferedReader.readLine()));
                }
                catch (Exception e){

//                    JOptionPane.showMessageDialog(null, "Неверный формат файла");
                    break;
                }
                }
            } catch (FileNotFoundException e1) {
            JOptionPane.showMessageDialog(null, e1.getMessage());
            e1.printStackTrace();

        } catch (IOException e1) {
            JOptionPane.showMessageDialog(null, e1.getMessage());
            e1.printStackTrace();
        }

//            for (Integer num : integers) {
//                System.out.println(num);
//            }





    }

    public static Integer fromHexToInt(String number){
        Integer num = Integer.parseInt(number, 16);

        return num;


    }




}
