package com.cineevent.eventservice.dto.request;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EventRequestDTO {
	 
	 private String name;
	 
	 private String eventDate;
	 
	 private String eventStartTime;
	 
	 private String eventDurationInMinutes;
	 
	 private String venue;
	 
	 private String description;
	 
	 private List<String> artistNames;
	 
}
