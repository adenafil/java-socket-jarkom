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
        return this.status;
    }

    public static void main(String[] args) {
        int port = 1111; // Change the port as needed
        Server server = new Server(port);

        // Register a shutdown hook to gracefully stop the server
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down the server...");
            server.stop();
        }));

        server.run();
    }
}
