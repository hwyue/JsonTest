package com.hwy;

import javax.swing.JTextArea;

public class AppendThread extends Thread {

	private JTextArea textArea;
	private String context;
	
	
	public AppendThread() {
	}
	
	public AppendThread(JTextArea textArea, String context){
		this.textArea = textArea;
		this.context = context;
	}
	@Override
	public void run() {
		if(textArea != null){
			int sL = context.length();
			int width = textArea.getWidth();
			int rp = sL/width;
			int per =sL%width; 
			try {
				textArea.setText("");
				for (int i = 0; i < rp; i++) {
					textArea.append(context.subSequence(i*width,(i+1)*width).toString());
					Thread.sleep(200);
				}
				if(per>0){
					textArea.append(context.subSequence(rp*width,sL).toString());
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	/*	String str="1234567890a";
		int sL = str.length();
		int width = 4;
		int rp = sL/width;
		int per =sL%width; 
		for (int i = 0; i < rp; i++) {
			System.out.println(str.subSequence(i*width,(i+1)*width).toString());
		}
		if(per>0){
			System.out.println(str.subSequence(rp*width,sL).toString());
		}*/
	}

}
