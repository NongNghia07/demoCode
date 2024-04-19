package com.example.demo.Service.Impl;

import com.example.demo.Config.ModelMapperConfig;
import com.example.demo.DTO.ChiTietHoaDonDTO;
import com.example.demo.DTO.HoaDonDTO;
import com.example.demo.Entity.ChiTietHoaDon;
import com.example.demo.Entity.SanPham;
import com.example.demo.Exception.ApiRequestException;
import com.example.demo.Repository.ChiTietHoaDonRepository;
import com.example.demo.Repository.SanPhamRepository;
import com.example.demo.Service.ChiTietHoaDonService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ChiTietHoaDonServiceImpl implements ChiTietHoaDonService {
    private final ChiTietHoaDonRepository chiTietHoaDonRepository;
    private final SanPhamRepository sanPhamRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<ChiTietHoaDonDTO> createAll(List<ChiTietHoaDonDTO> chiTietHoaDonDTOS) {
        chiTietHoaDonDTOS.forEach(p -> {
            SanPham sanPham = sanPhamRepository.findById(p.getSanPham().getId())
                    .orElseThrow(() -> new ApiRequestException("Sản phẩm không tồn tại"));
            p.setThanhTien(sanPham.getGiaThanh()*p.getSoLuong());
        });
        List<ChiTietHoaDon> lst = ModelMapperConfig.mapList(chiTietHoaDonDTOS, ChiTietHoaDon.class);
        chiTietHoaDonRepository.saveAll(lst);
        return chiTietHoaDonDTOS;
    }

    @Override
    public List<ChiTietHoaDonDTO> updateAll(HoaDonDTO hoaDonDTO) {
        hoaDonDTO.getChiTietHoaDonS().forEach(p -> {
            SanPham sanPham = sanPhamRepository.findById(p.getSanPham().getId())
                    .orElseThrow(() -> new ApiRequestException("Sản phẩm không tồn tại"));
            p.setThanhTien(sanPham.getGiaThanh()*p.getSoLuong());
            HoaDonDTO hd = new HoaDonDTO();
            hd.setId(hoaDonDTO.getId());
            p.setHoaDon(hd);
        });
        update(hoaDonDTO.getChiTietHoaDonS());
        return ModelMapperConfig.mapList(chiTietHoaDonRepository.findAllByHoaDon_Id(hoaDonDTO.getId()), ChiTietHoaDonDTO.class);
    }

    @Override
    public void delete(Integer id) {
        List<ChiTietHoaDon> lst = chiTietHoaDonRepository.findAllByHoaDon_Id(id);
        chiTietHoaDonRepository.deleteAll(lst);
    }

    private void update(List<ChiTietHoaDonDTO> lst){
        List<ChiTietHoaDon> lstOld = chiTietHoaDonRepository.findAllByHoaDon_Id(lst.get(0).getHoaDon().getId());
        List<ChiTietHoaDon> newLst = new ArrayList<>();
        for (ChiTietHoaDonDTO newItem: lst){
            if(newItem.getId()!=0){
                newLst.add(modelMapper.map(newItem, ChiTietHoaDon.class));
            }
            chiTietHoaDonRepository.save(modelMapper.map(newItem, ChiTietHoaDon.class));
        }
        List<ChiTietHoaDon> lstDelete = lstOld.stream()
                .filter(obj -> newLst.stream().noneMatch(item -> item.getId() == obj.getId()))
                .collect(Collectors.toList());
        chiTietHoaDonRepository.deleteAll(lstDelete);
    }

    public List<ChiTietHoaDonDTO> findAllByHoaDonID(Integer hoaDonID){
        List<ChiTietHoaDon> lst = chiTietHoaDonRepository.findAllByHoaDon_Id(hoaDonID);
        return ModelMapperConfig.mapList(lst, ChiTietHoaDonDTO.class);
    }

}
