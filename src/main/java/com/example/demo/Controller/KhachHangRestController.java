package com.example.demo.Controller;

import com.example.demo.Config.FileUploadUtil;
import com.example.demo.DTO.KhachHangDTO;
import com.example.demo.Service.KhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;

@RestController
@RequestMapping("api/v1/khachhang")
public class KhachHangRestController {
    private final KhachHangService khachHangService;

    @Autowired
    public KhachHangRestController(KhachHangService khachHangService) {
        this.khachHangService = khachHangService;
    }

    @GetMapping("findAll")
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok(khachHangService.findAll());
    }

    @PostMapping(value = "create", consumes = {"multipart/form-data"})
    public ResponseEntity<?> create(@RequestParam MultiValueMap<String, Object> requestMap,
                                    @RequestParam("image") MultipartFile multipartFile) throws IOException {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        KhachHangDTO khachHangDTO = new KhachHangDTO();
        khachHangDTO.setHoTen((String) requestMap.getFirst("hoTen"));
        khachHangDTO.setSdt((String) requestMap.getFirst("sdt"));
        khachHangDTO.setNgaySinh(LocalDate.parse((String) requestMap.getFirst("ngaySinh")));
        khachHangDTO.setImage(fileName);
        khachHangDTO = khachHangService.create(khachHangDTO);
        String uploadDir = "user-photos/" + khachHangDTO.getId();
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        return ResponseEntity.ok(khachHangDTO);
    }
}
