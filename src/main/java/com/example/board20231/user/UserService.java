package com.example.board20231.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SiteUser create (String username, String email, String password) {
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password)); // 객체를 직접 생성하여 사용하지 않고, 빈으로 등록한 passwordEncoder 객체를 주입받아 사용
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // BCrypt 해싱함수 를 사용해서 비밀번호를 암호화한다.
//        user.setPassword(passwordEncoder.encode(password));
        this.userRepository.save(user);
        return user;
    }
}
