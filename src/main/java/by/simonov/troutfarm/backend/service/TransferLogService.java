package by.simonov.troutfarm.backend.service;

import by.simonov.troutfarm.backend.dto.request.CreateTransferLogRequest;
import by.simonov.troutfarm.backend.dto.response.TransferLogDto;
import by.simonov.troutfarm.backend.entity.TransferLog;
import by.simonov.troutfarm.backend.entity.security.UserPrincipal;

import java.util.List;
import java.util.UUID;

public interface TransferLogService {
    List<TransferLogDto> findAll(UserPrincipal principal);

    TransferLogDto findById(UUID id);

    TransferLog save(TransferLog log);

    UUID create(CreateTransferLogRequest request);

    void delete(UUID id);

    void update(UUID id, CreateTransferLogRequest request);
}
