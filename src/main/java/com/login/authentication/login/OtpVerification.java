package com.login.authentication.login;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@Scope(value = "prototype")
public class OtpVerification {
    String otpNumber;

    public OtpVerification() {
        otpNumber = String.valueOf(new Random().nextInt(8999) + 1000);
    }

    public String getOtpNumber() {
        return otpNumber;
    }

    public void setOtpNumber(String otpNumber) {
        this.otpNumber = otpNumber;
    }
}
