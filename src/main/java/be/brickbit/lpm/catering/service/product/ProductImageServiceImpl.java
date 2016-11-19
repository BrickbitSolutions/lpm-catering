package be.brickbit.lpm.catering.service.product;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class ProductImageServiceImpl implements ProductImageService {

    @Value("${lpm.storage.images.products}")
    private String productImageLocation;

    @Override
    public Optional<File> getProductImage(String productId) {
        File image = new File(Paths.get(productImageLocation, productId + ".png").toString());

        if (image.exists()) {
            return Optional.of(image);
        }

        return Optional.empty();
    }

    @Override
    public Boolean deleteProductImage(String productId) {
        File fileToDelete = new File(Paths.get(productImageLocation, productId + ".png").toString());
        return fileToDelete.exists() && fileToDelete.delete();
    }

    @Override
    public void saveProductImage(String productId, byte[] imageFile) throws IOException {
        File newImage = new File(Paths.get(productImageLocation, productId + ".png").toString());
        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(newImage));
        stream.write(imageFile);
        stream.close();
    }
}
