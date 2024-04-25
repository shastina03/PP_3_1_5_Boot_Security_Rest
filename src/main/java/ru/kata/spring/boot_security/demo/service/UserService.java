package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.kata.spring.boot_security.demo.entity.User;

import java.util.List;

public interface UserService extends UserDetailsService {

    User findByUsername(String username);

    @Override
    UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;

    List<User> getAllUsers();

    User getUserById(Long id);

    void saveUser(User user);
    void updateUser(User user, Long id);

    void deleteUser(Long id);
}
