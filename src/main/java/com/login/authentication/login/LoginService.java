package com.login.authentication.login;

import com.login.authentication.appuser.AppUser;
import com.login.authentication.appuser.AppUserRepository;
import com.login.authentication.appuser.AppUserService;
import com.login.authentication.email.EmailSender;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LoginService {

    private final AppUserService appUserService;
    private final EmailSender emailSender;
    private final AppUserRepository appUserRepository;
    private OtpVerification otpVerification;


    public boolean loginUser(String emailId) {
        otpVerification = new OtpVerification();
        String otp = otpVerification.getOtpNumber();
        String emailContent = "WELCOME, Your OTP is : " + otp;
        System.out.println("*************************************************************");
        System.out.println(emailContent);
        AppUser user = appUserRepository.findByEmail(emailId);
        if (user == null)
            user = new AppUser(emailId, otp);
        else
            user.setOtp(otp);
        System.out.println("*************************************************************");
        System.out.println(user);
        System.out.println(user.toString());
        appUserService.signInUser(user);
        try{
        emailSender.send(emailId, emailContent);
        }
        catch(Exception e){
            System.out.println("failed to send "+ e);
        }
        return true;
    }

    public boolean verifyOtp(String generatedOtp, String emailId) {
        AppUser appUser = appUserService.getUser(emailId);
        return generatedOtp.equals(appUser.getOtp());
    }

}
