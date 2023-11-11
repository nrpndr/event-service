package com.cineevent.eventservice.dto.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EventResponseDTO {
	
	 private int eventId;
	
	 private String name;
	 
	 private String eventDate;
	 
	 private String eventStartTime;
	 
	 private int eventDurationInMinutes;
	 
	 private String venue;
	 
	 private String description;
	 
	 private List<String> artistNames;

}
