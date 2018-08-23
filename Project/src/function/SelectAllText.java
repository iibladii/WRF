package function;

import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.text.JTextComponent;
import javax.swing.text.TextAction;
/**
 * Выделение всего текста в JTextPane
 * @param JTextPane Компанент текст которого надо выделить
 * @version 1.0
 */
public class SelectAllText extends TextAction
   {
		JTextPane tp;
        public SelectAllText(JTextPane tp)
        {
            super("Select All");
            this.tp = tp;
            putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
        }

        public void actionPerformed(ActionEvent e)
        {
        	if(tp.getText().equals(""))
        		return;
            JTextComponent component = getFocusedComponent();
            component.selectAll();
            component.requestFocusInWindow();
        }
    }

