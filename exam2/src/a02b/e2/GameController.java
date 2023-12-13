package a02b.e2;

import java.util.HashMap;
import java.util.Map;

public class GameController implements Controller {

    private final int size;
    private Pair<Integer, Integer> direction;
    private Pair<Integer, Integer> currentPlayerPosition;
    private Map<Pair<Integer, Integer>, String> letterePositions;
    private boolean playerGotStuck;
    private final String goLeftSymbol;
    private final String goRightSymbol;

    public GameController(final int size, final String goRightSymbol, final String goLeftSymbol) {
        this.size = size;
        this.direction = new Pair<Integer, Integer>(-1, 0);
        letterePositions = new HashMap<>();
        playerGotStuck = false;
        this.goLeftSymbol = goLeftSymbol;
        this.goRightSymbol = goRightSymbol;
    }

    private boolean checkCoorValidity(Pair<Integer, Integer> coor) {
        return coor.getX() >= 0 && coor.getY() >= 0 && coor.getX() < size && coor.getY() < size;
    }

    private boolean changeDirectionIfTheresNeedTo(Pair<Integer, Integer> coor) {
        if (this.letterePositions.containsKey(coor)) {
            if (this.letterePositions.get(coor).equals(this.goRightSymbol)) {
                this.direction = new Pair<Integer, Integer>(0, 1);
            } else {
                this.direction = new Pair<Integer, Integer>(0, -1);
            }
            return true;
        }

        return false;
    }

    @Override
    public void movePlayer() {
        Pair<Integer, Integer> toCheck;
        int loopCounter = 0;

        do {
            toCheck = new Pair<Integer, Integer>(
                    this.direction.getX() + this.currentPlayerPosition.getX(),
                    this.direction.getY() + this.currentPlayerPosition.getY());
            loopCounter++;
        } while (changeDirectionIfTheresNeedTo(toCheck) || loopCounter >= 3);

        if (loopCounter >= 3) {
            playerGotStuck = true;
        }

        this.currentPlayerPosition = new Pair<Integer, Integer>(
                this.direction.getX() + this.currentPlayerPosition.getX(),
                this.direction.getY() + this.currentPlayerPosition.getY());

    }

    @Override
    public Pair<Integer, Integer> getPlayerPosition() {
        return this.currentPlayerPosition;
    }

    @Override
    public Map<Pair<Integer, Integer>, String> getLettersPosition() {
        return this.letterePositions;
    }

    @Override
    public void populateGamingField() {
        this.currentPlayerPosition = new Pair<Integer, Integer>((size - 1),
                (int) Math.floor(Math.random() * (size - 1)));
        for (int i = 0; i < 20; i++) {
            Pair<Integer, Integer> toCheck;

            do {
                toCheck = new Pair<Integer, Integer>((int) Math.floor(Math.random() * (size - 1)),
                        (int) Math.floor(Math.random() * (size - 1)));
            } while (this.letterePositions.containsKey(toCheck));

            this.letterePositions.put(toCheck, (i % 2 == 0) ? this.goRightSymbol : this.goLeftSymbol);
        }

    }

    @Override
    public Pair<Integer, Integer> getOldPlayerPosition() {
        return new Pair<Integer, Integer>(this.currentPlayerPosition.getX() - this.direction.getX(),
                this.currentPlayerPosition.getY() - this.direction.getY());
    }

    @Override
    public boolean checkGameEnd() {
        return !checkCoorValidity(this.currentPlayerPosition) || playerGotStuck;
    }

}
