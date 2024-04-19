package com.example.demo.DTO;

import com.example.demo.Entity.SanPham;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChiTietHoaDonDTO {
    private int id;
    private int soLuong;
    private String dvt;
    private double thanhTien;
    private HoaDonDTO hoaDon;
    private SanPhamDTO sanPham;
}
