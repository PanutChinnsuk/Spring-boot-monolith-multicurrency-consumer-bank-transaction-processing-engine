package com.project.banksystem.application.backend.sms.otp;

import com.project.banksystem.application.config.properties.TwilioConfig;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.type.PhoneNumber;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class InternalSmsOtpSenderService {
    private static final Logger log = LoggerFactory.getLogger(InternalSmsOtpSenderService.class);
    private final TwilioConfig twilioConfig;

    public InternalSmsOtpSenderService(TwilioConfig twilioConfig) {
        this.twilioConfig = twilioConfig;
    }


    //no money :(
    public void sendOtp(final String phoneNumber,
                        final String code) {
        log.info("Sent: " + phoneNumber + " : " + code);
        if (true) {
            return;
        }

        String to = phoneNumber;
        String from = "FROM NUMBER";

        Message message = Message.creator(
                new PhoneNumber(to),
                new PhoneNumber(from),
                "Phone number verification code: " + code
        ).create();
    }
}
