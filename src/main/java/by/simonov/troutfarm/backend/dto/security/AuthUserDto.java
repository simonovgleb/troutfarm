package by.simonov.troutfarm.backend.dto.security;

import java.util.List;
import java.util.UUID;

public record AuthUserDto(
        UUID id,
        String username,
        String name,
        List<String> authorities
) {
}
