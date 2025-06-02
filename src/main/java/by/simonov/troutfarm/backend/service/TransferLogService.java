package by.simonov.troutfarm.backend.service;

import by.simonov.troutfarm.backend.entity.TransferLog;
import by.simonov.troutfarm.backend.repository.TransferLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransferLogService {
    private final TransferLogRepository repository;

    public List<TransferLog> findAll() {
        return repository.findAll();
    }

    public Optional<TransferLog> findById(UUID id) {
        return repository.findById(id);
    }

    public TransferLog save(TransferLog log) {
        return repository.save(log);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }
}
