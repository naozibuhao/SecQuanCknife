package com.secquan.ui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JTabbedPane;

import com.secquan.ui.config.panel.ProxyPanel;
import com.secquan.ui.config.panel.RequestPanel;

public class ConfigDialog extends JDialog{
	public static ConfigDialog cdialog;
	public ConfigDialog() {
		super(MainFrame.main,"圈子菜刀 设置",true);
		//this.setComponent();
		cdialog = this;
		this.setVisible(true);
	}
	/**
	 * 根据参数显示tab顺序
	 * @param flag 0  按照原来顺序显示 1 优先显示请求头配置
	 */
	public ConfigDialog(String flag) {
		super(MainFrame.main,"圈子菜刀 设置",true);
		this.setComponent(flag);
		cdialog = this;
		this.setVisible(true);
	}
	
	private void setComponent(String flag)
	{
		Toolkit t = Toolkit.getDefaultToolkit();
		Dimension d = t.getScreenSize();
		this.setResizable(false);
		this.setSize(450, 240);
		this.setLocation((d.width - this.getWidth()) / 2,
				(d.height - this.getHeight()) / 2);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		JTabbedPane pane = new JTabbedPane();
		pane.addTab("代理", new ProxyPanel());
		pane.addTab("请求头", new RequestPanel());
		
		// 如果是直接设置请求头 则默认显示设置请求头
		if(flag ==  "1"){
			pane.setSelectedIndex(1);
		}
		this.getContentPane().add(pane);
	}
}
