package com.webapp.vascomm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.webapp.vascomm.table.log_response;

/*
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
*/
public interface log_response_rep extends JpaRepository<log_response, Integer> {
    
    
}
