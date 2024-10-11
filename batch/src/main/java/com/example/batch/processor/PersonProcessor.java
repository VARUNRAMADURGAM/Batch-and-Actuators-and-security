package com.example.batch.processor;

import com.example.batch.model.Person;
import org.springframework.batch.item.ItemProcessor;

public class PersonProcessor implements ItemProcessor<Person, Person> {

	@Override
	public Person process(Person person) throws Exception {
		if (person.getName().contains("Ali")) {
			if(person.getBirthDate().isEmpty()) {
				person.setBirthDate(null);
			}
			return person;
		}
		return null;
	}
}
