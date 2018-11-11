package delivery.events;

import org.springframework.kafka.support.serializer.JsonDeserializer;

import delivery.Shipment;

public class ShipmentDeserializer extends JsonDeserializer<Shipment> {

	public ShipmentDeserializer() {
		super(Shipment.class);
	}

}
