package com.project.banksystem.application.spring;

import java.nio.ByteBuffer;
import java.util.UUID;

public final class JdbcConverter {
    private JdbcConverter() {

    }

    public static byte[] convertToBytes(UUID uuid) {
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        return bb.array();
    }
}
