package objects;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import pt.iscte.poo.gui.ImageGUI;

public class HighscoreManager {

    private static final String FILE = "highscores.txt";
    private List<ScoreEntry> scores;

    public HighscoreManager() throws FileNotFoundException {
        scores = new ArrayList<>();
        load();
    }

    public void addScore(ScoreEntry entry) throws FileNotFoundException {
        scores.add(entry);
        Collections.sort(scores);
        save();
    }

    public List<ScoreEntry> getTop10() {
        return scores.stream().limit(10).toList();
    }

    private void load() throws FileNotFoundException  {
        File f = new File(FILE);
        if (!f.exists()) return;

        try (Scanner sc = new Scanner(f)) {
            while (sc.hasNextLine()) {
                scores.add(ScoreEntry.fromString(sc.nextLine()));
            }
        } catch (Exception e) {
        	throw new FileNotFoundException();
        }

        Collections.sort(scores);
    }

    private void save() throws FileNotFoundException {
        try (PrintWriter pw = new PrintWriter(FILE)) {
            for (ScoreEntry s : scores) {
                pw.println(s);
            }
        } catch (Exception e) {
        	throw new FileNotFoundException();
        }
    }
    
    
    public void showHighscoreTable(List<ScoreEntry> top10) {
        StringBuilder sb = new StringBuilder();
        sb.append("===== HIGHSCORES =====\n\n");

        int pos = 1;
        for (ScoreEntry s : top10) {
            sb.append(String.format(
                "%d.    Tempo: %ds   Movimentos: %d\n",
                pos++, s.time, s.moves
            ));
        }

        ImageGUI.getInstance().showMessage("Highscores", sb.toString());
    }
}
