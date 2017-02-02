import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import static org.junit.Assert.assertEquals;

public class EchoServerShould {

    @Test
    public void pipeAMessageBackToTheSender() throws IOException {
        EchoServer server = new EchoServer();
        server.run(1111);

        Socket clientSocket = new Socket("localhost", 1111);
        PrintStream toServer = new PrintStream(clientSocket.getOutputStream());
        BufferedReader fromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        toServer.println("Geronimo!");
        String response = fromServer.readLine();

        assertEquals("Geronimo!", response);
        clientSocket.close();
    }

    @Test
    public void pipeAMessageToOtherClients() throws IOException {
        EchoServer server = new EchoServer();
        server.run(1111);

        Socket clientSocketOne = new Socket("localhost", 1111);
        PrintStream toServerOne = new PrintStream(clientSocketOne.getOutputStream());
        BufferedReader fromServerOne = new BufferedReader(new InputStreamReader(clientSocketOne.getInputStream()));

        Socket clientSocketTwo = new Socket("localhost", 1111);
        PrintStream toServerTwo = new PrintStream(clientSocketTwo.getOutputStream());
        BufferedReader fromServerTwo = new BufferedReader(new InputStreamReader(clientSocketTwo.getInputStream()));

        toServerOne.println("Anybody out there?");

        assertEquals("someone messaged: Anybody out there?", fromServerTwo.readLine());
        clientSocketOne.close();
        clientSocketTwo.close();
    }
}
