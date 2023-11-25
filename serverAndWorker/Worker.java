package serverAndWorker;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class Worker implements Runnable {
    private Socket s;
    private PrintWriter out;
    private List<Worker> listWorkers;
    boolean status;
    boolean getName = false;
    String name;

    public Worker(Socket s, List<Worker> listWorkers) {
        this.s = s;
        this.listWorkers = listWorkers;
        this.status = true;
    }

    @Override
    public void run() {
        try {
            out = new PrintWriter(s.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                if (getName == false) {
                System.out.println(in.readLine());
                getName = true;
                this.name = in.readLine();
            }


            String message;
            while ((message = in.readLine()) != null) {
                sendSocketByClient(message);
            }
        } catch (Exception e) {
            System.out.println("Worker error : " + e.getMessage());
            // Notify the server when a client is disconnected
            notifyServerClientDisconnected();
        }
    }

    private void sendSocketByClient(String message) {
        for (Worker worker : listWorkers) {
            worker.send(message);
        }
    }

    private void send(String message) {
        out.println(message);
    }

    // Notify the server when a client is disconnected
    private void notifyServerClientDisconnected() {
        System.out.println("Client disconnected: " + s);
        // Add any additional logic to notify the server about the disconnection
        setStatus(false);
    }

    public boolean isDisconnected() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
