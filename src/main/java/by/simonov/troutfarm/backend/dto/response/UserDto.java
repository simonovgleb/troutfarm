package by.simonov.troutfarm.backend.dto.response;

import by.simonov.troutfarm.backend.entity.type.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {

    private UUID id;

    private String name;

    private String email;

    private Role role;

    private boolean isActive;
}

