package by.simonov.troutfarm.backend.service.impl;

import by.simonov.troutfarm.backend.dto.mapper.EntityMapper;
import by.simonov.troutfarm.backend.dto.request.CreateFishBatchRequest;
import by.simonov.troutfarm.backend.dto.response.FishBatchDto;
import by.simonov.troutfarm.backend.entity.FishBatch;
import by.simonov.troutfarm.backend.repository.FishBatchRepository;
import by.simonov.troutfarm.backend.service.FishBatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FishBatchServiceImpl implements FishBatchService {
    private final FishBatchRepository repository;
    private final EntityMapper mapper;

    @Override
    public List<FishBatchDto> findAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public FishBatchDto getById(UUID id) {
        return mapper.toDto(findById(id));
    }

    @Override
    public FishBatch findById(UUID id) {
        return repository.findById(id).orElseThrow();
    }

    @Override
    public FishBatch save(FishBatch batch) {
        return repository.save(batch);
    }

    @Override
    public UUID create(CreateFishBatchRequest request) {
        return save(mapper.toEntity(request)).getId();
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public void update(UUID id, CreateFishBatchRequest request) {
        FishBatch entity = repository.findById(id).orElseThrow();
        mapper.update(request, entity);
        save(entity);
    }
}
