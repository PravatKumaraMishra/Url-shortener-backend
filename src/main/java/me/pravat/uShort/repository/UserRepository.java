package me.pravat.uShort.repository;
import lombok.NonNull;
import me.pravat.uShort.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<@NonNull User,@NonNull Integer> {
    Optional<User> findByEmail(String username);
}
