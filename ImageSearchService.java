package com.hkcare.service;

import com.hkcare.model.SanPham;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.Normalizer;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Tìm sản phẩm bằng hình ảnh (MÔ PHỎNG).
 * Chưa có mô hình nhận diện thật: ưu tiên sản phẩm có tên trùng với tên file ảnh, phần còn lại chọn
 * ổn định theo nội dung ảnh (cùng ảnh -> cùng kết quả). Điểm khớp là điểm giả lập 85-99%.
 * Muốn nhận diện thật, thay thân hàm timKiem bằng lời gọi tới dịch vụ AI/vision.
 */
@Service
public class ImageSearchService {
    private static final long MAX_BYTES = 10L * 1024 * 1024;
    private final ProductService products;

    public ImageSearchService(ProductService products) { this.products = products; }

    private static String bo(String s) {
        String n = Normalizer.normalize(s == null ? "" : s, Normalizer.Form.NFD).replaceAll("\\p{M}", "");
        return n.replace('đ', 'd').replace('Đ', 'D').toLowerCase(Locale.ROOT);
    }

    public List<Map<String, Object>> timKiem(MultipartFile anh) throws IOException {
        if (anh == null || anh.isEmpty()) throw new IllegalArgumentException("Chưa chọn ảnh");
        if (anh.getSize() > MAX_BYTES) throw new IllegalArgumentException("Ảnh quá lớn (tối đa 10MB)");
        String ct = anh.getContentType();
        if (ct == null || !ct.startsWith("image/")) throw new IllegalArgumentException("File tải lên phải là ảnh");

        byte[] bytes = anh.getBytes();
        String tenFile = bo(anh.getOriginalFilename());
        Random rnd = new Random(Arrays.hashCode(bytes));

        List<SanPham> ds = new ArrayList<>(products.danhSach(null, null));
        Collections.shuffle(ds, rnd);
        // sản phẩm có từ khóa tên nằm trong tên file lên đầu
        ds.sort(Comparator.comparingInt((SanPham p) -> khop(tenFile, p) ? 0 : 1));

        int[] diem = {99 - rnd.nextInt(4), 93 - rnd.nextInt(4), 86 - rnd.nextInt(3)};
        List<SanPham> top = ds.stream().limit(3).collect(Collectors.toList());
        List<Map<String, Object>> out = new ArrayList<>();
        for (int i = 0; i < top.size(); i++) {
            SanPham p = top.get(i);
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("maSP", p.getMaSP());
            m.put("tenThuoc", p.getTenThuoc());
            m.put("maDM", p.getMaDM());
            m.put("giaBan", p.getGiaBan());
            m.put("hinhAnh", p.getHinhAnh());
            m.put("score", diem[i]);
            out.add(m);
        }
        return out;
    }

    private static boolean khop(String tenFile, SanPham p) {
        if (tenFile.isBlank()) return false;
        for (String tu : bo(p.getTenThuoc()).split("[^a-z0-9]+"))
            if (tu.length() >= 4 && tenFile.contains(tu)) return true;
        return false;
    }
}
