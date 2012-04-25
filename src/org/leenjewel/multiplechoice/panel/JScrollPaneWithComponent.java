package org.leenjewel.multiplechoice.panel;

import java.awt.Component;

import javax.swing.JScrollPane;

public class JScrollPaneWithComponent extends JScrollPane {

	private Component component;

	public JScrollPaneWithComponent(Component c){
		super(c, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.component = c;
		this.setPreferredSize(this.component.getPreferredSize());
                this.getVerticalScrollBar().setUnitIncrement(this.component.getPreferredSize().height/100);
	}

	public Component getComponent(){
		return component;
	}
}
