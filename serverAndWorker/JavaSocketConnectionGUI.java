package serverAndWorker;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Color;

public class JavaSocketConnectionGUI extends javax.swing.JFrame {
    Server server;
    JLabel label;
    static String log = "LOG SERVER : ";

    public JavaSocketConnectionGUI() {
        initComponents();
    }

    private void initComponents() {
        jButton1 = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        logging = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("CONNECT TO SERVER");
        setAutoRequestFocus(false);
        setFocusable(false);
        setPreferredSize(new java.awt.Dimension(1920, 1080));
        setResizable(false);
        getContentPane().setLayout(null);

        jButton1.setText("RUN SOCKET");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(870, 550, 100, 23);

        jTextField2.setText("PORT");
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });
        getContentPane().add(jTextField2);
        jTextField2.setBounds(820, 490, 198, 44);

        jLabel1.setText("<html><br>ade<br>ade</html>");
        getContentPane().add(jLabel1);
        JScrollPane scroller = new JScrollPane(jLabel1, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroller.setBounds(770, 600, 300, 200);
        getContentPane().add(scroller);

        pack();
        setLocationRelativeTo(null);
    }

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        if (evt.getSource() == jButton1) {
            Integer port = Integer.parseInt(jTextField2.getText());
            System.out.println(port);
            server = new Server(port);

            new Thread(() -> {
                try {
                    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                        System.out.println("Shutting down the server...");
                        server.stop();
                    }));

                    System.out.println("tes lagi");
                    server.run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }

        new Thread(() -> {
            int i = 0;
            String comparision = "";
            while (true) {
                String status = server.getStatus();
                if (i == 0 && status != null) {
                    log += "<br>" + server.getStatus() + "<br>";
                    jLabel1.setText("<html>" + "<br>" + log + "</html>");
                    comparision = status;
                    i++;
                }

                if (i != 0 && comparision.equals(status) == false) {
                    log += "<br>" + server.getStatus() + "<br>";
                    jLabel1.setText("<html>" + log + "</html>");
                    comparision = status;
                    i++;
                }

                try {
                    Thread.sleep(1000); // Adjust the sleep duration as needed
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private javax.swing.JTextField logging;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField jTextField2;
}
