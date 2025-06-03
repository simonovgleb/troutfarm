package by.simonov.troutfarm.backend.service.impl;

import by.simonov.troutfarm.backend.dto.mapper.EntityMapper;
import by.simonov.troutfarm.backend.dto.request.CreateTransferLogRequest;
import by.simonov.troutfarm.backend.dto.response.TransferLogDto;
import by.simonov.troutfarm.backend.entity.TransferLog;
import by.simonov.troutfarm.backend.repository.TransferLogRepository;
import by.simonov.troutfarm.backend.service.TransferLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransferLogServiceImpl implements TransferLogService {
    private final TransferLogRepository repository;
    private final EntityMapper mapper;

    @Override
    public List<TransferLogDto> findAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public TransferLogDto findById(UUID id) {
        return repository.findById(id).map(mapper::toDto).orElseThrow();
    }

    @Override
    public TransferLog save(TransferLog log) {
        return repository.save(log);
    }

    @Override
    public UUID create(CreateTransferLogRequest request) {
        return save(mapper.toEntity(request)).getId();
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public void update(UUID id, CreateTransferLogRequest request) {
        TransferLog entity = repository.findById(id).orElseThrow();
        mapper.update(request, entity);
        save(entity);
    }
}
