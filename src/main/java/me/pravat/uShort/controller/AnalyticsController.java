package me.pravat.uShort.controller;

import lombok.RequiredArgsConstructor;
import me.pravat.uShort.dto.ClickEventDto;
import me.pravat.uShort.service.AnalyticsService;
import me.pravat.uShort.service.UserService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    public ResponseEntity<Map<LocalDate, Long>> getTotalClicksByDate(
            Principal principal,
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        var user = userService.findUserByEmail(principal.getName());
        var totalClicks = analyticsService.getTotalClicksByUserAndDate(user, startDate.toLocalDate(), endDate.toLocalDate());
        return ResponseEntity.ok(totalClicks);
    }

    @GetMapping("/{shortUrl}")
    @PreAuthorize("hasRole('ROLES_USER')")
    public ResponseEntity<List<ClickEventDto>> getUrlAnalyticsByDate(
            @PathVariable String shortUrl,
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        var clickEventDTOs = analyticsService.getClickEventByDate(shortUrl, startDate, endDate);
        return ResponseEntity.ok(clickEventDTOs);
    }
}
