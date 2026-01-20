package dto;

public class FormFactory {

    public static FormAttributes getSimpleRegistrationFormAttributes() {
        return FormAttributes.builder()
            .formName("Простая форма регистрации")
            .usernameLabel("Username")
            .emailLabel("Email")
            .passwordLabel("Password")
            .countryLabel("Country")
            .checkboxLabel("I agree to the Terms and Conditions")
            .buttonName("Register")
            .build();
    }

    public static FormAttributes getFormWithValidationAttributes() {
        return FormAttributes.builder()
            .formName("Форма с валидацией")
            .usernameLabel("Username")
            .emailLabel("Email")
            .passwordLabel("Password")
            .confirmPasswordLabel("Подтвердите Password")
            .buttonName("отправить")
            .build();
    }

    public static FormAttributes getDynamicFormAttributes() {
        return FormAttributes.builder()
            .formName("Динамическая форма")
            .usernameLabel("Ваше Имя")
            .emailLabel("Email адреса")
            .phoneLabel("Номера телефонов")
            .addEmailButtonName("Добавить email")
            .addPhoneButtonName("Добавить телефон")
            .build();
    }
}
