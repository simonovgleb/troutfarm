package by.simonov.troutfarm.backend.service;

import by.simonov.troutfarm.backend.dto.mapper.EntityMapper;
import by.simonov.troutfarm.backend.dto.request.CreateFishBatchRequest;
import by.simonov.troutfarm.backend.dto.response.FishBatchDto;
import by.simonov.troutfarm.backend.entity.FishBatch;
import by.simonov.troutfarm.backend.repository.FishBatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FishBatchService {
    private final FishBatchRepository repository;
    private final EntityMapper mapper;

    public List<FishBatchDto> findAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    public FishBatchDto findById(UUID id) {
        return repository.findById(id).map(mapper::toDto).orElseThrow();
    }

    public FishBatch save(FishBatch batch) {
        return repository.save(batch);
    }

    public UUID create(CreateFishBatchRequest request) {
        return save(mapper.toEntity(request)).getId();
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }

    public void update(UUID id, CreateFishBatchRequest request) {
        FishBatch entity = repository.findById(id).orElseThrow();
        mapper.update(request, entity);
        save(entity);
    }
}
