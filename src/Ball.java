import acm.graphics.GOval;

import java.awt.*;

public class Ball extends GOval {


    public Ball(double x, double y, double width, double height)
    {
        super(x, y, width, height);
        setFillColor(Color.MAGENTA);
        setFilled(true);

    }
}
