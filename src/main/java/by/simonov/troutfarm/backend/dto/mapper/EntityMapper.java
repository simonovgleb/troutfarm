package by.simonov.troutfarm.backend.dto.mapper;

import by.simonov.troutfarm.backend.dto.request.CreateFeedingLogRequest;
import by.simonov.troutfarm.backend.dto.request.CreateFishBatchRequest;
import by.simonov.troutfarm.backend.dto.request.CreateMortalityLogRequest;
import by.simonov.troutfarm.backend.dto.request.CreateTankRequest;
import by.simonov.troutfarm.backend.dto.request.CreateTaskRequest;
import by.simonov.troutfarm.backend.dto.request.CreateTransferLogRequest;
import by.simonov.troutfarm.backend.dto.request.CreateUserRequest;
import by.simonov.troutfarm.backend.dto.response.FeedingLogDto;
import by.simonov.troutfarm.backend.dto.response.FishBatchDto;
import by.simonov.troutfarm.backend.dto.response.MortalityLogDto;
import by.simonov.troutfarm.backend.dto.response.TankDto;
import by.simonov.troutfarm.backend.dto.response.TaskDto;
import by.simonov.troutfarm.backend.dto.response.TransferLogDto;
import by.simonov.troutfarm.backend.dto.response.UserDto;
import by.simonov.troutfarm.backend.entity.FeedingLog;
import by.simonov.troutfarm.backend.entity.FishBatch;
import by.simonov.troutfarm.backend.entity.MortalityLog;
import by.simonov.troutfarm.backend.entity.Tank;
import by.simonov.troutfarm.backend.entity.Task;
import by.simonov.troutfarm.backend.entity.TransferLog;
import by.simonov.troutfarm.backend.entity.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface EntityMapper {
    @Mappings({
            @Mapping(target = "active", source = "isActive"),
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "password", ignore = true),
    })
    User toEntity(CreateUserRequest request);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "fromTank", ignore = true),
            @Mapping(target = "toTank", ignore = true),
            @Mapping(target = "operator", ignore = true),
            @Mapping(target = "batch", ignore = true),
    })
    TransferLog toEntity(CreateTransferLogRequest request);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "assignedTo", ignore = true),
            @Mapping(target = "createdBy", ignore = true),
    })
    Task toEntity(CreateTaskRequest request);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "batch", ignore = true),
    })
    Tank toEntity(CreateTankRequest request);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "batch", ignore = true),
            @Mapping(target = "operator", ignore = true),
    })
    MortalityLog toEntity(CreateMortalityLogRequest request);

    @Mappings({
            @Mapping(target = "id", ignore = true),
    })
    FishBatch toEntity(CreateFishBatchRequest request);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "batch", ignore = true),
            @Mapping(target = "operator", ignore = true),
    })
    FeedingLog toEntity(CreateFeedingLogRequest request);

    UserDto toDto(User user);

    TransferLogDto toDto(TransferLog transferLog);

    TaskDto toDto(Task task);

    TankDto toDto(Tank tank);

    MortalityLogDto toDto(MortalityLog mortalityLog);

    FishBatchDto toDto(FishBatch fishBatch);

    FeedingLogDto toDto(FeedingLog feedingLog);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mappings({
            @Mapping(target = "active", source = "isActive"),
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "password", ignore = true),
    })
    void update(CreateUserRequest request, @MappingTarget User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "fromTank", ignore = true),
            @Mapping(target = "toTank", ignore = true),
            @Mapping(target = "operator", ignore = true),
            @Mapping(target = "batch", ignore = true),
    })
    void update(CreateTransferLogRequest request, @MappingTarget TransferLog transferLog);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "assignedTo", ignore = true),
            @Mapping(target = "createdBy", ignore = true),
    })
    void update(CreateTaskRequest request, @MappingTarget Task task);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "batch", ignore = true),
    })
    void update(CreateTankRequest request, @MappingTarget Tank entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "batch", ignore = true),
            @Mapping(target = "operator", ignore = true),
    })
    void update(CreateMortalityLogRequest request, @MappingTarget MortalityLog mortalityLog);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mappings({
            @Mapping(target = "id", ignore = true),
    })
    void update(CreateFishBatchRequest request, @MappingTarget FishBatch fishBatch);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "batch", ignore = true),
            @Mapping(target = "operator", ignore = true),
    })
    void update(CreateFeedingLogRequest request, @MappingTarget FeedingLog feedingLog);
}
