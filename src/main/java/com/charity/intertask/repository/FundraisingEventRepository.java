package com.charity.intertask.repository;

import com.charity.intertask.model.FundraisingEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FundraisingEventRepository extends JpaRepository<FundraisingEvent, Long> {
}