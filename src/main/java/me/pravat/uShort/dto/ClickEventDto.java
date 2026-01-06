package me.pravat.uShort.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ClickEventDto {
    private LocalDateTime clickDate;
    private Long count;
}
