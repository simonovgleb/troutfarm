package by.simonov.troutfarm.backend.service;

import by.simonov.troutfarm.backend.entity.FishBatch;
import by.simonov.troutfarm.backend.repository.FishBatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FishBatchService {
    private final FishBatchRepository repository;

    public List<FishBatch> findAll() {
        return repository.findAll();
    }

    public Optional<FishBatch> findById(UUID id) {
        return repository.findById(id);
    }

    public FishBatch save(FishBatch batch) {
        return repository.save(batch);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }
}
