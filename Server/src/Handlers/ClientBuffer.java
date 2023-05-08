package Handlers;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ClientBuffer  {
    private BlockingQueue<Client> clientQueue;

    public ClientBuffer() {
        clientQueue = new LinkedBlockingQueue<>(10);
    }

    public void add(Client client) {
        clientQueue.offer(client);
        notifyAll();
    }

    public Client take() throws InterruptedException {
        while(clientQueue.isEmpty()) {
            wait();
        }
        return clientQueue.take();
    }
}
