package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;


import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService { //предоставляет по имни пользователя самого юзера

    private RoleRepository roleRepository;
    private UserRepository userRepository;

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public User findByUsername(String username) {

        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = findByUsername(username); //ищем юзера
        if(user == null){
            throw new UsernameNotFoundException(String.format("User '%s' not found",username));
        }
        return user;
    }

    public List<Role> listRoles() {
        return roleRepository.findAll();
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.getOne(id);
    }
    @Transactional
    public void saveUser(User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        Role roleUser = roleRepository.findByName("ROLE_USER");
        user.addRole(roleUser);
        userRepository.save(user);
    }

    @Transactional
    public void editUser(Long id, User updatedUser) {
        User toChange = getUserById(id);

        if (toChange != null) {
            toChange.setUsername(updatedUser.getUsername());
            toChange.setPassword(updatedUser.getPassword());
            toChange.setRoles(updatedUser.getRoles());
            userRepository.save(toChange);
        }
    }
    @Transactional
    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }

}
