package be.brickbit.lpm.catering.service.product;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public interface ProductImageService {
    Optional<File> getProductImage(String productId);

    Boolean deleteProductImage(String productId);

    void saveProductImage(String productId, byte[] imageFile) throws IOException;
}
