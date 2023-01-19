import javafx.scene.control.Button;

public class GameButton extends Button {
    private int x, y;

    private int playerOccupies;

    public GameButton(int x, int y) {
        super();
        this.x = x;
        this.y = y;
        playerOccupies = 0;
    }

    public int getX() {return x;}
    public int getY() {return y;}

    public void setPlayerOccupies(int x) {
        playerOccupies = x;
    }

    public int getPlayerOccupies() {
        return playerOccupies;
    }

    public String getPosAsStr() {
        return ("(" + x + "," + y + ")");
    }
}
