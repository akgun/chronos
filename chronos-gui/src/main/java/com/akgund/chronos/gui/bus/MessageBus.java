package com.akgund.chronos.gui.bus;

import java.util.ArrayList;
import java.util.List;

public class MessageBus {
    private static MessageBus instance = new MessageBus();
    private List<IMessageClient> clients = new ArrayList<>();

    private MessageBus() {
    }

    public static MessageBus getInstance() {
        return instance;
    }

    public void sendMessage(Class<? extends IMessageClient> target, String message) {
        clients.stream().filter((c) -> c.getClass() == target).
                forEach(c -> c.receiveMessage(message));
    }

    public void register(IMessageClient client) {
        clients.add(client);
    }
}
