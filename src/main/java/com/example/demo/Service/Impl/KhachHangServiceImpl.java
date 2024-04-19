package com.example.demo.Service.Impl;

import com.example.demo.Config.ModelMapperConfig;
import com.example.demo.DTO.KhachHangDTO;
import com.example.demo.Entity.KhachHang;
import com.example.demo.Exception.ApiRequestException;
import com.example.demo.Repository.KhachHangRepository;
import com.example.demo.Service.KhachHangService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KhachHangServiceImpl implements KhachHangService {
    private final KhachHangRepository khachHangRepository;
    private final ModelMapper modelMapper;

    @Override
    public KhachHangDTO create(KhachHangDTO khachHangDTO) {
        KhachHang khachHang = modelMapper.map(khachHangDTO, KhachHang.class);
        khachHangRepository.save(khachHang);
        khachHangDTO.setId(khachHang.getId());
        return khachHangDTO;
    }

    @Override
    public List<KhachHangDTO> findAll() {
        List<KhachHang> lst = khachHangRepository.findAll();
        List<KhachHangDTO> lstDTO = ModelMapperConfig.mapList(lst, KhachHangDTO.class);
        for (KhachHangDTO p: lstDTO){
            if(p.getImage() != null){
                p.setImagePath("user-photos/"+p.getId()+ "/" +p.getImage());
            }
        }
        return lstDTO;
    }
}
