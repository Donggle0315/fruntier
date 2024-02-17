package com.fruntier.fruntier.record.controller;

import com.fruntier.fruntier.record.domain.Record;
import com.fruntier.fruntier.record.domain.RecordDTO;
import com.fruntier.fruntier.record.service.RecordUpdateService;
import com.fruntier.fruntier.record.service.RouteRetrieveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @PostMapping("/update")
    public String updateRecord(@ModelAttribute RecordDTO recordDTO) {
        System.out.println("RecordController.updateRecord");
        Long routeId = recordDTO.getRouteId();
        Record record = new Record();
        record.setRoute(routeRetrieveService.getRouteFoundById(routeId));
        record.setRunningDate(LocalDateTime.parse(recordDTO.getRunningDate(), DateTimeFormatter.ISO_DATE_TIME));
        record.setMinutesForRunning(recordDTO.getMinutesForRunning());

        recordUpdateService.updateRecord(routeId, record);

        return "redirect:/routes/" + routeId;
    }
}
