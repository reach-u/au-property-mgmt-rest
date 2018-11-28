package au.property.mgmt.rest.controller.priv;

import au.property.mgmt.rest.util.Constants;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;

@RestController
@RequestMapping(Constants.PRIVATE_API_V1_URL + "/thumbnail")
public class ThumbnailController {

    @RequestMapping(value = "{propertyId}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<byte[]> getImage(@PathVariable("propertyId") long propertyId) throws IOException {

        HttpHeaders headers = new HttpHeaders();
        String imageSrc = "classpath:images/default.png";

        if (propertyId >= 1 && propertyId <= 10) {
            imageSrc = "classpath:images/PropID_1-10_WajirRd_55.png";
        } else if (propertyId >= 11 && propertyId <= 18) {
            imageSrc = "classpath:images/PropID_11-18_WajirRd_49.png";
        } else if (propertyId == 19) {
            imageSrc = "classpath:images/PropID_19_WajirRd_31.png";
        } else if (propertyId >= 20 && propertyId <= 27) {
            imageSrc = "classpath:images/PropID_20-27_WajirRd_5.png";
        } else if (propertyId == 28) {
            imageSrc = "classpath:images/PropID_28_KeinoSt_12.png";
        } else if (propertyId == 29) {
            imageSrc = "classpath:images/PropID_29_JipeSt_12.png";
        } else if (propertyId == 30) {
            imageSrc = "classpath:images/PropID_30_KirinyagaRd_124.png";
        } else if (propertyId == 31) {
            imageSrc = "classpath:images/PropID_31_MwangekaRd_168.png";
        } else if (propertyId == 32) {
            imageSrc = "classpath:images/32_preview_pic.png";
        } else if (propertyId == 33) {
            imageSrc = "classpath:images/33_preview_pic.png";
        }

        byte[] media = Files.readAllBytes(ResourceUtils.getFile(imageSrc).toPath());
        headers.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<>(media, headers, HttpStatus.OK);
    }

}
