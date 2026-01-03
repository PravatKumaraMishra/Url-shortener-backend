package me.pravat.uShort.service;

import lombok.RequiredArgsConstructor;
import me.pravat.uShort.dto.UrlMappingDto;
import me.pravat.uShort.entity.UrlMapper;
import me.pravat.uShort.entity.User;
import me.pravat.uShort.repository.UrlMappingRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UrlMappingService {
    private final UrlMappingRepository urlMappingRepository;
    public UrlMappingDto createShortUrl(String longUrl, User user) {
        String shortUrl = generateRandom();
        var urlMapper = new UrlMapper();
        urlMapper.setLongUrl(longUrl);
        urlMapper.setShortUrl(shortUrl);
        urlMapper.setCreatedAt(LocalDateTime.now());
        urlMapper.setUser(user);
        var savedUrlMapper = urlMappingRepository.save(urlMapper);
        return convertToDto(savedUrlMapper);
    }

    private UrlMappingDto convertToDto(UrlMapper urlMapper) {
        var dto = new UrlMappingDto();
        dto.setId(urlMapper.getId());
        dto.setOriginalUrl(urlMapper.getLongUrl());
        dto.setShortUrl(urlMapper.getShortUrl());
        dto.setClickCount(urlMapper.getClickCount());
        dto.setCreatedAt(urlMapper.getCreatedAt());
        dto.setUserName(urlMapper.getUser().getName());
        return dto;
    }

    private String generateRandom() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        var shortRandom = new StringBuilder();
        var random = new Random();
        for(int i=0; i<5; i++)
            shortRandom.append(characters.charAt(random.nextInt(characters.length())));
        return shortRandom.toString();
    }

    public List<UrlMappingDto> getAllUrls(User user) {
        var urls = urlMappingRepository.findAllByUser(user);
        return urls.stream().map(this::convertToDto).toList();
    }
}