import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import java.awt.Dimension;
import java.awt.FlowLayout;

public class EnterYourIP extends JFrame {

    EnterYourIP() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());

        JTextField ip = new JTextField();
        ip.setPreferredSize(new Dimension(250, 40));
        ip.setText("IP Address/Hostname");

        JTextField port = new JTextField();
        port.setPreferredSize(new Dimension(250, 40));
        port.setText("Port");

        JTextField name = new JTextField();
        name.setPreferredSize(new Dimension(250, 40));
        name.setText("Your Name");

        JButton button = new JButton("Connect");
        
        this.add(button);
        button.addActionListener(e -> {
            if (e.getSource() == button) {
                System.out.println(name.getText() + " Trying to connect to " + ip.getText() + " with port : " + port.getText());
                dispose();
                java.awt.EventQueue.invokeLater(new Runnable() {
                    public void run() {
                         System.out.println("Stastus : 200 OK");
                        new GuiClientJava(ip.getText(), Integer.parseInt(port.getText()), name.getText()).setVisible(true);
        
                    }
                });            
                    System.out.println("tes");
            }
        });

        this.add(ip);
        this.add(port);
        this.add(name);
        this.pack();
        this.setVisible(true);
    }
}
