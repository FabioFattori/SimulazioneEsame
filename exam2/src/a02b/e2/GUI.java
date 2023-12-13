package a02b.e2;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GUI extends JFrame {

    private Controller gameController;
    private final Map<Pair<Integer, Integer>, JButton> map;

    public GUI(int size,String goRightSymbol,String goLeftSymbol) {

        this.gameController = new GameController(size,goRightSymbol,goLeftSymbol);
        this.gameController.populateGamingField();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(50 * size, 50 * size);

        JPanel panel = new JPanel(new GridLayout(size, size));
        this.getContentPane().add(panel);

        ActionListener al = e -> {
            this.gameController.movePlayer();
            if (this.gameController.checkGameEnd()) {
                this.dispose();
            } else {
                repaintGui();
            }
        };

        var lettersPositions = this.gameController.getLettersPosition();
        var playerPos = this.gameController.getPlayerPosition();
        this.map = new HashMap<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                var pos = new Pair<>(i, j);
                final JButton jb = new JButton(" ");

                if (lettersPositions.containsKey(pos)) {
                    jb.setText(lettersPositions.get(pos));
                } else if (playerPos.getX() == pos.getX() && playerPos.getY() == pos.getY()) {
                    jb.setText("*");
                }
                this.map.put(pos, jb);

                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        this.setVisible(true);
    }

    private void repaintGui() {
        this.map.get(this.gameController.getPlayerPosition()).setText("*");
        this.map.get(this.gameController.getOldPlayerPosition()).setText("");
    }

}
