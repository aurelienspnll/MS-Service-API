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

	@KafkaListener(topics = "${bank.topic.name}", containerFactory = "bankContainerFactory")
	public void listenPayementConfirmation(String message) {
		System.out.println("Received Message in group 'bank': " + message);
		latch.countDown();
	}

	public CountDownLatch getLatch() {
		return latch;
	}
}
