package com.eventflowerexchange.service;

import com.eventflowerexchange.dto.request.EventCategoryRequestDTO;
import com.eventflowerexchange.dto.request.TypeRequestDTO;
import com.eventflowerexchange.entity.Category;
import com.eventflowerexchange.entity.Type;

import java.util.List;

public interface TypeService {

    void createType(TypeRequestDTO typeRequestDTO);

    Type getTypeById(long id);

    List<Type> getAllTypes();

    Type updateType(long id, TypeRequestDTO typeRequestDTO);

    void deleteType(long id);
}
