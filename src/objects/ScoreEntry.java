package objects;

public class ScoreEntry implements Comparable<ScoreEntry> {
    public int moves;
    public int time;

    public ScoreEntry( int moves, int time) {
        
        this.moves = moves;
        this.time = time;
    }

    @Override
    public int compareTo(ScoreEntry other) {
        // Tempo tem maior prioridade
        if (this.time != other.time)
            return Integer.compare(this.time, other.time);
        // Se tempo for igual, desempata por movimentos
        return Integer.compare(this.moves, other.moves);
    }

    @Override
    public String toString() {
        return time + ";" + moves;
    }

    public static ScoreEntry fromString(String line) {
        String[] p = line.split(";");
        return new ScoreEntry(Integer.parseInt(p[0]), Integer.parseInt(p[1]));
    }
}

