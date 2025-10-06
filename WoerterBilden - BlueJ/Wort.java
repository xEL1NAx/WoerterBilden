public abstract class Wort {
    protected String loesung;
    protected String anzeigeBuchstaben; // aktuell sichtbarer Zustand
    private Boolean istRichtig; // null = noch nicht gespielt, true = richtig, false = falsch

    public Wort(String loesung) {
        this.loesung = loesung.toUpperCase();
        this.istRichtig = null;
        this.anzeigeBuchstaben = ""; // initial leer
    }

    public abstract String gibBuchstaben();

    public abstract boolean pruefeLoesung(String antw);

    public void setzeStatusAufNie() {
        istRichtig = null;
        loescheLoesung(); // auch Anzeige zurücksetzen
    }

    public Boolean gibStatus() {
        return istRichtig;
    }

    public void markiereRichtig() {
        istRichtig = true;
    }

    public void markiereFalsch() {
        istRichtig = false;
    }

    public static Wort gibWort(String text, boolean usePuzzle) {
        if (usePuzzle) {
            return new WortPuzzle(text);
        } else {
            return new WortMitLuecke(text);
        }
    }

    public void loescheLoesung() {
        anzeigeBuchstaben = "";
    }
}
