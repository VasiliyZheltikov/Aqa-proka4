package dto;

import com.github.javafaker.Faker;

public class PersonFactory {

    public static PersonData getPersonData() {
        Faker faker = new Faker();
        return PersonData.builder()
            .username(faker.name().username())
            .email(faker.internet().emailAddress())
            .password(faker.internet().password())
            .build();
    }
}
