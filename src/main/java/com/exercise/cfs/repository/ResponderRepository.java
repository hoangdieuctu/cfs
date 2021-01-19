package com.exercise.cfs.repository;

import com.exercise.cfs.model.Responder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ResponderRepository extends CrudRepository<Responder, UUID> {
}
