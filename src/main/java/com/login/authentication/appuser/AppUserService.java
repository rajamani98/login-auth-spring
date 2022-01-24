package com.login.authentication.appuser;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AppUserService {

    private final AppUserRepository appUserRepository;

    public void signInUser(AppUser appUser) {
        appUserRepository.save(appUser);
    }

    public AppUser getUser(String email) {
        return appUserRepository.findByEmail(email);
    }
}
