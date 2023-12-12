package a02b.e2;

import java.util.HashMap;

public class GameController implements Controller {

    private final int size;
    private Pair<Integer, Integer> direction;
    private Pair<Integer, Integer> currentPlayerPosition;
    private HashMap<Pair<Integer, Integer>, String> lettersPositions;
    private boolean playerGotStock;

    public GameController(final int size) {
        this.size = size;
        this.direction = new Pair<Integer, Integer>(-1, 0);
        lettersPositions = new HashMap<>();
        playerGotStock = false;
    }

    private boolean checkCoorValidity(Pair<Integer, Integer> coor) {
        return coor.getX() >= 0 && coor.getY() >= 0 && coor.getX() < size && coor.getY() < size;
    }

    private boolean changeDirectionIfTheresNeedTo(Pair<Integer, Integer> coor) {
        if (this.lettersPositions.containsKey(coor)) {
            if (this.lettersPositions.get(coor) == "R") {
                this.direction = new Pair<Integer, Integer>(0, 1);
            } else {
                this.direction = new Pair<Integer, Integer>(0, -1);
            }
            return true;
        }

        return false;
    }

    @Override
    public void togleBtn() {
        Pair<Integer, Integer> toCheck;
        int loopCounter = 0;
        
        do {
             toCheck= new Pair<Integer, Integer>(
                this.direction.getX() + this.currentPlayerPosition.getX(),
                this.direction.getY() + this.currentPlayerPosition.getY());
                loopCounter ++;
        } while (changeDirectionIfTheresNeedTo(toCheck) || loopCounter >=3);

        if(loopCounter >= 3){
            playerGotStock = true;
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
    public HashMap<Pair<Integer, Integer>, String> getLettersPosition() {
        return this.lettersPositions;
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
            } while (this.lettersPositions.containsKey(toCheck));

            this.lettersPositions.put(toCheck, (i % 2 == 0) ? "R" : "L");
        }

    }

    @Override
    public Pair<Integer, Integer> getOldPlayerPosition() {
        return new Pair<Integer, Integer>(this.currentPlayerPosition.getX() - this.direction.getX(),
                this.currentPlayerPosition.getY() - this.direction.getY());
    }

    @Override
    public boolean checkGameEnd() {
        return !checkCoorValidity(this.currentPlayerPosition)||playerGotStock;
    }

}
