package com.example.demo.Controller;

import com.example.demo.DTO.HoaDonDTO;
import com.example.demo.Service.HoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/hoadon")
public class HoaDonRestController {
    private final HoaDonService hoaDonService;

    @Autowired
    public HoaDonRestController(HoaDonService hoaDonService) {
        this.hoaDonService = hoaDonService;
    }

    @PostMapping("create")
    public ResponseEntity<?> create(@RequestBody HoaDonDTO hoaDonDTO){
        return ResponseEntity.ok(hoaDonService.create(hoaDonDTO));
    }

    @PostMapping("update")
    public ResponseEntity<?> update(@RequestBody HoaDonDTO hoaDonDTO){
        return ResponseEntity.ok(hoaDonService.update(hoaDonDTO));
    }

    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable("id") Integer id){
        hoaDonService.delete(id);
    }

    @GetMapping("findAll")
    public ResponseEntity<?> findAll(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                     @RequestParam(name = "size", defaultValue = "20") Integer size){
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(hoaDonService.findAll(pageable));
    }

    @GetMapping("findAllByYearAndMonth")
    public ResponseEntity<?> findAll(@RequestParam Integer year, @RequestParam Integer month,
                                     @RequestParam(name = "page", defaultValue = "0") Integer page,
                                     @RequestParam(name = "size", defaultValue = "20") Integer size){
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(hoaDonService.findAllByYearAndMonth(year, month, pageable));
    }

    @GetMapping("findAllByDateRange")
    public ResponseEntity<?> findALlByDateRange(@RequestParam LocalDate startDate,
                                                @RequestParam LocalDate endDate,
                                                @RequestParam(name = "page", defaultValue = "0") Integer page,
                                                @RequestParam(name = "size", defaultValue = "20") Integer size){
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(hoaDonService.findALlByDateRange(startDate, endDate, pageable));
    }

    @GetMapping("findAllByTotalRange")
    public ResponseEntity<?> findAllByTotalRange(@RequestParam double min, @RequestParam double max,
                                                 @RequestParam(name = "page", defaultValue = "0") Integer page,
                                                 @RequestParam(name = "size", defaultValue = "20") Integer size){
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(hoaDonService.findAllByTotalRange(min, max, pageable));
    }

    @GetMapping("findByMaGiaoDichOrTenHoaDon")
    public ResponseEntity<?> findByMaGiaoDichOrTenHoaDon(@RequestParam String maGiaoDich, @RequestParam String tenHoaDon){
        return ResponseEntity.ok(hoaDonService.findByMaGiaoDichOrTenHoaDon(maGiaoDich, tenHoaDon));
    }
}
