package dechet;

public abstract class Dechet implements Comparable<Dechet>{
    protected String type;
    protected int masseVolumique;
    protected int pourcentage;

    public int getMasseVolumique() {
        return masseVolumique;
    }

    public int compareTo(Dechet o) {
        return (masseVolumique-o.masseVolumique);
    }

    public String getType() {
        return type;
    }

    public int getPourcentage() {
        return pourcentage;
    }

}
