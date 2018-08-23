package component;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.MaskFormatter;
import javax.swing.text.PlainDocument;

import main_form.picture;

import javax.swing.JFormattedTextField;
import javax.swing.ImageIcon;
import javax.swing.JTextField;

public class SizeDialog extends JDialog{
	private JTextField textField;
	private JTextField textField_1;
	
	class DigitFilter extends DocumentFilter {
	    private static final String DIGITS = "\\d+";

	    @Override
	    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {

	        if (string.matches(DIGITS)) {
	            super.insertString(fb, offset, string, attr);
	        }
	    }

	    @Override
	    public void replace(FilterBypass fb, int offset, int length, String string, AttributeSet attrs) throws BadLocationException {
	        if (string.matches(DIGITS)) {
	            super.replace(fb, offset, length, string, attrs);
	        }
	    }
	}
	
		public SizeDialog(JFrame owner, picture pi, JPanel pa) throws ParseException
		{
			super(owner, "Изменение размера", true);
			setLocationRelativeTo(null);
			// При активизации кнопки ОК диалогове окно закрывается.
	 
					JButton ok = new JButton("\u041E\u043A");
					
					JPanel panel = new JPanel();
					panel.add(ok);
					getContentPane().add(panel, BorderLayout.SOUTH);
					
					JButton end = new JButton("\u041E\u0442\u043C\u0435\u043D\u0430");
					panel.add(end);
					
					JPanel panel_1 = new JPanel();
					getContentPane().add(panel_1, BorderLayout.CENTER);
					panel_1.setLayout(null);
					
					JLabel label = new JLabel("\u041F\u043E \u0433\u043E\u0440\u0438\u0437\u043E\u043D\u0442\u0430\u043B\u0438:");
					label.setIcon(new ImageIcon(SizeDialog.class.getResource("/resource/height_.png")));
					label.setBounds(10, 32, 118, 14);
					panel_1.add(label);
					
					JLabel label_1 = new JLabel("\u041F\u043E \u0432\u0435\u0440\u0442\u0438\u043A\u0430\u043B\u0438:");
					label_1.setIcon(new ImageIcon(SizeDialog.class.getResource("/resource/width_.png")));
					label_1.setBounds(10, 57, 118, 14);
					panel_1.add(label_1);
					
					textField = new JTextField();
					PlainDocument doc = (PlainDocument) textField.getDocument();
					doc.setDocumentFilter(new DigitFilter());
					textField.setBounds(138, 29, 77, 20);
					textField.setText(Integer.toString(pi.isDimension().getX()));
					textField.setColumns(10);
					panel_1.add(textField);
					
					
					textField_1 = new JTextField();
					PlainDocument doc1 = (PlainDocument) textField_1.getDocument();
					doc1.setDocumentFilter(new DigitFilter());
					textField_1.setColumns(10);
					textField_1.setText(Integer.toString(pi.isDimension().getY()));
					textField_1.setBounds(138, 54, 77, 20);
					panel_1.add(textField_1);
					setSize(241, 174);
					
					
					//События
					ok.addActionListener(new ActionListener() {
	 
						public void actionPerformed(ActionEvent event) {
							pi.isResize(textField, textField_1, pa);
							setVisible(false);
						}
						
					});
					
					end.addActionListener(new ActionListener() {
						 
						public void actionPerformed(ActionEvent event) {
							setVisible(false);
						}
						
					});
		}
}
