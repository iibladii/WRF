package component;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextField;

import function.searchFileName;

public class CustomListener implements FocusListener {

	JFormattedTextField jftf;
	JFormattedTextField jftf1;
	JTextField jtf;
	JTextField jtf_;
	JLabel label1;
	CustomListener(JFormattedTextField jftf1, JLabel label1, JFormattedTextField jftf, JTextField jtf, JTextField jtf_){
		this.jftf=jftf;
		this.label1=label1;
		this.jftf1=jftf1;
		this.jtf=jtf;
		this.jtf_=jtf_;
	}
	
    public void focusGained(FocusEvent e) {
    	label1.setText("Введите широту и долготу искомого участка");
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
				label1.setText(searchFileName.fName_(rd, jtf.getText() + "\\" + ""));
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
    }
}