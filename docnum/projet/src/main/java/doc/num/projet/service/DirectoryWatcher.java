/*
 * Service that will permit to watch a directory activity: new file added, file updated or file deleted
 */

package doc.num.projet.service;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import javax.inject.Inject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import doc.num.projet.modele.Message;

/**
 *
 * @author Solofo R.
 */
@Component
@Scope("prototype")
public class DirectoryWatcher extends Thread {

    @Inject
    FileChecker checker;

    @Inject
    FileParser parser;

    @Inject
    RestClient restClient;

    private static String lastFilename = "";

    @Override
    public void run() {
        System.out.println("Le scrutateur est lancé et attends des fichiers XML dans : projet/data/XML/");
        while (true) {
            try {
                WatchService watchService = FileSystems.getDefault().newWatchService();

                // Path path = Paths.get("src/main/resources/XML/");
                Path path = Paths.get("data/XML/");

                path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE,
                        StandardWatchEventKinds.ENTRY_MODIFY);

                WatchKey key;
                String fileName = "";
                while ((key = watchService.take()) != null) {
                    WatchEvent<?> event = key.pollEvents().get(0); // TRAITER UN FICHIER À LA FOIS
                    // for (WatchEvent<?> event : key.pollEvents()) {
                    if (event.kind().toString() == "ENTRY_CREATE" && !lastFilename.equals(event.context().toString())) {
                        fileName = event.context().toString();
                        lastFilename = fileName;
                        boolean isValid = checker.checkFile(fileName);
                        if (isValid) { // si le fichier XML est valide
                            // ask rest client to send success message
                            String contenus = "le fichier " + fileName + " a été accépté !";
                            restClient.addNewMessage(new Message(contenus, "success"));
                            parser.parse(fileName);
                        } else { // ask rest client to send failure message
                            String contenus = "le fichier " + fileName + " a été rejeté !";
                            restClient.addNewMessage(new Message(contenus, "failure"));
                            lastFilename = "";
                        }
                    }
                    // }
                    key.reset();
                    System.out.println("le scrutateur attends un autre fichier XML");
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}
