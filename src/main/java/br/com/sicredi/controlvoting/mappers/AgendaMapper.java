package br.com.sicredi.controlvoting.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import br.com.sicredi.controlvoting.dto.AgendaRequest;
import br.com.sicredi.controlvoting.dto.AgendaResponse;
import br.com.sicredi.controlvoting.entity.Agenda;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface AgendaMapper {

	AgendaMapper INSTANCE = Mappers.getMapper(AgendaMapper.class);

	public Agenda convertFromRequest(AgendaRequest agendaRequest);

	public List<AgendaResponse> convertToAgendaResponses(List<Agenda> agendas);

	public AgendaResponse convertToAgendaResponse(Agenda agenda);

}
