package com.hkcare.service;

import com.hkcare.model.KhuyenMai;
import com.hkcare.repository.DataStore;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PromotionService {
    private final DataStore db;

    public PromotionService(DataStore db) { this.db = db; }

    public List<KhuyenMai> danhSach() { return new ArrayList<>(db.khuyenMais); }
}
