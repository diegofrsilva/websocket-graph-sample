package io.github.diegofrsilva.server;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Random;

public class RandomDataGenerator implements Runnable {

    private static final Long INTERVAL = 100L;

    private final Session session;
    private boolean running;

    public RandomDataGenerator(Session session) {
        this.session = session;
    }

    @Override
    public void run() {
        this.running = true;

        while (this.running) {
            try {
                session.getBasicRemote().sendText(String.valueOf(new Random().nextInt(100)));
                waitSomeTime(INTERVAL);
            } catch (IOException e) {
                throw new RuntimeException();
            }
        }
    }

    private void waitSomeTime(long timeout) {
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void stop() {
        this.running = false;
    }
}
