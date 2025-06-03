package by.simonov.troutfarm.backend.service;

import by.simonov.troutfarm.backend.dto.mapper.EntityMapper;
import by.simonov.troutfarm.backend.dto.request.CreateFeedingLogRequest;
import by.simonov.troutfarm.backend.dto.response.FeedingLogDto;
import by.simonov.troutfarm.backend.entity.FeedingLog;
import by.simonov.troutfarm.backend.repository.FeedingLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FeedingLogService {
    private final FeedingLogRepository repository;
    private final EntityMapper mapper;

    public List<FeedingLogDto> findAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    public FeedingLogDto findById(UUID id) {
        return repository.findById(id).map(mapper::toDto).orElseThrow();
    }

    public FeedingLog save(FeedingLog log) {
        return repository.save(log);
    }

    public UUID create(CreateFeedingLogRequest request) {
        return save(mapper.toEntity(request)).getId();
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }

    public void update(UUID id, CreateFeedingLogRequest request) {
        FeedingLog entity = repository.findById(id).orElseThrow();
        mapper.update(request, entity);
        save(entity);
    }
}

