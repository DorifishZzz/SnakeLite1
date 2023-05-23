import acm.graphics.GLabel;

import java.awt.*;

public class Scoreboard extends GLabel {

    public Scoreboard(String message, double x, double y){

        super(message, x, y);

        setColor(Color.CYAN);
        setFont(new Font("SERIF", Font.BOLD, 15));
    }
}
