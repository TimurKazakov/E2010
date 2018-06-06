import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.InvalidDnDOperationException;
import java.awt.dnd.peer.DragSourceContextPeer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.awt.im.InputMethodHighlight;
import java.awt.image.ColorModel;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.awt.peer.*;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static java.awt.Component.TOP_ALIGNMENT;

import static javax.swing.JFrame.EXIT_ON_CLOSE;
import jssc.*;
import oracle.jrockit.jfr.JFR;

public class MainFrame {

    private static SerialPort sp;
    public static void main(String[] args) {
        JFrame mainWindow = new JFrame("Main");
        mainWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainWindow.setVisible(true);
//        mainWindow.setLayout(new FlowLayout());
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        mainWindow.setBounds(dimension.width/2-400,dimension.height/2-300, 800,600);


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
        JMenuItem ChoosePort = new JMenuItem("Port to Collect");
        collectData.add(ChoosePort);
        collectData.addSeparator();
        collectData.add(takeData);
        final String[] choosingPort = {"COM3"};

        ChoosePort.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame portChooserFrame = getFrame();
                portChooserFrame.setTitle("Выберите порт");
                JPanel paneX = new JPanel();
                portChooserFrame.add(paneX);
                JTextField field = new JTextField();
                paneX.add(new JLabel("Port: "));
                paneX.add(field);
                field.setText(choosingPort[0]);
                choosingPort[0] = field.getText();
                JButton ok = new JButton("OK");
                JButton abort = new JButton("Abort");
                paneX.add(ok);
                paneX.add(abort);
                ok.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        choosingPort[0] = field.getText();
                        portChooserFrame.dispose();
//                        System.out.println(choosingPort[0]);
                    }
                });
                abort.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        portChooserFrame.dispose();
//                        System.out.println(choosingPort[0]);
                    }
                });
            }
        });

        takeData.addActionListener(e -> {
            sp=new SerialPort(choosingPort[0]);
            File file1;
            FileWriter writer;
            PrintWriter output;
            try{
                file1 = new File("data.txt");
                writer = new FileWriter(file1, false);
                output = new PrintWriter(writer);
                sp.openPort();
                sp.setParams(SerialPort.BAUDRATE_115200,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);
                PortReader pr = new PortReader(sp,100);
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
                JFileChooser savedata = new JFileChooser("Save as");
                savedata.setCurrentDirectory(new File("."));
//                savedata.showSaveDialog(pane1);
                if (savedata.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                     {
                        FileInputStream saveToOut = new FileInputStream(file1);
                        FileOutputStream fileStream = new FileOutputStream(savedata.getSelectedFile());
                         while (saveToOut.available() > 0)
                         {
                             int data = saveToOut.read();
                             fileStream.write(data);
                         }

                         saveToOut.close();
                         fileStream.close();

                    }

                }



            }catch (SerialPortException ex){
                JOptionPane.showMessageDialog(null, ex);

                System.out.println(ex);
            } catch (FileNotFoundException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage());
                e1.printStackTrace();
            } catch (UnsupportedEncodingException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage());
                e1.printStackTrace();
            } catch (InterruptedException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage());
                e1.printStackTrace();
            } catch (IOException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage());
                e1.printStackTrace();
            }
        });

        exit.addActionListener(e -> System.exit(0));

        addFile.addActionListener(e -> {
            JFileChooser addFileChooser = new JFileChooser();
            addFileChooser.setCurrentDirectory(new File("."));
            addFileChooser.showOpenDialog(pane1);
            File fileForGraph = addFileChooser.getSelectedFile();
            JFrame popupFrame = getFrame();
            JPanel panel = new JPanel();
            popupFrame.add(panel);
            JButton hex = new JButton("Hex");
            JButton Integ = new JButton("Int");

            panel.add(hex);
            panel.add(Integ);
            hex.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Action myAction = new Action(fileForGraph, "hex");
                }
            });
            Integ.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Action myAction = new Action(fileForGraph, "integ");
                }
            });


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

static JFrame getFrame(){

        JFrame frame= new JFrame("Выберите формат");
        frame.setVisible(true);
        frame.setBounds(0, 0, 500,500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        frame.setBounds(dimension.width/2-125,dimension.height/2-50, 250,100);



        return frame;
}

}
