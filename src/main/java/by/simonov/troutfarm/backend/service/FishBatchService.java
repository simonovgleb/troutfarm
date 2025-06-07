package by.simonov.troutfarm.backend.service;

import by.simonov.troutfarm.backend.dto.request.CreateFishBatchRequest;
import by.simonov.troutfarm.backend.dto.response.FishBatchDto;
import by.simonov.troutfarm.backend.entity.FishBatch;

import java.util.List;
import java.util.UUID;

public interface FishBatchService {
    List<FishBatchDto> findAll();

    FishBatchDto getById(UUID id);

    FishBatch findById(UUID id);

    FishBatch save(FishBatch batch);

    UUID create(CreateFishBatchRequest request);

    void delete(UUID id);

    void update(UUID id, CreateFishBatchRequest request);
}
