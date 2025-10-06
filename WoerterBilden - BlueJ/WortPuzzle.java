import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WortPuzzle extends Wort {

    public WortPuzzle(String loesung) {
        super(loesung);
    }

    @Override
    public String gibAufgabe() {
        List<String> buchstaben = new ArrayList<>();
        for (char c : loesung.toCharArray()) buchstaben.add(String.valueOf(c));
        Collections.shuffle(buchstaben);  // bei jeder Aufgabe neu mischen
        return String.join(" ", buchstaben);
    }

    @Override
    public boolean pruefe(String eingabe) {
        return loesung.equalsIgnoreCase(eingabe.trim());
    }
}
