package by.simonov.troutfarm.backend.service.impl;

import by.simonov.troutfarm.backend.dto.mapper.EntityMapper;
import by.simonov.troutfarm.backend.dto.request.CreateTransferLogRequest;
import by.simonov.troutfarm.backend.dto.response.TransferLogDto;
import by.simonov.troutfarm.backend.entity.TransferLog;
import by.simonov.troutfarm.backend.entity.security.UserPrincipal;
import by.simonov.troutfarm.backend.entity.type.Role;
import by.simonov.troutfarm.backend.repository.TransferLogRepository;
import by.simonov.troutfarm.backend.service.FishBatchService;
import by.simonov.troutfarm.backend.service.TankService;
import by.simonov.troutfarm.backend.service.TransferLogService;
import by.simonov.troutfarm.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransferLogServiceImpl implements TransferLogService {
    private final TransferLogRepository repository;
    private final EntityMapper mapper;
    private final UserService userService;
    private final FishBatchService batchService;
    private final TankService tankService;

    @Override
    public List<TransferLogDto> findAll(UserPrincipal principal) {
        List<TransferLog> logs;

        if (principal.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_" + Role.ADMIN))) {
            logs = repository.findAll();
        } else {
            logs = repository.findAllByOperatorId(principal.getId());
        }

        return logs.stream().map(mapper::toDto).toList();
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
        TransferLog entity = mapper.toEntity(request);

        entity.setBatch(batchService.findById(request.batchId()));
        entity.setOperator(userService.findById(request.operatorId()));
        entity.setFromTank(tankService.findById(request.fromTankId()));
        entity.setToTank(tankService.findById(request.toTankId()));

        return save(entity).getId();
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public void update(UUID id, CreateTransferLogRequest request) {
        TransferLog entity = repository.findById(id).orElseThrow();
        mapper.update(request, entity);

        if (!entity.getBatch().getId().equals(request.batchId())) {
            entity.setBatch(batchService.findById(request.batchId()));
        }
        if (!entity.getOperator().getId().equals(request.operatorId())) {
            entity.setOperator(userService.findById(request.operatorId()));
        }
        if (!entity.getFromTank().getId().equals(request.fromTankId())) {
            entity.setFromTank(tankService.findById(request.fromTankId()));
        }
        if (!entity.getToTank().getId().equals(request.toTankId())) {
            entity.setToTank(tankService.findById(request.toTankId()));
        }

        save(entity);
    }
}
