import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    JTextField tfPlayer1Name = new JTextField("", 10);
    JTextField tfPlayer2Name = new JTextField("", 10);
    JTextField tfPlayer1Points = new JTextField("0", 4);
    JTextField tfPlayer2Points = new JTextField("0", 4);

    JRadioButton rbType1 = new JRadioButton("1: Lücken füllen");
    JRadioButton rbType2 = new JRadioButton("2: Buchstabenpuzzle");
    JButton btnStartNew = new JButton("Starte neues Spiel");

    JPanel messagePanel;
    JLabel messageLabel;
    JButton btnMessageOk;

    JTextField tfActivePlayerName = new JTextField(12);
    JPanel puzzleLettersPanel;
    JTextField tfSolution = new JTextField(18);
    JButton btnFinish = new JButton("Fertig");

    Steuerung dieSteuerung;

    public GUI(Steuerung s) {
        super("Wortspiel");
        this.dieSteuerung = s;

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(920, 640);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(8, 8));

        JPanel topContainer = new JPanel(new BorderLayout(8, 8));
        topContainer.setBorder(new EmptyBorder(8, 8, 0, 8));
        topContainer.add(createPlayersPanel(), BorderLayout.NORTH);
        topContainer.add(gibSpielTyp(), BorderLayout.CENTER);
        messagePanel = clickOk();
        messagePanel.setVisible(false);
        topContainer.add(messagePanel, BorderLayout.SOUTH);
        add(topContainer, BorderLayout.NORTH);

        add(createGameArea(), BorderLayout.CENTER);
        setVisible(true);
        setGameAreaEnabled(false);
    }

    private JPanel createPlayersPanel() {
        JPanel wrap = new JPanel(new BorderLayout());
        wrap.setBorder(new CompoundBorder(new LineBorder(Color.LIGHT_GRAY, 3), new EmptyBorder(6, 6, 6, 6)));
        JPanel players = new JPanel(new GridLayout(1, 2, 12, 0));
        players.add(createSinglePlayerPanel("Spieler 1", tfPlayer1Name, tfPlayer1Points));
        players.add(createSinglePlayerPanel("Spieler 2", tfPlayer2Name, tfPlayer2Points));
        wrap.add(players, BorderLayout.CENTER);
        return wrap;
    }

    private JPanel createSinglePlayerPanel(String title, JTextField nameField, JTextField pointsField) {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBorder(new TitledBorder(title));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 6, 6, 6);
        c.anchor = GridBagConstraints.WEST;
        c.gridx = 0; c.gridy = 0;
        p.add(new JLabel("Name"), c);
        c.gridx = 1;
        p.add(nameField, c);
        c.gridx = 0; c.gridy = 1;
        p.add(new JLabel("Punkte"), c);
        c.gridx = 1;
        pointsField.setHorizontalAlignment(JTextField.CENTER);
        pointsField.setEditable(false);
        p.add(pointsField, c);
        return p;
    }

    private JPanel gibSpielTyp() {
        JPanel optionsWrap = new JPanel(new BorderLayout());
        JPanel options = new JPanel(new GridBagLayout());
        options.setBorder(new TitledBorder("Auswahl Typ"));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 6, 6, 6);
        c.anchor = GridBagConstraints.WEST;

        ButtonGroup bg = new ButtonGroup();
        bg.add(rbType1);
        bg.add(rbType2);
        rbType1.setSelected(true);

        c.gridx = 0; c.gridy = 0; options.add(rbType1, c);
        c.gridx = 1; options.add(rbType2, c);
        optionsWrap.add(options, BorderLayout.CENTER);

        JPanel startWrap = new JPanel(new FlowLayout(FlowLayout.CENTER));
        startWrap.add(btnStartNew);
        optionsWrap.add(startWrap, BorderLayout.SOUTH);

        clickStart();

        return optionsWrap;
    }

    private void clickStart() {
        btnStartNew.addActionListener(e -> {
            boolean usePuzzle = rbType2.isSelected();
            dieSteuerung.gedruecktStart(tfPlayer1Name.getText(), tfPlayer2Name.getText(), usePuzzle);
            setGameAreaEnabled(true);
        });
    }

    private JPanel clickOk() {
        JPanel mp = new JPanel(new BorderLayout(8, 8));
        mp.setBackground(new Color(200, 200, 200));
        mp.setBorder(new CompoundBorder(new LineBorder(Color.DARK_GRAY), new EmptyBorder(6, 6, 6, 6)));
        messageLabel = new JLabel("", SwingConstants.CENTER);
        mp.add(messageLabel, BorderLayout.CENTER);
        btnMessageOk = new JButton("Ok");
        btnMessageOk.addActionListener(e -> dieSteuerung.gedruecktOk()); // parameterlos
        mp.add(btnMessageOk, BorderLayout.EAST);
        return mp;
    }

    private JPanel createGameArea() {
        JPanel game = new JPanel();
        game.setLayout(new BoxLayout(game, BoxLayout.Y_AXIS));
        game.setBorder(new EmptyBorder(12, 12, 12, 12));

        game.add(anzeigenAmZug());

        JPanel aufgabeRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 6));
        aufgabeRow.add(new JLabel("Aufgabe:"));
        puzzleLettersPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        puzzleLettersPanel.setPreferredSize(new Dimension(800, 48));
        puzzleLettersPanel.setBorder(new LineBorder(Color.GRAY));
        aufgabeRow.add(puzzleLettersPanel);
        game.add(aufgabeRow);

        JPanel loesungRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 6));
        loesungRow.add(new JLabel("Deine Lösung:"));
        tfSolution.setColumns(14);
        loesungRow.add(tfSolution);
        loesungRow.add(btnFinish);

        clickFertig();

        tfSolution.addActionListener(e -> btnFinish.doClick());

        game.add(loesungRow);

        return game;
    }

    private void setFinishAction(ActionListener action) {
        for (ActionListener a : btnFinish.getActionListeners()) {
            btnFinish.removeActionListener(a);
        }
        btnFinish.addActionListener(action);
    }

    private void clickFertig() {
        setFinishAction(e -> dieSteuerung.gedruecktFertig()); // parameterlos
    }

    private JPanel anzeigenAmZug() {
        JPanel activePanel = new JPanel();
        activePanel.setLayout(new BoxLayout(activePanel, BoxLayout.Y_AXIS));
        activePanel.setBorder(new EmptyBorder(0, 0, 8, 0));

        JPanel activeRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 6));
        tfActivePlayerName.setColumns(10);
        tfActivePlayerName.setEditable(false);
        activeRow.add(tfActivePlayerName);
        activeRow.add(new JLabel("ist am Zug"));
        activePanel.add(activeRow);

        return activePanel;
    }

    public void anzeigenAufgabe(String dieBuchstaben) {
        puzzleLettersPanel.removeAll();
        if (dieBuchstaben != null && !dieBuchstaben.isEmpty()) {
            for (String s : dieBuchstaben.split("\\s+")) {
                JLabel box = new JLabel(s, SwingConstants.CENTER);
                box.setPreferredSize(new Dimension(48, 40));
                box.setBorder(new LineBorder(Color.DARK_GRAY));
                box.setFont(box.getFont().deriveFont(Font.BOLD, 18f));
                puzzleLettersPanel.add(box);
            }
        }
        puzzleLettersPanel.revalidate();
        puzzleLettersPanel.repaint();
    }

    public void loescheLoesung() {
        tfSolution.setText("");
    }

    public void anzeigenPunkte(String name1, int punkte1, String name2, int punkte2, String aktiv) {
        tfPlayer1Name.setText(name1);
        tfPlayer2Name.setText(name2);
        tfPlayer1Points.setText(String.valueOf(punkte1));
        tfPlayer2Points.setText(String.valueOf(punkte2));
        tfActivePlayerName.setText(aktiv);
    }

    public void anzeigenMeldung(String text) {
        messageLabel.setText(text);
        messagePanel.setVisible(true);
    }

    private void setGameAreaEnabled(boolean enabled) {
        tfSolution.setEnabled(enabled);
        btnFinish.setEnabled(enabled);
    }
}
