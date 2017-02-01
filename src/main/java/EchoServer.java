import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
    public static void main(String[] args) {
        try (
            ServerSocket serverSocket = new ServerSocket(2222);
            Socket clientSocket = serverSocket.accept();
            BufferedReader fromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintStream toClient = new PrintStream(clientSocket.getOutputStream());
        ) {
            String message_received;

            while ((message_received = fromClient.readLine()) != null) {
                toClient.println(message_received);
                System.out.println("~> " + message_received);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
