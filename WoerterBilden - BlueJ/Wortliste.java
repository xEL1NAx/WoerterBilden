import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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

        List<String> woerterAusDatei = ladeWoerterAusJson("Woerter.json");

        for (String wort : woerterAusDatei) {
            liste.add(Wort.gibWort(wort, usePuzzle));
        }

        Collections.shuffle(liste);
    }

    // ----------------------------------------------------------
    //  File-Reading: Wörter aus Woerter.json laden
    // ----------------------------------------------------------
    private List<String> ladeWoerterAusJson(String dateiname) {
        List<String> result = new ArrayList<>();
        try {
            String json = Files.readString(Paths.get(dateiname)).trim();

            // Sehr einfaches Parsing: 
            // sucht nach: "woerter": [ "ABC", "DEF", ... ]
            int start = json.indexOf("[");
            int end = json.indexOf("]");

            if (start == -1 || end == -1) return result;

            String arr = json.substring(start + 1, end);

            String[] teile = arr.split(",");

            for (String t : teile) {
                t = t.replace("\"", "").trim();
                if (!t.isEmpty()) result.add(t);
            }

        } catch (IOException e) {
            System.out.println("Konnte Datei nicht lesen: " + dateiname);
            e.printStackTrace();
        }

        return result;
    }

    // ----------------------------------------------------------
    // Methode ohne Rückgabewert
    // ----------------------------------------------------------
    public void auswaehlenWort() { // kein Rückgabewert mehr
        for (Wort w : liste) {
            if (w.gibStatus() == null) {
                dasWort = w; // direkt ins Feld speichern
                return;
            }
        }
        // Alle Wörter gespielt → Status zurücksetzen
        for (Wort w : liste) {
            w.setzeStatusAufNie();
        }
        auswaehlenWort(); // rekursiver Aufruf setzt auch dasWort
    }

    public String gibAufgabe() {
        auswaehlenWort(); // jetzt ohne Zuweisung
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
