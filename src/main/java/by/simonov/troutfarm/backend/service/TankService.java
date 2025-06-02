package by.simonov.troutfarm.backend.service;

import by.simonov.troutfarm.backend.entity.Tank;
import by.simonov.troutfarm.backend.repository.TankRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TankService {
    private final TankRepository repository;

    public List<Tank> findAll() {
        return repository.findAll();
    }

    public Optional<Tank> findById(UUID id) {
        return repository.findById(id);
    }

    public Tank save(Tank tank) {
        return repository.save(tank);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }
}

