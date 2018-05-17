package planete;

import dechet.*;
import vaisseau.Vaisseau;

import java.util.Random;

public class Planete {
    protected int nbPlutonium;
    protected int nbNeptunium;
    protected int nbTerbium;
    protected int nbGadolinium;
    protected int nbThulium;

    public void charge(Vaisseau vaisseau){
        Random choix = new Random();
        int index;

        for (int i =0; i< vaisseau.getMatiereMax();i++){
            index = choix.nextInt(100);
            if (index < nbGadolinium){
                vaisseau.getListeMatiere().add(new Gadolinium());
            }
            else if (index >= nbGadolinium && index < (nbGadolinium+nbNeptunium)){
                vaisseau.getListeMatiere().add(new Neptunium());
            }
            else if (index >= (nbGadolinium+nbNeptunium) && index < (nbGadolinium+nbNeptunium+nbPlutonium)){
                vaisseau.getListeMatiere().add(new Plutonium());
            }
            else if (index >= (nbGadolinium+nbNeptunium+nbPlutonium) && index < (nbGadolinium+nbNeptunium+nbPlutonium+nbTerbium)){
                vaisseau.getListeMatiere().add(new Terbium());
            }
            else if (index >= (nbGadolinium+nbNeptunium+nbPlutonium+nbTerbium) && index <100){
                vaisseau.getListeMatiere().add(new Thulium());
            }
        }
    }
}


