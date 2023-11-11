package com.cineevent.eventservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cineevent.eventservice.model.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer>{

}
