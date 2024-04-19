package com.example.demo.Config;

import com.example.demo.Exception.ApiRequestException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUploadUtil {
    public static void saveFile(String uploadDir, String fileName, MultipartFile multiparFile) throws IOException {
        // Tạo đường dẫn path từ uploadDir
        Path path = Paths.get(uploadDir);
        // Kiểm tra xem thư mục đã tồn tại hay chưa, nếu không, tạo mới
        if(!Files.exists(path)){
            Files.createDirectories(path);
        }
        // Mở InputStream từ MultipartFile và sao chép nó vào đường dẫn đích
        try (InputStream inputStream = multiparFile.getInputStream()){
            // Kết hợp đường dẫn path với tên tệp tin fileName để có đường dẫn hoàn chỉnh.
            Path filePath = path.resolve(fileName);
            // Sao chép dữ liệu từ InputStream vào đường dẫn đích.
            // StandardCopyOption.REPLACE_EXISTING sẽ thay thế tệp tin nếu nó đã tồn tại.
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        }catch (Exception e){
            throw new ApiRequestException("Could not save image file: "+fileName);
        }
    }
}
