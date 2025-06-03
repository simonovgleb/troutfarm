package by.simonov.troutfarm.backend.service;

import by.simonov.troutfarm.backend.dto.request.CreateTankRequest;
import by.simonov.troutfarm.backend.dto.response.TankDto;
import by.simonov.troutfarm.backend.entity.Tank;

import java.util.List;
import java.util.UUID;

public interface TankService {
    List<TankDto> findAll();

    TankDto findById(UUID id);

    Tank save(Tank tank);

    UUID create(CreateTankRequest request);

    void delete(UUID id);

    void update(UUID id, CreateTankRequest request);
}
