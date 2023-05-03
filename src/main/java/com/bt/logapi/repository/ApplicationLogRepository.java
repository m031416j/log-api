package com.bt.logapi.repository;

import com.bt.logapi.model.entity.ApplicationLog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationLogRepository extends CrudRepository<ApplicationLog, Long> {

    List<ApplicationLog> findAllLogsByApplicationId(String id);


}
