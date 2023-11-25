package guiSocketClient;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class GuiClientJava extends javax.swing.JFrame {
    static Socket socket;
    static BufferedReader serverInput;
    static PrintWriter out;
    String name;
    int index = 0;
    
    /**
     * Creates new form GuiClientJava
     */
    public GuiClientJava(String ip, int port, String name) {
        this.name = name;
        initComponents();
        try {
            socket = new Socket(ip, port);
            serverInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            // Start a background thread to listen for server messages
            new Thread(this::listenForMessages).start();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initComponents() {
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jTextField1.addActionListener(this::jTextField1ActionPerformed);

        jButton1.setText("click twice this button!!");
        jButton1.addActionListener(e -> {
			try {
				jButton1ActionPerformed(e);
			} catch (IOException ew) {
				// TODO Auto-generated catch block
				ew.printStackTrace();
			}
		});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTextField1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addContainerGap())
        );

        pack();
    }

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {
        // Handle action if needed
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) throws IOException {
        if (evt.getSource() == jButton1) {
        if (index == 2) {
            out.println(this.name + " joint the server" );

        }

        index++;
        System.out.println(this.index);
        String message = jTextField1.getText();
        out.println(this.name + " : " + message);
        jTextField1.setText("");
        if (index == 2) {
            jButton1.setText("send");
        }

        }

    }

    private void listenForMessages() {
        try {
            String serverMessage;
            while ((serverMessage = serverInput.readLine()) != null) {
                appendMessageToTextArea(serverMessage);
                System.out.println("message log : " + serverMessage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void appendMessageToTextArea(String message) {
    
        SwingUtilities.invokeLater(() -> {
            jTextArea1.append(message + "\n");
        });
    }


    // Variables declaration - do not modify
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration
}
