package mate.academy.bookshop.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import mate.academy.bookshop.validation.FieldMatch;

@Data
@FieldMatch(first = "password", second = "repeatPassword")
public class UserRegistrationRequestDto {
    @Email
    @NotBlank
    @Size(max = 320,
            message = "Email cannot be longer than 320 characters")
    private String email;

    @NotBlank
    @Size(min = 8, max = 64,
            message = "Password must be between 8 and 64 characters")
    private String password;

    @NotBlank
    @Size(min = 8, max = 64,
            message = "Repeat password must be between 8 and 64 characters")
    private String repeatPassword;

    @NotBlank
    @Size(min = 2, max = 50,
            message = "First name must be between 2 and 50 characters")
    private String firstName;

    @NotBlank
    @Size(min = 2, max = 50,
            message = "Last name must be between 2 and 50 characters")
    private String lastName;

    @Size(min = 5, max = 255,
            message = "Shipping address must be between 5 and 255 characters")
    private String shippingAddress;
}
