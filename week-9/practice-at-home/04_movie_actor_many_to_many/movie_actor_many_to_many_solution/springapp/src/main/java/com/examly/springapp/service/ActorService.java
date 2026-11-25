package com.examly.springapp.service;

import java.util.Optional;

import com.examly.springapp.model.Actor;

public interface ActorService {

    Actor saveActor(Actor actor);

    Optional<Actor> getActorById(Long id);
}
