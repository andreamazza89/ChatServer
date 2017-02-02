import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        EchoServer server = new EchoServer();
        server.run(2221);
    }
}