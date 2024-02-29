package com.fruntier.fruntier.record.controller;

import com.fruntier.fruntier.RequireTokenValidation;
import com.fruntier.fruntier.record.domain.Record;
import com.fruntier.fruntier.record.domain.RecordDTO;
import com.fruntier.fruntier.record.service.RecordUpdateService;
import com.fruntier.fruntier.record.service.RouteRetrieveService;
import com.fruntier.fruntier.user.domain.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/record")
public class RecordController {
    private final RouteRetrieveService routeRetrieveService;
    private final RecordUpdateService recordUpdateService;

    @Autowired
    public RecordController(RouteRetrieveService routeRetrieveService, RecordUpdateService recordUpdateService) {
        this.routeRetrieveService = routeRetrieveService;
        this.recordUpdateService = recordUpdateService;
    }

    @GetMapping("/update")
    @RequireTokenValidation
    public String updateRecordPage(@RequestParam String routeId,
                                   Model model)
             {
        model.addAttribute("routeId", routeId);

        return "record/record-update";
    }

    @PostMapping("/update")
    @RequireTokenValidation
    public String updateRecord(@ModelAttribute RecordDTO recordDTO,
                               HttpServletRequest request) {
        User user = (User)request.getAttribute("validatedUser") ;

        Long routeId = recordDTO.getRouteId();
        try {
            Record record = convertDTOToRecord(recordDTO, routeId);
            record.setUserId(user.getId());
            recordUpdateService.updateRecord(routeId, record);
        } catch (NoSuchElementException e) {
            return "redirect:/running";
        }

        return "redirect:/route";
    }

    private Record convertDTOToRecord(RecordDTO recordDTO, Long routeId) throws NoSuchElementException{
        Record record = new Record();
        record.setRoute(routeRetrieveService.findRouteById(routeId));
        record.setRunningDate(LocalDateTime.parse(recordDTO.getRunningDate(), DateTimeFormatter.ISO_DATE_TIME));
        record.setMinutesForRunning(recordDTO.getMinutesForRunning());
        return record;
    }
}
