package image;

import java.awt.Image;

import javax.swing.ImageIcon;

public class getImage {
	static String getImgname;
	static int width, length;
	public getImage(String getImgname, int width, int length) {
        this.getImgname = getImgname;
        this.width = width;
        this.length = length;
        getting();
    }
	
	
	private void getting() {
		String search = ("src/img/"+getImgname+".png");
	}
	
//	ImageIcon getImg = new ImageIcon(new ImageIcon(search)
//    		.getImage().getScaledInstance(width, length, Image.SCALE_SMOOTH));
	
	
}
