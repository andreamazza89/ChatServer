import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class EchoServer {

    void run(int portNumber) {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        List<Socket> allClients = new ArrayList<>();

       threadPool.submit(new Runnable() {
           @Override
           public void run() {
               try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
                   while (true) {
                       Socket clientSocket = serverSocket.accept();
                       allClients.add(clientSocket);
                       threadPool.submit(new EchoServerThread(clientSocket, allClients));
                   }
               } catch (IOException e) {
                   e.printStackTrace();
               }

           }
       });
    }

    private class EchoServerThread implements Runnable {
        private final Socket clientSocket;
        private final List<Socket> allClients;
        private BufferedReader fromClient = null;
        private PrintStream toClient = null;

        EchoServerThread(Socket clientSocket, List<Socket> allClients) {
            this.clientSocket = clientSocket;
            this.allClients = allClients;
            try {
                this.toClient = new PrintStream(clientSocket.getOutputStream());
                this.fromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {

            String messageFromClient;

            try {
                while ((messageFromClient = fromClient.readLine()) != null) {
                    for (Socket socket : allClients) {
                        if (socket != clientSocket) {
                            PrintStream asdf = new PrintStream(socket.getOutputStream());
                            asdf.println("someone messaged: " + messageFromClient);
                        }
                    }
                }
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
