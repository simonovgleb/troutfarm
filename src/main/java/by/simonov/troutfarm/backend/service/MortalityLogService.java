package by.simonov.troutfarm.backend.service;

import by.simonov.troutfarm.backend.dto.request.CreateMortalityLogRequest;
import by.simonov.troutfarm.backend.dto.response.MortalityLogDto;
import by.simonov.troutfarm.backend.entity.MortalityLog;
import by.simonov.troutfarm.backend.entity.security.UserPrincipal;

import java.util.List;
import java.util.UUID;

public interface MortalityLogService {
    List<MortalityLogDto> findAll(UserPrincipal principal);

    MortalityLogDto findById(UUID id);

    MortalityLog save(MortalityLog log);

    UUID create(CreateMortalityLogRequest request);

    void delete(UUID id);

    void update(UUID id, CreateMortalityLogRequest request);
}
