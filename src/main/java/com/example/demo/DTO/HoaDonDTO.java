package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HoaDonDTO {
    private int id;
    private String tenHoaDon;
    private String maGiaoDich;
    private LocalDateTime thoiGianTao;
    private LocalDateTime thoiGianCapNhat;
    private String ghiChu;
    private double tongTien;
    private List<ChiTietHoaDonDTO> chiTietHoaDonS;
    private KhachHangDTO khachHang;
}
