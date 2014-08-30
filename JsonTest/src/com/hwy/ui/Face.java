package com.hwy.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import com.hwy.AppendThread;
import com.hwy.cache.CacheUtils;
import com.hwy.http.JsonPost;

public class Face extends FaceBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2201176087525876620L;
	
	/*========= button ============*/
	private final JButton pasteUrl_btn = new JButton(CacheUtils.getValueByKey("pasteUrl_btn"));
	private final JButton pasteParam_btn = new JButton(CacheUtils.getValueByKey("pasteParam_btn"));
	private final JButton post_btn = new JButton(CacheUtils.getValueByKey("post_btn"));
	private final JButton copyResult_btn = new JButton(CacheUtils.getValueByKey("copyResult_btn"));
	private final JButton cleanAll_btn = new JButton(CacheUtils.getValueByKey("cleanAll_btn"));

	/*========= label ============*/
	private final JLabel url_lb = new JLabel(CacheUtils.getValueByKey("url_lb")); 
	private final JLabel param_lb = new JLabel(CacheUtils.getValueByKey("param_lb")); 

	/*========= textarea ============*/
	private static final JTextArea url_area = new JTextArea(10,30);
	private static final JTextArea param_area = new JTextArea(10,30);
	public static final JTextArea result_area = new JTextArea(15,70);
	
	/*========= panel ============*/
	private static final JPanel post_jp = new JPanel();
	private static final JPanel result_jp = new JPanel();
	private static final JPanel postArea_jp = new JPanel();
	private static final JScrollPane url_jscrp = new JScrollPane(url_area);
	private static final JScrollPane param_jscrp = new JScrollPane(param_area);
	private static final JScrollPane rs_jscrp = new JScrollPane(result_area);
	
	public Face() {
		init();
	}
	private  void init(){
		post_btn.setBackground(new Color(255,200,0));
		cleanAll_btn.setBackground(new Color(255,0,0));
		
		post_btn.addActionListener(new post_btnAction());
		pasteUrl_btn.addActionListener(new pasteUrl_btnAction());
		pasteParam_btn.addActionListener(new pasteParam_btnAction());
		copyResult_btn.addActionListener(new copyResult_btnAction());
		cleanAll_btn.addActionListener(new cleanAll_btnAction());
		
		postArea_jp.setSize(800, 250);
		postArea_jp.add(url_lb);
		postArea_jp.add(url_jscrp);
		postArea_jp.add(param_lb);
		postArea_jp.add(param_jscrp);
//		url_area.setText("http://119.145.89.144:9080/ahk_mshop_macau/m_order!syncProducts.action");
		url_area.setText("http://119.145.89.144:9080/ahk_mshop_macau/m_dist!login.action");
		url_area.setLineWrap(true);
//		param_area.setText("{\"updateTime\":\"2014-06-15 00:00:00\",\"ada\":9300}");
		param_area.setText("{\"ada\":\"9300\",\"language\":\"en\"}");
		param_area.setLineWrap(true);
		//postArea_jp.add(post_btn);
		post_jp.add(postArea_jp);
		post_jp.setSize(800, 250);
		
		result_area.setEditable(false);
		result_area.setLineWrap(true);
		result_area.setBackground(new Color(180,200,100));
		rs_jscrp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		result_jp.add(rs_jscrp);
		
		result_jp.add(cleanAll_btn);
		result_jp.add(pasteUrl_btn);
		result_jp.add(pasteParam_btn);
		result_jp.add(copyResult_btn);
		result_jp.add(post_btn);
		
		this.setTitle(CacheUtils.getValueByKey("appTitle"));
		this.setSize(800, 550);
		this.add(post_jp, BorderLayout.NORTH);
		this.add(result_jp, BorderLayout.CENTER);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setLocationRelativeTo(null);//居中
		this.setResizable(false);
	}
	
	/*===============================btn_action start===================================================*/
	class post_btnAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			String url = url_area.getText();
			if(url != null && !"".equals(url)){
				String  str = JsonPost.post(url, param_area.getText());
				System.out.println(str);
				new AppendThread(result_area,str).start();
				//result_area.setText(str);
				// result_area.setText("");
				// JsonPost.postNotReturn(url, param_area.getText());
				//result_area.append(str);
			}else{
				showMessageDialog("错误","请求地址有误",0);
			}
		}
	}
	class copyResult_btnAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			String result = result_area.getText();
			if(result != null && !"".equals(result)){
			    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();//获取系统剪切板  
		        StringSelection selection = new StringSelection(result);//构建String数据类型  
		        clipboard.setContents(selection, selection);//添加文本到系统剪切板
			}
		}
	}
	
	class pasteUrl_btnAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();// 获取系统剪切板
			Transferable contents = clipboard.getContents(this);
			DataFlavor flavor = DataFlavor.stringFlavor;
			if (contents.isDataFlavorSupported(flavor)){
				try {
					String str;
					str = (String) contents.getTransferData(flavor);
					url_area.setText(str);
				} catch (Exception ee) {

				}
			}
		}
	}
	class pasteParam_btnAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();// 获取系统剪切板
			Transferable contents = clipboard.getContents(this);
			DataFlavor flavor = DataFlavor.stringFlavor;
			if (contents.isDataFlavorSupported(flavor)){
				try {
					String str;
					str = (String) contents.getTransferData(flavor);
					param_area.setText(str);
				} catch (Exception ee) {
					
				}
			}
		}
	}

	class cleanAll_btnAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			url_area.setText("");
			param_area.setText("");
			result_area.setText("");
		}
	}
	/*===============================btn_action end====================================================*/
/*	private static void initGlobalFontSetting(Font fnt) {
		FontUIResource fontRes = new FontUIResource(fnt);
		for (Enumeration<Object> keys = UIManager.getDefaults().keys(); keys.hasMoreElements();) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value instanceof FontUIResource) {
				UIManager.put(key, fontRes);
			}
		}
	}*/
	/**
	 * 提示信息
	 * @param title
	 * @param message
	 * @param i  :0错误，1信息，2警告，3疑问
	 */
	public static void showMessageDialog(String title,String message,int i){
		JOptionPane.showMessageDialog(null,message,title,i);
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Face();
	}

}
