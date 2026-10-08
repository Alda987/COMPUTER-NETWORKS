import java.net.*;

public class TimeServer {

    public static void main(String[] args) throws Exception {

        DatagramSocket socket = new DatagramSocket(5000);

        System.out.println("Time Server is running...");
        System.out.println("Listening on UDP port 5000...");

        while (true) {

            byte[] buffer = new byte[100];

            DatagramPacket request =
                    new DatagramPacket(buffer, buffer.length);

            // Wait for client request
            socket.receive(request);

            // Create a new thread for each client
            new Thread(() -> {

                try {

                    // Get current date and time
                    String time = new java.util.Date().toString();

                    byte[] data = time.getBytes();

                    // Create response packet
                    DatagramPacket response =
                            new DatagramPacket(
                                    data,
                                    data.length,
                                    request.getAddress(),
                                    request.getPort()
                            );

                    // Send time to client
                    socket.send(response);

                    System.out.println(
                            "Time sent to client: " +
                            request.getAddress()
                    );

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }).start();
        }
    }
}


