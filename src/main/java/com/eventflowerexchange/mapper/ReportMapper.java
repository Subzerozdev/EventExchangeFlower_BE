package com.eventflowerexchange.mapper;

import com.eventflowerexchange.dto.response.ReportResponseDTO;
import com.eventflowerexchange.entity.Report;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReportMapper {
    ReportResponseDTO toReportResponseDTO(Report report);
}
