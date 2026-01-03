package me.pravat.uShort.repository;

import me.pravat.uShort.entity.UrlMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlMappingRepository extends JpaRepository<UrlMapper, Long> {

}
