import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Wortliste {
    private List<Wort> liste;
    private int index;
    private Wort aktuellesWort;

    public Wortliste() {
        liste = new ArrayList<>();
        index = 0;
    }

    public void ladeStandardWoerter(boolean usePuzzle) {
        liste.clear();
        String[] worte = {
            "BUS", "JAVA", "CODE", "SPIEL", "PROGRAMM", "TASTATUR",
            "COMPUTER", "BILDUNG", "SCHULE", "PUZZLE", "ALGORITHMUS",
            "DATEN", "INFORMATIK", "SCHNELL", "VERSCHLUESSELUNG", "LOGIK"
        };
        for (String wort : worte) {
            // Nur über die abstrakte Klasse Wort
            liste.add(Wort.erstelle(wort, usePuzzle));
        }
        Collections.shuffle(liste);
        index = 0;
    }

    public String stelleAufgabe() {
        if (liste.isEmpty()) return "";
        if (index >= liste.size()) index = 0;
        aktuellesWort = liste.get(index++);
        return aktuellesWort.gibAufgabe();
    }

    public boolean pruefeLoesung(String eingabe) {
        if (aktuellesWort == null) return false;
        return aktuellesWort.pruefe(eingabe);
    }
}
