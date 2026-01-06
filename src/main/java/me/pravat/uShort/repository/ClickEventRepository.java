package me.pravat.uShort.repository;

import me.pravat.uShort.entity.ClickEvent;
import me.pravat.uShort.entity.UrlMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ClickEventRepository extends JpaRepository<ClickEvent, Long> {
   List<ClickEvent> findByUrlMapperInAndClickDateBetween(List<UrlMapper> urlMapper, LocalDateTime startDate, LocalDateTime endDate);

    List<ClickEvent> findByUrlMapperAndClickDateBetween(UrlMapper urlMapper, LocalDateTime start, LocalDateTime end);
}
