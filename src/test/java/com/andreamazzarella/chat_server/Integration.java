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
    public void twoClientsConnectAndCommunicate() throws IOException, InterruptedException, ExecutionException {
        ChatRoom chatRoom = new ChatRoom(new CommunicationProtocol());
        int port = 4242;

        ExecutorService testServer = Executors.newSingleThreadExecutor();
        testServer.submit(() -> Main.start(chatRoom, port));

        Socket socketOne = new Socket("localhost", port);
        PrintWriter socketOneWriter = new PrintWriter(socketOne.getOutputStream());
        System.out.println("Socket One connected");

        Socket socketTwo = new Socket("localhost", port);
        BufferedReader socketTwoReader = new BufferedReader(new InputStreamReader(socketTwo.getInputStream()));
        System.out.println("Socket Two connected");

        socketOneWriter.println("ciao there");
        socketOneWriter.flush();

        assertThat(socketTwoReader.readLine()).isEqualTo("ciao there");

        socketOne.close();
        socketTwo.close();
    }
}
