package com.fruntier.fruntier.record.controller;

import com.fruntier.fruntier.record.domain.Record;
import com.fruntier.fruntier.record.domain.RecordDTO;
import com.fruntier.fruntier.record.service.RecordUpdateService;
import com.fruntier.fruntier.record.service.RouteRetrieveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    public String updateRecordPage(@RequestParam String routeId, Model model) {
        model.addAttribute("routeId", routeId);

        return "record/record-update";
    }

    @PostMapping("/update")
    public String updateRecord(@ModelAttribute RecordDTO recordDTO) {
        Long routeId = recordDTO.getRouteId();
        Record record = convertDTOToRecord(recordDTO, routeId);
        recordUpdateService.updateRecord(routeId, record);

        return "redirect:/route";
    }

    private Record convertDTOToRecord(RecordDTO recordDTO, Long routeId) {
        Record record = new Record();
        record.setRoute(routeRetrieveService.getRouteFoundById(routeId));
        record.setRunningDate(LocalDateTime.parse(recordDTO.getRunningDate(), DateTimeFormatter.ISO_DATE_TIME));
        record.setMinutesForRunning(recordDTO.getMinutesForRunning());
        return record;
    }
}
