public class WortMitLuecke extends Wort {
    public WortMitLuecke(String loesung) {
        super(loesung);
    }

    @Override
    public String gibBuchstaben() {
        // Falls noch keine Anzeige generiert wurde, neu aufbauen
        if (anzeigeBuchstaben == null || anzeigeBuchstaben.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < loesung.length(); i++) {
                sb.append(i % 2 == 0 ? loesung.charAt(i) : "_");
                if (i < loesung.length() - 1) sb.append(" ");
            }
            anzeigeBuchstaben = sb.toString();
        }
        return anzeigeBuchstaben;
    }

    @Override
    public boolean pruefeLoesung(String pWort) {
        return loesung.equalsIgnoreCase(pWort.trim());
    }
}
