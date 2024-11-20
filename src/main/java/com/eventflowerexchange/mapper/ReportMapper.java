package com.eventflowerexchange.mapper;

import com.eventflowerexchange.dto.response.ApplicationResponseDTO;
import com.eventflowerexchange.entity.Application;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReportMapper {
    ApplicationResponseDTO toReportResponseDTO(Application application);
}
