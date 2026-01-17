package dto;

import com.github.javafaker.Faker;
import java.util.ArrayList;

public class PersonFactory {

    private static ArrayList<String> countries = new ArrayList<>() {{
        add("Russia");
        add("United States");
        add("United Kingdom");
        add("Germany");
    }};

    public static PersonData getPersonData() {
        Faker faker = new Faker();
        return PersonData.builder()
            .username(faker.name().username())
            .email(faker.internet().emailAddress())
            .password(faker.internet().password())
            .country(faker.options().nextElement(countries))
            .build();
    }
}
