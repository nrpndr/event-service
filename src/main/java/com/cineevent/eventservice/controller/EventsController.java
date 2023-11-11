package com.cineevent.eventservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cineevent.eventservice.dto.request.EventRequestDTO;
import com.cineevent.eventservice.dto.response.EventResponseDTO;
import com.cineevent.eventservice.service.EventService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/events")
public class EventsController {

	@Autowired
	private EventService eventService;
	
	@GetMapping
	public List<EventResponseDTO> getAllEvents() {
		return eventService.getAllEvents();
	}

	@GetMapping("/{eventId}")
	public EventResponseDTO getEvent(@PathVariable("eventId") int eventId) {
		return eventService.getEvent(eventId);
	}

	@Operation(security = { @SecurityRequirement (name = "bearer-key") })
	@PostMapping
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<EventResponseDTO> createEvent(@RequestBody EventRequestDTO eventRequestDTO) {
		return new ResponseEntity<>(eventService.createEvent(eventRequestDTO), null, HttpStatus.CREATED);
	}

	@Operation(security = { @SecurityRequirement (name = "bearer-key") })
	@PutMapping("/{eventId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public EventResponseDTO createOrUpdateEvent(@PathVariable("eventId") int eventId,
			@RequestBody EventRequestDTO eventRequestDTO) {
		return eventService.createOrUpdateEvent(eventId, eventRequestDTO);
	}
	
	@Operation(security = { @SecurityRequirement (name = "bearer-key") })
	@PatchMapping("/{eventId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public EventResponseDTO updateEvent(@PathVariable("eventId") int eventId,
			@RequestBody EventRequestDTO eventRequestDTO) {
		return eventService.updateEvent(eventId, eventRequestDTO);
	}

	@Operation(security = { @SecurityRequirement (name = "bearer-key") })
	@DeleteMapping("/{eventId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<?> deleteEvent(@PathVariable("eventId") int eventId) {
		eventService.deleteEventById(eventId);
		return ResponseEntity.noContent().build();
	}

}
