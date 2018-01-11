package com.secquan.ui;




import javax.swing.*;

import com.secquan.ui.panel.DatabasePanel;
import com.secquan.ui.panel.FileManagerPanel;
import com.secquan.ui.panel.HeadPanel;
import com.secquan.ui.panel.ListPanel;
import com.secquan.ui.panel.ShellPanel;
import com.secquan.ui.panel.TextPanel;

public class TabFrame extends JTabbedPane {
	private ListPanel list;
	private String url;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public JPanel addPanel(String type) {
		switch (type) {
		case "list":
			if(list==null)
			{
				list = new ListPanel();
				this.addTab("列表", list);
				this.setSelectedIndex(this.indexOfComponent(list));
			}
			return list;
		case "database":
			DatabasePanel database = new DatabasePanel();
			this.addTab("数据库管理", database);
			this.setSelectedIndex(this.indexOfComponent(database));
	        this.setTabComponentAt(this.getTabCount()-1, new HeadPanel(database));
	        return database;
		case "filemanager":
			FileManagerPanel filemanager = new FileManagerPanel();
			this.addTab("文件管理", filemanager);
			this.setSelectedIndex(this.indexOfComponent(filemanager));
	        this.setTabComponentAt(this.getTabCount()-1, new HeadPanel(filemanager));
	        return filemanager;
		case "shell":
			ShellPanel shell = new ShellPanel();
			this.addTab("模拟终端", shell);
			this.setSelectedIndex(this.indexOfComponent(shell));
		    this.setTabComponentAt(this.getTabCount()-1, new HeadPanel(shell));
			shell.setVisible(true);
			shell.requestFocus();
			return shell;
		case "text":
			TextPanel text = new TextPanel();
			this.addTab("文本文件", text);
			this.setSelectedIndex(this.indexOfComponent(text));
		    this.setTabComponentAt(this.getTabCount()-1, new HeadPanel(text));
			return text;
		case "forceshell": // 添加破解asp 或php shell密码
			System.out.println(this.getClass().getName()+" 61 "+ url);
			ForceShellPanel forceShellPanel = new ForceShellPanel(url);
			this.addTab("破解shell", forceShellPanel);
			this.setSelectedIndex(this.indexOfComponent(forceShellPanel));
		    this.setTabComponentAt(this.getTabCount()-1, new HeadPanel(forceShellPanel));
			return forceShellPanel;
		default:
			return null;
		}
	
	}

}
