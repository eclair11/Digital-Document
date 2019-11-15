package doc.num.projet;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import lombok.var;

/**
 * FirstController
 */
@Controller
public class FirstController {

    @Inject
    public AvionRepository avionRep;

    @RequestMapping("/")
    public String redirect() {
        return "redirect:/home";
    }

    @RequestMapping("/home")
    public String index(Model model) {
        UploadFiles upload = new UploadFiles();
        model.addAttribute("upload", upload);
        return "index";
    }

    @RequestMapping(value = "/addfiles", method = RequestMethod.POST)
    public String addFiles(Model model, @ModelAttribute("upload") UploadFiles upload) {
        return this.multiFileUpload(model, upload);
    }

    /* De BDD vers XML */
    @ResponseBody
    @RequestMapping(value = "/serializeXML", produces = MediaType.APPLICATION_ATOM_XML_VALUE)
    public Iterable<Avion> SerializeXMLFlotte() {
        return avionRep.findAll();
    }

    @ResponseBody
    @RequestMapping(value = "/serializeXML/{avionId}", produces = MediaType.APPLICATION_ATOM_XML_VALUE)
    public Avion SerializeXMLAvion(@PathVariable Long avionId) {
        var avion = avionRep.findById(avionId).get();
        return avion;
    }

    @RequestMapping(value = "/deserializationJackson")
    public String DeserializationJackson() {
        ParserJacksonXml.Deserialize();
        return "redirect:/home";
    }

    @RequestMapping(value = "/deserializationDOM")
    public String DeserializationDOM() {
        System.out.println(avionRep);
        ParserDomJava.parseXMLDomJava(avionRep);
        return "redirect:/home";
    }

    @RequestMapping(value = "/deserializationSAX")
    public String DeserializationSAX() {
        ParserSaxJava.ParserXMLSaxJava();
        return "redirect:/home";
    }

    private String multiFileUpload(Model model, UploadFiles upload) {
        String uploadRootPath = "./src/main/resources/XML/";
        File uploadRootDir = new File(uploadRootPath);
        if (!uploadRootDir.exists()) {
            uploadRootDir.mkdirs();
        }
        MultipartFile[] fileDatas = upload.getFiles();
        List<String> linkedFiles = new ArrayList<>();
        List<String> failedFiles = new ArrayList<>();
        for (MultipartFile fileData : fileDatas) {
            String name = fileData.getOriginalFilename();
            if (name != null && name.length() > 0) {
                try {
                    File serverFile = new File(uploadRootDir.getAbsolutePath() + File.separator + name);
                    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                    stream.write(fileData.getBytes());
                    stream.close();
                    linkedFiles.add(name);
                } catch (Exception e) {
                    System.out.println("Error Write file: " + name);
                    failedFiles.add(name);
                }
            }
        }
        model.addAttribute("linkedFiles", linkedFiles);
        model.addAttribute("failedFiles", failedFiles);
        return "redirect:/home";
    }

}