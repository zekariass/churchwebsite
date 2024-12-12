package com.churchwebsite.churchwebsite.services;

import com.churchwebsite.churchwebsite.entities.Event;
import com.churchwebsite.churchwebsite.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {
    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event save(Event event){
        event.setActive(true);
        event.setArchived(false);
        return eventRepository.save(event);
    }

    public Page<Event> findEventsByPage(int page, int size) {
        Pageable paging = PageRequest.of(page-1, size);
        Page<Event> pagedEvents = eventRepository.findAll(paging);

        return pagedEvents;
    }

    public Event findById(int eventId) {

        return eventRepository.findById(eventId).orElseThrow(
                () -> new RuntimeException("Event not found!")
        );

    }

    public Event updateEvent(Event event) {
        return eventRepository.save(event);
    }

    public void deleteById(Integer eventId) {
        eventRepository.deleteById(eventId);
    }
}
