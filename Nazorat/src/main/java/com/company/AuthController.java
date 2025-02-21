package com.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private EmailService emailService;

    private List<User> users = new ArrayList<>();

    @PostMapping("/register")
    public String register(@RequestParam String email, @RequestParam String password) {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return "Bu email allaqachon ro‘yxatdan o‘tgan!";
            }
        }

        String verificationCode = generateVerificationCode();
        User newUser = new User(email, password, verificationCode);
        users.add(newUser);

        emailService.sendEmail(email, "Tasdiqlash kodi", "Sizning tasdiqlash kodingiz: " + verificationCode);

        return "Tasdiqlash kodi emailga yuborildi: " + email;
    }

    @PostMapping("/verify")
    public String verify(@RequestParam String email, @RequestParam String code) {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                if (user.getVerificationCode().equals(code)) {
                    user.setVerified(true);
                    return "Email muvaffaqiyatli tasdiqlandi!";
                } else {
                    return "Noto‘g‘ri tasdiqlash kodi!";
                }
            }
        }
        return "Bunday email mavjud emas!";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password) {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                if (!user.isVerified()) {
                    return "Email tasdiqlanmagan!";
                }
                if (user.getPassword().equals(password)) {
                    return "Muvaffaqiyatli tizimga kirdingiz: " + email;
                } else {
                    return "Noto‘g‘ri parol!";
                }
            }
        }
        return "Foydalanuvchi topilmadi!";
    }

    private String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }
}
