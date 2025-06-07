package by.simonov.troutfarm.backend.repository;

import by.simonov.troutfarm.backend.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

    List<Task> findAllByAssignedToId(UUID id);
}
