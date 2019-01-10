package com.kundu.customerstatement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kundu.customerstatement.entity.CustomerStatement;

/**
 * This class is used as repository of CustomerStatement entity. It saves,
 * updates or queries of the CustomerStatement data.
 * 
 * @author ukundukan
 *
 */
@Repository
public interface CustomerStatementRepository extends JpaRepository<CustomerStatement, Long> {

}
