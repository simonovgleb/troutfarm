package by.simonov.troutfarm.backend.repository;

import by.simonov.troutfarm.backend.entity.FeedingLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FeedingLogRepository extends JpaRepository<FeedingLog, UUID> {
}
