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

    public Worker(Socket s, List<Worker> listWorkers) {
        this.s = s;
        this.listWorkers = listWorkers;
    }

    @Override
    public void run() {
        try {
            out = new PrintWriter(s.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));

            String message;
            while ((message = in.readLine()) != null) {
                sendSocketByClient(message);

            }
        } catch (Exception e) {
            System.out.println("Worker error : " + e.getMessage());
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
}
