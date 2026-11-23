package com.hkcare.service;

import com.hkcare.model.Payment;
import com.hkcare.repository.DataStore;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;

/** Thanh toán giả lập: tiền mặt (paid ngay), QR / chuyển khoản (pending, chờ xác nhận). */
@Service
public class PaymentService {
    private static final Set<String> PHUONG_THUC = Set.of("cash", "qr", "bank");
    private final DataStore db;

    public PaymentService(DataStore db) { this.db = db; }

    public List<Payment> danhSach() { return new ArrayList<>(db.payments); }

    public Payment tao(Payment req) {
        if (req.getMethod() == null || !PHUONG_THUC.contains(req.getMethod()))
            throw new IllegalArgumentException("Phương thức thanh toán không hợp lệ");
        if (req.getAmount() <= 0) throw new IllegalArgumentException("Số tiền không hợp lệ");

        Payment p = new Payment();
        p.setId("PAY" + UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase());
        p.setMethod(req.getMethod());
        p.setAmount(req.getAmount());
        p.setOrderInfo(req.getOrderInfo());
        p.setCreatedAt(LocalDateTime.now());

        if (p.getMethod().equals("cash")) {
            p.setStatus("paid");
            p.setPaidAt(p.getCreatedAt());
        } else {
            p.setStatus("pending");
            Map<String, String> bank = new LinkedHashMap<>();
            bank.put("bankName", "Vietcombank");
            bank.put("accountNo", "0123456789");
            bank.put("accountName", "HKCARE PHARMACY");
            if (p.getMethod().equals("bank")) bank.put("content", p.getId());
            p.setBankInfo(bank);
            if (p.getMethod().equals("qr")) p.setQrFallback(qrGiaLap(p.getId()));
        }
        db.payments.add(p);
        return p;
    }

    public synchronized void xacNhan(String id) {
        Payment p = db.payments.stream().filter(x -> x.getId().equals(id)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy giao dịch"));
        if (!"paid".equals(p.getStatus())) {
            p.setStatus("paid");
            p.setPaidAt(LocalDateTime.now());
        }
    }

    /** Ảnh QR minh họa (SVG data URI). Muốn QR thật: thay bằng URL VietQR/ảnh QR do thư viện sinh ra. */
    private static String qrGiaLap(String id) {
        String svg = "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"260\" height=\"260\"><rect width=\"260\" height=\"260\" fill=\"#fff\"/>"
                + "<rect x=\"20\" y=\"20\" width=\"220\" height=\"220\" fill=\"none\" stroke=\"#12231d\" stroke-width=\"10\" stroke-dasharray=\"26 8\"/>"
                + "<text x=\"130\" y=\"125\" text-anchor=\"middle\" font-family=\"sans-serif\" font-size=\"22\" font-weight=\"700\" fill=\"#0e7a5a\">QR DEMO</text>"
                + "<text x=\"130\" y=\"155\" text-anchor=\"middle\" font-family=\"sans-serif\" font-size=\"14\" fill=\"#6b7c75\">" + id + "</text></svg>";
        return "data:image/svg+xml;utf8," + URLEncoder.encode(svg, StandardCharsets.UTF_8).replace("+", "%20");
    }
}
