package be.brickbit.lpm.catering.service.resources;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Iterator;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import be.brickbit.lpm.infrastructure.AbstractController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@RestController
@RequestMapping("/file")
public class FileResourceController extends AbstractController {

	@Value("${lpm.storage.images.products}")
	private String productImageLocation;

	@RequestMapping(value = "/image/product/{id}", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
	@PreAuthorize(value = "hasAnyRole('USER')")
	public ResponseEntity<InputStreamResource> getProductImage(@PathVariable("id") String id) throws IOException {
		File image = new File(Paths.get(productImageLocation, id + ".png").toString());

		if (image.exists()) {
			return ResponseEntity.ok(new InputStreamResource(new FileInputStream(image)));
		} else {
			return ResponseEntity.ok((new InputStreamResource(this.getClass().getClassLoader().getResourceAsStream("nocover.png"))));
		}
	}

    @RequestMapping(value = "/image/product/{id}", method = RequestMethod.POST)
    @PreAuthorize(value = "hasAnyRole('CATERING_ADMIN', 'ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProductImage(MultipartHttpServletRequest request, @PathVariable("id") String id) throws IOException{
        Iterator<String> itr=request.getFileNames();
        MultipartFile file=request.getFile(itr.next());
        if(file.getContentType().equals("image/png")) {
            File newImage = new File(Paths.get(productImageLocation, id + ".png").toString());
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(newImage));
            stream.write(file.getBytes());
            stream.close();
        }else{
            throw new RuntimeException("File format not supported.");
        }
    }
}
