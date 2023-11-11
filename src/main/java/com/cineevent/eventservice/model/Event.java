package com.cineevent.eventservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "events")
public class Event {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name="name", nullable = false)
	private String name;

	@Column(name="event_date", nullable = false)
	private String eventDate;

	@Column(name="event_start_time", nullable = false)
	private String eventStartTime;

	@Column(name="event_duration_in_minutes", nullable = false)
	private int eventDurationInMinutes;

	@Column(name="venue", nullable = false)
	private String venue;

	@Column(name="description", nullable = false)
	private String description;

	@Column(name="artist_names", nullable = false)
	private String artistNames;

}
