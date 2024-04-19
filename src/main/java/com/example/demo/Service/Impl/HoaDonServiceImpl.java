package com.example.demo.Service.Impl;

import com.example.demo.Config.ModelMapperConfig;
import com.example.demo.DTO.ChiTietHoaDonDTO;
import com.example.demo.DTO.HoaDonDTO;
import com.example.demo.Entity.HoaDon;
import com.example.demo.Repository.HoaDonRepository;
import com.example.demo.Service.ChiTietHoaDonService;
import com.example.demo.Service.HoaDonService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HoaDonServiceImpl implements HoaDonService {
    private final HoaDonRepository hoaDonRepository;
    private final ModelMapper modelMapper;
    private final ChiTietHoaDonService chiTietHoaDonService;

    @Override
    public HoaDonDTO create(HoaDonDTO hoaDonDTO) {
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        // tìm hóa đơn cuối trong ngày
        HoaDon hd = hoaDonRepository.findByThoiGianTaoLimit1(localDate.getYear(), localDate.getMonth().getValue(), localDate.getDayOfMonth());
        // set mã giao dịch dựa vào mã hóa đơn cuối trong ngày
        hoaDonDTO.setMaGiaoDich(generateMaGiaoDich(hd!=null?hd.getMaGiaoDich():null));
        hoaDonDTO.setThoiGianTao(LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter));
        HoaDon hoaDon = modelMapper.map(hoaDonDTO, HoaDon.class);
        hoaDonRepository.save(hoaDon);
        // set id hóa đơn
        hoaDonDTO.getChiTietHoaDonS().forEach(p -> {
            HoaDonDTO newHD = new HoaDonDTO();
            newHD.setId(hoaDon.getId());
            p.setHoaDon(newHD);
        });
        // thêm hóa đơn chi tiết
        List<ChiTietHoaDonDTO> lstCTHD = chiTietHoaDonService.createAll(hoaDonDTO.getChiTietHoaDonS());
        // tính tổng tiền hóa đơn
        double tongTien = tinhTongTien(lstCTHD);
        hoaDon.setTongTien(tongTien);
        hoaDonDTO.setTongTien(tongTien);
        hoaDonRepository.save(hoaDon);
        return hoaDonDTO;
    }

    @Override
    public HoaDonDTO update(HoaDonDTO hoaDonDTO) {
        HoaDon hoaDonOld = hoaDonRepository.findById(hoaDonDTO.getId()).orElseThrow();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        hoaDonDTO.setThoiGianCapNhat(LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter));
        List<ChiTietHoaDonDTO> lstCTHD = chiTietHoaDonService.updateAll(hoaDonDTO);
        hoaDonDTO.setTongTien(tinhTongTien(lstCTHD));
        hoaDonDTO.setMaGiaoDich(hoaDonOld.getMaGiaoDich());
        hoaDonDTO.setThoiGianTao(hoaDonOld.getThoiGianTao());
        HoaDon hoaDon = modelMapper.map(hoaDonDTO, HoaDon.class);
        hoaDonRepository.save(hoaDon);
        hoaDonDTO = modelMapper.map(hoaDon, HoaDonDTO.class);
        return hoaDonDTO;
    }

    @Override
    public void delete(Integer id) {
        chiTietHoaDonService.delete(id);
        hoaDonRepository.deleteById(id);
    }

    @Override
    public Page<HoaDonDTO> findAll(Pageable pageable) {
        Page<HoaDon> page = hoaDonRepository.findAllByOrderByThoiGianTaoDesc(pageable);
        List<HoaDonDTO> lstDTO = ModelMapperConfig.mapList(page.getContent(), HoaDonDTO.class);
        lstDTO = setChiTietHoaDon(lstDTO);
        return new PageImpl<>(lstDTO, pageable, page.getTotalElements());
    }

    @Override
    public Page<HoaDonDTO> findAllByYearAndMonth(Integer year, Integer month, Pageable pageable) {
        Page<HoaDon> page = hoaDonRepository.findByThoiGianTaoYearAndThoiGianTaoMonth(year, month, pageable);
        List<HoaDonDTO> lstDTO = ModelMapperConfig.mapList(page.getContent(), HoaDonDTO.class);
        lstDTO = setChiTietHoaDon(lstDTO);
        return new PageImpl<>(lstDTO, pageable, page.getTotalElements());
    }

    @Override
    public Page<HoaDonDTO> findALlByDateRange(LocalDate startDate, LocalDate endDate, Pageable pageable) {
        Page<HoaDon> page = hoaDonRepository.findByThoiGianTaoBetween(startDate, endDate, pageable);
        List<HoaDonDTO> lstDTO = ModelMapperConfig.mapList(page.getContent(), HoaDonDTO.class);
        lstDTO = setChiTietHoaDon(lstDTO);
        return new PageImpl<>(lstDTO, pageable, page.getTotalElements());
    }

    @Override
    public Page<HoaDonDTO> findAllByTotalRange(double min, double max, Pageable pageable) {
        Page<HoaDon> page = hoaDonRepository.findByTongTienBetweenOrderByThoiGianTaoDesc(min, max, pageable);
        List<HoaDonDTO> lstDTO = ModelMapperConfig.mapList(page.getContent(), HoaDonDTO.class);
        lstDTO = setChiTietHoaDon(lstDTO);
        return new PageImpl<>(lstDTO, pageable, page.getTotalElements());
    }

    @Override
    public HoaDonDTO findByMaGiaoDichOrTenHoaDon(String maGiaoDich, String tenHoaDon) {
        HoaDon hoaDon = hoaDonRepository.findByMaGiaoDichOrTenHoaDon(maGiaoDich, tenHoaDon);
        HoaDonDTO hoaDonDTO = modelMapper.map(hoaDon, HoaDonDTO.class);
        hoaDonDTO.setChiTietHoaDonS(chiTietHoaDonService.findAllByHoaDonID(hoaDonDTO.getId()));
        return hoaDonDTO;
    }

    private String generateMaGiaoDich(String maGiaoDich){
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYYMMdd");
        String formattedDate = localDate.format(formatter);
        if(maGiaoDich != null){
            String str[] = maGiaoDich.split("_");
            int soGD = Integer.parseInt(str[1]) + 1;
            return formattedDate+"_"+(String.format("%03d", soGD));
        }
        return formattedDate+"_001";
    }

    private double tinhTongTien(List<ChiTietHoaDonDTO> lst){
        double tong = 0;
        if(!lst.isEmpty()){
            for (ChiTietHoaDonDTO p: lst){
                tong += p.getThanhTien();
            }
        }
        return tong;
    }

    private List<HoaDonDTO> setChiTietHoaDon(List<HoaDonDTO> lst){
        for (HoaDonDTO p: lst){
            p.setChiTietHoaDonS(chiTietHoaDonService.findAllByHoaDonID(p.getId()));
        }
        return lst;
    }
}
