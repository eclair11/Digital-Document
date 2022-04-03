# DETAIL DES FICHIERS DE TEST
_____________________________

## AVANT PROPOS
___________________

### Objet des fichiers de test

Voici les fichiers de tests qui nous ont permis de prouver que :
* Le xsdValidator fait bien son travail
* Nos différentes sécurité ne laissent passé que les fichiers qui :
	* répondent aux critères requis
	* sont bien formés
* Le FileChecker fait bien son travail
* Les évènements sont bien enregistrés dans le fichier de log


### Contenu du dossier " testdata "

* 4 fichiers xml valides
* 7 fichiers xml non valides

### Usage des fichiers de test

Déplacer selon l'action souhaitée chaque fichier xml dans le répertoire :
* " projet/data/XML "

### Protocole de test pour la présentation du 28/11/2019

Afin de mieux démontrer que le cahier des charges a bien été respecté, les fichiers XML doivent être ajouté au dossier " projet/data/XML " en respectant cet ordre général :
* Ajouter les fichiers valides puis les fichiers non valides
* Pour les fichiers non valides, l'ordre a moins d'importance puisque :
	* chaque erreur est indépendante
	* une seule erreur entraine le rejet du fichier

Dans le détail, il sera souhaitable et utile à la démonstration de procéder ainsi :
* Ajout des fichiers valides dans cet ordre :
	* valide_add.xml
		* Insertion des 5 avions de base
		* Permet toutes les manipulations ultérieures sur la BDD
	* valide_del.xml
		* Efface ce qui vient d'être ajouté
	* valide_update.xml
		* Met à jour les avions présents
		* Ne crée pas en doublons des avions non présents dans la BDD
	* valide_add.xml (à nouveau)
		* A ce moment là, le fichier add_1 est déjà en mémoire
		* Permet de démontrer que la contrainte de l'identifiant unique est respectée
	* valide_add_idFic.xml
		* Comme le fichier avec l'identifiant unique add_1 est déjà présent en mémoire
			* si on essaie de la rajouter à nouveau
				* une erreur sera signalée et le fichier sera rejeté
			* si on met un fichier d'ajout avec un idFic différent (add_2)
				* le fichier doit passer sans problème
				* les avions qui ne sont pas déjà présents sont rajoutés
					* ceux déjà présent ne sont pas impactés
* Ajout des fichiers non valides dans l'ordre de son choix (aucune importance) :
	* non_valide_Latin.xml
	* non_valide_Blank.xml
	* non_valide_add_structure.xml
	* non_valide_add_date.xml
	* non_valide_add_Checksum.xml
	* non_valide_add_1000carac.xml
	* non_valide_add_1carac.xml



## FICHIERS VALIDES
___________________

### valide_add.xml

* Fichier de test valide - ajout
	* doit passer sans problème s'il n'est pas déjà en mémoire

### valide_del.xml

* Fichier de test valide - delete
	* doit passer sans problème s'il n'est pas déjà en mémoire

### valide_update.xml

* Fichier de test valide - update
	* doit passer sans problème s'il n'est pas déjà en mémoire

### valide_add_idFic.xml

* 2ème fichier de test valide - ajout - idFic modifié pour permettre second ajout
	* doit passer sans problème s'il n'est pas déjà en mémoire
	* sert à prouver que l'identifiant unique du fichier "idFic" est bien contrôlé
	* contenu identique au fichier "valide_add.xml" en dehors de l'attribut idFic


## FICHIERS NON VALIDES
_______________________

### non_valide_Latin.xml

* Fichier de test non valide - présence de caractère non ASCII
	* Soukhoï Su-27 non accepté
	* doit rejeter le fichier et retourner une erreur de type de caractère

### non_valide_Blank.xml

* Fichier de test non valide - structure du fichier qui ne respecte pas le validator.xsd
	* doit rejeter le fichier et retourner une erreur de fichier invalide car complètement vide

### non_valide_add_structure.xml

* Fichier de test non valide - structure du fichier qui ne respecte pas le validator.xsd
	* doit rejeter le fichier et retourner une erreur de  fichier invalide

### non_valide_add_date.xml

* Fichier de test non valide - date déclarée supérieure à 3 mois (par rapport au 2019-11-28)
	* doit rejeter le fichier et retourner une erreur de date supérieure à 3 mois

### non_valide_add_Checksum.xml

* Fichier de test non valide - checksum déclaré mis a 1 au mieu de 5
	* doit rejeter le fichier et retourner une erreur de checksum

### non_valide_add_1000carac.xml

* Fichier de test non valide - champs <name> d'avion qui a plus de 1000 caractères
	* doit rejeter le fichier et retourner une erreur du nombre de caractères égal supérieur à 1000
	* (<name>A ...</name>) 1006 caractères pour être précis
	* le fichier fait également **plus de 5ko**, l'erreur doit également être signalée

### non_valide_add_1carac.xml

* Fichier de test non valide - champs <name> d'avion qui n'a qu'un seul caractère (<name>A</name>)
	* doit rejeter le fichier et retourner une erreur du nombre de caractères égal à 1