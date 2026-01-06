package me.pravat.uShort.controller;

import lombok.RequiredArgsConstructor;
import me.pravat.uShort.dto.UrlMappingDto;
import me.pravat.uShort.entity.User;
import me.pravat.uShort.service.UrlMappingService;
import me.pravat.uShort.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
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

    @GetMapping("/myUrls")
    @PreAuthorize("hasRole('ROLES_USER')")
    public ResponseEntity<List<UrlMappingDto>> getAllUrls(Principal principal) {
        var user = userService.findUserByEmail(principal.getName());
        var urls = urlMappingService.getAllUrls(user);
        return ResponseEntity.ok(urls);
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<Void> redirect(@PathVariable String shortUrl){
        var url = urlMappingService.getOriginalUrl(shortUrl);
        if (url != null) {
            var httpHeaders = new HttpHeaders();
            // Ensure URL has a protocol scheme
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "https://" + url;
            }
            
            httpHeaders.add("Location", url);
            return ResponseEntity.status(302).headers(httpHeaders).build();
        }
        else
            return ResponseEntity.notFound().build();
    }
}
