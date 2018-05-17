package main;

import centre.*;
import planete.*;
import vaisseau.*;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class Main {
    public static List<Planete> listeDePlanete=new ArrayList<>();
    public static List<Vaisseau> listeVaisseau=new ArrayList<>();
    public static List<Centre> listeCentre=new ArrayList<>();
    public static int nbCentre=3;
    public static float[][] dechets=new float[5][2];
    public static float[][] planetes = new float[5][5];
    public static int[] vaisseaux = new int[3];
    public static void main(String[] args) {
        getData();
        int nbVaisseau=30;
        listeDePlanete.add(new Alpha());
        listeDePlanete.add(new Quebec());
        listeDePlanete.add(new Delta());
        listeDePlanete.add(new Charlie());
        listeDePlanete.add(new Bravo());
        for (int i =0; i<10;i++){
            listeVaisseau.add(new VaisseauLeger());
            listeVaisseau.add(new VaisseauNormal());
            listeVaisseau.add(new VaisseauLourd());
        }

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
            Socket socket = new Socket("127.0.0.1", 8080);

            OutputStream fluxSortant = socket.getOutputStream();
            OutputStreamWriter sortie = new OutputStreamWriter(fluxSortant);
            sortie.write("getData\n");
            sortie.flush();

            InputStream fluxEntrant = socket.getInputStream();
            BufferedReader entree = new BufferedReader(new InputStreamReader(fluxEntrant));
            nbCentre = Integer.parseInt(entree.readLine());
            for (int i = 0; i < 5; i++)
                for (int j = 0; j < 2; j++)
                    dechets[i][j] = Float.parseFloat(entree.readLine());
            for (int i = 0; i < 5; i++)
                for (int j = 0; j < 5; j++)
                    planetes[i][j] = Float.parseFloat(entree.readLine());
            for (int i = 0; i < 3; i++)
                vaisseaux[i] = Integer.parseInt(entree.readLine());
        } catch(Exception e){
            e.printStackTrace();
            System.out.println(" ERREUR: Connexion impossible");
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
