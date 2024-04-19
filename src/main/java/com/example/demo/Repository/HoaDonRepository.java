package com.example.demo.Repository;

import com.example.demo.Entity.HoaDon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface HoaDonRepository extends JpaRepository<HoaDon, Integer> {
    @Query(value = "select * from hoadon where " +
            "YEAR(thoigiantao) = ?1 and MONTH(thoigiantao) = ?2 and DAY(thoigiantao) = ?3 " +
            "order by id desc limit 1", nativeQuery = true)
    HoaDon findByThoiGianTaoLimit1(int year, int month, int day);

    Page<HoaDon> findAllByOrderByThoiGianTaoDesc(Pageable pageable);

    @Query(value = "select * from hoadon where " +
            "YEAR(thoigiantao) = ?1 and MONTH(thoigiantao) = ?2 " +
            "order by thoigiantao desc", nativeQuery = true)
    Page<HoaDon> findByThoiGianTaoYearAndThoiGianTaoMonth(Integer year, Integer month, Pageable pageable);

    @Query(value = "select * from hoadon where " +
            "DATE(thoigiantao) between ?1 and ?2 " +
            "order by thoigiantao desc", nativeQuery = true)
    Page<HoaDon> findByThoiGianTaoBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);

    Page<HoaDon> findByTongTienBetweenOrderByThoiGianTaoDesc(Double min, Double max, Pageable pageable);

    HoaDon findByMaGiaoDichOrTenHoaDon(String maGiaoDich, String tenHoaDon);
}
