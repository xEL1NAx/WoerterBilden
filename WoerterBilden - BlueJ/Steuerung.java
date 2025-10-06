import javax.swing.UIManager;

public class Steuerung {
    private GUI dieGUI;
    private Wortliste dieWortListe;

    private Spieler spieler1;
    private Spieler spieler2;
    private Spieler aktuellerSpieler;
    private boolean zweiterVersuch = false;

    public Steuerung() {
        dieGUI = new GUI(this);
    }

    public void neuesSpiel(String name1, String name2, boolean usePuzzleType) {
        spieler1 = new Spieler((name1 == null || name1.isEmpty()) ? "Spieler 1" : name1);
        spieler2 = new Spieler((name2 == null || name2.isEmpty()) ? "Spieler 2" : name2);
        spieler1.resetPunkte();
        spieler2.resetPunkte();
        aktuellerSpieler = spieler1;
        zweiterVersuch = false;

        dieWortListe = new Wortliste();
        dieWortListe.ladeStandardWoerter(usePuzzleType);

        aktualisiereAnzeige();
        dieGUI.zeigeMeldung("Neues Spiel gestartet!");
        stelleAufgabe();
    }

    public void gedrueckt(String eingabe) {
        if (dieWortListe == null) {
            dieGUI.zeigeMeldung("Keine Wörter vorhanden.");
            return;
        }

        boolean korrekt = dieWortListe.pruefeLoesung(eingabe);

        if (korrekt) {
            if (zweiterVersuch) {
                aktuellerSpieler.addPunkte(1);
                dieGUI.zeigeMeldung("Richtig beim zweiten Versuch! +1 Punkt für " + aktuellerSpieler.getName());
            } else {
                aktuellerSpieler.addPunkte(5);
                dieGUI.zeigeMeldung("Richtig! +5 Punkte für " + aktuellerSpieler.getName());
            }
            zweiterVersuch = false;
            stelleAufgabe();
        } else {
            if (!zweiterVersuch) {
                zweiterVersuch = true;
                dieGUI.zeigeMeldung("Leider falsch! Du hast noch einen Versuch.");
                // Spieler bleibt am Zug, neue Aufgabe muss **nicht** gestellt werden.
            } else {
                zweiterVersuch = false;
                wechsleSpieler();
                dieGUI.zeigeMeldung("Zweiter Versuch auch falsch! Spielerwechsel.");
                stelleAufgabe();
            }
        }

        aktualisiereAnzeige();
        dieGUI.loescheLoesung();
    }

    private void wechsleSpieler() {
        aktuellerSpieler = (aktuellerSpieler == spieler1) ? spieler2 : spieler1;
    }

    private void stelleAufgabe() {
        String aufgabe = dieWortListe.stelleAufgabe();
        dieGUI.anzeigenAufgabe(aufgabe);
    }

    private void aktualisiereAnzeige() {
        dieGUI.zeigeSpieler(
            spieler1.getName(), spieler1.getPunkte(),
            spieler2.getName(), spieler2.getPunkte(),
            aktuellerSpieler.getName()
        );
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) {}
        new Steuerung();
    }
}
