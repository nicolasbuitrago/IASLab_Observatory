package com.co.ias.observatory.birds.application.services;

import com.co.ias.observatory.birds.application.domain.Bird;
import com.co.ias.observatory.birds.application.domain.valueObjs.BirdCommonName;
import com.co.ias.observatory.birds.application.domain.valueObjs.BirdConfirmedQuantity;
import com.co.ias.observatory.birds.application.domain.valueObjs.BirdScientificName;
import com.co.ias.observatory.birds.application.domain.valueObjs.BirdZoneName;
import com.co.ias.observatory.birds.application.ports.input.CreateBirdUseCase;
import com.co.ias.observatory.birds.application.ports.output.BirdRepository;
import com.co.ias.observatory.infrastructure.models.BirdDTO;
import org.springframework.stereotype.Service;

@Service
public class CreateBirdService implements CreateBirdUseCase {

    private final BirdRepository birdRepository;

    public CreateBirdService(BirdRepository birdRepository) {
        this.birdRepository = birdRepository;
    }

    @Override
    public BirdDTO execute(BirdDTO birdDTO) {
        Bird bird = new Bird(
                null,
                new BirdCommonName(birdDTO.getCommonName()),
                new BirdScientificName(birdDTO.getScientificName()),
                new BirdZoneName(birdDTO.getZoneName()),
                new BirdConfirmedQuantity(birdDTO.getConfirmedQuantity())
        );
        birdRepository.store(bird);
        birdDTO.setStatus("Created");
        return birdDTO;
    }
}
