package com.churchwebsite.churchwebsite.controllers;

import com.churchwebsite.churchwebsite.dtos.ChurchDetailDTO;
import com.churchwebsite.churchwebsite.entities.Event;
import com.churchwebsite.churchwebsite.services.ChurchDetailService;
import com.churchwebsite.churchwebsite.services.EventService;
import com.churchwebsite.churchwebsite.services.PaginationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/events")
public class PublicEventController {

    private final ChurchDetailService churchDetailService;
    private final PaginationService paginationService;

    private final String PUBLIC_CONTENT = "layouts/base";

    private final EventService eventService;

    @Autowired
    public PublicEventController(EventService eventService,
                                 PaginationService paginationService,
                                 ChurchDetailService churchDetailService) {
        this.eventService = eventService;
        this.paginationService = paginationService;
        this.churchDetailService = churchDetailService;
        ;
    }


    @GetMapping("")
    public  String showEventsList(Model model,
                                  HttpServletRequest request,
                                  @RequestParam(value = "page", defaultValue = "1", required = false) int page,
                                  @RequestParam(value = "size", required = false) Integer pageSize){

        pageSize = (pageSize != null && pageSize > 0) ? pageSize: paginationService.getPageSize();

        Page<Event> pagedEvents = eventService.findEventsByPage(page, pageSize);
        List<Event> events = pagedEvents.getContent();

        model.addAttribute("activeContentPage", "events-list");
        model.addAttribute("events", events);
        model.addAttribute("currentPage", pagedEvents.getNumber()+1);
        model.addAttribute("totalItems", pagedEvents.getTotalElements());
        model.addAttribute("totalPages", pagedEvents.getTotalPages());
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());
        model.addAttribute("currentUrl", request.getRequestURL());

        return PUBLIC_CONTENT;
    }

    @GetMapping("/detail/{id}")
    public  String showEventDetail(@PathVariable(value = "id", required = false) int eventId,
                                   Model model){

        Event event = eventService.findById(eventId);
        model.addAttribute("activeContentPage", "event-detail");
        model.addAttribute("event", event);
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());

        return PUBLIC_CONTENT;
    }


}
