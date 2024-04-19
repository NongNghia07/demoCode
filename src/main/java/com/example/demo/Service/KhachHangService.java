package com.example.demo.Service;

import com.example.demo.DTO.KhachHangDTO;

import java.util.List;

public interface KhachHangService {
    public KhachHangDTO create(KhachHangDTO khachHangDTO);
    public List<KhachHangDTO> findAll();
}
