package serverAndWorker;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {

    private List<Worker> clients = new CopyOnWriteArrayList<>();
    private String status;
    private int port;
    private volatile boolean isRunning = true;

    public Server(int port) {
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
        this.status = message;
        System.out.println(message);
    }

    public String getStatus() {

        for (int i = 0; i < clients.size(); i++) {
            System.out.println("status : " + clients.get(i).status);
            if (clients.get(i).status == false) {
                System.out.println("buddy left");
                setStatus("Hi, there someone has just left my fellow ade, idk who, im just kind of tired of debuggin alone");
            }
        }

        return this.status;
    }

    public void removeDisconnectedWorker(Worker worker) {
        clients.remove(worker);
        System.out.println("Worker removed: " + worker);
    }

    public List<Worker> getClients() {
        return clients;
    }
}
