package by.simonov.troutfarm.backend.service.impl;

import by.simonov.troutfarm.backend.dto.filter.UserFilter;
import by.simonov.troutfarm.backend.dto.mapper.EntityMapper;
import by.simonov.troutfarm.backend.dto.request.CreateUserRequest;
import by.simonov.troutfarm.backend.dto.response.UserDto;
import by.simonov.troutfarm.backend.entity.User;
import by.simonov.troutfarm.backend.repository.UserRepository;
import by.simonov.troutfarm.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final EntityMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserDto> findAll(UserFilter filter) {
        List<User> users;
        if (filter.hasRole()) {
            users = userRepository.findAllByRoleIn(filter.role());
        } else {
            users = userRepository.findAll();
        }
        return users.stream().map(mapper::toDto).toList();
    }

    @Override
    public UserDto getById(UUID id) {
        return mapper.toDto(findById(id));
    }

    @Override
    public User findById(UUID id) {
        return userRepository.findById(id).orElseThrow();
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public UUID create(CreateUserRequest request) {
        User user = mapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.password()));
        return save(user).getId();
    }

    @Override
    public void delete(UUID id) {
        userRepository.deleteById(id);
    }

    @Override
    public void update(UUID id, CreateUserRequest request) {
        User entity = userRepository.findById(id).orElseThrow();
        mapper.update(request, entity);
        if (StringUtils.isNotEmpty(request.password())) {
            entity.setPassword(passwordEncoder.encode(request.password()));
        }
        save(entity);
    }
}
