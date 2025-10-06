public class WortMitLuecke extends Wort {
    public WortMitLuecke(String loesung) {
        super(loesung);
    }

    @Override
    public String gibAufgabe() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < loesung.length(); i++) {
            sb.append(i % 2 == 0 ? loesung.charAt(i) : "_");
            if (i < loesung.length() - 1) sb.append(" ");
        }
        return sb.toString();
    }

    @Override
    public boolean pruefe(String eingabe) {
        return loesung.equalsIgnoreCase(eingabe.trim());
    }
}
