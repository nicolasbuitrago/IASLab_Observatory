package com.co.ias.observatory.birds.application.services;

import com.co.ias.observatory.birds.application.domain.Bird;
import com.co.ias.observatory.birds.application.domain.valueObjs.BirdId;
import com.co.ias.observatory.birds.application.ports.input.DeleteBirdUseCase;
import com.co.ias.observatory.birds.application.ports.output.BirdRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeleteBirdService implements DeleteBirdUseCase {

    private final BirdRepository birdRepository;

    public DeleteBirdService(BirdRepository birdRepository) {
        this.birdRepository = birdRepository;
    }

    @Override
    public Boolean execute(Long id) {
        BirdId birdId = new BirdId(id);
        Optional<Bird> birdOptional = birdRepository.get(birdId);
        if(birdOptional.isPresent()) {
            birdRepository.delete(birdId);
        }
        return birdOptional.isPresent();
    }
}
