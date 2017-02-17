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

## Note on persistent message history storage

By default, the application uses an in-memory repository to store message history, which means this is lost when the
server is shut down. An alternative that uses PostgreSQL exists; this is the `SQLMessageRepository` class, which can be
swapped with the `InMemoryMessageRepository` inside the ChatServer `Main` class.
Please notice that this is not very flexible at this stage, and it requires the following in order to work:

- A PostgreSQL server running on localhost:5432
- The user with userName `Andrea` and no password to access the server
- A database called `chat_server`
- A table called `messages`, with the following columns: text:content, text:username
