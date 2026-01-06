package me.pravat.uShort.controller;

import lombok.RequiredArgsConstructor;
import me.pravat.uShort.dto.ClickEventDto;
import me.pravat.uShort.service.AnalyticsService;
import me.pravat.uShort.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/analytics")
@RequiredArgsConstructor
public class AnalyticsController {
    private final UserService userService;
    private final AnalyticsService analyticsService;

    @GetMapping("/urls")
    @PreAuthorize("hasRole('ROLES_USER')")
    public ResponseEntity<Map<LocalDate, Long>> getTotalClicksByDate(Principal principal, @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate ){
        var formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        var user = userService.findUserByEmail(principal.getName());
        var start = LocalDate.parse(startDate, formatter);
        var end = LocalDate.parse(endDate, formatter);
        var totalClicks = analyticsService.getTotalClicksByUserAndDate(user, start, end);
        return ResponseEntity.ok(totalClicks);
    }

    @GetMapping("/{shortUrl}")
    @PreAuthorize("hasRole('ROLES_USER')")
    public ResponseEntity<List<ClickEventDto>> getUrlAnalyticsByDate(@PathVariable String shortUrl, @RequestParam("startDate")String startDate, @RequestParam("endDate")String endDate) {
        var formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        var start = LocalDateTime.parse(startDate, formatter);
        var end = LocalDateTime.parse(endDate, formatter);
        var clickEventDTOs = analyticsService.getClickEventByDate(shortUrl, start, end);
        return ResponseEntity.ok(clickEventDTOs);
    }
}
