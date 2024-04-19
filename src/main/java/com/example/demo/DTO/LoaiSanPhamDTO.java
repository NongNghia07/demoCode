package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoaiSanPhamDTO {
    private int id;
    private String tenLoaiSanPham;
    private Set<SanPhamDTO> sanPhamS;
}
