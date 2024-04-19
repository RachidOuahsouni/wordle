package application;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ParserMot{


    public List<String> par() {
        List<String> motsFiltres = new ArrayList<>();
        //*********************** parser *************************************/
       File file = new File("liens.txt");
        if (file.exists()) {
            System.out.println("Le fichier existe par");
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    motsFiltres.add(line.toUpperCase());
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else{
        try {

            String url = "https://fr.wiktionary.org/wiki/Wiktionnaire:Liste_de_1750_mots_fran%C3%A7ais_les_plus_courants";

            Document doc = Jsoup.connect(url).get();

            Element bodyContent = doc.select("div#bodyContent").first();

            // Si l'élément <div> est trouvé, sélectionner les éléments <a> avec un attribut href à l'intérieur
            if (bodyContent != null) {
                Elements links = bodyContent.select("a[href^=\"/wiki/\"]");
                // Créer un FileWriter pour écrire dans un fichier
                FileWriter fileWriter = new FileWriter("liens.txt");

                // Créer un BufferedWriter pour écrire en mode tampon
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                // Parcourir les liens et écrire leur texte dans le fichier
                for (Element link : links) {
                    String linkText = link.text();
                    // Remplacer les voyelles accentuées par des voyelles simples
                    linkText = linkText
                            .replace("é", "e")
                            .replace("è", "e")
                            .replace("à", "a")
                            .replace("â", "a")
                            .replace("ê", "e")
                            .replace("û", "u")
                            // Ajoutez d'autres substitutions pour d'autres voyelles accentuées au besoin
                            .toLowerCase(); // Convertir en minuscules pour une correspondance insensible à la casse


                    // Appliquer la logique de filtrage ici (exclure les mots contenant '-' par exemple)
                    if (!linkText.contains("-") && !linkText.contains("’") && !linkText.contains(" ") && linkText.length() >= 5) {
                        motsFiltres.add(linkText.toUpperCase());
                        bufferedWriter.write(linkText.toUpperCase());
                        bufferedWriter.newLine(); // Ajoute une nouvelle ligne pour chaque lien
                    }
                }

                // Fermer le BufferedWriter
                bufferedWriter.close();
            }
            System.out.println("Les liens ont été écrits dans le fichier 'liens.txt'.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        }
        return motsFiltres;
    }
    public List<String> parplus(String url, List<String> motsFiltres) {
        /*File file = new File("liens.txt");
        if (file.exists()) {
            System.out.println("Le fichier existe ext");
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    motsFiltres.add(line.toUpperCase());
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else{*/
            try {
                Document doc = Jsoup.connect(url).get();
                String[] words = doc.body().text().split("\\s+"); // Split by whitespace

                FileWriter fileWriter = new FileWriter("liens.txt", true); // Append to the file
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                for (String word : words) {
                    // Apply any additional filtering logic if needed
                    if (!word.contains("-") && !word.contains("’") && !word.contains(" ") && word.length() >= 5) {
                        motsFiltres.add(word);
                        bufferedWriter.write(word);
                        bufferedWriter.newLine();
                    }
                }

                bufferedWriter.close();
                System.out.println("Les mots ont été ajoutés au fichier 'liens.txt'.");
            } catch (IOException e) {
                e.printStackTrace();
            }
       // }
        return motsFiltres;
    }
    public String slectMot(int n,List<String> mots){
        String randomWord = null;
        if (!mots.isEmpty()) {
            Random random = new Random();
            List<String> LetterWords = new ArrayList<>();

            for (String mot : mots) {
                if (mot.length() == n) {
                    LetterWords.add(mot);
                }
            }

            if (!LetterWords.isEmpty()) {
                int randomIndex = random.nextInt(LetterWords.size());
                randomWord = LetterWords.get(randomIndex);
                System.out.println("Mot de"+ n +"lettres sélectionné au hasard : " + randomWord);
            } else {
                System.out.println("Aucun mot de"+ n +"lettres trouvé dans le fichier.");
            }
        } else {
            System.out.println("Aucun mot trouvé dans le fichier.");
        }
        return randomWord;
    }
}


