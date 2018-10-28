package messageKafka;

import org.springframework.kafka.annotation.KafkaListener;

import java.util.concurrent.CountDownLatch;


/**
 * Class messageListener
 *
 * @author Joël CANCELA VAZ
 */
public class messageListener {

	private CountDownLatch latch = new CountDownLatch(3);

	@KafkaListener(topics = "${order.topic.name}", containerFactory = "orderContainerFactory")
	public void listenNewOrder(String message) {
		System.out.println("Received Message in group 'order': " + message);
		latch.countDown();
	}

	@KafkaListener(topics = "${payement.topic.name}", containerFactory = "payementContainerFactory")
	public void listenNewPayement(String message) {
		System.out.println("Received Message in group 'bank': " + message);
		latch.countDown();
	}

	public CountDownLatch getLatch() {
		return latch;
	}
}
