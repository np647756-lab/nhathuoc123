package com.hkcare.service;

import com.hkcare.model.NguoiDung;
import com.hkcare.repository.DataStore;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/** Danh sách nhân viên (Quản trị viên, Dược sĩ trưởng, Dược sĩ). */
@Service
public class UserService {
    private final DataStore db;

    public UserService(DataStore db) { this.db = db; }

    public List<NguoiDung> danhSach() { return new ArrayList<>(db.nguoiDungs); }

    public NguoiDung tim(String maND) {
        return db.nguoiDungs.stream().filter(u -> u.getMaND().equals(maND)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy người dùng"));
    }
}
