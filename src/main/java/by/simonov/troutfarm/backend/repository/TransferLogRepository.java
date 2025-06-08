package by.simonov.troutfarm.backend.repository;

import by.simonov.troutfarm.backend.entity.TransferLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransferLogRepository extends JpaRepository<TransferLog, UUID> {
    List<TransferLog> findAllByOperatorId(UUID id);
}
