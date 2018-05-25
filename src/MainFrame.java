import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.concurrent.TimeUnit;

import static java.awt.Component.TOP_ALIGNMENT;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import jssc.*;

public class MainFrame {

    private static SerialPort sp;
    public static void main(String[] args) {
        JFrame mainWindow = new JFrame("Main");
        mainWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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

        takeData.addActionListener(e -> {
            sp=new SerialPort("COM9");
            File file1;
            FileWriter writer;
            PrintWriter output;
            try{
                file1 = new File("data.txt");
                writer = new FileWriter(file1, false);
                output = new PrintWriter(writer);
                sp.openPort();
                sp.setParams(SerialPort.BAUDRATE_115200,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);
                PortReader pr = new PortReader(sp,500);
                sp.addEventListener(pr,SerialPort.MASK_RXCHAR);
                sp.writeString("10000");
                TimeUnit.MILLISECONDS.sleep(10);
                while (!pr.isDone()) {
                    System.out.println(pr.getCnt());
                    if(pr.isDataRdy()) {
                        pr.setDataRdy(false);
                        output.println(pr.getData());
                    }
                    TimeUnit.MILLISECONDS.sleep(10);
                }
                output.close();
                writer.close();
                sp.closePort();
                JOptionPane.showMessageDialog(null, "Данные загружены");
            }catch (SerialPortException ex){
                System.out.println(ex);
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        exit.addActionListener(e -> System.exit(0));
        addFile.addActionListener(e -> {
            JFileChooser addFileChooser = new JFileChooser();
            addFileChooser.setCurrentDirectory(new File("."));
            addFileChooser.showOpenDialog(pane1);
            File fileForGraph = addFileChooser.getSelectedFile();
            Action myAction = new Action(fileForGraph);
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
