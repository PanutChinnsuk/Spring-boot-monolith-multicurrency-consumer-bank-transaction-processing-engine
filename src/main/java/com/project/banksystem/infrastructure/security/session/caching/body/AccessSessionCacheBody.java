package com.project.banksystem.infrastructure.security.session.caching.body;

import java.util.UUID;

public class AccessSessionCacheBody {
    private UUID accessSessionId;
    private UUID userId;
    private String deviceIp;
    private Boolean revoked;
    private Long version;

    protected AccessSessionCacheBody() {

    }

    public AccessSessionCacheBody(UUID accessSessionId,
                                  UUID userId,
                                  String deviceIp,
                                  Boolean revoked,
                                  Long version) {
        this.accessSessionId = accessSessionId;
        this.userId = userId;
        this.deviceIp = deviceIp;
        this.revoked = revoked;
        this.version = version;

    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        UUID accessSessionId;
        UUID userId;
        String deviceIp;
        Boolean revoked;
        Long version;

        public Builder accessSessionId(UUID accessSessionId) {
            this.accessSessionId = accessSessionId;
            return this;
        }

        public Builder userId(UUID userId) {
            this.userId = userId;
            return this;
        }

        public Builder deviceIp(String deviceIp) {
            this.deviceIp = deviceIp;
            return this;
        }

        public Builder revoked(Boolean revoked) {
            this.revoked = revoked;
            return this;
        }

        public Builder version(Long version) {
            this.version = version;
            return this;
        }

        public AccessSessionCacheBody build() {
            if (accessSessionId == null) {
                throw new IllegalStateException("Access session id is null.");
            }
            if (userId == null) {
                throw new IllegalStateException("User id is null.");
            }
            if (deviceIp == null) {
                throw new IllegalArgumentException("Device ip is null.");
            }
            if (revoked == null) {
                throw new IllegalArgumentException("Revoked is null.");
            }
            if (version == null) {
                throw new IllegalStateException("Version is null.");
            }

            return new AccessSessionCacheBody(accessSessionId, userId, deviceIp, revoked,
                    version);
        }
    }






    public void setRevoked(Boolean revoked) {
        this.revoked = revoked;
    }

    public String getDeviceIp() {
        return deviceIp;
    }

    public Boolean getRevoked() {
        return revoked;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getAccessSessionId() {
        return accessSessionId.toString();
    }

    public Long getVersion() {
        return version;
    }
}
