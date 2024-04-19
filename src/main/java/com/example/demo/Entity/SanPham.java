package com.example.demo.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "sanpham")
@Data
public class SanPham {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "tensanpham")
    private String tenSanPham;

    @Column(name = "giathanh")
    private double giaThanh;

    @Column(name = "mota")
    private String moTa;

    @Column(name = "ngayhethan")
    private LocalDateTime ngayHetHan;

    @Column(name = "kyhieusanpham")
    private String kyHieuSanPham;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loaisanphamid")
    @JsonIgnore
    private LoaiSanPham loaiSanPham;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sanPham")
    @JsonIgnore
    private Set<ChiTietHoaDon> chiTietHoaDons;
}
