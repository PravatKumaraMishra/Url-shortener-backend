package me.pravat.uShort.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UrlMappingDto {
    private Long id;
    private String originalUrl;
    private String shortUrl;
    private Integer clickCount;
    private LocalDateTime createdAt;
    private String userName;
}
