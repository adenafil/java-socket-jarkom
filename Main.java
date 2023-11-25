import serverAndWorker.JavaSocketConnectionGUI;
import guiSocketClient.EnterYourIP;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Main {
    static JFrame frame;
    static JLabel label;
    static JButton button1;
    static JButton button2;

    public static void main(String[] args) {
        frame = new JFrame();
        frame.setSize(1000, 1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setResizable(false);
        
        label = new JLabel("Choose between them");
        label.setBounds(500, 500, 1000, 20);

        button1 = new JButton("Server");
        button2 = new JButton("Chatroom");

        button1.setBounds(600, 550, 100, 50);
        button2.setBounds(400, 550, 100, 50);

        // button server
        button1.addActionListener(e -> {
            if (e.getSource() == button1) {
                System.out.println("hello world men");
                java.awt.EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        new JavaSocketConnectionGUI().setVisible(true);
                    }
                });

            }
        });

        button2.addActionListener(e -> {
            if (e.getSource() == button2) {
                System.out.println("hello world men");
                java.awt.EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        new EnterYourIP();
                    }
                });
            }
        });

        frame.add(label);
        frame.add(button1);
        frame.add(button2);
        frame.setVisible(true);
    }
}
