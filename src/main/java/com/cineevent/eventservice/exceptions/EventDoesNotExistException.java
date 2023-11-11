package com.cineevent.eventservice.exceptions;

public class EventDoesNotExistException extends RuntimeException{

	private static final long serialVersionUID = -255986935176609859L;

	public EventDoesNotExistException(String message) {
        super(message);
    }
}
