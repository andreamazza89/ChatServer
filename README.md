# Chat Server

A Java chat application. The Server allows multiple clients to connect to it and have a chat.

### Installation-server

- Clone this repo `git clone git@github.com:andreamazza89/ChatServer.git`
- Move to it `cd ChatServer`
- Build `./gradlew build`
- And run it `java -jar build/libs/serverJar... 1234`, where `1234` is the port number you would like the server to run at.

### Installation-client (install the server first)
- Move to the repo's root folder
- Generate the client jar `./gradlew clientJar`
- Start the client `java -jar build/libs/clientJar... localhost 1234`, where `localhost` is the IP address of the machine
running the server (in this case the same as the client) and `1234` the port on which the server is accepting connections.

To start chatting without the Client application you can use NetCat, like so:

- Run the server as per the above.
- Open a client connection to the server `nc localhost|server_ip_address 1234`
- Open at least another client connection.
- Have a chat.
