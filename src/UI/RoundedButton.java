package UI;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class RoundedButton extends JButton {
    private int width;
    private int height;
    private Color backgroundColor;
    private Color textColor;
    private int fontSize;

    public RoundedButton(int fontSize, String text, int width, int height, Color textColor, int r, int g, int b) {
        super(text);
        this.width = width;
        this.height = height;
        this.backgroundColor = new Color(r, g, b);
        this.textColor = textColor;
        this.fontSize = fontSize;
        decorate();
    }

    protected void decorate() {
        setBorderPainted(false);
        setOpaque(false);
        setPreferredSize(new Dimension(width, height));
        setBackground(backgroundColor);
        setForeground(textColor);
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {}

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(backgroundColor.brighter());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(backgroundColor);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        int width = getWidth();
        int height = getHeight();
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setColor(getBackground());
        graphics.fillRoundRect(0, 0, width, height, 10, 10);
        
        graphics.setColor(getForeground());
        graphics.setFont(getFont().deriveFont((float) fontSize));
        drawString(graphics, this.getText(), width, height);
        
        graphics.dispose();
    }

    private void drawString(Graphics2D graphics, String text, int width, int height) {
        FontMetrics fontMetrics = graphics.getFontMetrics();
        int lineHeight = fontMetrics.getHeight();
        int y = (height - (lineHeight * getLineCount(text))) / 2 + fontMetrics.getAscent();
        
        for (String line : text.split("\n")) {
            Rectangle stringBounds = fontMetrics.getStringBounds(line, graphics).getBounds();
            int textX = (width - stringBounds.width) / 2;
            graphics.drawString(line, textX, y);
            y += lineHeight;
        }
    }

    private int getLineCount(String text) {
        return text.split("\n").length;
    }

    // 텍스트 크기 설정 메서드
    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
        repaint();
    }
}
