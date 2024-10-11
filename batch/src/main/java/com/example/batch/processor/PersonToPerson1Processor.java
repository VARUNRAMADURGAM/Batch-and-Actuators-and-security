package com.example.batch.processor;

import com.example.batch.model.Person;
import com.example.batch.model.Person1;
import org.springframework.batch.item.ItemProcessor;

public class PersonToPerson1Processor implements ItemProcessor<Person, Person1> {

    @Override
    public Person1 process(Person person) throws Exception {
        // Copy data from Person to Person1
        Person1 person1 = new Person1();
        person1.setName(person.getName());
        person1.setBirthDate(person.getBirthDate());
        return person1;
    }
}
