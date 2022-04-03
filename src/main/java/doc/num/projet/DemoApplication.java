package doc.num.projet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import doc.num.projet.AppConfig;
import doc.num.projet.service.DirectoryWatcher;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        DirectoryWatcher watcher = (DirectoryWatcher) ctx.getBean("directoryWatcher");
        watcher.start();
    }

}
