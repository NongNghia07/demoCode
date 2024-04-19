package com.example.demo.Config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;
@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        // Tạo object và cấu hình
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }

    /**
     * @param lst List muốn map
     * @param myClass Class muốn trả về
     * @return  Return về 1 list của class nhận được
     * @param <T>
     * @param <D>
     */
    public static <T, D> List<D> mapList(List<T> lst, Class<D> myClass){
        //        List<ChiTietPhieuThu> chiTietPhieuThus = chiTietPhieuThuDTOS.stream()
//                .map(p -> {
//                    p.setPhieuThuID(phieuThuID);
//                    return modelMapper.map(p, ChiTietPhieuThu.class);
//                }).collect(Collectors.toList());
        ModelMapper modelMapper = new ModelMapper();
        return lst.stream()
                .map(entity -> modelMapper.map(entity, myClass)).collect(Collectors.toList());
    }
}
