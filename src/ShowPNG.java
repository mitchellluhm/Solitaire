import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

@SuppressWarnings("serial")
public class ShowPNG extends JFrame
{
    /*private ShowPNG(String arg){
        if (arg == null ) {
            arg = "/home/mitchell/IdeaProjects/Solitaire/src/png/2_of_clubs.png";
        }
        JPanel panel = new JPanel();
        panel.setSize(500,640);
        panel.setBackground(Color.WHITE);
        ImageIcon icon = new ImageIcon(arg);
        JLabel label = new JLabel();
        label.setIcon(icon);
        panel.add(label);
        this.getContentPane().add(panel);
    }
    public static void main(String[] args) {
        //new ShowPNG(args.length == 0 ? null : args[0]).setVisible(true);

    }*/

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
