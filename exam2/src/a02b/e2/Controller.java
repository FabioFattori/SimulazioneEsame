package a02b.e2;

import java.util.HashMap;

public interface Controller {
    public void togleBtn();

    public void populateGamingField();

    public Pair<Integer, Integer> getPlayerPosition();

    public Pair<Integer, Integer> getOldPlayerPosition();

    public HashMap<Pair<Integer, Integer>, String> getLettersPosition();

    public boolean checkGameEnd();
}
