package by.simonov.troutfarm.backend.dto.response;

import by.simonov.troutfarm.backend.entity.type.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class UserInfoDto {

    private UUID id;

    private String name;

    private String email;

    private String password;

    private Role role;

    private boolean isActive;
}
