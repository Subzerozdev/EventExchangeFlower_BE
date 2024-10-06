package com.eventflowerexchange.repository;

import com.eventflowerexchange.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TypeRepository  extends JpaRepository<Type, Long> {

    List<Type> findByIdIn(List<Long> typeID);

}
