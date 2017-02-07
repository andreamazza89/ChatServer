# Chat Server

A Java chat application. The Server allows multiple clients to connect to it and have a chat.

See the separate Chat Client repo fot the client side.

### Installation

- Clone this repo `git clone git@github.com:andreamazza89/ChatServer.git`
- Move to it `cd ChatServer`
- Build `./gradlew build`
- And run it `java -jar build/lib/NAME_OF_LATEST_JAR 1234`, where `1234` is the port you would like the server to run at.

To start chatting without the Client application you can use NetCat, like so:

- Run the server as per the above.
- Open a client connection to the server `nc localhost|server_ip_address 1234`
- Open at least another client connection.
- Have a chat.
