public abstract class Wort {
    protected String loesung;

    public Wort(String loesung) {
        this.loesung = loesung.toUpperCase();
    }

    public abstract String gibAufgabe();
    public abstract boolean pruefe(String eingabe);

    // Statische Fabrikmethode zum Erstellen von Wörtern
    public static Wort erstelle(String text, boolean usePuzzle) {
        if (usePuzzle) {
            return new WortPuzzle(text);
        } else {
            return new WortMitLuecke(text);
        }
    }
}
