import javax.swing.UIManager;

public class Steuerung {
    private GUI dieGUI;
    private Wortliste dieWortListe;
    private Spieler[] spieler;
    private int aktuellerSpielerIndex;
    private boolean zweiterVersuch = false;

    public Steuerung() {
        dieGUI = new GUI(this);
    }

    public void gedruecktStart(String name1, String name2, boolean usePuzzleType) {
        spieler = new Spieler[2];
        spieler[0] = new Spieler((name1 == null || name1.isEmpty()) ? "Spieler 1" : name1);
        spieler[1] = new Spieler((name2 == null || name2.isEmpty()) ? "Spieler 2" : name2);

        for (Spieler s : spieler) {
            s.resetPunkte();
        }

        aktuellerSpielerIndex = 0;
        zweiterVersuch = false;

        dieWortListe = new Wortliste();
        dieWortListe.ladeStandardWoerter(usePuzzleType);

        aktualisiereAnzeige();
        dieGUI.anzeigenMeldung("Neues Spiel gestartet!");
        stelleAufgabe();
    }

    public void gedruecktFertig(String antw) {
        if (dieWortListe == null) {
            dieGUI.anzeigenMeldung("Keine Wörter vorhanden.");
            return;
        }

        boolean korrekt = dieWortListe.testeLoesung(antw);

        if (korrekt) {
            if (zweiterVersuch) {
                spieler[aktuellerSpielerIndex].addPunkte(1);
                dieGUI.anzeigenMeldung("Gut, das ist jetzt richtig! (1 Punkt)");
            } else {
                spieler[aktuellerSpielerIndex].addPunkte(5);
                dieGUI.anzeigenMeldung("Prima, das ist richtig! (5 Punkte)");
            }
            zweiterVersuch = false;
            stelleAufgabe();
        } else {
            if (!zweiterVersuch) {
                zweiterVersuch = true;
                dieGUI.anzeigenMeldung("Falsch, du hast aber noch eine Chance.");
            } else {
                zweiterVersuch = false;
                wechsleSpieler();
                dieGUI.anzeigenMeldung("Leider auch Falsch → Spielerwechsel.");
                stelleAufgabe();
            }
        }

        aktualisiereAnzeige();
        dieGUI.loescheLoesung();

        gedrueckt();
    }

    public void gedrueckt() {
        if (dieWortListe == null) return;

        if (dieWortListe.alleWoerterVerwendet()) {
            dieWortListe.resetStatusAllerWoerter();
            dieGUI.anzeigenMeldung("Alle Wörter wurden gespielt! Neue Runde startet.");
            stelleAufgabe();
        }

        aktualisiereAnzeige();
    }

    private void wechsleSpieler() {
        aktuellerSpielerIndex = (aktuellerSpielerIndex + 1) % spieler.length;
    }

    private void stelleAufgabe() {
        String dieBuchstaben = dieWortListe.gibAufgabe();
        dieGUI.anzeigenAufgabe(dieBuchstaben);
    }

    private void aktualisiereAnzeige() {
        dieGUI.anzeigenPunkte(
            spieler[0].gibName(), spieler[0].gibPunkte(),
            spieler[1].gibName(), spieler[1].gibPunkte(),
            spieler[aktuellerSpielerIndex].gibName()
        );
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}
        new Steuerung();
    }
}
