package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SanPhamDTO {
    private int id;
    private String tenSanPham;
    private double giaThanh;
    private String moTa;
    private LocalDate ngayHetHan;
    private String kyHieuSanPham;
    private Set<ChiTietHoaDonDTO> chiTietHoaDonS;
    private LoaiSanPhamDTO loaiSanPham;
}
