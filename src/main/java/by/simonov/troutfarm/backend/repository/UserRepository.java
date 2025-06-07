package by.simonov.troutfarm.backend.repository;

import by.simonov.troutfarm.backend.entity.User;
import by.simonov.troutfarm.backend.entity.type.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    List<User> findAllByRoleIn(List<Role> roles);
}
