package com.test;

import com.google.protobuf.TextFormat;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.test.model.PersonProto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.stream.IntStream;

public class TestCaseV2 {

    int DATA_SIZE = 10;
    String MAP = "person";
    HazelcastInstance client;

    @Before
    public void init() {
        client = HazelcastClient.newHazelcastClient();
    }

    @After
    public void clear() {
        client.shutdown();
    }

    @Test
    public void loadData() {
        IMap<Integer, PersonProto.Person> personMap = client.getMap(MAP);
        IntStream.range(0, DATA_SIZE).parallel().forEach(i -> personMap.put(i, generatePerson(i)));
    }

    @Test
    public void checkData() {
        IMap<Integer, PersonProto.Person> personMap = client.getMap(MAP);
        IntStream.range(0, DATA_SIZE).parallel().forEach(i -> System.out.println(TextFormat.shortDebugString(personMap.get(i))));
    }

    @Test
    public void updateData() {
        IMap<Integer, PersonProto.Person> personMap = client.getMap(MAP);
        IntStream.range(0, DATA_SIZE).parallel().forEach(i -> {
            PersonProto.Person person = personMap.get(i);
            PersonProto.Person newPerson = PersonProto.Person.newBuilder(person).setName(person.getName() + "-xxx").setAge("12").build();
            personMap.set(i, newPerson);
        });
    }

    @Test
    public void clearData() {
        client.getMap(MAP).clear();
    }

    private PersonProto.Person generatePerson(int i) {
        return PersonProto.Person.newBuilder().setId(i).setName("Gokhan").setEmail("aa@bb.com").setAge(i + 20 + "").build();
    }
}
