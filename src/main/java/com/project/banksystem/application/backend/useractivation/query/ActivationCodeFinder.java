package com.project.banksystem.application.backend.useractivation.query;

import com.project.banksystem.application.backend.PasswordEncoderService;
import com.project.banksystem.infrastructure.exception.ex.BusinessException;
import com.project.banksystem.infrastructure.exception.ex.NotFoundException;
import com.project.banksystem.infrastructure.model.ActivationCode;
import com.project.banksystem.infrastructure.model.User;
import com.project.banksystem.infrastructure.repository.ActivationCodeRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
public class ActivationCodeFinder {
    private final ActivationCodeRepository activationCodeRepository;
    private final PasswordEncoderService passwordEncoderService;

    public ActivationCodeFinder(ActivationCodeRepository activationCodeRepository,
                                PasswordEncoderService passwordEncoderService) {
        this.activationCodeRepository = activationCodeRepository;
        this.passwordEncoderService = passwordEncoderService;
    }



    //WORST CASE SCENARIO IS 1+ NEW CODES BEING GENERATED DUE TO TECHNICAL ISSUE
    //BUT 99% OF THE TIME EACH PENDING USER WOULD HAVE ONLY 1 USABLE CODE
    //ACTIVATE THE USER THE FIRST TIME AT LOCAL BANK!
    public ActivationCode findMatchedCode(final User user,
                                    final String code) {
        List<ActivationCode> activationCodes =
                activationCodeRepository.findValidCodesByUserId(user.getUserId(), false, LocalDateTime.now());

        for (ActivationCode ac : activationCodes) {
            if (passwordEncoderService.isMatched(code, ac.getCodeHash())) {
                return ac;
            }
        }

        throw new BusinessException("Activation code not found.");
    }
}
