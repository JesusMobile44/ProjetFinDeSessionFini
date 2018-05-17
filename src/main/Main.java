package main;

import centre.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import planete.*;
import vaisseau.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.net.Socket;
import java.util.*;

public class Main {
    public static List<Planete> listeDePlanete=new ArrayList<>();
    public static List<Vaisseau> listeVaisseau=new ArrayList<>();
    public static List<Centre> listeCentre=new ArrayList<>();
    public static int nbCentre=10;
    public static int nbLeger;
    public static int nbNormal;
    public static int nbLourd;



    public static java.lang.String rouge = (char)27 + "[31m";
    public static java.lang.String rose = (char)27 + "[35m";
    public static java.lang.String vert = (char)27 + "[32m";
    public static java.lang.String bleu = (char)27 + "[34m";
    public static java.lang.String teal = (char)27 + "[36m";
    public static java.lang.String brillant = (char)27 + "[1m";
    public static java.lang.String noir = (char)27 + "[30m";

    public static void main(String[] args) {
        getData();

        listeDePlanete.add(new Alpha());
        listeDePlanete.add(new Quebec());
        listeDePlanete.add(new Delta());
        listeDePlanete.add(new Charlie());
        listeDePlanete.add(new Bravo());
        for (int i =0; i<nbLeger;i++){
            listeVaisseau.add(new VaisseauLeger());
        }
        for (int i =0; i<nbNormal;i++){
            listeVaisseau.add(new VaisseauNormal());
        }
        for (int i =0; i<nbLourd;i++){
            listeVaisseau.add(new VaisseauLourd());
        }
        int nbVaisseau=nbLeger+nbNormal+nbLourd;
        for(int i=0;i<nbCentre;i++) {
            listeCentre.add(new Centre());
        }
        for(int i=0;i<nbVaisseau;i++){
            envoieVaisseau(listeVaisseau.get(i));
        }
        System.out.println("Simulation terminé: Tous les vaisseaux on été envoyés");
        sendData();
        affichageFin();
    }
    public static void envoieVaisseau(Vaisseau vaisseau){
        int random=(int)(Math.random()*5);
        listeDePlanete.get(random).charge(vaisseau);
        vaisseau.setCentreActuel(vaisseau.getCentreActuel()+1);
        try {
            listeCentre.get(vaisseau.getCentreActuel()).decharger(vaisseau);
        } catch (Exception e){
            System.out.println("Simulation terminé: Toutes les files d'attentes sont pleines");
            affichageFin();
        }
    }
    public static void affichageFin(){
        for(int i=0;i<nbCentre;i++){
            System.out.println("Centre #"+(i+1));
            System.out.println("Nombre de vaisseaux en attente: "+listeCentre.get(i).getFileVaisseau().size());
            System.out.println("Pile de Gadolinium: "+listeCentre.get(i).getListePile().get("Gadolinium").getPile().size());
            System.out.println("Pile de Neptunium: "+listeCentre.get(i).getListePile().get("Neptunium").getPile().size());
            System.out.println("Pile de Plutonium: "+listeCentre.get(i).getListePile().get("Plutonium").getPile().size());
            System.out.println("Pile de Terbium: "+listeCentre.get(i).getListePile().get("Terbium").getPile().size());
            System.out.println("Pile de Thulium: "+listeCentre.get(i).getListePile().get("Thulium").getPile().size());
        }
        System.exit(0);
    }
    public static void getData(){
            try {
                File fXmlFile = new File("Data.xml");
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(fXmlFile);
                doc.getDocumentElement().normalize();
                System.out.println("" + doc.getDocumentElement().getNodeName());

                NodeList nList = doc.getElementsByTagName("Centre");
                Node entree = nList.item(0);
                Element premier = (Element) entree;

                nbCentre = Integer.parseInt(premier.getElementsByTagName("nbCentre").item(0).getTextContent());

                nList = doc.getElementsByTagName("Dechet");
                System.out.println("----------------------------");
                for (int temp = 0; temp < nList.getLength(); temp++) {
                    Node nNode = nList.item(temp);
                    System.out.println(brillant+"\nElement actuel :" + nNode.getNodeName()+noir);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;
                        System.out.println(bleu+"First Name : " + eElement.getElementsByTagName("type").item(0).getTextContent());
                        System.out.println("Masse volumique : " + eElement.getElementsByTagName("masseVolumique").item(0).getTextContent());
                        System.out.println("Pourcentage : " + eElement.getElementsByTagName("pourcentage").item(0).getTextContent()+noir);
                        System.out.println();
                    }
                }
                nList = doc.getElementsByTagName("Planete");
                for (int temp = 0; temp < nList.getLength(); temp++) {
                    Node nNode = nList.item(temp);
                    System.out.println(brillant+"\nElement actuel : " + nNode.getNodeName());
                    System.out.println();
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;
                        System.out.println(vert+"Planete: " + eElement.getElementsByTagName("Nom").item(0).getTextContent());
                        System.out.println("Gadolinium en %: " + eElement.getElementsByTagName("nbGadolinium").item(0).getTextContent());
                        System.out.println("Neptunium en %: " + eElement.getElementsByTagName("nbNeptunium").item(0).getTextContent());
                        System.out.println("Plutonium en %: " + eElement.getElementsByTagName("nbPlutonium").item(0).getTextContent());
                        System.out.println("Terbium en %: " + eElement.getElementsByTagName("nbTerbium").item(0).getTextContent());
                        System.out.println("Thulium en %: " + eElement.getElementsByTagName("nbThulium").item(0).getTextContent()+noir);
                        System.out.println();
                    }
                }
                nList = doc.getElementsByTagName("Vaisseaux");
                for (int temp = 0; temp < nList.getLength(); temp++) {
                    Node nNode = nList.item(temp);
                    System.out.println(brillant+"\nElement actuel : " + nNode.getNodeName());
                    System.out.println();
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;
                        nbLeger=Integer.parseInt(eElement.getElementsByTagName("nbLeger").item(0).getTextContent());
                        System.out.println(teal+"Vaisseau léger: " + nbLeger);
                        nbNormal=Integer.parseInt(eElement.getElementsByTagName("nbNormal").item(0).getTextContent());
                        System.out.println("Vaisseau normal: " +nbNormal);
                        nbLourd=Integer.parseInt(eElement.getElementsByTagName("nbLourd").item(0).getTextContent());
                        System.out.println("Vaisseau lourd: " +nbLourd +noir);
                        System.out.println();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    public static void sendData(){
        try {
            Socket socket = new Socket("127.0.0.1", 8080);

            OutputStream fluxSortant = socket.getOutputStream();
            OutputStreamWriter sortie = new OutputStreamWriter(fluxSortant);
            sortie.write("sendData\n");
            sortie.write(listeCentre.size()+"\n");
            for(int i=0;i<listeCentre.size();i++) {
                sortie.write(listeCentre.get(i).getFileVaisseau().size()+"\n");
                sortie.write(listeCentre.get(i).getListePile().get("Gadolinium").getPile().size()+"\n");
                sortie.write(listeCentre.get(i).getListePile().get("Neptunium").getPile().size()+"\n");
                sortie.write(listeCentre.get(i).getListePile().get("Plutonium").getPile().size()+"\n");
                sortie.write(listeCentre.get(i).getListePile().get("Terbium").getPile().size()+"\n");
                sortie.write(listeCentre.get(i).getListePile().get("Thulium").getPile().size()+"\n");
            }
            sortie.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
