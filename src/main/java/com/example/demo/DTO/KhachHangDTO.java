package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KhachHangDTO {
    private int id;
    private String hoTen;
    private String sdt;
    private LocalDate ngaySinh;
    private String image;
    private String imagePath;
    private Set<HoaDonDTO> hoaDonS;
}
