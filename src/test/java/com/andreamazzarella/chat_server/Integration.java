package com.andreamazzarella.chat_server;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class Integration {

    @Test
    public void twoClientsConnectAndChat() throws IOException, InterruptedException, ExecutionException {
        ChatRoom chatRoom = new ChatRoom();

        ExecutorService testServer = Executors.newSingleThreadExecutor();
        testServer.submit(() -> Main.start(chatRoom, 4242));


        Socket socketOne = new Socket("localhost", 4242);
        PrintWriter writer = new PrintWriter(socketOne.getOutputStream());
        System.out.println("Connected SocketOne to server");

        Socket socketTwo = new Socket("localhost", 4242);
        BufferedReader reader = new BufferedReader(new InputStreamReader(socketTwo.getInputStream()));
        System.out.println("Connected SocketTwo to server");

        writer.println("ciao there");
        writer.flush();

        assertThat(reader.readLine()).isEqualTo("ciao there");

        socketOne.close();
        socketTwo.close();
    }
}
