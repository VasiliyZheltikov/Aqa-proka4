package dto;

import com.github.javafaker.Faker;
import java.util.ArrayList;
import java.util.Locale;

public class PersonFactory {

    private static ArrayList<String> countries = new ArrayList<>() {{
        add("Russia");
        add("United States");
        add("United Kingdom");
        add("Germany");
    }};

    public static PersonData getPersonData() {
        Faker faker = new Faker();
        Faker ruFaker = new Faker(new Locale("ru"));
        return PersonData.builder()
            .username(faker.name().username())
            .email(faker.internet().emailAddress())
            .password(faker.internet().password())
            .country(faker.options().nextElement(countries))
            .isCountrySelected(true)
            .isCheckboxChecked(true)
            .phoneNumber(ruFaker.phoneNumber().cellPhone())
            .build();
    }
}
