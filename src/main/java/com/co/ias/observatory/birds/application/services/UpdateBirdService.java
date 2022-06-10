package com.co.ias.observatory.birds.application.services;

import com.co.ias.observatory.birds.application.domain.Bird;
import com.co.ias.observatory.birds.application.ports.input.UpdateBirdUseCase;
import com.co.ias.observatory.birds.application.ports.output.BirdRepository;
import com.co.ias.observatory.infrastructure.models.BirdDTO;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdateBirdService implements UpdateBirdUseCase {

    private final BirdRepository birdRepository;

    public UpdateBirdService(BirdRepository birdRepository) {
        this.birdRepository = birdRepository;
    }

    @Override
    public BirdDTO execute(BirdDTO birdDTO) {
        Bird bird = birdDTO.toDomain();
        Optional<Bird> birdOptional = birdRepository.get(bird.getId());

        if (birdOptional.isPresent()) {
            birdRepository.update(bird);
            birdDTO.setStatus("Updated");
        } else {
            birdDTO.setStatus("No updated");
        }
        return birdDTO;
    }
}
