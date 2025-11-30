import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WortPuzzle extends Wort {
    public WortPuzzle(String loesung) {
        super(loesung);
    }

    @Override
    public String gibBuchstaben() {
        if (anzeigeBuchstaben == null || anzeigeBuchstaben.isEmpty()) {
            List<String> buchstaben = new ArrayList<>();
            for (char c : loesung.toCharArray()) {
                buchstaben.add(String.valueOf(c));
            }
            Collections.shuffle(buchstaben);
            anzeigeBuchstaben = String.join(" ", buchstaben);
        }
        return anzeigeBuchstaben;
    }

    @Override
    public boolean pruefeLoesung(String pWort) {
        return loesung.equalsIgnoreCase(pWort.trim());
    }
}
