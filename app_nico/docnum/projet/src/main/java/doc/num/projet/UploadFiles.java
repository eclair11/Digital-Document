package doc.num.projet;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class UploadFiles {

    MultipartFile[] files;

    UploadFiles() {

    }

}