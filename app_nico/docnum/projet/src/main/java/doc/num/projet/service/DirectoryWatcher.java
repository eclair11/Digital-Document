/*
 * Service that will permit to watch a directory activity: new file added, file updated or file deleted
 */

package doc.num.projet.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import java.nio.file.*;
import java.io.*; 

/**
 *
 * @author Solofo R.
 */
@Component
@Scope("prototype")
public class DirectoryWatcher extends Thread{
    @Override
    public void run() {
        try {
            WatchService watchService
             = FileSystems.getDefault().newWatchService();

           Path path = Paths.get("src/main/resources/XML/");

           path.register(
             watchService, 
               StandardWatchEventKinds.ENTRY_CREATE, 
                 StandardWatchEventKinds.ENTRY_DELETE, 
                   StandardWatchEventKinds.ENTRY_MODIFY);

           WatchKey key;
           String lastFilename = "";
           while ((key = watchService.take()) != null) {
               for (WatchEvent<?> event : key.pollEvents()) {
                   if (event.kind().toString() == "ENTRY_CREATE" && lastFilename != event.context().toString()) {
                    System.out.println(
                        "Event kind:" + event.kind() 
                        + ". File affected: " + event.context() + ".");
                    lastFilename = event.context().toString();
                   }
                   key.reset();
               }
               key.reset();
           }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
