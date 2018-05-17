package centre;
import vaisseau.*;
import main.Main;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Centre {
    private HashMap<String, Pile> listePile;
    private Queue<Vaisseau> fileVaisseau;


    public Centre() {
        this.listePile = creerHashmap();
        this.fileVaisseau = new LinkedList<>();
    }

    public void recyclerPile(Vaisseau vaisseau, Pile pile, Centre autre){
        int nbToRecycle=((pile.getMax()*pile.getPile().peek().getPourcentage())/100);
        for (int i=0;i<nbToRecycle;i++){
            vaisseau.getListeMatiere().add(pile.getPile().pop());
            if (vaisseau.getListeMatiere().size() == vaisseau.getMatiereMax()){
                i=nbToRecycle;
            }
        }
        vaisseau.setCentreActuel(vaisseau.getCentreActuel()+1);
        autre.decharger(vaisseau);
    }

    public void decharger(Vaisseau vaisseau){
        fileVaisseau.add(vaisseau);
        vaisseau.triAvantLeDépot();
        for (int i=0; i<vaisseau.getMatiereMax(); i++){
            if (listePile.get(vaisseau.getListeMatiere().get(i).getType()).getPile().size() == listePile.get(vaisseau.getListeMatiere().get(i).getType()).getMax()){
                try{
                recyclerPile(fileVaisseau.poll(),listePile.get(vaisseau.getListeMatiere().get(i).getType()),Main.listeCentre.get(vaisseau.getCentreActuel()+1));
                } catch (Exception e){
                    System.out.println("Simulation terminé: La dernière pile de "+vaisseau.getListeMatiere().get(i).getType().toUpperCase()+" est pleine");
                    Main.affichageFin();
                }
            }
            listePile.get(vaisseau.getListeMatiere().get(i).getType()).getPile().push(vaisseau.getListeMatiere().get(i));
        }
        if (fileVaisseau.size()==10){
            Main.envoieVaisseau(fileVaisseau.peek());
            fileVaisseau.remove();
        }

    }

    public HashMap<String, Pile> creerHashmap(){
        HashMap<String, Pile> listePile = new HashMap<>();
        listePile.put("Plutonium", new Pile("Plutonium"));
        listePile.put("Neptunium", new Pile("Neptunium"));
        listePile.put("Gadolinium", new Pile("Gadolinium"));
        listePile.put("Terbium", new Pile("Terbium"));
        listePile.put("Thulium", new Pile("Thulium"));
        return listePile;
    }
    public HashMap<String, Pile> getListePile() {
        return listePile;
    }

    public Queue<Vaisseau> getFileVaisseau() {
        return fileVaisseau;
    }


}
