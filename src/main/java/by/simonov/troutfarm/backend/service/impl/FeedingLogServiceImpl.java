package by.simonov.troutfarm.backend.service.impl;

import by.simonov.troutfarm.backend.dto.mapper.EntityMapper;
import by.simonov.troutfarm.backend.dto.request.CreateFeedingLogRequest;
import by.simonov.troutfarm.backend.dto.response.FeedingLogDto;
import by.simonov.troutfarm.backend.entity.FeedingLog;
import by.simonov.troutfarm.backend.entity.security.UserPrincipal;
import by.simonov.troutfarm.backend.entity.type.Role;
import by.simonov.troutfarm.backend.repository.FeedingLogRepository;
import by.simonov.troutfarm.backend.service.FeedingLogService;
import by.simonov.troutfarm.backend.service.FishBatchService;
import by.simonov.troutfarm.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FeedingLogServiceImpl implements FeedingLogService {
    private final FeedingLogRepository repository;
    private final EntityMapper mapper;
    private final UserService userService;
    private final FishBatchService batchService;

    @Override
    public List<FeedingLogDto> findAll(UserPrincipal principal) {
        List<FeedingLog> logs;
        if (principal.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_" + Role.ADMIN))) {
            logs = repository.findAll();
        } else {
            logs = repository.findAllByOperatorId(principal.getId());
        }
        return logs.stream().map(mapper::toDto).toList();
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
        FeedingLog log = mapper.toEntity(request);

        log.setBatch(batchService.findById(request.batchId()));
        log.setOperator(userService.findById(request.operatorId()));

        return save(log).getId();
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public void update(UUID id, CreateFeedingLogRequest request) {
        FeedingLog entity = repository.findById(id).orElseThrow();
        mapper.update(request, entity);

        if (!entity.getOperator().getId().equals(request.operatorId())) {
            entity.setOperator(userService.findById(request.operatorId()));
        }

        if (!entity.getBatch().getId().equals(request.batchId())) {
            entity.setBatch(batchService.findById(request.batchId()));
        }

        save(entity);
    }
}

