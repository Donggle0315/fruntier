package com.fruntier.fruntier.record.controller;

import com.fruntier.fruntier.JwtTokenService;
import com.fruntier.fruntier.globalexception.UserNotLoggedInException;
import com.fruntier.fruntier.record.domain.Record;
import com.fruntier.fruntier.record.domain.RecordDTO;
import com.fruntier.fruntier.record.service.RecordUpdateService;
import com.fruntier.fruntier.record.service.RouteRetrieveService;
import com.fruntier.fruntier.user.domain.User;
import com.fruntier.fruntier.user.exceptions.TokenValidationException;
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
    private final JwtTokenService jwtTokenService;

    @Autowired
    public RecordController(RouteRetrieveService routeRetrieveService, RecordUpdateService recordUpdateService, JwtTokenService jwtTokenService) {
        this.routeRetrieveService = routeRetrieveService;
        this.recordUpdateService = recordUpdateService;
        this.jwtTokenService = jwtTokenService;
    }

    @GetMapping("/update")
    public String updateRecordPage(@RequestParam String routeId,
                                   @CookieValue(value = "authToken", required = false) String authCookie,
                                   Model model)
            throws UserNotLoggedInException {
        checkCookieValueExist(authCookie);
        model.addAttribute("routeId", routeId);

        return "record/record-update";
    }

    @PostMapping("/update")
    public String updateRecord(@ModelAttribute RecordDTO recordDTO,
                               @CookieValue(value = "authToken", required = false) String authCookie)
            throws UserNotLoggedInException {
        checkCookieValueExist(authCookie);


        Long userId;
        try {
            User user = jwtTokenService.validateTokenReturnUser(authCookie);
            userId = user.getId();
        } catch (TokenValidationException e) {
            throw new UserNotLoggedInException("user not logged in");
        }

        Long routeId = recordDTO.getRouteId();
        Record record = convertDTOToRecord(recordDTO, routeId);
        record.setUserId(userId);

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

    private static void checkCookieValueExist(String authCookie) throws UserNotLoggedInException {
        if (authCookie == null) {
            throw new UserNotLoggedInException("User isn't logged in");
        }
    }
}
