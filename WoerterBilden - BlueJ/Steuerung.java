import javax.swing.UIManager;

public class Steuerung {
    private GUI dieGUI;
    private Wortliste dieWortListe;
    private Spieler[] spieler;
    private int aAktiverSpieler;
    private int aZustand; // 0 = neues Wort, 1 = zweiter Versuch (GZ laut Klassendiagramm)
    // private boolean zweiterVersuch = false; // fällt weg (replaced mit a Zustand)

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

        aAktiverSpieler = 0;
        aZustand = 0; // neues Wort

        dieWortListe = new Wortliste();
        dieWortListe.ladeStandardWoerter(usePuzzleType);

        aktualisiereAnzeige();
        dieGUI.anzeigenMeldung("Neues Spiel gestartet!");
        stelleAufgabe();
    }

    public void gedruecktFertig() {
        if (dieWortListe == null) {
            dieGUI.anzeigenMeldung("Keine Wörter vorhanden.");
            return;
        }

        String pWort = dieGUI.tfSolution.getText().trim();
        boolean korrekt = dieWortListe.testeLoesung(pWort);

        if (korrekt) {
            if (aZustand == 1) { // zweiter Versuch
                spieler[aAktiverSpieler].addPunkte(1);
                dieGUI.anzeigenMeldung("Gut, das ist jetzt richtig! (1 Punkt)");
            } else {
                spieler[aAktiverSpieler].addPunkte(5);
                dieGUI.anzeigenMeldung("Prima, das ist richtig! (5 Punkte)");
            }
            aZustand = 0;
            stelleAufgabe();
        } else {
            if (aZustand == 0) { // erster Versuch
                aZustand = 1; // zweiter Versuch aktivieren
                dieGUI.anzeigenMeldung("Falsch, du hast aber noch eine Chance.");
            } else { // zweiter Versuch
                aZustand = 0;
                wechsleSpieler();
                dieGUI.anzeigenMeldung("Leider auch Falsch → Spielerwechsel.");
                stelleAufgabe();
            }
        }

        aktualisiereAnzeige();
        dieGUI.loescheLoesung();

        gedrueckt();
    }

    public void gedruecktOk() {
        dieGUI.messagePanel.setVisible(false);
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
        aAktiverSpieler = (aAktiverSpieler + 1) % spieler.length;
    }

    private void stelleAufgabe() {
        String dieBuchstaben = dieWortListe.gibAufgabe();
        dieGUI.anzeigenAufgabe(dieBuchstaben);
    }

    private void aktualisiereAnzeige() {
        dieGUI.anzeigenPunkte(
            spieler[0].gibName(), spieler[0].gibPunkte(),
            spieler[1].gibName(), spieler[1].gibPunkte(),
            spieler[aAktiverSpieler].gibName()
        );
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}
        new Steuerung();
    }
}
