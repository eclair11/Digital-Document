package doc.num.projet.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Nicolas T.
 */
public class Scribe {

    public static void logMemoire(String info) throws IOException{

        /* teste si le fichier existe */
        File fichierLog = new File("data/XML/log.txt");
        boolean fichierLogPresent = fichierLog.exists();

        /* créer le fichier s'il n'existe pas
         * le récupère sinon
         * */
        BufferedWriter writer = new BufferedWriter(new FileWriter("data/XML/log.txt", true));

        /* récupération de la date */
        SimpleDateFormat dateFormattee = new SimpleDateFormat("yyyy-MM-dd à HH:mm:ss");
        Date dateDuJour = new Date();
        String dateFormatteeString = dateFormattee.format(dateDuJour);

        /* si le fichier n'existait pas on a du le créer et ajouter un entête */
        if(!fichierLogPresent){  
            String entete = "Fichier de log du scrutateur crée le " + dateFormatteeString + "\n\n";
            writer.write(entete);
        }

        /* s'il existe, on ajoute à la suite du log */
        else{
            String separation = "\n\n--------------------------------\n\n";
            writer.write(separation + "Modification en date du " + dateFormatteeString + "\n\n");
        }

        /* on reprend l'écriture normale du fichier */
        writer.append(info);
     
        /* à la fin, on ferme le fichier */
        writer.close();
    }

    /* simple ajout d'infos dans le log une fois qu'on s'est assurée de sa création */
    public static void logRajout(String info) throws IOException{

        BufferedWriter writer = new BufferedWriter(new FileWriter("data/XML/log.txt", true));

        /* on reprend l'écriture normale du fichier */
        writer.append(info);
     
        /* à la fin, on ferme le fichier */
        writer.close();
    }

}