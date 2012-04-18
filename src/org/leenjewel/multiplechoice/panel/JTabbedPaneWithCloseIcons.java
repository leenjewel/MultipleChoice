package org.leenjewel.multiplechoice.panel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Icon;
import javax.swing.JTabbedPane;

public class JTabbedPaneWithCloseIcons extends JTabbedPane  implements MouseListener {

	public JTabbedPaneWithCloseIcons() {
        super();
        init();
        setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    }

	public JTabbedPaneWithCloseIcons(int panexy) {
        super(panexy);
        init();
    }

	private void init(){
		addMouseListener(this);
	}

    @Override
    public void addTab(String title, Component component) {
        this.addTab(title, component, null);
    }

    public void addTab(String title, Component component, Icon extraIcon) {
    	JScrollPaneWithComponent jsPane = new JScrollPaneWithComponent(component);
    	jsPane.setViewportView(component);
        super.addTab(title, new CloseTabIcon(extraIcon), jsPane);
        super.setSelectedComponent(jsPane);
    }

    public Component getSelectedScrollPane() {
        return super.getSelectedComponent();
    }

    @Override
    public Component getSelectedComponent() {
    	JScrollPaneWithComponent jsPane = (JScrollPaneWithComponent) super.getSelectedComponent();
        if (jsPane == null) { return null; }
    	return jsPane.getComponent();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (isClickClose(e) == true) {
            //the tab is being closed
            this.removeTabAt(getSelectedIndex(e));
        }
    }

    public int getSelectedIndex(MouseEvent e) {
        return getUI().tabForCoordinate(this, e.getX(), e.getY());
    }

    public boolean isClickClose(MouseEvent e) {
        int tabNumber = getSelectedIndex(e);
        if (tabNumber < 0) return false;
        Rectangle rect=((CloseTabIcon)getIconAt(tabNumber)).getBounds();
        if (rect.contains(e.getX(), e.getY())) {
            return true;
        }
        return false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}

}

/**
 * The class which generates the 'X' icon for the tabs. The constructor
 * accepts an icon which is extra to the 'X' icon, so you can have tabs
 * like in JBuilder. This value is null if no extra icon is required.
 */
class CloseTabIcon implements Icon {
    private int x_pos;
    private int y_pos;
    private int width;
    private int height;
    private Icon fileIcon;

    public CloseTabIcon(Icon fileIcon) {
        this.fileIcon=fileIcon;
        width=16;
        height=16;
    }

    public void paintIcon(Component c, Graphics g, int x, int y) {
        this.x_pos=x;
        this.y_pos=y;

        Color col=g.getColor();

        g.setColor(Color.black);
        int y_p=y+2;
        g.drawLine(x+1, y_p, x+12, y_p);
        g.drawLine(x+1, y_p+13, x+12, y_p+13);
        g.drawLine(x, y_p+1, x, y_p+12);
        g.drawLine(x+13, y_p+1, x+13, y_p+12);
        g.drawLine(x+3, y_p+3, x+10, y_p+10);
        g.drawLine(x+3, y_p+4, x+9, y_p+10);
        g.drawLine(x+4, y_p+3, x+10, y_p+9);
        g.drawLine(x+10, y_p+3, x+3, y_p+10);
        g.drawLine(x+10, y_p+4, x+4, y_p+10);
        g.drawLine(x+9, y_p+3, x+3, y_p+9);
        g.setColor(col);
        if (fileIcon != null) {
            fileIcon.paintIcon(c, g, x+width, y_p);
        }
    }

    public int getIconWidth() {
        return width + (fileIcon != null? fileIcon.getIconWidth() : 0);
    }

    public int getIconHeight() {
        return height;
    }

    public Rectangle getBounds() {
        return new Rectangle(x_pos, y_pos, width, height);
    }

}
