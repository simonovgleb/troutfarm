package by.simonov.troutfarm.backend.service;

import by.simonov.troutfarm.backend.entity.MortalityLog;
import by.simonov.troutfarm.backend.repository.MortalityLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MortalityLogService {
    private final MortalityLogRepository repository;

    public List<MortalityLog> findAll() {
        return repository.findAll();
    }

    public Optional<MortalityLog> findById(UUID id) {
        return repository.findById(id);
    }

    public MortalityLog save(MortalityLog log) {
        return repository.save(log);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }
}
