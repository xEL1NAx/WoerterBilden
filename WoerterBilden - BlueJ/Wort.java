public abstract class Wort {
    protected String aWort;
    protected Boolean aStatus;
    protected String loesung;
    protected String anzeigeBuchstaben;

    public Wort(String loesung) {
        this.loesung = loesung.toUpperCase();

        this.aWort = loesung;
        this.aStatus = null;  // entspricht vorher: istRichtig = null

        this.anzeigeBuchstaben = ""; // initial leer
    }

    public abstract String gibBuchstaben();

    public abstract boolean pruefeLoesung(String pWort);

    public void setzeStatusAufNie() {
        aStatus = null;
        loescheLoesung(); // auch Anzeige zurücksetzen
    }

    public Boolean gibStatus() {
        return aStatus;
    }

    public void markiereRichtig() {
        aStatus = true;
    }

    public void markiereFalsch() {
        aStatus = false;
    }

    public static Wort gibWort(String pWort, boolean usePuzzle) {
        if (usePuzzle) {
            return new WortPuzzle(pWort);
        } else {
            return new WortMitLuecke(pWort);
        }
    }

    public void loescheLoesung() {
        anzeigeBuchstaben = "";
    }
}
