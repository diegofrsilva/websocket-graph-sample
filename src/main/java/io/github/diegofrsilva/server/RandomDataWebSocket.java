package io.github.diegofrsilva.server;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@ServerEndpoint(value = "/randomData")
public class RandomDataWebSocket {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    private List<RandomDataGenerator> generators;

    public RandomDataWebSocket() {
        this.generators = new ArrayList<>();
    }

    @OnOpen
    public void onOpen(Session session) {
        logger.info("Session opened: " + session.getId());

        RandomDataGenerator randomDataGenerator = new RandomDataGenerator(session);
        generators.add(randomDataGenerator);
        new Thread(randomDataGenerator).start();

    }

    @OnMessage
    public String onMessage(String message, Session session) {
        logger.info("Message received: " + message);
        return message;
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        logger.info("Session closed. Reason: " + closeReason);

        for (RandomDataGenerator generator : generators) {
            generator.stop();
        }
    }
}
