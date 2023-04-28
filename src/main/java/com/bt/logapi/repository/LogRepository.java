package com.bt.logapi.repository;

import com.bt.logapi.model.entity.ApplicationLogs;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogRepository extends CrudRepository<ApplicationLogs, Long> {

    List<ApplicationLogs> findAllLogsByApplicationId(String id);


}
