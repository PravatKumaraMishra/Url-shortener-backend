package me.pravat.uShort.controller;

import lombok.RequiredArgsConstructor;
import me.pravat.uShort.dto.UrlMappingDto;
import me.pravat.uShort.entity.User;
import me.pravat.uShort.service.UrlMappingService;
import me.pravat.uShort.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/url")
@RequiredArgsConstructor
public class UrlMappingController {

    private final UserService userService;
    private final UrlMappingService urlMappingService;


    @PostMapping("/shorten")
    public ResponseEntity<UrlMappingDto> createShortUrl(@RequestBody Map<String, String> request, Principal principal){
        var longUrl = request.get("longUrl");
        User user = userService.findUserByEmail(principal.getName());
        var response = urlMappingService.createShortUrl(longUrl, user);
        return ResponseEntity.ok(response);
    }
}
