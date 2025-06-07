package by.simonov.troutfarm.backend.dto.filter;

import by.simonov.troutfarm.backend.entity.type.Role;
import org.springframework.util.CollectionUtils;

import java.util.List;

public record UserFilter(
        List<Role> role
) {
    public boolean hasRole() {
        return !CollectionUtils.isEmpty(role);
    }
}
