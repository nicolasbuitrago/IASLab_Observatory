package com.co.ias.observatory.birds.application.ports.input;

import com.co.ias.observatory.commons.UseCase;
import com.co.ias.observatory.infrastructure.models.BirdDTO;

import java.util.Optional;

public interface QueryBirdByIdUseCase extends UseCase<Long, Optional<BirdDTO>> {
}
