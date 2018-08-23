package component;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextField;

import function.searchFile;
import function.searchFileName;
import type.LatLonDot;

public class CustomListener2 implements FocusListener {

	JFormattedTextField jftf;
	JFormattedTextField jftf1;
	JTextField jtf;
	JTextField jtf_;
	JLabel label1;
	JLabel label3;
	JLabel label4;
	JLabel label5;
	JButton ok;
	CustomListener2(JFormattedTextField jftf1, JLabel label1, JFormattedTextField jftf, JTextField jtf, JTextField jtf_, JLabel label3, JLabel label4, JLabel label5, JButton ok){
		this.jftf=jftf;
		this.label1=label1;
		this.label3=label3;
		this.label4=label4;
		this.label5=label5;
		this.ok=ok;
		this.jftf1=jftf1;
		this.jtf=jtf;
		this.jtf_=jtf_;
	}
	
    public void focusGained(FocusEvent e) {
    	label1.setText("");
    	label3.setText("");
    	label4.setText("");
    	label5.setText("");
    	label1.setOpaque(false);
		label3.setOpaque(false);
		label4.setOpaque(false);
		label5.setOpaque(false);
		label1.updateUI();
		label3.updateUI();
		label4.updateUI();
		label5.updateUI();
    }

    public void focusLost(FocusEvent e) {
    	//label1.setText("Button 2 — focusLost()");
    	String find = "";
		//Поиск имени искомого файла
		String rd[];
		rd = new String[5];
		rd[0]="--locate";
		rd[1]=jftf.getText();
		rd[2]=jftf1.getText();
		rd[3]="Output";
		rd[4]="PNG";
		if(!jftf.getText().equals("") && !jftf1.getText().equals("") && !jtf_.getText().equals("") && !jtf.getText().equals(""))
		{
			try {
				LatLonDot lld = new LatLonDot();
				lld.GetDot(rd, jtf.getText() + "\\" + "");
				label1.setText(searchFileName.fName_(rd, jtf.getText() + "\\" + ""));
				if(lld.getnx12()>0 && lld.getny12()>0)
				{
					rd[1]=Double.toString(lld.getnx12());
					rd[2]=Double.toString(lld.getny12());
					label3.setText(searchFileName.fName_(rd, jtf.getText() + "\\" + ""));
				}
				if(lld.getnx21()>0 && lld.getny21()>0)
				{
					rd[1]=Double.toString(lld.getnx21());
					rd[1]=Double.toString(lld.getny21());
					label4.setText(searchFileName.fName_(rd, jtf.getText() + "\\" + ""));
				}
				if(lld.getnx22()>0 && lld.getny22()>0)
				{
					rd[1]=Double.toString(lld.getnx22());
					rd[1]=Double.toString(lld.getny22());
					label5.setText(searchFileName.fName_(rd, jtf.getText() + "\\" + ""));
				}
				//ok.requestFocus();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			//Поиск файла
			searchFile.newPoisk(jtf_.getText(), label1.getText());
			String fileLoad = searchFile.getResult();
			if(fileLoad.equals(""))
			{
				label1.setBackground(Color.RED);
			}
			else//Загрузка файла если он найден
			{
				label1.setBackground(Color.GREEN);
			}
			
			
			searchFile.newPoisk(jtf_.getText(), label3.getText());
			fileLoad = searchFile.getResult();
			if(fileLoad.equals(""))
			{
				label3.setBackground(Color.RED);
			}
			else//Загрузка файла если он найден
			{
				label3.setBackground(Color.GREEN);
			}
			searchFile.newPoisk(jtf_.getText(), label4.getText());
			fileLoad = searchFile.getResult();
			if(fileLoad.equals(""))
			{
				label4.setBackground(Color.RED);
			}
			else//Загрузка файла если он найден
			{
				label4.setBackground(Color.GREEN);
			}
			searchFile.newPoisk(jtf_.getText(), label5.getText());
			fileLoad = searchFile.getResult();
			if(fileLoad.equals(""))
			{
				label5.setBackground(Color.RED);
			}
			else//Загрузка файла если он найден
			{
				label5.setBackground(Color.GREEN);
			}
			label1.setOpaque(true);
			label3.setOpaque(true);
			label4.setOpaque(true);
			label5.setOpaque(true);
			label1.updateUI();
			label3.updateUI();
			label4.updateUI();
			label5.updateUI();
		}
    }
}