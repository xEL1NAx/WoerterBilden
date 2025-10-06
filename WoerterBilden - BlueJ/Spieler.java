public class Spieler {
    private String name;
    private int punkte;

    public Spieler(String name) {
        this.name = name;
        this.punkte = 0;
    }

    public String getName() {
        return name;
    }

    public int getPunkte() {
        return punkte;
    }

    public void addPunkte(int p) {
        punkte += p;
    }

    public void resetPunkte() {
        punkte = 0;
    }
}
