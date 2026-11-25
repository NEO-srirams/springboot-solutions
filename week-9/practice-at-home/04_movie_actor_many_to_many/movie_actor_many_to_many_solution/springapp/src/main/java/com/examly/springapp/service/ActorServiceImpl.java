package com.examly.springapp.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.examly.springapp.model.Actor;
import com.examly.springapp.repository.ActorRepository;

@Service
public class ActorServiceImpl implements ActorService {

    private final ActorRepository actorRepository;

    public ActorServiceImpl(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    @Override
    public Actor saveActor(Actor actor) {
        return actorRepository.save(actor);
    }

    @Override
    public Optional<Actor> getActorById(Long id) {
        return actorRepository.findById(id);
    }
}
