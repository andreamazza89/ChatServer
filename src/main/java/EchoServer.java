import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class EchoServer {

    void run(int portNumber) {
        ExecutorService connections = Executors.newCachedThreadPool();

        connections.submit(() -> {
            try (
                ServerSocket serverSocket = new ServerSocket(portNumber);
                Socket clientSocket = serverSocket.accept();
                BufferedReader fromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintStream toClient = new PrintStream(clientSocket.getOutputStream())
            ) {
                String message_received;

                while ((message_received = fromClient.readLine()) != null) {
                    toClient.println(message_received);
                    System.out.println("~> " + message_received);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }
}
