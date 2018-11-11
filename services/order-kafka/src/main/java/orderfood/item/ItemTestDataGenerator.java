package orderfood.item;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ItemTestDataGenerator {

	private final ItemRepository itemRepository;

	@Autowired
	public ItemTestDataGenerator(ItemRepository itemRepository) {
		this.itemRepository = itemRepository;
	}

	@PostConstruct
	public void generateTestData() {
		itemRepository.save(new Item("LAZAGNE", 10.0));
		itemRepository.save(new Item("PIZZA", 8.0));
		itemRepository.save(new Item("FRITE", 2.0));
		itemRepository.save(new Item("COUSCOUS", 20.0));
	}

}
