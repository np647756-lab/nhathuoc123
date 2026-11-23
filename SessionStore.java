package com.hkcare.repository;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/** Lưu phiên đăng nhập trong bộ nhớ (token -> Session). Phiên hết hạn sau 12 giờ. */
@Component
public class SessionStore {
    private static final long TTL_SECONDS = 12 * 3600L;
    private final Map<String, Session> sessions = new ConcurrentHashMap<>();

    public static class Session {
        public final String token;
        public final String maND;
        public final String maTK;
        public final int maQuyen;
        public final Instant taoLuc = Instant.now();

        public Session(String token, String maND, String maTK, int maQuyen) {
            this.token = token; this.maND = maND; this.maTK = maTK; this.maQuyen = maQuyen;
        }
    }

    public Session taoSession(String maND, String maTK, int maQuyen) {
        Session s = new Session(UUID.randomUUID().toString(), maND, maTK, maQuyen);
        sessions.put(s.token, s);
        return s;
    }

    public Session laySession(String token) {
        if (token == null) return null;
        Session s = sessions.get(token);
        if (s == null) return null;
        if (s.taoLuc.plusSeconds(TTL_SECONDS).isBefore(Instant.now())) {
            sessions.remove(token);
            return null;
        }
        return s;
    }

    public void xoaSession(String token) {
        if (token != null) sessions.remove(token);
    }
}
