package by.simonov.troutfarm.backend.service;

import by.simonov.troutfarm.backend.dto.filter.UserFilter;
import by.simonov.troutfarm.backend.dto.request.CreateUserRequest;
import by.simonov.troutfarm.backend.dto.response.UserDto;
import by.simonov.troutfarm.backend.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<UserDto> findAll(UserFilter filter);

    User findById(UUID id);

    UserDto getById(UUID id);

    User save(User user);

    UUID create(CreateUserRequest request);

    void delete(UUID id);

    void update(UUID id, CreateUserRequest request);
}
