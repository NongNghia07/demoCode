package com.example.demo.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Table(name = "loaisanpham")
@Data
public class LoaiSanPham {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "tenloaisanpham")
    private String tenLoaiSanPham;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "loaiSanPham")
    @JsonIgnore
    private Set<SanPham> sanPhams;
}
