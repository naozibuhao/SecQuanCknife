package com.secquan.ui;

import javax.swing.*;

import java.awt.*;

public class MainFrame {
	public static TabFrame tab;
	public static JFrame main;
	public MainFrame() {
		// TODO Auto-generated constructor stub
		Toolkit t = Toolkit.getDefaultToolkit();
		Dimension d = t.getScreenSize();
		main = new JFrame("圈子版--菜刀1.6");
		main.setIconImage(new ImageIcon(getClass().getResource("/com/secquan/images/logos.png")).getImage());
		main.setSize(900, 480);
		main.setLocation((d.width - main.getWidth()) / 2,
				(d.height - main.getHeight()) / 2);
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.getContentPane().setLayout(new BorderLayout(0, 0));
		tab = new TabFrame();
		tab.addPanel("list");
		main.add(tab);
		main.setVisible(true);
		
	}
}