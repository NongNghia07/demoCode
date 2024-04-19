package com.example.demo.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "chitiethoadon")
@Data
public class ChiTietHoaDon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "soluong")
    private int soLuong;

    @Column(name = "dvt")
    private String dvt;

    @Column(name = "thanhtien")
    private double thanhTien;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hoadonid")
    @JsonIgnore
    private HoaDon hoaDon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sanphamid")
    @JsonIgnore
    private SanPham sanPham;
}
