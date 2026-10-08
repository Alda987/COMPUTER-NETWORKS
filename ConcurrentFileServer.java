import java.io.*;
import java.net.*;

public class ConcurrentFileServer {

    private static final int PORT = 5000;

    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {

            long pid = ProcessHandle.current().pid();

            System.out.println("Concurrent File Server Started...");
            System.out.println("Server PID: " + pid);
            System.out.println("Listening on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();

                System.out.println(
                    "New client connected: " +
                    clientSocket.getInetAddress()
                );

                // Create a separate thread for every client
                new Thread(
                    new ClientHandler(clientSocket, pid)
                ).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ClientHandler implements Runnable {

    private final Socket clientSocket;
    private final long serverPid;

    public ClientHandler(Socket socket, long pid) {
        this.clientSocket = socket;
        this.serverPid = pid;
    }

    @Override
    public void run() {

        try (
            BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream())
            );

            PrintWriter out = new PrintWriter(
                clientSocket.getOutputStream(), true
            )
        ) {

            // Read requested filename
            String fileName = in.readLine();

            System.out.println(
                "[" + Thread.currentThread().getId() +
                "] Requested file: " + fileName
            );

            File file = new File(fileName);

            // Send server PID and servicing thread ID
            out.println("Server PID: " + serverPid);
            out.println(
                "Servicing Thread ID: " +
                Thread.currentThread().getId()
            );

            // Check whether the requested file exists
            if (file.exists() && file.isFile()) {

                out.println("--- FILE FOUND ---");

                try (BufferedReader fileReader =
                         new BufferedReader(new FileReader(file))) {

                    String line;

                    while ((line = fileReader.readLine()) != null) {
                        out.println(line);
                    }
                }

            } else {

                out.println(
                    "--- ERROR: File '" +
                    fileName +
                    "' does not exist on server. ---"
                );
            }

            // Signal end of response
            out.println("EOF");

        } catch (IOException e) {
            System.out.println("Client communication error: "
                               + e.getMessage());

        } finally {

            try {
                clientSocket.close();
            } catch (IOException e) {
                // Ignore close error
            }
        }
    }
}
