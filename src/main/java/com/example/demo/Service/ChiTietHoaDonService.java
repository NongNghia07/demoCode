package com.example.demo.Service;


import com.example.demo.DTO.ChiTietHoaDonDTO;
import com.example.demo.DTO.HoaDonDTO;

import java.util.List;
import java.util.Set;

public interface ChiTietHoaDonService {
    public List<ChiTietHoaDonDTO> createAll(List<ChiTietHoaDonDTO> chiTietHoaDonDTOS);
    public List<ChiTietHoaDonDTO> updateAll(HoaDonDTO hoaDonDTO);
    public List<ChiTietHoaDonDTO> findAllByHoaDonID(Integer hoaDonID);
    public void delete(Integer id);
}
