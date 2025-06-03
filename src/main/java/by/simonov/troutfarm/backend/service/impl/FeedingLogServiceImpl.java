package by.simonov.troutfarm.backend.service.impl;

import by.simonov.troutfarm.backend.dto.mapper.EntityMapper;
import by.simonov.troutfarm.backend.dto.request.CreateFeedingLogRequest;
import by.simonov.troutfarm.backend.dto.response.FeedingLogDto;
import by.simonov.troutfarm.backend.entity.FeedingLog;
import by.simonov.troutfarm.backend.repository.FeedingLogRepository;
import by.simonov.troutfarm.backend.service.FeedingLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FeedingLogServiceImpl implements FeedingLogService {
    private final FeedingLogRepository repository;
    private final EntityMapper mapper;

    @Override
    public List<FeedingLogDto> findAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public FeedingLogDto findById(UUID id) {
        return repository.findById(id).map(mapper::toDto).orElseThrow();
    }

    @Override
    public FeedingLog save(FeedingLog log) {
        return repository.save(log);
    }

    @Override
    public UUID create(CreateFeedingLogRequest request) {
        return save(mapper.toEntity(request)).getId();
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public void update(UUID id, CreateFeedingLogRequest request) {
        FeedingLog entity = repository.findById(id).orElseThrow();
        mapper.update(request, entity);
        save(entity);
    }
}

