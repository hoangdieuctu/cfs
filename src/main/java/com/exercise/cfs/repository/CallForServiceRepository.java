package com.exercise.cfs.repository;

import com.exercise.cfs.model.CallForService;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CallForServiceRepository extends CrudRepository<CallForService, UUID>, JpaSpecificationExecutor<CallForService> {
}
