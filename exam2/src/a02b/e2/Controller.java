package a02b.e2;

import java.util.Map;

public interface Controller {
    void movePlayer();

    void populateGamingField();

    Pair<Integer, Integer> getPlayerPosition();

    Pair<Integer, Integer> getOldPlayerPosition();

    Map<Pair<Integer, Integer>, String> getLettersPosition();

    boolean checkGameEnd();
}
