package io.github.diegofrsilva.server;

import org.glassfish.tyrus.server.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class WebSocketServer {

    public static void main(String[] args) throws IOException {
        Server server = new Server("localhost", 8080, "/websockets", RandomDataWebSocket.class);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            server.start();
            System.out.print("Press any key to stop the server");
            reader.readLine();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            server.stop();
            reader.close();
        }
    }
}
