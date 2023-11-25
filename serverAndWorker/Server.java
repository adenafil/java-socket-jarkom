package serverAndWorker;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {

    private List<Worker> clients = new CopyOnWriteArrayList<>();
    private Set<String> status;
    private int port;
    private volatile boolean isRunning = true;

    public Server(int port) {
        this.status = new HashSet<>();
        this.status.add("log : ");
        this.port = port;
    }

    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(this.port)) {
            System.out.println("Server started on port " + port);

            while (isRunning) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket);
                setStatus("New client connected: " + clientSocket);

                Worker worker = new Worker(clientSocket, clients);
                clients.add(worker);
                System.out.println("tes");
                new Thread(worker).start();
            }
        } catch (Exception e) {
            System.err.println("Server error: " + e.getMessage());
            setStatus("Error: " + e.getMessage());
        }
    }

    public void stop() {
        isRunning = false;
    }

    public void setStatus(String message) {
        this.status.add(message);
        System.out.println(message);
    }

    public Set<String> getStatus() {

        for (Worker worker : clients) {
            if (worker.status == false) {
                System.out.println("why");
                removeDisconnectedWorker(worker);                
            }
        }

        return this.status;
    }

    public void removeDisconnectedWorker(Worker worker) {
        setStatus("worker has been removed with last message -> " + worker.name);
        clients.remove(worker);
        System.out.println("Worker removed: " + worker.name);
    }

    public List<Worker> getClients() {
        return clients;
    }
}
