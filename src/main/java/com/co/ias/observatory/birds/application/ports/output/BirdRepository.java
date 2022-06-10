package com.co.ias.observatory.birds.application.ports.output;

import com.co.ias.observatory.birds.application.domain.Bird;
import com.co.ias.observatory.birds.application.domain.valueObjs.BirdId;

import java.util.Optional;

public interface BirdRepository {

    void store(Bird bird);

    Optional<Bird> get(BirdId birdId);

    void update(Bird bird);

    boolean delete(BirdId birdId);

}
