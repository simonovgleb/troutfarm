package by.simonov.troutfarm.backend.service.impl;

import by.simonov.troutfarm.backend.dto.mapper.EntityMapper;
import by.simonov.troutfarm.backend.dto.request.CreateTankRequest;
import by.simonov.troutfarm.backend.dto.response.TankDto;
import by.simonov.troutfarm.backend.entity.Tank;
import by.simonov.troutfarm.backend.repository.TankRepository;
import by.simonov.troutfarm.backend.service.TankService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TankServiceImpl implements TankService {
    private final TankRepository repository;
    private final EntityMapper mapper;

    @Override
    public List<TankDto> findAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Override
    public TankDto findById(UUID id) {
        return repository.findById(id).map(mapper::toDto).orElseThrow();
    }

    @Override
    public Tank save(Tank tank) {
        return repository.save(tank);
    }

    @Override
    public UUID create(CreateTankRequest request) {
        return save(mapper.toEntity(request)).getId();
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public void update(UUID id, CreateTankRequest request) {
        Tank entity = repository.findById(id).orElseThrow();
        mapper.update(request, entity);
        save(entity);
    }

}

