import javax.swing.*;

public class Window extends JFrame {



    public Window() {

        super("КликерБот");
        setSize(500,200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(new Actions());

        setVisible(true);

    }

    public static void main(String[] args) {

        Window mn = new Window();
    }
}
