import javax.swing.*;
import java.awt.*;

public class Button extends JButton {

    public Button(String text, Icon icon, Event event) {
        super(text, icon);
        setVerticalTextPosition(SwingConstants.BOTTOM);
        setHorizontalTextPosition(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.TOP);
        setHorizontalAlignment(SwingConstants.CENTER);
        setMargin(new Insets(5, 5, 5, 5));
        addActionListener(e -> event.action());
    }
}
