package com.example.demo.Service;


import com.example.demo.DTO.HoaDonDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface HoaDonService {
    public HoaDonDTO create(HoaDonDTO hoaDonDTO);
    public HoaDonDTO update(HoaDonDTO hoaDonDTO);
    public void delete(Integer id);
    public Page<HoaDonDTO> findAll(Pageable pageable);
    public Page<HoaDonDTO> findAllByYearAndMonth(Integer year, Integer month, Pageable pageable);
    public Page<HoaDonDTO> findALlByDateRange(LocalDate startDate, LocalDate endDate, Pageable pageable);
    public Page<HoaDonDTO> findAllByTotalRange(double min, double max, Pageable pageable);
    public HoaDonDTO findByMaGiaoDichOrTenHoaDon(String maGiaoDich, String tenHoaDon);
}
