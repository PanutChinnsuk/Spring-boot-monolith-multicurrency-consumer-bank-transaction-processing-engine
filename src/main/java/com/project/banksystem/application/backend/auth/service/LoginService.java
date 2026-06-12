package com.project.banksystem.application.backend.auth.service;

import com.project.banksystem.api.request.dto.auth.UserLoginDTO;
import com.project.banksystem.application.backend.auth.body.AuthResponseBody;

public interface LoginService {
    AuthResponseBody login(final UserLoginDTO userLoginDTO, final String deviceIp);
}
