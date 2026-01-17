package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonData {

    @Builder.Default
    private String username = "UsernameTest";
    @Builder.Default
    private String email = "EmailTest";
    @Builder.Default
    private String password = "PasswordTest";
    @Builder.Default
    private String country = "Russia";
    @Builder.Default
    private boolean isCountrySelected = true;
    @Builder.Default
    private boolean isCheckboxChecked = true;
}
