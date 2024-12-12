package com.churchwebsite.churchwebsite.controllers;

import com.churchwebsite.churchwebsite.entities.Event;
import com.churchwebsite.churchwebsite.services.EventService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/dashboard/events")
public class EventController {

    private final String DASHBOARD_MAIN_PANEL = "dashboard/dash-fragments/dash-main-panel";

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/add")
    public String showEventsRegistrationForm(Model model){
        model.addAttribute("activeDashPage", "eventRegistrationForm");
        model.addAttribute("event", new Event());

        return DASHBOARD_MAIN_PANEL;
    }

    @PostMapping("/processAdd")
    public String processEventsRegistration(@ModelAttribute Event event, Model model){
        Event savedEvent = eventService.save(event);

        model.addAttribute("activeDashPage", "eventsList");

        return "redirect:/dashboard/events/list";
    }

    @GetMapping("/list")
    public  String showEventsList(Model model,
                                  HttpServletRequest request,
                                  @RequestParam(defaultValue = "1") int page,
                                  @RequestParam(defaultValue = "3") int size){

        Page<Event> pagedEvents = eventService.findEventsByPage(page, size);
        List<Event> events = pagedEvents.getContent();

        model.addAttribute("activeDashPage", "eventsList");
        model.addAttribute("events", events);
        model.addAttribute("currentPage", pagedEvents.getNumber()+1);
        model.addAttribute("totalItems", pagedEvents.getTotalElements());
        model.addAttribute("totalPages", pagedEvents.getTotalPages());
        model.addAttribute("pageSize", size);
        model.addAttribute("currentUrl", request.getRequestURL());

        return DASHBOARD_MAIN_PANEL;
    }

    @GetMapping("/detail/{id}")
    public  String showEventDetail(@PathVariable(value = "id", required = false) int eventId,
                                   Model model){

        Event event = eventService.findById(eventId);
        model.addAttribute("activeDashPage", "eventDetail");
        model.addAttribute("event", event);

        return DASHBOARD_MAIN_PANEL;
    }

    @GetMapping("/update/{id}")
    public  String showUpdateForm(@PathVariable(value = "id", required = false) int eventId,
                               Model model){

        Event event = eventService.findById(eventId);
        model.addAttribute("activeDashPage", "eventRegistrationForm");
        model.addAttribute("event", event);

        return DASHBOARD_MAIN_PANEL;
    }

    @PostMapping("/processUpdate")
    public String processUpdateEvent(@ModelAttribute Event event,
                                     Model model){

        Event savedEvent = eventService.save(event);

        model.addAttribute("activeDashPage", "eventDetail");
        model.addAttribute("event", savedEvent);
        return "redirect:/dashboard/events/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteEvent(@PathVariable("id") Integer eventId){
        eventService.deleteById(eventId);

        return "redirect:/dashboard/events/list";
    }


}
