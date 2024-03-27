package dev.mehdi.connectly.mapper;

import dev.mehdi.connectly.dto.report.ReportResponseDto;
import dev.mehdi.connectly.model.Report;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        builder = @Builder(disableBuilder = true),
        uses = { MemberMapper.class, PostMapper.class }
)
public interface ReportMapper {
    ReportResponseDto toDto(Report report);
}
