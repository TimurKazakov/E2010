import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import static java.awt.Component.TOP_ALIGNMENT;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import jssc.*;

public class MainFrame {

    private static SerialPort sp;
    public static void main(String[] args) {
        JFrame mainWindow = new JFrame("Main");
        mainWindow.setDefaultCloseOperation(mainWindow.EXIT_ON_CLOSE);
        mainWindow.setVisible(true);
//        mainWindow.setLayout(new FlowLayout());
        mainWindow.setSize(800, 600);
//menu
        JMenuBar mainMenuBar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenu collectData = new JMenu("Collect data");

        mainMenuBar.add(file);
        mainMenuBar.add(collectData);
        mainWindow.setJMenuBar(mainMenuBar);
        JPanel pane1 = new JPanel();
        // submenu
        JMenuItem addFile = file.add(new JMenuItem("Add File"));
        file.addSeparator();
        JMenuItem exit = file.add(new JMenuItem("Exit"));
        JMenuItem takeData = new JMenuItem("Take Data From Device");
        collectData.add(takeData);

        takeData.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sp=new SerialPort("COM9");
                PrintWriter writer;
                try{
                    writer = new PrintWriter("data.txt", "UTF-8");
                    sp.openPort();
                    sp.setParams(SerialPort.BAUDRATE_115200,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);
                    PortReader pr = new PortReader(sp,200);
                    sp.addEventListener(pr,SerialPort.MASK_RXCHAR);
                    sp.writeString("4000");
                    while (!pr.isDone()) {
                        System.out.println(pr.getData());
                        if(pr.isDataRdy()) {
                            pr.setDataRdy(false);
                            writer.println(pr.getData());
                            System.out.println(pr.getData());
                        }
                    }
                    System.out.println("done!");
                    writer.close();
                    sp.closePort();
                    //TODO: add pop-up message to show end of the readings
                }catch (SerialPortException ex){
                    System.out.println(ex);
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
            }
        });
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        addFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser addFileChooser = new JFileChooser();
                addFileChooser.setCurrentDirectory(new File("."));
                addFileChooser.showOpenDialog(pane1);
                File fileForGraph = addFileChooser.getSelectedFile();
                Action myAction = new Action(fileForGraph);
            }
        });
//        mainWindow.add(panel, BorderLayout.NORTH);
//        mainWindow.add(pane2, BorderLayout.WEST);
//
//        JTextField voltage = new JTextField("def", 10);
//       JLabel inoutVoltage = new JLabel("Voltage");
//       pane1.add(inoutVoltage);
//        pane1.add(voltage);
//        JButton okButton = new JButton("OK");
//        pane1.add(okButton);
//
//
//
//
//        pane1.revalidate();
//
//        okButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                pane1.setBackground(Color.BLUE);
//                System.out.println(voltage.getText());
//                Action myAction = new Action();
//            }
//        });
//    }
    }
}
