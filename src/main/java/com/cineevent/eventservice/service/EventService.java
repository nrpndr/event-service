package com.cineevent.eventservice.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.cineevent.eventservice.dto.request.EventRequestDTO;
import com.cineevent.eventservice.dto.response.EventResponseDTO;
import com.cineevent.eventservice.exceptions.EventDoesNotExistException;
import com.cineevent.eventservice.exceptions.InValidUserInputException;
import com.cineevent.eventservice.model.Event;
import com.cineevent.eventservice.repository.EventRepository;
import com.cineevent.eventservice.transformer.EventDTOTransformer;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class EventService {

	@Autowired
	private EventRepository eventRepository;
	
	private static String timeFormat = "^([1-9]|0[1-9]|1[0-2]):[0-5][0-9] ([AaPp][Mm])$";
	private Pattern pattern = Pattern.compile(timeFormat);
	
	public EventResponseDTO createEvent(EventRequestDTO eventRequestDTO) {
	   
	   validateInput(eventRequestDTO, false);
	   Event event = EventDTOTransformer.transformToEvent(eventRequestDTO);
	   eventRepository.save(event);
	   
	   return EventDTOTransformer.transformToEventResponseDTO(event);
	}
	
	public EventResponseDTO createOrUpdateEvent(int eventId, EventRequestDTO eventRequestDTO) {
		validateInput(eventRequestDTO, false);
		
		Event event = eventRepository.findById(eventId).orElse(null);
		
		if(event != null) {
			//Updating the event
			EventDTOTransformer.updateEventFromDB(event, eventRequestDTO);
		}else {
			//Creating an event
			event = EventDTOTransformer.transformToEvent(eventRequestDTO);
			event.setId(eventId);
		}
		
		eventRepository.save(event);

		return EventDTOTransformer.transformToEventResponseDTO(event);
	}
	
	public EventResponseDTO updateEvent(int eventId, EventRequestDTO eventRequestDTO) {
		validateInput(eventRequestDTO, true);
		
		Event eventFromDB = eventRepository.findById(eventId).orElse(null);
		if(eventFromDB == null) {
			throw constructEventDoesNotExistException(eventId);
		}
		
		EventDTOTransformer.updateEventFromDB(eventFromDB, eventRequestDTO);
		eventRepository.save(eventFromDB);
		return EventDTOTransformer.transformToEventResponseDTO(eventFromDB);
	}
	
	private void validateInput(EventRequestDTO eventRequestDTO, boolean isPartialUpdate) {
		
		if (eventRequestDTO == null) {
			throw new InValidUserInputException("EventRequest Input cannot be null");
		}
		
		String eventName = eventRequestDTO.getName();
		String eventDescription = eventRequestDTO.getDescription();
		String venue = eventRequestDTO.getVenue();
		List<String> artistNames = eventRequestDTO.getArtistNames();
		String eventDurationInMinutes =  eventRequestDTO.getEventDurationInMinutes();
		
		String eventDate = eventRequestDTO.getEventDate();
		String eventStartTime = eventRequestDTO.getEventStartTime();

		if(Strings.isBlank(eventName) && !isPartialUpdate) {
			throw new InValidUserInputException("name is missing in input");
		}
		
		if(Strings.isBlank(eventDescription) && !isPartialUpdate) {
			throw new InValidUserInputException("description is missing in input");
		}
		
		if(Strings.isBlank(venue) && !isPartialUpdate) {
			throw new InValidUserInputException("venue is missing in input");
		}
		
		if(CollectionUtils.isEmpty(artistNames) && !isPartialUpdate) {
			throw new InValidUserInputException("artistNames is missing in input");
		}
		
		if(Strings.isBlank(eventDurationInMinutes) && !isPartialUpdate) {
			throw new InValidUserInputException("eventDurationInMinutes value should be int, greater than 0");
		}
		
		if(!Strings.isBlank(eventDurationInMinutes)) {
			validateEventDurationInMinutes(eventDurationInMinutes);
		}
		
		if(Strings.isBlank(eventDate) && !isPartialUpdate) {
			throw new InValidUserInputException("eventDate is missing in input");
		}
		
		if(!Strings.isBlank(eventDate)) {
			validateDate(eventDate);
		}
		
		if(Strings.isBlank(eventStartTime) && !isPartialUpdate) {
			throw new InValidUserInputException("eventStartTime is missing in input");
		}
		
		if(!Strings.isBlank(eventStartTime)) {
			validateEventStartTime(eventStartTime);
		}
	}

	private void validateEventDurationInMinutes(String eventDurationInMinutes) {
		try {
			int duration = Integer.parseInt(eventDurationInMinutes);
			if(duration < 0) {
				throw new InValidUserInputException("eventDurationInMinutes value should be int, greater than 0");
			}
		}catch (NumberFormatException e) {
			throw new InValidUserInputException("eventDurationInMinutes value should be int, greater than 0");
		}
		
	}

	private void validateDate(String eventDate) {
		try {
			new SimpleDateFormat("dd/MM/yyyy").parse(eventDate);  
		} catch (ParseException e) {
			throw new InValidUserInputException("eventDate should be in format dd/MM/yyyy");
		}
	}
	
	private void validateEventStartTime(String eventStartTime) {
		Matcher matcher = pattern.matcher(eventStartTime);
		if (!matcher.matches()) {
			throw new InValidUserInputException(
					"eventStartTime should in format hh:mm [AM/PM]");
		}
	}

	public List<EventResponseDTO> getAllEvents() {
		List<EventResponseDTO> eventResponseDTOs = new ArrayList<>();
		List<Event> events = eventRepository.findAll();
		if(!CollectionUtils.isEmpty(events)) {
			for(Event event : events) {
				eventResponseDTOs.add(EventDTOTransformer.transformToEventResponseDTO(event));
			}
		}
		return eventResponseDTOs;
	}

	public EventResponseDTO getEvent(int eventId) {
		Event event = eventRepository.findById(eventId).orElse(null);
		if(event != null) {
			return EventDTOTransformer.transformToEventResponseDTO(event);
		}else {
			throw constructEventDoesNotExistException(eventId);
		}
	}

	public void deleteEventById(int eventId) {
		log.info("deleteEventById:: event Id {}", eventId);
		Event event = eventRepository.findById(eventId).orElse(null);
		if(event != null) {
			eventRepository.deleteById(eventId);
		}else {
			throw constructEventDoesNotExistException(eventId);
		}
		log.info("Event with id {} has been deleted.", eventId);
	}
	
	private EventDoesNotExistException constructEventDoesNotExistException(int eventId) {
		String msg = String.format("There is no event with id %s", eventId);
		return new EventDoesNotExistException(msg);
	}
}
