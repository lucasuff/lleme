package uff.ic.lleme.tcc00328.aulas.concurrency.producer_consumer;

import uff.ic.lleme.tcc00328.aulas.concurrency.producer_consumer.Drop;
import uff.ic.lleme.tcc00328.aulas.concurrency.producer_consumer.Producer;
import uff.ic.lleme.tcc00328.aulas.concurrency.producer_consumer.Consumer;

public class ProducerConsumerExample {

    public static void main(String[] args) {
        Drop drop = new Drop();
        (new Thread(new Producer(drop))).start();
        (new Thread(new Consumer(drop))).start();
    }
}
