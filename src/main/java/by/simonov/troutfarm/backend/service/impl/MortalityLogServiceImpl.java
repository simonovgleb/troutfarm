package by.simonov.troutfarm.backend.service.impl;

import by.simonov.troutfarm.backend.dto.mapper.EntityMapper;
import by.simonov.troutfarm.backend.dto.request.CreateMortalityLogRequest;
import by.simonov.troutfarm.backend.dto.response.MortalityLogDto;
import by.simonov.troutfarm.backend.entity.MortalityLog;
import by.simonov.troutfarm.backend.entity.security.UserPrincipal;
import by.simonov.troutfarm.backend.entity.type.Role;
import by.simonov.troutfarm.backend.repository.MortalityLogRepository;
import by.simonov.troutfarm.backend.service.FishBatchService;
import by.simonov.troutfarm.backend.service.MortalityLogService;
import by.simonov.troutfarm.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MortalityLogServiceImpl implements MortalityLogService {
    private final MortalityLogRepository repository;
    private final EntityMapper mapper;
    private final FishBatchService batchService;
    private final UserService userService;

    @Override
    public List<MortalityLogDto> findAll(UserPrincipal principal) {
        List<MortalityLog> logs;
        if (principal.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_" + Role.ADMIN))) {
            logs = repository.findAll();
        } else {
            logs = repository.findAllByOperatorId(principal.getId());
        }
        return logs.stream().map(mapper::toDto).toList();
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
        MortalityLog log = mapper.toEntity(request);

        log.setBatch(batchService.findById(request.batchId()));
        log.setOperator(userService.findById(request.operatorId()));

        return save(log).getId();
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public void update(UUID id, CreateMortalityLogRequest request) {
        MortalityLog entity = repository.findById(id).orElseThrow();
        mapper.update(request, entity);

        if (!entity.getBatch().getId().equals(request.batchId())) {
            entity.setBatch(batchService.findById(request.batchId()));
        }
        if (!entity.getOperator().getId().equals(request.operatorId())) {
            entity.setOperator(userService.findById(request.operatorId()));
        }

        save(entity);
    }
}
