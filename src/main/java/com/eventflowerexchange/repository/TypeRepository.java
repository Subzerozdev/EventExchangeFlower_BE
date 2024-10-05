package com.eventflowerexchange.repository;

import com.eventflowerexchange.entity.Category;
import com.eventflowerexchange.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeRepository  extends JpaRepository<Type, Long> {


}
