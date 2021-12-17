package fr.afpa.cda.swing;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.*;
import java.awt.event.ActionEvent;

public class testWindows extends JFrame {

    public testWindows()  {
        super("Mail Manager");

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(1940,1600);
        this.setLocationRelativeTo(null);

        JPanel contentPane = (JPanel) this.getContentPane();
       contentPane.add(createToolBar(), BorderLayout.NORTH);
       JScrollPane westElement = createScrollBar();
       westElement.setPreferredSize(new Dimension(200, 0));
       contentPane.add(westElement, BorderLayout.WEST);
       JTextArea txtContent = new JTextArea("The content");
       JScrollPane scrContent = new JScrollPane(txtContent);
       contentPane.add(scrContent);
       contentPane.add(createStatusBar(), BorderLayout.SOUTH);
       this.setJMenuBar(createMenuBar());


    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");

        JMenuItem newMail = new JMenuItem("New Mail");
        newMail.addActionListener(this::mnuFileListner);
        fileMenu.add(newMail);
        fileMenu.addSeparator();
        JMenuItem quit = new JMenuItem("Quit");
        fileMenu.add(quit);

        menuBar.add(fileMenu);

        return menuBar;
    }

    public static void main(String[] args) throws UnsupportedLookAndFeelException {

        //Apply a look'n feel
        UIManager.setLookAndFeel(new NimbusLookAndFeel());

        testWindows myWindow = new testWindows();
        myWindow.setVisible(true);


    }

    private JToolBar createToolBar(){

        JToolBar toolBar = new JToolBar();
        JButton btnPushMe = new JButton("Push me!");
        JButton btnClickMe = new JButton("Click me!");
        JCheckBox checkBox = new JCheckBox("Check me ! ");
        JTextField txtEditMe = new JTextField("Edit me! ");

        btnPushMe.addActionListener(e -> System.err.println("Ici"));
        toolBar.add(btnPushMe);
        toolBar.add(btnClickMe);
        toolBar.add(checkBox);
        toolBar.add(txtEditMe);


        return toolBar;
    }

    private JScrollPane createScrollBar(){

        return new JScrollPane(new JTree());
    }

    private JPanel createStatusBar(){
        JPanel statusBar = new JPanel(new FlowLayout());

        JLabel lblStatus1 = new JLabel("Message 1");
        lblStatus1.setPreferredSize(new Dimension(100, 30));
        JLabel lblStatus2 = new JLabel("Message 2");
        lblStatus2.setPreferredSize(new Dimension(100, 30));
        JLabel lblStatus3 = new JLabel("Message 3");
        lblStatus3.setPreferredSize(new Dimension(100, 30));

        statusBar.add(lblStatus1);
        statusBar.add(lblStatus2);
        statusBar.add(lblStatus3);
        return  statusBar;
    }

    private void mnuFileListner(ActionEvent e){
        JOptionPane.showMessageDialog(this, "New Mail here");
    }

}
