public class Spieler {
    private String aName;
    private int aPunkte;

    public Spieler(String aName) {
        this.aName = aName;
        this.aPunkte = 0;
    }

    public String gibName() {
        return aName;
    }

    public int gibPunkte() {
        return aPunkte;
    }

    public void addPunkte(int pPunkte) {
        aPunkte += pPunkte;
    }

    public void resetPunkte() {
        aPunkte = 0;
    }
}
