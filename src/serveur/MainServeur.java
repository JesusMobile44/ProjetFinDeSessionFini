package serveur;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainServeur {
    public static String nbCentre;
    public static String[][] dechets = new String[5][2];
    public static String[][] planetes = new String[5][5];
    public static String[] vaisseaux = new String[3];
    public static void main(String[] args) {
        readData();
        while(true) {
            try {
                ServerSocket serveur = new ServerSocket(8080);

                Socket socket = serveur.accept();

                InputStream fluxEntrant = socket.getInputStream();
                BufferedReader entree = new BufferedReader(new InputStreamReader(fluxEntrant));
                String message = entree.readLine();
                System.out.println("Protocole " + message + " demandé");

                if (message.equals("getData")) {
                    OutputStream fluxSortant = socket.getOutputStream();
                    OutputStreamWriter sortie = new OutputStreamWriter(fluxSortant);
                    sortie.write(nbCentre + "\n");
                    for (int i = 0; i < 5; i++)
                        for (int j = 0; j < 2; j++)
                            sortie.write(dechets[i][j] + "\n");
                    for (int i = 0; i < 5; i++)
                        for (int j = 0; j < 5; j++)
                            sortie.write(planetes[i][j] + "\n");
                    for (int i = 0; i < 3; i++)
                        sortie.write(vaisseaux[i] + "\n");
                    sortie.close();
                    System.out.println("Load terminé");
                } else if (message.equals("sendData")) {
                    int nbCentre = Integer.parseInt(entree.readLine());
                    String parts[] = new String[nbCentre*6];
                    for(int i=0;i<parts.length;i++)
                        parts[i] = entree.readLine();
                    save(parts);
                    System.out.println("Save terminé");
                }
                entree.close();
                socket.close();
                serveur.close();
            } catch (Exception e) {
                System.out.println("ERREUR: Le client n'a pas pu se connecter");
            }
        }
    }
    public static void readData(){
        try {
            File file = new File("Data.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbf.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            Node root = doc.getDocumentElement();
            clean(root);
            NodeList data = root.getChildNodes();
            nbCentre = data.item(0).getTextContent();
            for(int i=0;i<5;i++)
                for(int j=0;j<2;j++)
                    dechets[i][j] = data.item(1).getChildNodes().item(i).getChildNodes().item(j).getTextContent();
            for(int i=0;i<5;i++)
                for(int j=0;j<5;j++)
                    planetes[i][j] = data.item(2).getChildNodes().item(i).getChildNodes().item(j).getTextContent();
            for(int i=0;i<3;i++)
                vaisseaux[i] = data.item(3).getChildNodes().item(i).getTextContent();
        } catch(Exception e) {
            System.out.println(e);
            System.out.println("ERREUR: Lecture du fichier Data.xml impossible");
        }
    }
    public static void clean(Node node) {
        NodeList childNodes = node.getChildNodes();
        for (int n = childNodes.getLength() - 1; n >= 0; n--)
        {
            Node child = childNodes.item(n);
            short nodeType = child.getNodeType();

            if (nodeType == Node.ELEMENT_NODE)
                clean(child);
            else if (nodeType == Node.TEXT_NODE)
            {
                String trimmedNodeVal = child.getNodeValue().trim();
                if (trimmedNodeVal.length() == 0)
                    node.removeChild(child);
                else
                    child.setNodeValue(trimmedNodeVal);
            }
            else if (nodeType == Node.COMMENT_NODE)
                node.removeChild(child);
        }
    }
    static void save(String[] parts){
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date=new Date();
        try {
            ObjectOutputStream sortie = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("Simulation_"+df.format(date)+".dat")));
            for(int i=0;i<parts.length;i++)
                sortie.writeObject(parts[i]);
            sortie.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
