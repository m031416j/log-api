package com.bt.logapi.repository;

import com.bt.logapi.model.entity.ApplicationLogs;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogRepository extends CrudRepository<ApplicationLogs, String> {

    List<ApplicationLogs> findAllLogsByApplicationId(String id);


}
