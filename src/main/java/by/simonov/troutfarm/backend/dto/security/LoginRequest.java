package by.simonov.troutfarm.backend.dto.security;

public record LoginRequest(
        String username,
        String password
) {
}
