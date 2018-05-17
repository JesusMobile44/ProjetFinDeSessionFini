package vaisseau;

import dechet.*;

import java.util.ArrayList;
import java.util.List;

public abstract class Vaisseau {
    protected List<Dechet> listeMatiere = new ArrayList<Dechet>();
    protected int matiereMax;
    protected int centreActuel =-1;

    public void triAvantLeDépot()
    {
        int elementNonTrié[] = new int[this.getMatiereMax()];
        for (int i=0; i <this.getMatiereMax();i++){
            if (this.getListeMatiere().get(i).getMasseVolumique() == new Gadolinium().getMasseVolumique()){
                elementNonTrié[i] = new Gadolinium().getMasseVolumique();
            }
            if (this.getListeMatiere().get(i).getMasseVolumique() == new Plutonium().getMasseVolumique()){
                elementNonTrié[i] = new Plutonium().getMasseVolumique();
            }
            if (this.getListeMatiere().get(i).getMasseVolumique() == new Neptunium().getMasseVolumique()){
                elementNonTrié[i] = new Neptunium().getMasseVolumique();
            }
            if (this.getListeMatiere().get(i).getMasseVolumique() == new Thulium().getMasseVolumique()){
                elementNonTrié[i] = new Thulium().getMasseVolumique();
            }
            if (this.getListeMatiere().get(i).getMasseVolumique() == new Terbium().getMasseVolumique()){
                elementNonTrié[i] = new Terbium().getMasseVolumique();
            }
        }
        int N = elementNonTrié.length;
        if (N == 0)
            return;
        int max = elementNonTrié[0], min = elementNonTrié[0];
        for (int i = 1; i < N; i++)
        {
            if (elementNonTrié[i] > max){
                max = elementNonTrié[i];
            }
            if (elementNonTrié[i] < min){
                min = elementNonTrié[i];
            }
        }
        int écart = max - min + 1;
        int[] count = new int[écart];
        for (int i = 0; i < N; i++){
            count[elementNonTrié[i] - min]++;
        }
        for (int i = 1; i < écart; i++){
            count[i] += count[i - 1];
        }
        int j = 0;
        for (int i = 0; i < écart; i++){
            while (j < count[i])
                elementNonTrié[j++] = i + min;
        }
        for (int i = 0; i <this.getMatiereMax();i++){
            if (elementNonTrié[i] == new Gadolinium().getMasseVolumique() )
                this.getListeMatiere().set(i,new Gadolinium());

            if (elementNonTrié[i] == new Plutonium().getMasseVolumique())
                this.getListeMatiere().set(i,new Plutonium());

            if (elementNonTrié[i] == new Neptunium().getMasseVolumique())
                this.getListeMatiere().set(i,new Neptunium());

            if (elementNonTrié[i] == new Terbium().getMasseVolumique())
                this.getListeMatiere().set(i,new Terbium());

            if (elementNonTrié[i] == new Thulium().getMasseVolumique())
                this.getListeMatiere().set(i, new Thulium());
        }
    }

    public int getMatiereMax() {
        return matiereMax;
    }

    public List<Dechet> getListeMatiere() {
        return listeMatiere;
    }

    public int getCentreActuel() {
        return centreActuel;
    }

    public void setCentreActuel(int centreActuel) {
        this.centreActuel = centreActuel;
    }
}
