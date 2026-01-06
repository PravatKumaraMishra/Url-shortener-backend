package me.pravat.uShort.repository;

import me.pravat.uShort.entity.ClickEvent;
import me.pravat.uShort.entity.UrlMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Repository
public interface ClickEventRepository extends JpaRepository<ClickEvent, Long> {
   List<ClickEvent> findByUrlMapperInAndClickDateBetween(Collection<UrlMapper> urlMapper, LocalDateTime clickDate, LocalDateTime clickDate2);

    List<ClickEvent> findByUrlMapperAndClickDateBetween(UrlMapper urlMapper, LocalDateTime start, LocalDateTime end);
}
