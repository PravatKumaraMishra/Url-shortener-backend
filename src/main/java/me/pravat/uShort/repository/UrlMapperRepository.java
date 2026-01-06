package me.pravat.uShort.repository;

import me.pravat.uShort.entity.UrlMapper;
import me.pravat.uShort.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UrlMapperRepository extends JpaRepository<UrlMapper, Long> {

    List<UrlMapper> findAllByUser(User user);

    UrlMapper findByShortUrl(String shortUrl);
}
