package by.simonov.troutfarm.backend.service;

import by.simonov.troutfarm.backend.dto.request.CreateFeedingLogRequest;
import by.simonov.troutfarm.backend.dto.response.FeedingLogDto;
import by.simonov.troutfarm.backend.entity.FeedingLog;
import by.simonov.troutfarm.backend.entity.security.UserPrincipal;

import java.util.List;
import java.util.UUID;

public interface FeedingLogService {
    List<FeedingLogDto> findAll(UserPrincipal principal);

    FeedingLogDto findById(UUID id);

    FeedingLog save(FeedingLog log);

    UUID create(CreateFeedingLogRequest request);

    void delete(UUID id);

    void update(UUID id, CreateFeedingLogRequest request);
}
