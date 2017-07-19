import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

@SuppressWarnings("serial")
public class ShowPNG extends JFrame
{

    // cards are width: 222px height: 323px
    public JPanel getCard(String cardFile) {
        String path = "/home/mitchell/IdeaProjects/Solitaire/src/png/";
        cardFile = path + cardFile;
        JPanel cardPanel = new JPanel();
        //cardPanel.setSize(11, 61);
        ImageIcon icon = new ImageIcon(cardFile);
        // me
        Image scaledImg = icon.getImage().getScaledInstance(111, 161, Image.SCALE_DEFAULT);
        icon = new ImageIcon(scaledImg);
        JLabel label = new JLabel();
        label.setIcon(icon);
        cardPanel.add(label);
        //cardPanel.setSize(11, 61);
        Border boxBorder = BorderFactory.createTitledBorder("");
        cardPanel.setBorder(boxBorder);
        return cardPanel;
    }
}
