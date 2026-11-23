package com.hkcare.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hkcare.model.ApiResponse;
import com.hkcare.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.nio.charset.StandardCharsets;

/** Chặn mọi /api/** (trừ đăng nhập/đăng ký) nếu chưa có token hợp lệ. */
@Component
public class AuthInterceptor implements HandlerInterceptor {
    private final AuthService auth;
    private final ObjectMapper mapper = new ObjectMapper();

    public AuthInterceptor(AuthService auth) { this.auth = auth; }

    /** Lấy token từ header "Authorization: Bearer xxx". */
    public static String token(HttpServletRequest req) {
        String h = req.getHeader("Authorization");
        return (h != null && h.startsWith("Bearer ")) ? h.substring(7).trim() : null;
    }

    /** Dùng trong controller: ném SecurityException (-> 403) nếu không thuộc nhóm quyền cho phép. */
    public static void yeuCauQuyen(AuthService auth, HttpServletRequest req, int... maQuyens) {
        if (!auth.coQuyen(token(req), maQuyens))
            throw new SecurityException("Bạn không có quyền thực hiện chức năng này");
    }

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
        if ("OPTIONS".equalsIgnoreCase(req.getMethod())) return true;
        String path = req.getRequestURI();
        if (path.equals("/api/auth/login") || path.equals("/api/auth/register")) return true;

        String t = token(req);
        if (t != null && auth.coQuyen(t, 1, 2, 3, 4, 5)) return true;

        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        res.setContentType(MediaType.APPLICATION_JSON_VALUE);
        res.setCharacterEncoding(StandardCharsets.UTF_8.name());
        res.getWriter().write(mapper.writeValueAsString(ApiResponse.loi("Phiên đăng nhập hết hạn")));
        return false;
    }
}
