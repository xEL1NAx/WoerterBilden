import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Wortliste {
    private List<Wort> liste;
    private Wort dasWort;

    public Wortliste() {
        liste = new ArrayList<>();
    }

    public void ladeStandardWoerter(boolean usePuzzle) {
        liste.clear();
        String[] woerter = {
                "BUS", "JAVA", "CODE", "SPIEL", "PROGRAMM", "TASTATUR",
                "COMPUTER", "BILDUNG", "SCHULE", "PUZZLE", "ALGORITHMUS",
                "DATEN", "INFORMATIK", "SCHNELL", "LOGIK"
            };
        for (String wort : woerter) {
            liste.add(Wort.gibWort(wort, usePuzzle));
        }
        Collections.shuffle(liste);
    }

    // Wählt ein Wort, das noch nicht gespielt wurde (istRichtig == null)
    public Wort auswaehlenWort() {
        for (Wort w : liste) {
            if (w.gibStatus() == null) {
                return w;
            }
        }
        // Alle Wörter gespielt → Status zurücksetzen
        for (Wort w : liste) {
            w.setzeStatusAufNie();
        }
        return auswaehlenWort();
    }

    public String gibAufgabe() {
        dasWort = auswaehlenWort();
        return dasWort.gibBuchstaben();
    }

    public boolean testeLoesung(String antw) {
        if (dasWort == null) return false;

        boolean korrekt = dasWort.pruefeLoesung(antw);
        if (korrekt) {
            dasWort.markiereRichtig();
        } else {
            dasWort.markiereFalsch();
        }
        return korrekt;
    }

    public boolean alleWoerterVerwendet() {
        for (Wort w : liste) {
            if (w.gibStatus() == null) {
                return false;
            }
        }
        return true;
    }

    public void resetStatusAllerWoerter() {
        for (Wort w : liste) {
            w.setzeStatusAufNie();
        }
    }

    public Wort getAktuellesWort() {
        return dasWort;
    }
}
