package com.secquan.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.Document;

import com.secquan.util.HttpRequestUtil;
import com.secquan.util.ReadFromFile;

public class ForceShellPanel extends JPanel {
	private static JTextField urlPath;
	private static JTextField filePath;
	private JTextPane jtp;
	private String url;
	private String type;
	private JTextArea reSource;
	private JTextPane console;
	private JScrollPane console_scroll;
	private Document shell_doc;//
	private static JTextArea textArea;
	private JTextField countThread;
	private static List list = new ArrayList();
	private static JLabel statusLable;

	public ForceShellPanel() {

	}

	/**
	 * Create the panel.
	 */
	public ForceShellPanel(String str) {
		this.setSize(900, 480);
		setLayout(null);
		String[] strs = str.split("\t");
		for (int i = 0; i < strs.length; i++) {
			System.out.println(this.getClass().getName() + "30 " + strs[i]);

		}
		JLabel lblNewLabel = new JLabel("URL");
		lblNewLabel.setBounds(17, 21, 54, 15);
		add(lblNewLabel);

		statusLable = new JLabel("完成");
		statusLable.setBounds(9, 395, 500, 20);
		add(statusLable);

		urlPath = new JTextField();
		urlPath.setBounds(81, 18, 522, 21);
		urlPath.setText(strs[1]);
		add(urlPath);
		urlPath.setColumns(10);
		String labels[] = {  "PHP" };

		JComboBox comboBox = new JComboBox(labels);
		// 根据连接类型判断类型 后面要加上asp的破解
//		if (strs[4].indexOf("ASP(Eval)") > -1) {
//			comboBox.setSelectedIndex(0);
//		} else if (strs[4].indexOf("PHP(Eval)") > -1) {
//			comboBox.setSelectedIndex(1);
//		}
		/**
		 * 目前仅支持PHP 和asp 因为jsp只识别一个参数
		 */

		// comboBox.setModel(new DefaultComboBoxModel(new String[] {"PHP",
		// "ASP"}));
		comboBox.setToolTipText("");
		comboBox.setBounds(613, 18, 61, 21);
		add(comboBox);

		JLabel label = new JLabel("密码文件");
		label.setBounds(17, 49, 54, 15);
		add(label);

		filePath = new MyTextField();
		filePath.setColumns(10);
		filePath.setBounds(81, 46, 522, 21);
		add(filePath);

		JButton button = new JButton("浏览");
		button.addActionListener(new ActionListener() {
			//
			public void actionPerformed(ActionEvent e) {
				jButton3ActionPerformed(e);
			}
		});
		button.setBounds(684, 49, 93, 23);
		add(button);

		JButton clearButton = new JButton("清空");
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearMsg(e);
			}
		});

		clearButton.setBounds(787, 17, 93, 23);
		add(clearButton);
		JButton startButton = new JButton("开始");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startButtonAction(e);
			}
		});
		startButton.setBounds(684, 17, 93, 23);
		add(startButton);

		textArea = new JTextArea();
		textArea.setBounds(5, 77, 880, 317);
		add(textArea);
		// 绑定滚动条
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setBounds(5, 77, 880, 317);
		// 设定滚动条显示时机
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		add(scrollPane);
		// countThread 一次要提交的参数个数，但是如果是密码文件数量太少，分组会有问题，这里也要适当改小
		// 最好不好超过1000个 当然可以根据服务器特性来设定个数
		countThread = new JTextField();
		countThread.setText("1000");
		countThread.setBounds(613, 49, 61, 21);
		add(countThread);
		countThread.setColumns(10);
		//

	}

	// 清空按钮事件
	public void clearMsg(java.awt.event.ActionEvent evt) {
		// 清空所有消息
		statusLable.setText("清理密码信息...");
		list.clear();
		textArea.setText("");
		statusLable.setText("清理密码信息完成");

	}

	// 开始按钮点击事件
	private void startButtonAction(java.awt.event.ActionEvent evt) {
		forcePassword();
	}

	public static void readFile() {

		// 获取路径
		String filepath = filePath.getText();

		filepath = filepath.trim();
		// 如果长度为0 那么表示没有字典文件 弹出警告
		if (filepath.length() == 0) {

		}
		list = ReadFromFile.readFileByLines(filepath);
		for (int x = 0; x < list.size(); x++) {
			// 获取list列表中所有的密码
			// System.out.println(list.get(x));
			textArea.append((String) list.get(x));
			textArea.append("\n");
			// 这里是为了及时刷新textArea
			System.out.println(x);
			if (x != 0 && x % 1000 == 0) {
				textArea.paintImmediately(textArea.getBounds());
			}

		}
		System.out.println("文件加载完毕");
		String listsize = String.valueOf(list.size());
		textArea.append("密码共 " + listsize + " 个");

	}

	// 准备回调加载密码文件
	// 如果把文件拖动到地址框里面去 那么直接刷新内容框
	public static synchronized void callBack() {
		statusLable.setText("正在加载密码文件...");

		readFile();
		statusLable.setText("密码文件加载完成");
	}

	// 浏览按钮点击事件
	private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
		this.filePath.setText(reFileAbso());
		statusLable.setText("正在加载密码文件...");
		readFile();
		statusLable.setText("密码文件加载完成");
	}

	// 点击浏览按钮逻辑处理事件
	private String reFileAbso() {
		JFileChooser jfc = new JFileChooser();
		jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		jfc.showDialog(new JLabel(), "选择");
		File file = jfc.getSelectedFile();
		if (file.isDirectory()) {
			System.out.println("文件夹:" + file.getAbsolutePath());
			return "二货,不要选文件夹 要选文件";
			// return file.getAbsolutePath();
			// this.jTextField1.setText(file.getAbsolutePath());
		} else if (file.isFile()) {
			return file.getAbsolutePath();
			// System.out.println("文件:"+file.getAbsolutePath());

		}
		return "";
	}

	// 准备破解密码
	public void forcePassword() {
		long startTime = System.currentTimeMillis();
		statusLable.setText("正在进行破解...");
		// 密码文件大小
		int listsize = list.size();
		// 每1000个分一组
		int quantity = Integer.valueOf(countThread.getText());

		List wrapList = new ArrayList();
		List passwordList;
		int count = 0; // 起步值
		String urls = urlPath.getText();
		// 将数组进行分组
		String msgs = "";
		while (count < list.size()) {
			// wrapList.add(new ArrayList(list.subList(count, (count + quantity)
			// > list.size() ? list.size() : count + quantity)));

			passwordList = new ArrayList(
					list.subList(count, (count + quantity) > list.size() ? list.size() : count + quantity));
			String msg = HttpRequestUtil.realyPost(urls, passwordList);
			if (msg.length() > 0) {
				msgs = msg;
				break;
			}
			count += quantity;
		}
		textArea.append("\n" + msgs);
		long endTime = System.currentTimeMillis();
		long Times = endTime - startTime;
		String strTimes = String.valueOf(Times);
		statusLable.setText("破解完成" + " 共耗时" + " " + strTimes + " 毫秒");

	}

}
