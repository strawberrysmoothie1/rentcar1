package UI;

import java.awt.Image;

import javax.swing.ImageIcon;

public class GetImage {

    private String imageName;
    private int width;
    private int height;

    public GetImage(String imageName, int width, int height) {
        this.imageName = imageName;
        this.width = width;
        this.height = height;
    }

    public ImageIcon getImage() {
        String imagePath = "/img/" + imageName;
        ImageIcon imageIcon = new ImageIcon(getClass().getResource(imagePath));
        Image image = imageIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }
}
