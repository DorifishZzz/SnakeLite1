import acm.graphics.GLabel;

import java.awt.*;

public class Instructions extends GLabel {

    public Instructions(String message, double x, double y) {
        super(message, x, y);

        setColor(Color.YELLOW);
        setFont(new Font("SERIF", Font.BOLD, 30));

    }
}
