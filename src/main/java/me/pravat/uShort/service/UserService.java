package me.pravat.uShort.service;

import lombok.RequiredArgsConstructor;
import me.pravat.uShort.entity.User;
import me.pravat.uShort.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final PasswordEncoder encoder;
    private final UserRepository userRepository;

    // Method to load user details by username (email)
    @Override
    public UserDetailsImp loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        return new UserDetailsImp(user);
    }

    // Add any additional methods for registering or managing users
    public String addUser(User user) {
        // Encrypt password before saving
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRoles("ROLES_USER");
        userRepository.save(user);
        return "User added successfully!";
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("Email not exists"));
    }
}