package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FormAttributes {

    @Builder.Default
    private String formName = null;
    @Builder.Default
    private String usernameLabel = null;
    @Builder.Default
    private String emailLabel = null;
    @Builder.Default
    private String passwordLabel = null;
    @Builder.Default
    private String confirmPasswordLabel = null;
    @Builder.Default
    private String countryLabel = null;
    @Builder.Default
    private String checkboxLabel = null;
    @Builder.Default
    private String buttonName = null;
}
