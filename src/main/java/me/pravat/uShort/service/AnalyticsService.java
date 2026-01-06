package me.pravat.uShort.service;

import lombok.RequiredArgsConstructor;
import me.pravat.uShort.dto.ClickEventDto;
import me.pravat.uShort.entity.User;
import me.pravat.uShort.repository.ClickEventRepository;
import me.pravat.uShort.repository.UrlMapperRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final UrlMapperRepository urlMapperRepository;
    private final ClickEventRepository clickEventRepository;

    public Map<LocalDate, Long> getTotalClicksByUserAndDate(User user, LocalDate start, LocalDate end) {
        var urlMappings = urlMapperRepository.findAllByUser(user);
        var clickEvents = clickEventRepository.findByUrlMapperInAndClickDateBetween(urlMappings, start.atStartOfDay(), end.plusDays(1).atStartOfDay());
        return clickEvents.stream().collect(Collectors.groupingBy(click-> click.getClickDate().toLocalDate(), Collectors.counting()));
    }


    public List<ClickEventDto> getClickEventByDate(String shortUrl, LocalDateTime start, LocalDateTime end) {
        var urlMapper = urlMapperRepository.findByShortUrl(shortUrl);
        if (urlMapper != null) {
            var clickEvents = clickEventRepository.findByUrlMapperAndClickDateBetween(urlMapper, start, end);
            return clickEvents.stream().collect(Collectors.groupingBy(click->click.getClickDate().toLocalDate(), Collectors.counting())).entrySet().stream().map(entry -> {
                var clickEventDto = new ClickEventDto();
                clickEventDto.setClickDate(entry.getKey().atStartOfDay());
                clickEventDto.setCount(entry.getValue());
                return clickEventDto;
            }).collect(Collectors.toList());
        }
        return null;
    }
}
