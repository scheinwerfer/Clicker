import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Actions extends JPanel implements ActionListener {

    JLabel lbl1 = new JLabel("Имя и Фамилия");
    JLabel lbl2 = new JLabel("Текст");

    JTextArea txtArea1 = new JTextArea(1,15);
    JTextArea txtArea2 = new JTextArea(1,15);

    JButton btn = new JButton("Запуск");


    private boolean check1 = false;
    private boolean check2 = false;
    private final String WRITEAMES = "Написать сообщение";



    public Actions() {

        add(lbl1);
        add(txtArea1);
        btn.addActionListener(this);

        add(lbl2);
        add(txtArea2);

        add(btn);

        setVisible(true);

    }

    public void checkOnEmpty() {
        if (txtArea1.getText().isEmpty() || txtArea1.getText().equals("Имя введи, чепуха!")) {
            txtArea1.setText("Имя введи, чепуха!");
        } else {check1 = true;}
        if (txtArea2.getText().isEmpty() || txtArea2.getText().equals("Введите текст")) {
            txtArea2.setText("Введите текст");
        } else {check2 = true;}
    }

    public void start() throws IOException, InterruptedException, AWTException, URISyntaxException {
        URI url = new URI("https://vk.com/friends");
        Desktop.getDesktop().browse(url);
        Thread.sleep(10000);
        ctrlF();
        pasteName();
        Thread.sleep(500);
        screenShot();
        loop();

        Robot mm2 = new Robot();
        mm2.mouseMove(0,0);

        Thread.sleep(2000);
        ctrlF();
        Thread.sleep(500);

        StringSelection selectWRITEAMES = new StringSelection(WRITEAMES);
        Clipboard copyWRITEAMES = Toolkit.getDefaultToolkit().getSystemClipboard();
        copyWRITEAMES.setContents(selectWRITEAMES, null);
        ctrlV();

        Thread.sleep(500);
        screenShot();
        loop();
        Thread.sleep(500);
        pasteText();
        Thread.sleep(1000);
        ctrlEnter();

    }

    public void ctrlF() throws AWTException {
        Robot ctrlF = new Robot();
        ctrlF.keyPress(KeyEvent.VK_CONTROL);
        ctrlF.keyPress(KeyEvent.VK_F);
        ctrlF.keyRelease(KeyEvent.VK_F);
        ctrlF.keyRelease(KeyEvent.VK_CONTROL);
    }

    public void ctrlV() throws AWTException {
        Robot ctrlV = new Robot();
        ctrlV.keyPress(KeyEvent.VK_CONTROL);
        ctrlV.keyPress(KeyEvent.VK_V);
        ctrlV.keyRelease(KeyEvent.VK_V);
        ctrlV.keyRelease(KeyEvent.VK_CONTROL);
    }

    public void ctrlEnter() throws AWTException {
        Robot ctrlEnter = new Robot();
        ctrlEnter.keyPress(KeyEvent.VK_CONTROL);
        ctrlEnter.keyPress(KeyEvent.VK_ENTER);
        ctrlEnter.keyRelease(KeyEvent.VK_ENTER);
        ctrlEnter.keyRelease(KeyEvent.VK_CONTROL);
    }

    public void screenShot() throws AWTException, IOException {
        BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        ImageIO.write(image, "png", new File("D:\\", "ScreenShot.png"));
    }

    public void pasteName() throws AWTException {
        String name = txtArea1.getText();

        StringSelection selectName = new StringSelection(name);
        Clipboard copyName = Toolkit.getDefaultToolkit().getSystemClipboard();
        copyName.setContents(selectName, null);

        ctrlV();
    }

    public void pasteText() throws AWTException {
        String text = txtArea2.getText();

        StringSelection selectText = new StringSelection(text);
        Clipboard copyText = Toolkit.getDefaultToolkit().getSystemClipboard();
        copyText.setContents(selectText, null);

        ctrlV();
    }

    public void loop() throws IOException, AWTException {
        BufferedImage image = ImageIO.read(new File("D:\\", "ScreenShot.png"));

        int h = image.getHeight();
        int w = image.getWidth();

        outLoop:
        for (int i = 250; i < h; i++) {
            for (int j = 450; j < w; j++) {

                int pixel = image.getRGB(j,i);
                int red = (pixel & 0x00ff0000) >> 16;
                int green = (pixel & 0x0000ff00) >> 8;
                int blue = pixel & 0x000000ff;

                if (red == 255 && green == 150 && blue == 50) {
                    Robot mm1 = new Robot();
                    mm1.mouseMove(j,i);
                    mm1.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    mm1.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                    break outLoop;
                }

            }
        }        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btn) {
            checkOnEmpty();

            if (check1 && check2) {
                try {
                    start();
                } catch (IOException | InterruptedException | AWTException | URISyntaxException ioException) {
                    ioException.printStackTrace();
                }
            }
            check1 = false;
            check2 = false;
        }
    }
}
