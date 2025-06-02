package by.simonov.troutfarm.backend.service;

import by.simonov.troutfarm.backend.entity.FeedingLog;
import by.simonov.troutfarm.backend.repository.FeedingLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FeedingLogService {
    private final FeedingLogRepository repository;

    public List<FeedingLog> findAll() {
        return repository.findAll();
    }

    public Optional<FeedingLog> findById(UUID id) {
        return repository.findById(id);
    }

    public FeedingLog save(FeedingLog log) {
        return repository.save(log);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }
}

