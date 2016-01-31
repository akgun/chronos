package com.akgund.chronos.gui.bus;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class MessageBus {
    private static final Logger logger = LogManager.getLogger(MessageBus.class);
    private static MessageBus instance = new MessageBus();
    private List<IMessageClient> clients = new ArrayList<>();

    private MessageBus() {
    }

    public static MessageBus getInstance() {
        return instance;
    }

    public void sendMessage(Class<? extends IMessageClient> target, MessageType message) {
        logger.debug(String.format("Sending message. Target: %s, Message Type: %s", target, message));

        Stream<IMessageClient> clientStream = clients.stream().filter((c) -> c.getClass() == target);

        if (clientStream.count() == 0) {
            throw new NoClientException(String.format("There is no client '%s'", target.getName()));
        }

        clients.stream().filter((c) -> c.getClass() == target).forEach(c -> c.receiveMessage(message));
    }

    public void register(IMessageClient client) {
        logger.debug(String.format("Registering client: '%s'", client));
        clients.add(client);
    }
}
