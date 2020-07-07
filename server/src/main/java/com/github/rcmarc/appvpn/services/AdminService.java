package com.github.rcmarc.appvpn.services;

import java.time.LocalDateTime;
import java.util.Scanner;

import com.github.rcmarc.appvpn.models.Admin;
import com.github.rcmarc.appvpn.models.User;
import com.github.rcmarc.appvpn.repositories.AdminRepository;
import com.github.rcmarc.appvpn.repositories.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public void createNewAdminInteractive() {
        try (Scanner in = new Scanner(System.in)) {
            System.out.println("Create super user");
            System.out.print("username: ");
            final String username = in.next();
            final String email = askEmail(in);
            final String password = askPassword(in);
            saveDefaultAdmin(username, email, password);
        }
    }

    private void saveDefaultAdmin(final String username, final String email, final String password) {
        final User user = User.builder().username(username).email(email).password(passwordEncoder.encode(password))
                .createdDate(LocalDateTime.now()).enabled(false).build();
        userRepository.save(user);
        final Admin admin = Admin.builder().user(user).build();
        adminRepository.save(admin);
    }

    private String askEmail(final Scanner in) {
        System.out.print("email: ");
        final String email = in.next();
        if (!checkEmail(email)) {
            System.out.println("Invalid email");
            askEmail(in);
        }
        return email;
    }

    private boolean checkEmail(final String email) {
        if (!email.contains("@") || !email.contains(".")) {
            return false;
        }
        return email.chars().filter(c -> c == '@').count() == 1;
    }

    private String askPassword(final Scanner in) {
        System.out.print("password: ");
        final String password = in.next();
        if(password.length() < 5){
            System.out.println("Password must have more than five characters");
            askPassword(in);
        }
        System.out.print("retype the password: ");
        final String retyped = in.next();
        if(!retyped.equals(password)){
            System.out.println("passwords dont match");
            askPassword(in);
        }
        return password;
    }

}