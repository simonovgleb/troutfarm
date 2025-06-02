package by.simonov.troutfarm.backend.repository;

import by.simonov.troutfarm.backend.entity.Tank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TankRepository extends JpaRepository<Tank, UUID> {
}
