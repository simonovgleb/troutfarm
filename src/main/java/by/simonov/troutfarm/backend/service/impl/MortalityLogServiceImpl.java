package by.simonov.troutfarm.backend.service.impl;

import by.simonov.troutfarm.backend.dto.mapper.EntityMapper;
import by.simonov.troutfarm.backend.dto.request.CreateMortalityLogRequest;
import by.simonov.troutfarm.backend.dto.response.MortalityLogDto;
import by.simonov.troutfarm.backend.entity.MortalityLog;
import by.simonov.troutfarm.backend.repository.MortalityLogRepository;
import by.simonov.troutfarm.backend.service.MortalityLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MortalityLogServiceImpl implements MortalityLogService {
    private final MortalityLogRepository repository;
    private final EntityMapper mapper;

    @Override
    public List<MortalityLogDto> findAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public MortalityLogDto findById(UUID id) {
        return repository.findById(id).map(mapper::toDto).orElseThrow();
    }

    @Override
    public MortalityLog save(MortalityLog log) {
        return repository.save(log);
    }

    @Override
    public UUID create(CreateMortalityLogRequest request) {
        return save(mapper.toEntity(request)).getId();
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public void update(UUID id, CreateMortalityLogRequest request) {
        MortalityLog entity = repository.findById(id).orElseThrow();
        mapper.update(request, entity);
        save(entity);
    }
}
