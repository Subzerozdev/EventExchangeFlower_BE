package com.eventflowerexchange.service.impl;

import com.eventflowerexchange.dto.request.TypeRequestDTO;
import com.eventflowerexchange.entity.Type;
import com.eventflowerexchange.repository.TypeRepository;
import com.eventflowerexchange.service.TypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TypeServiceImpl implements TypeService {
    private final TypeRepository typeRepository;

    @Override
    public void createType(TypeRequestDTO typeRequestDTO) {
        Type newType = Type.builder()
                .name(typeRequestDTO.getName())
                .build();
        typeRepository.save(newType);
    }

    @Override
    public Type getTypeById(long id) {
        return typeRepository.findById(id).orElseThrow( () -> new RuntimeException("Type is not found"));
    }

    @Override
    public List<Type> getAllTypes() {
        return typeRepository.findAll();
    }

    @Override
    public void updateType(long id, TypeRequestDTO typeRequestDTO) {
        Type existingType = getTypeById(id);
        existingType.setName(typeRequestDTO.getName());
        typeRepository.save(existingType);
    }

    @Override
    public void deleteType(long id) {
        typeRepository.deleteById(id);
    }
}
