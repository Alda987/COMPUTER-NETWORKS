// Import networking classes
import java.net.*;

// Import Scanner class to read user input
import java.util.Scanner;

public class UDPClient {

    public static void main(String args[]) throws Exception {

        // Create Scanner object to read input from keyboard
        Scanner sc = new Scanner(System.in);

        // Create a UDP socket for the client
        // No port number is given, so Java assigns one automatically
        DatagramSocket socket = new DatagramSocket();

        // Get the IP address of the server
        // "localhost" means the server is running on the same computer
        InetAddress ip = InetAddress.getByName("localhost");

        // Ask the user to enter a sentence
        System.out.print("Enter a sentence: ");

        // Read the complete sentence
        String sentence = sc.nextLine();

        // Convert the sentence into bytes
        // UDP sends data in the form of byte arrays
        byte[] sendData = sentence.getBytes();

        // Create a packet containing:
        // 1. Data to send
        // 2. Length of data
        // 3. Server IP address
        // 4. Server port number (9876)
        DatagramPacket sendPacket =
                new DatagramPacket(sendData, sendData.length, ip, 9876);

        // Send the packet to the server
        socket.send(sendPacket);

        // Create a byte array to receive the translated sentence
        byte[] receiveData = new byte[1024];

        // Create an empty packet to store the received data
        DatagramPacket receivePacket =
                new DatagramPacket(receiveData, receiveData.length);

        // Wait until the server sends the translated sentence
        socket.receive(receivePacket);

        // Convert the received bytes into a String
        String translated = new String(receivePacket.getData(), 0,
                receivePacket.getLength());

        // Display the translated sentence
        System.out.println("\nTranslated Sentence:");
        System.out.println(translated);

        // Close the socket
        socket.close();

        // Close Scanner
        sc.close();
    }
}
