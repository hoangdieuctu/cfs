package com.exercise.cfs.repository;

import com.exercise.cfs.model.Dispatcher;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DispatcherRepository extends CrudRepository<Dispatcher, UUID> {
}
