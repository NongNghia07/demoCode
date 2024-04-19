package com.example.demo.Entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name="hoadon")
@Data
public class HoaDon{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "tenhoadon")
    private String tenHoaDon;

    @Column(name = "magiaodich")
    private String maGiaoDich;

    @Column(name = "thoigiantao")
    private LocalDateTime thoiGianTao;

    @Column(name = "thoigiancapnhat")
    private LocalDateTime thoiGianCapNhat;

    @Column(name = "ghichu")
    private String ghiChu;

    @Column(name = "tongtien")
    private double tongTien;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "khachhangid")
    @JsonIgnore
    private KhachHang khachHang;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "hoaDon")
    @JsonIgnore
    private Set<ChiTietHoaDon> chiTietHoaDons;
}
