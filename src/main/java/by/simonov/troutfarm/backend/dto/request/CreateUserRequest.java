package by.simonov.troutfarm.backend.dto.request;

import by.simonov.troutfarm.backend.entity.type.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(
        @NotBlank String name,
        @Email @NotBlank String email,
        @Size(min = 8) String password,
        @NotNull Role role,
        boolean isActive
) {
}
