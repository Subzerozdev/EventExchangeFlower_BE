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
                .build(); // map đói tượng DTO thành type   ( nhớ là phải có @Builder )
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
        existingType.setName(typeRequestDTO.getName());   // category thì chỉ có mỗi trường name mà thôi
        typeRepository.save(existingType);   // update xong  phải save lại nhé
    }

    @Override
    public void deleteType(long id) {
        // xóa xong
        typeRepository.deleteById(id);
    }
}
