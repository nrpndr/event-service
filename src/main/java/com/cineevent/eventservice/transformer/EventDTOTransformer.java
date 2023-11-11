package com.cineevent.eventservice.transformer;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.util.Strings;
import org.springframework.util.CollectionUtils;

import com.cineevent.eventservice.dto.request.EventRequestDTO;
import com.cineevent.eventservice.dto.response.EventResponseDTO;
import com.cineevent.eventservice.model.Event;

public final class EventDTOTransformer {
	
	private EventDTOTransformer() {
		 throw new IllegalStateException("Tranformer class");
	}
	
	public static Event transformToEvent(EventRequestDTO eventRequestDTO) {
		Event event = new Event();
		
		String artistNames = String.join(",",eventRequestDTO.getArtistNames());
		
		event.setArtistNames(artistNames);
		event.setDescription(eventRequestDTO.getDescription());
		event.setEventDate(eventRequestDTO.getEventDate());
		event.setEventStartTime(eventRequestDTO.getEventStartTime());
		event.setName(eventRequestDTO.getName());
		event.setVenue(eventRequestDTO.getVenue());
		event.setEventDurationInMinutes(Integer.parseInt(eventRequestDTO.getEventDurationInMinutes()));
		
		return event;
	}

	public static EventResponseDTO transformToEventResponseDTO(Event event) {
		EventResponseDTO eventResponseDTO = new EventResponseDTO();
		
		eventResponseDTO.setEventId(event.getId());
		
		String[] artistNamesList = event.getArtistNames().split(",");
		List<String> artistNames = new ArrayList<>();
		for(String artistName : artistNamesList) {
			artistNames.add(artistName);
		}
		eventResponseDTO.setArtistNames(artistNames);
		eventResponseDTO.setDescription(event.getDescription());
		eventResponseDTO.setEventDate(event.getEventDate());
		eventResponseDTO.setEventStartTime(event.getEventStartTime());
		eventResponseDTO.setName(event.getName());
		eventResponseDTO.setVenue(event.getVenue());
		eventResponseDTO.setEventDurationInMinutes(event.getEventDurationInMinutes());
		
		return eventResponseDTO;
	}

	public static void updateEventFromDB(Event eventFromDB, EventRequestDTO eventRequestDTO) {
		
		if(!CollectionUtils.isEmpty(eventRequestDTO.getArtistNames()) ) {
			String artistNames = String.join(",",eventRequestDTO.getArtistNames());
			eventFromDB.setArtistNames(artistNames);
		}
		
		if(!Strings.isBlank(eventRequestDTO.getDescription())) {
			eventFromDB.setDescription(eventRequestDTO.getDescription());
		}
		
		if(!Strings.isBlank(eventRequestDTO.getEventDate())) {
			eventFromDB.setEventDate(eventRequestDTO.getEventDate());
		}
		
		if(!Strings.isBlank(eventRequestDTO.getEventStartTime())) {
			eventFromDB.setEventStartTime(eventRequestDTO.getEventStartTime());
		}

		if(!Strings.isBlank(eventRequestDTO.getName())) {
			eventFromDB.setName(eventRequestDTO.getName());
		}
		
		if(!Strings.isBlank(eventRequestDTO.getVenue())) {
			eventFromDB.setVenue(eventRequestDTO.getVenue());
		}
		
		String eventDurationInMinutes = eventRequestDTO.getEventDurationInMinutes();
		if(!Strings.isBlank(eventDurationInMinutes) && Integer.parseInt(eventDurationInMinutes) > 0) {
			eventFromDB.setEventDurationInMinutes(Integer.parseInt(eventRequestDTO.getEventDurationInMinutes()));
		}
	}

}
