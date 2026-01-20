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
    private String username = null;
    @Builder.Default
    private String email = null;
    @Builder.Default
    private String password = null;
    @Builder.Default
    private String country = null;
    @Builder.Default
    private boolean isCountrySelected = false;
    @Builder.Default
    private boolean isCheckboxChecked = false;
    @Builder.Default
    private String phoneNumber = null;
}
