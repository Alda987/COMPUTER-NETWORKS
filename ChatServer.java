import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    static Vector<ClientHandler> clients = new Vector<>();

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(5000);

        System.out.println("Chat Server Started...");

        while (true) {
            Socket socket = serverSocket.accept();

            System.out.println("New client connected");

            ClientHandler handler = new ClientHandler(socket);
            clients.add(handler);

            handler.start();
        }
    }
}

class ClientHandler extends Thread {
    Socket socket;
    BufferedReader in;
    PrintWriter out;
    String name;

    ClientHandler(Socket socket) throws IOException {
        this.socket = socket;

        in = new BufferedReader(
                new InputStreamReader(socket.getInputStream())
        );

        out = new PrintWriter(
                socket.getOutputStream(), true
        );
    }

    public void run() {
        try {
            name = in.readLine();

            broadcast(name + " joined the chat");

            String message;

            while ((message = in.readLine()) != null) {
                broadcast(name + ": " + message);
            }

        } catch (Exception e) {
            // Client disconnected
        } finally {
            try {
                ChatServer.clients.remove(this);

                if (name != null) {
                    broadcast(name + " left the chat");
                }

                socket.close();

            } catch (Exception e) {
                // Ignore closing errors
            }
        }
    }

    void broadcast(String message) {
        for (ClientHandler client : ChatServer.clients) {
            if (client != this) {
                client.out.println(message);
            }
        }
    }
}
