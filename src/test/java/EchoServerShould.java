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

        Socket testClientSocket = new Socket("localhost", 1111);
        PrintStream toServer = new PrintStream(testClientSocket.getOutputStream());
        BufferedReader fromServer = new BufferedReader(new InputStreamReader(testClientSocket.getInputStream()));

        toServer.println("Geronimo!");
        String response = fromServer.readLine();

        assertEquals("Geronimo!", response);
        testClientSocket.close();
    }
}
