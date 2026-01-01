package me.pravat.uShort.service;

import lombok.RequiredArgsConstructor;
import me.pravat.uShort.entity.User;
import me.pravat.uShort.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final PasswordEncoder encoder;
    private final UserRepository userRepository;

    // Method to load user details by username (email)
    @Override
    public UserDetailsImp loadUserByUsername(String username) throws UsernameNotFoundException {

        // Fetch user from the database by email (username)
        Optional<User> userInfo = userRepository.findByEmail(username);

        if (userInfo.isEmpty()) {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }

        // Convert UserInfo to UserDetails (UserInfoDetails)
        User user = userInfo.get();
        return new UserDetailsImp(user);
    }

    // Add any additional methods for registering or managing users
    public String addUser(User user) {
        // Encrypt password before saving
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User added successfully!";
    }
}