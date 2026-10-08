import java.net.*;

public class UDPServer {

    public static void main(String args[]) throws Exception {

        // Create a UDP socket on port 9876
        // The server listens for client requests on this port
        DatagramSocket socket = new DatagramSocket(9876);

        // Create a byte array to receive client data
        byte[] receiveData = new byte[1024];

        // Display server status
        System.out.println("Server Started...");

        // Create an empty packet to receive data
        DatagramPacket receivePacket =
                new DatagramPacket(receiveData, receiveData.length);

        // Wait until a client sends a message
        socket.receive(receivePacket);

        // Convert the received bytes into a String
        String sentence = new String(receivePacket.getData(), 0,
                receivePacket.getLength());

        // Display the received sentence
        System.out.println("Received: " + sentence);

        // Replace abbreviations with their full forms
        sentence = sentence.replace(" tbh", " to be honest");
        sentence = sentence.replace(" ig", " I guess");
        sentence = sentence.replace(" tbf", "  to be fair");
        sentence = sentence.replace(" atm", " at the moment");
        sentence = sentence.replace(" irl", " in real life");
        sentence = sentence.replace(" lol", " laughing out loud");
        sentence = sentence.replace(" asap", " as soon as possible");
        sentence = sentence.replace(" omg", " oh my God");
        sentence = sentence.replace(" ttyl", " talk to you later");
        sentence = sentence.replace(" idk", " I don't know");
        sentence = sentence.replace(" nvm", " never mind");
        sentence = sentence.replace(" idc", " I don't care");

        // Convert the translated sentence into bytes
        byte[] sendData = sentence.getBytes();

        // Create a packet to send the translated sentence back
        // receivePacket.getAddress() -> Client's IP Address
        // receivePacket.getPort() -> Client's Port Number
        DatagramPacket sendPacket =
                new DatagramPacket(sendData, sendData.length,
                        receivePacket.getAddress(),
                        receivePacket.getPort());

        // Send the translated sentence to the client
        socket.send(sendPacket);

        // Display confirmation message
        System.out.println("Translation Sent.");

        // Close the socket
        socket.close();
    }
}
