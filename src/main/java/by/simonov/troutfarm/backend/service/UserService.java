package by.simonov.troutfarm.backend.service;

import by.simonov.troutfarm.backend.dto.mapper.EntityMapper;
import by.simonov.troutfarm.backend.dto.request.CreateUserRequest;
import by.simonov.troutfarm.backend.dto.response.UserDto;
import by.simonov.troutfarm.backend.entity.User;
import by.simonov.troutfarm.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final EntityMapper mapper;

    public List<UserDto> findAll() {
        return userRepository.findAll().stream().map(mapper::toDto).toList();
    }

    public UserDto findById(UUID id) {
        return userRepository.findById(id).map(mapper::toDto).orElseThrow();
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public UUID create(CreateUserRequest request) {
        return save(mapper.toEntity(request)).getId();
    }

    public void delete(UUID id) {
        userRepository.deleteById(id);
    }

    public void update(UUID id, CreateUserRequest request) {
        User entity = userRepository.findById(id).orElseThrow();
        mapper.update(request, entity);
        save(entity);
    }
}
