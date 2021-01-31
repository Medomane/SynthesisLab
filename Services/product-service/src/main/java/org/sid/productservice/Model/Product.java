package org.sid.productservice.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String name;
    private double price;
    private int quantityAvailable;
    @JsonIgnore
    private Long supplierId;
    @Transient
    private Supplier supplier;
    @JsonIgnore
    public String getImagePath(){
        return getImagesPath()+"/"+id+".jpg";
    }
    public static String getImagesPath(){
        return System.getProperty("user.home")+"/e-commerce/products" ;
    }
    public void saveFile(MultipartFile multipartFile) throws IOException {
        var fileName = getId()+".jpg";
        Path uploadPath = Paths.get(Product.getImagesPath());
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + fileName, ioe);
        }
    }
}
