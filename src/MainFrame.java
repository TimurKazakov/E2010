import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import static java.awt.Component.TOP_ALIGNMENT;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class MainFrame {


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
                // код для обработки
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
