package messageKafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;


public class messageProducer {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;


	//@Value(value = "${message.topic.name}")
	//private String topicName;

	public messageProducer() {
	}

	public void sendMessage(String message) {
		kafkaTemplate.send("order:1:1", message);
		System.out.println("Send Message" + message);
	}
}
