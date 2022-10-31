package com.shopme.admin.user;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    // 用BCryptPasswordEncoder的方式进行编码
    private PasswordEncoder passwordEncoder;

    public List<User> listAll() {
        return (List<User>) userRepo.findAll();
    }

    public List<Role> listRoles() {
        return (List<Role>) roleRepo.findAll();
    }

    public void save(User user) {
        boolean isUpdatingUser = (user.getId() != null);
        if (isUpdatingUser) {
            User existingUser = userRepo.findById((user.getId())).get();
            if (user.getPassword().isEmpty()) {
                // has not updated the passwords
                user.setPassword(existingUser.getPassword());
            } else {
                // new passwords
                encodePassword(user);
            }
        } else {
            encodePassword(user);
        }

        userRepo.save(user);
    }

    private void encodePassword(User user) {
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
    }

    public boolean isEmailUnique(Integer id, String email) {
        // 不管我们是edit还是create一个新的user，当email是null，说明是unique的，我们直接返回true
        User userByEmail = userRepo.getUserByEmail(email);

        if (userByEmail == null) return true;
        // userByEmail != null -> 说明email在database中存在
        // 改名字，其他信息
        if (userByEmail.getId() == id) return true;
        return false;
    }

    public User getById(Integer id) throws UserNotFoundException {
        try {
            return userRepo.findById(id).get();
        } catch (NoSuchElementException ex) {
            throw new UserNotFoundException("Could not find any user with ID: " + id);
        }
    }

    public void deleteById(Integer id) throws UserNotFoundException {
        Long countById = userRepo.countById(id);
        if (countById == null || countById == 0) {
            throw new UserNotFoundException("Could not find any user with ID " + id);
        }
        userRepo.deleteById(id);
    }
}