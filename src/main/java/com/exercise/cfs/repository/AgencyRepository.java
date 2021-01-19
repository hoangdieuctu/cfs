package com.exercise.cfs.repository;

import com.exercise.cfs.model.Agency;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AgencyRepository extends CrudRepository<Agency, UUID> {
}
