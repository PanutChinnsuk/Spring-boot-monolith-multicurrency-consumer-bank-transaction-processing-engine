package com.project.banksystem.application.backend.verification.internal.body;

import java.util.UUID;

public record TwoFactorDataResultFromRequest(String deviceIp,
                                             UUID codeId,
                                             UUID userIdFromToken) {
}
