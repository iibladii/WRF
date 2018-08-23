package main_form;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.JMenu;
import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.text.ParseException;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import javax.swing.JTextArea;
import java.awt.Color;
import javax.swing.JTabbedPane;
import javax.swing.JLayeredPane;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;
import javax.swing.JSeparator;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.Action;
import javax.swing.Box;
import javax.swing.JSpinner;
import javax.swing.JLabel;
import java.awt.Canvas;
import java.awt.Scrollbar;
import java.awt.ScrollPane;
import java.awt.Panel;
import java.awt.Label;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import component.SizeDialog;
import component.dataBox;
import function.SelectAllText;
import function.genIndexFile;
import function.verifyLine;

import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.border.LineBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import java.awt.SystemColor;
import java.awt.CardLayout;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.JScrollBar;
import javax.swing.JPopupMenu;

public class work_form extends JFrame {
	public static work_form frame;
	private JPanel contentPane;
	private int fit_fl=0;//1-Кнопка нажата, 0-кнопка отпущена
	private int fit_x=0;
	private int fit_y=0;
	private JDialog dialog;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new work_form();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the frame.
	 */
	public work_form() {
		ScrollPane scrollPane = new ScrollPane();
		JPanel panel_58 = new JPanel();
		JPanel panel_50 = new JPanel();
		JPanel panel = new JPanel();
		panel_50.setBorder(new LineBorder(new Color(0, 0, 0)));
		FlowLayout flowLayout = (FlowLayout) panel_50.getLayout();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 845, 635);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setToolTipText("");
		setJMenuBar(menuBar);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.NORTH);

		JLayeredPane layeredPane = new JLayeredPane();
		tabbedPane.addTab("\u0413\u043B\u0430\u0432\u043D\u0430\u044F", null, layeredPane, null);
		layeredPane.setLayout(new MigLayout("", "[210px:n:210px,grow][-30.00][200px:n:200px,grow][][40px:n:40px,grow][][224px:n:224px,grow][]", "[60px:n:60px,grow][grow]"));
		
		JPanel panel_2 = new JPanel();
		layeredPane.add(panel_2, "cell 0 0,grow");
		panel_2.setLayout(new MigLayout("", "[40px:n:40px,grow][40px:n:40px,grow][115px:n:115px,grow]", "[50px:n:50px,grow]"));
		
		JPanel panel_10 = new JPanel();
		panel_2.add(panel_10, "cell 0 0,grow");
		panel_10.setLayout(null);
		
		JButton btnVidelenieButton = new JButton("");
		btnVidelenieButton.setIcon(new ImageIcon(work_form.class.getResource("/resource/tire_.png")));
		btnVidelenieButton.setToolTipText("\u0412\u044B\u0434\u0435\u043B\u0438\u0442\u044C");
		btnVidelenieButton.setBounds(0, 0, 40, 50);
		panel_10.add(btnVidelenieButton);
		
		JButton button_38 = new JButton("");
		button_38.setBounds(0, 49, 5, 1);
		panel_10.add(button_38);
		button_38.setIcon(new ImageIcon(work_form.class.getResource("/resource/tire_any.png")));
		button_38.setToolTipText("\u0412\u044B\u0434\u0435\u043B\u0435\u043D\u0438\u0435 \u043F\u0440\u043E\u0438\u0437\u0432\u043E\u043B\u044C\u043D\u043E\u0439 \u043E\u0431\u043B\u0430\u0441\u0442\u0438");
		button_38.enable(false);
		button_38.setVisible(false);
		
		JPanel panel_70 = new JPanel();
		panel_70.setLayout(null);
		panel_2.add(panel_70, "cell 1 0,grow");
		
		JButton button_39 = new JButton("");
		button_39.setBounds(0, 0, 40, 50);
		panel_70.add(button_39);
		button_39.setIcon(new ImageIcon(work_form.class.getResource("/resource/star_empty (1).png")));
		button_39.setToolTipText("\u0410\u0432\u0442\u043E\u0432\u044B\u0434\u0435\u043B\u0435\u043D\u0438\u0435 \u0432 \u043F\u0440\u0435\u0434\u0435\u043B\u0430\u0445 \u0446\u0432\u0435\u0442\u0430");
		
		JPanel panel_12 = new JPanel();
		panel_2.add(panel_12, "cell 2 0,grow");
		panel_12.setLayout(null);
		
		JButton btnNewButton_1 = new JButton("\u0420\u0430\u0437\u043C\u0435\u0440");
		btnNewButton_1.setToolTipText("\u0420\u0430\u0437\u043C\u0435\u0440");
		btnNewButton_1.setHorizontalAlignment(SwingConstants.LEFT);
		btnNewButton_1.setIcon(new ImageIcon(work_form.class.getResource("/resource/expand (1).png")));
		btnNewButton_1.setBounds(0, 0, 115, 23);
		panel_12.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("\u041F\u043E\u0432\u043E\u0440\u043E\u0442");
		btnNewButton_2.setToolTipText("\u041F\u043E\u0432\u043E\u0440\u043E\u0442");
		btnNewButton_2.setHorizontalAlignment(SwingConstants.LEFT);
		btnNewButton_2.setIcon(new ImageIcon(work_form.class.getResource("/resource/repeat (2).png")));
		btnNewButton_2.setBounds(0, 27, 115, 23);
		panel_12.add(btnNewButton_2);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(Color.BLACK);
		layeredPane.add(separator_1, "cell 1 0");
		
		JPanel panel_3 = new JPanel();
		layeredPane.add(panel_3, "cell 2 0 1 2,grow");
		panel_3.setLayout(new MigLayout("", "[23px:23px:23px][23px:23px:23px][23px:n:23px,grow][23px:n:23px,grow][23px:n:23px,grow][23px:n:23px,grow][23px:n:23px,grow]", "[23px,grow][23px,grow]"));
		
		JPanel panel_11 = new JPanel();
		panel_3.add(panel_11, "cell 0 0,grow");
		panel_11.setLayout(null);
		
		JButton btn111 = new JButton("");
		btn111.setIcon(new ImageIcon(work_form.class.getResource("/resource/pencil (2).png")));
		btn111.setToolTipText("\u041A\u0430\u0440\u0430\u043D\u0434\u0430\u0448");
		btn111.setBounds(0, 0, 23, 23);
		panel_11.add(btn111);
		
		JPanel panel_14 = new JPanel();
		panel_3.add(panel_14, "cell 1 0,grow");
		panel_14.setLayout(null);
		
		JButton btn112 = new JButton("");
		btn112.setIcon(new ImageIcon(work_form.class.getResource("/resource/eraser (2).png")));
		btn112.setToolTipText("\u041B\u0430\u0441\u0442\u0438\u043A");
		btn112.setBounds(0, 0, 23, 23);
		panel_14.add(btn112);
		
		JPanel panel_53 = new JPanel();
		panel_53.setLayout(null);
		panel_3.add(panel_53, "cell 2 0,grow");
		
		JButton btn113 = new JButton("");
		btn113.setIcon(new ImageIcon(work_form.class.getResource("/resource/pipette.png")));
		btn113.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btn113.setToolTipText("\u041F\u0438\u043F\u0435\u0442\u043A\u0430");
		btn113.setBounds(0, 0, 23, 23);
		panel_53.add(btn113);
		
		JPanel panel_55 = new JPanel();
		panel_55.setLayout(null);
		panel_3.add(panel_55, "cell 3 0,grow");
		
		JButton btn117 = new JButton("");
		btn117.setIcon(new ImageIcon(work_form.class.getResource("/resource/line.png")));
		btn117.setToolTipText("\u041B\u0438\u043D\u0438\u044F");
		btn117.setBounds(0, 0, 23, 23);
		panel_55.add(btn117);
		
		JPanel panel_56 = new JPanel();
		panel_56.setLayout(null);
		panel_3.add(panel_56, "cell 4 0,grow");
		
		JButton btn119 = new JButton("");
		btn119.setIcon(new ImageIcon(work_form.class.getResource("/resource/elepse.png")));
		btn119.setToolTipText("\u041E\u0432\u0430\u043B");
		btn119.setBounds(0, 0, 23, 23);
		panel_56.add(btn119);
		
		JPanel panel_61 = new JPanel();
		panel_61.setLayout(null);
		panel_3.add(panel_61, "cell 5 0,grow");
		
		JButton btn118 = new JButton("");
		btn118.setBounds(0, 0, 23, 23);
		panel_61.add(btn118);
		btn118.setIcon(new ImageIcon(work_form.class.getResource("/resource/kvadrat.png")));
		btn118.setToolTipText("\u041A\u0432\u0430\u0434\u0440\u0430\u0442");
		
		JPanel panel_64 = new JPanel();
		panel_64.setLayout(null);
		panel_3.add(panel_64, "cell 6 0,grow");
		
		JButton button_2 = new JButton("");
		button_2.setIcon(new ImageIcon(work_form.class.getResource("/resource/25x75.png")));
		button_2.setToolTipText("\u0420\u0430\u0437\u0431\u0430\u0432\u043B\u0435\u043D\u0438\u0435 25 x 75");
		button_2.setBounds(0, 0, 23, 23);
		panel_64.add(button_2);
		
		JPanel panel_13 = new JPanel();
		panel_3.add(panel_13, "cell 0 1,grow");
		panel_13.setLayout(null);
		
		JButton btn114 = new JButton("");
		btn114.setIcon(new ImageIcon(work_form.class.getResource("/resource/pen (1).png")));
		btn114.setToolTipText("\u041A\u0438\u0441\u0442\u044C");
		btn114.setBounds(0, 0, 23, 23);
		panel_13.add(btn114);
		
		JPanel panel_15 = new JPanel();
		panel_3.add(panel_15, "cell 1 1,grow");
		panel_15.setLayout(null);
		
		JButton btn115 = new JButton("");
		btn115.setIcon(new ImageIcon(work_form.class.getResource("/resource/paint (1).png")));
		btn115.setToolTipText("\u0417\u0430\u043B\u0438\u0432\u043A\u0430");
		btn115.setBounds(0, 0, 23, 23);
		panel_15.add(btn115);
		
		JPanel panel_52 = new JPanel();
		panel_52.setLayout(null);
		panel_3.add(panel_52, "cell 2 1,grow");
		
		JButton btn116 = new JButton("");
		btn116.setIcon(new ImageIcon(work_form.class.getResource("/resource/puring.png")));
		btn116.setToolTipText("\u0410\u044D\u0440\u043E\u0437\u043E\u043B\u044C");
		btn116.setBounds(0, 0, 23, 23);
		panel_52.add(btn116);
		
		JPanel panel_54 = new JPanel();
		panel_54.setLayout(null);
		panel_3.add(panel_54, "cell 3 1,grow");
		
		JButton btn120 = new JButton("");
		btn120.setIcon(new ImageIcon(work_form.class.getResource("/resource/cor.png")));
		btn120.setToolTipText("\u0417\u0430\u043C\u0435\u043D\u0430 \u0446\u0432\u0435\u0442\u0430");
		btn120.setBounds(0, 0, 23, 23);
		panel_54.add(btn120);
		
		JPanel panel_57 = new JPanel();
		panel_57.setLayout(null);
		panel_3.add(panel_57, "cell 4 1,grow");
		
		JButton btn121 = new JButton("");
		btn121.setIcon(new ImageIcon(work_form.class.getResource("/resource/elepse2 (2).png")));
		btn121.setToolTipText("\u0417\u0430\u043A\u0440\u0430\u0448\u0435\u043D\u043D\u044B\u0439 \u043E\u0432\u0430\u043B");
		btn121.setBounds(0, 0, 23, 23);
		panel_57.add(btn121);
		
		JPanel panel_63 = new JPanel();
		panel_63.setLayout(null);
		panel_3.add(panel_63, "cell 5 1,grow");
		
		JButton btn122 = new JButton("");
		btn122.setIcon(new ImageIcon(work_form.class.getResource("/resource/kvadrat2.png")));
		btn122.setToolTipText("\u0417\u0430\u043A\u0440\u0430\u0448\u0435\u043D\u043D\u044B\u0439 \u043A\u0432\u0430\u0434\u0440\u0430\u0442");
		btn122.setBounds(0, 0, 23, 23);
		panel_63.add(btn122);
		
		JPanel panel_65 = new JPanel();
		panel_65.setLayout(null);
		panel_3.add(panel_65, "cell 6 1,grow");
		
		JButton button_3 = new JButton("");
		button_3.setIcon(new ImageIcon(work_form.class.getResource("/resource/50x50.png")));
		button_3.setToolTipText("\u0420\u0430\u0437\u0431\u0430\u0432\u043B\u0435\u043D\u0438\u0435 50 x 50");
		button_3.setBounds(0, 0, 23, 23);
		panel_65.add(button_3);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setForeground(Color.BLACK);
		layeredPane.add(separator_2, "cell 3 0");
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setForeground(Color.BLACK);
		layeredPane.add(separator_3, "cell 5 0");
		
		JPanel panel_4 = new JPanel();
		layeredPane.add(panel_4, "cell 6 0,grow");
		panel_4.setLayout(new MigLayout("", "[14px:n:14px,grow][14px:n:14px,grow][14px:n:14px,grow][14px:n:14px,grow][14px:n:14px,grow][14px:n:14px,grow][14px:n:14px,grow][14px:n:14px,grow][14px:n:14px,grow][14px:n:14px,grow][14px:n:14px,grow][14px:n:14px,grow]", "[14px:n:14px,grow][14px:n:14px,grow][14px:n:14px,grow]"));
		
		JPanel panel_5 = new JPanel();
		panel_4.add(panel_5, "cell 0 0,grow");
		panel_5.setLayout(null);
		
		JButton btnNewButton_4 = new JButton("");
		btnNewButton_4.setToolTipText("0");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panel_50.setBackground(new java.awt.Color(210,210,210));
			}
		});
		btnNewButton_4.setBackground(new java.awt.Color(210,210,210));
		btnNewButton_4.setBounds(0, 0, 14, 14);
		panel_5.add(btnNewButton_4);
		
		JPanel panel_18 = new JPanel();
		panel_4.add(panel_18, "cell 1 0,grow");
		panel_18.setLayout(null);
		
		JButton button_5 = new JButton("");
		button_5.setToolTipText("1");
		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel_50.setBackground(new java.awt.Color(0,0,0));
			}
		});
		button_5.setBackground(new Color(0, 0, 0));
		button_5.setBounds(0, 0, 14, 14);
		panel_18.add(button_5);
		
		JPanel panel_19 = new JPanel();
		panel_4.add(panel_19, "cell 2 0,grow");
		panel_19.setLayout(null);
		
		JButton button_6 = new JButton("");
		button_6.setToolTipText("2");
		button_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel_50.setBackground(new java.awt.Color(180,255,0));
			}
		});
		button_6.setBackground(new Color(180, 255, 0));
		button_6.setBounds(0, 0, 14, 14);
		panel_19.add(button_6);
		
		JPanel panel_20 = new JPanel();
		panel_4.add(panel_20, "cell 3 0,grow");
		panel_20.setLayout(null);
		
		JButton button_7 = new JButton("");
		button_7.setToolTipText("3");
		button_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel_50.setBackground(new java.awt.Color(0,120,130));
			}
		});
		button_7.setBackground(new Color(0,120,130));
		button_7.setBounds(0, 0, 14, 14);
		panel_20.add(button_7);
		
		JPanel panel_21 = new JPanel();
		panel_4.add(panel_21, "cell 4 0,grow");
		panel_21.setLayout(null);
		
		JButton button_8 = new JButton("");
		button_8.setToolTipText("4");
		button_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel_50.setBackground(new java.awt.Color(255,80,0));
			}
		});
		button_8.setBackground(new Color(255, 80, 0));
		button_8.setBounds(0, 0, 14, 14);
		panel_21.add(button_8);
		
		JPanel panel_22 = new JPanel();
		panel_4.add(panel_22, "cell 5 0,grow");
		panel_22.setLayout(null);
		
		JButton button_9 = new JButton("");
		button_9.setToolTipText("5");
		button_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel_50.setBackground(new java.awt.Color(255,255,0));
			}
		});
		button_9.setBackground(new Color(255, 255, 0));
		button_9.setBounds(0, 0, 14, 14);
		panel_22.add(button_9);
		
		JPanel panel_23 = new JPanel();
		panel_4.add(panel_23, "cell 6 0,grow");
		panel_23.setLayout(null);
		
		JButton button_10 = new JButton("");
		button_10.setToolTipText("6");
		button_10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel_50.setBackground(new java.awt.Color(90,255,0));
			}
		});
		button_10.setBackground(new Color(90, 255, 0));
		button_10.setBounds(0, 0, 14, 14);
		panel_23.add(button_10);
		
		JPanel panel_24 = new JPanel();
		panel_4.add(panel_24, "cell 7 0,grow");
		panel_24.setLayout(null);
		
		JButton button_11 = new JButton("");
		button_11.setToolTipText("7");
		button_11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel_50.setBackground(new java.awt.Color(255,150,0));
			}
		});
		button_11.setBackground(new Color(255, 150, 0));
		button_11.setBounds(0, 0, 14, 14);
		panel_24.add(button_11);
		
		JPanel panel_25 = new JPanel();
		panel_4.add(panel_25, "cell 8 0,grow");
		panel_25.setLayout(null);
		
		JButton button_12 = new JButton("");
		button_12.setToolTipText("8");
		button_12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel_50.setBackground(new java.awt.Color(255,150,150));
			}
		});
		button_12.setBackground(new Color(255, 150, 150));
		button_12.setBounds(0, 0, 14, 14);
		panel_25.add(button_12);
		
		JPanel panel_26 = new JPanel();
		panel_4.add(panel_26, "cell 9 0,grow");
		panel_26.setLayout(null);
		
		JButton button_13 = new JButton("");
		button_13.setToolTipText("9");
		button_13.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel_50.setBackground(new java.awt.Color(200,170,0));
			}
		});
		button_13.setBackground(new Color(200, 170, 0));
		button_13.setBounds(0, 0, 14, 14);
		panel_26.add(button_13);
		
		JPanel panel_27 = new JPanel();
		panel_4.add(panel_27, "cell 10 0,grow");
		panel_27.setLayout(null);
		
		JButton button_14 = new JButton("");
		button_14.setToolTipText("10");
		button_14.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel_50.setBackground(new java.awt.Color(200,140,80));
			}
		});
		button_14.setBackground(new Color(200, 140, 80));
		button_14.setBounds(0, 0, 14, 14);
		panel_27.add(button_14);
		
		JPanel panel_28 = new JPanel();
		panel_4.add(panel_28, "cell 11 0,grow");
		panel_28.setLayout(null);
		
		JButton button_15 = new JButton("");
		button_15.setToolTipText("11");
		button_15.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel_50.setBackground(new java.awt.Color(0,255,0));
			}
		});
		button_15.setBackground(new Color(0, 255, 0));
		button_15.setBounds(0, 0, 14, 14);
		panel_28.add(button_15);
		
		JPanel panel_16 = new JPanel();
		panel_4.add(panel_16, "cell 0 1,grow");
		panel_16.setLayout(null);
		
		JButton button_16 = new JButton("");
		button_16.setToolTipText("12");
		button_16.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel_50.setBackground(new java.awt.Color(255,0,0));
			}
		});
		button_16.setBackground(new Color(255,0,0));
		button_16.setBounds(0, 0, 14, 14);
		panel_16.add(button_16);
		
		JPanel panel_29 = new JPanel();
		panel_4.add(panel_29, "cell 1 1,grow");
		panel_29.setLayout(null);
		
		JButton button_17 = new JButton("");
		button_17.setToolTipText("13");
		button_17.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel_50.setBackground(new java.awt.Color(0,150,0));
			}
		});
		button_17.setBackground(new Color(0, 150, 0));
		button_17.setBounds(0, 0, 14, 14);
		panel_29.add(button_17);
		
		JPanel panel_30 = new JPanel();
		panel_4.add(panel_30, "cell 2 1,grow");
		panel_30.setLayout(null);
		
		JButton button_18 = new JButton("");
		button_18.setToolTipText("14");
		button_18.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel_50.setBackground(new java.awt.Color(0,140,90));
			}
		});
		button_18.setBackground(new Color(0, 140, 90));
		button_18.setBounds(0, 0, 14, 14);
		panel_30.add(button_18);
		
		JPanel panel_31 = new JPanel();
		panel_4.add(panel_31, "cell 3 1,grow");
		panel_31.setLayout(null);
		
		JButton button_19 = new JButton("");
		button_19.setToolTipText("15");
		button_19.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel_50.setBackground(new java.awt.Color(0,210,30));
			}
		});
		button_19.setBackground(new Color(0, 210, 30));
		button_19.setBounds(0, 0, 14, 14);
		panel_31.add(button_19);
		
		JPanel panel_32 = new JPanel();
		panel_4.add(panel_32, "cell 4 1,grow");
		panel_32.setLayout(null);
		
		JButton button_20 = new JButton("");
		button_20.setToolTipText("16");
		button_20.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel_50.setBackground(new java.awt.Color(0,100,255));
			}
		});
		button_20.setBackground(new Color(0, 100, 255));
		button_20.setBounds(0, 0, 14, 14);
		panel_32.add(button_20);
		
		JPanel panel_33 = new JPanel();
		panel_4.add(panel_33, "cell 5 1,grow");
		panel_33.setLayout(null);
		
		JButton button_21 = new JButton("");
		button_21.setToolTipText("17");
		button_21.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel_50.setBackground(new java.awt.Color(50,140,0));
			}
		});
		button_21.setBackground(new Color(50, 140, 0));
		button_21.setBounds(0, 0, 14, 14);
		panel_33.add(button_21);
		
		JPanel panel_34 = new JPanel();
		panel_4.add(panel_34, "cell 6 1,grow");
		panel_34.setLayout(null);
		
		JButton button_22 = new JButton("");
		button_22.setToolTipText("18");
		button_22.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel_50.setBackground(new java.awt.Color(70,180,0));
			}
		});
		button_22.setBackground(new Color(70, 180, 0));
		button_22.setBounds(0, 0, 14, 14);
		panel_34.add(button_22);
		
		JPanel panel_35 = new JPanel();
		panel_4.add(panel_35, "cell 7 1,grow");
		panel_35.setLayout(null);
		
		JButton button_23 = new JButton("");
		button_23.setToolTipText("19");
		button_23.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel_50.setBackground(new java.awt.Color(255,255,150));
			}
		});
		button_23.setBackground(new Color(255, 255, 150));
		button_23.setBounds(0, 0, 14, 14);
		panel_35.add(button_23);
		
		JPanel panel_36 = new JPanel();
		panel_4.add(panel_36, "cell 8 1,grow");
		panel_36.setLayout(null);
		
		JButton button_24 = new JButton("");
		button_24.setToolTipText("20");
		button_24.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel_50.setBackground(new java.awt.Color(255,0,255));
			}
		});
		button_24.setBackground(new Color(255, 0, 255));
		button_24.setBounds(0, 0, 14, 14);
		panel_36.add(button_24);
		
		JPanel panel_37 = new JPanel();
		panel_4.add(panel_37, "cell 9 1,grow");
		panel_37.setLayout(null);
		
		JButton button_25 = new JButton("");
		button_25.setToolTipText("21");
		button_25.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel_50.setBackground(new java.awt.Color(255,100,255));
			}
		});
		button_25.setBackground(new Color(255, 100, 255));
		button_25.setBounds(0, 0, 14, 14);
		panel_37.add(button_25);
		
		JPanel panel_38 = new JPanel();
		panel_4.add(panel_38, "cell 10 1,grow");
		panel_38.setLayout(null);
		
		JButton button_26 = new JButton("");
		button_26.setToolTipText("22");
		button_26.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel_50.setBackground(new java.awt.Color(175,0,175));
			}
		});
		button_26.setBackground(new Color(175, 0, 175));
		button_26.setBounds(0, 0, 14, 14);
		panel_38.add(button_26);
		
		JPanel panel_39 = new JPanel();
		panel_4.add(panel_39, "cell 11 1,grow");
		panel_39.setLayout(null);
		
		JButton button_27 = new JButton("");
		button_27.setToolTipText("23");
		button_27.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel_50.setBackground(new java.awt.Color(175,50,255));
			}
		});
		button_27.setBackground(new Color(175, 50, 255));
		button_27.setBounds(0, 0, 14, 14);
		panel_39.add(button_27);
		
		JPanel panel_17 = new JPanel();
		panel_4.add(panel_17, "cell 0 2,grow");
		panel_17.setLayout(null);
		
		JButton button_28 = new JButton("");
		button_28.setToolTipText("24");
		button_28.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel_50.setBackground(new java.awt.Color(255,255,255));
			}
		});
		button_28.setBackground(new Color(255, 255, 255));
		button_28.setBounds(0, 0, 14, 14);
		panel_17.add(button_28);
		
		JPanel panel_40 = new JPanel();
		panel_4.add(panel_40, "cell 1 2,grow");
		panel_40.setLayout(null);
		
		JButton button_29 = new JButton("");
		button_29.setToolTipText("25");
		button_29.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel_50.setBackground(new java.awt.Color(210,210,210));
			}
		});
		button_29.setBackground(new Color(210, 210, 210));
		button_29.setBounds(0, 0, 14, 14);
		panel_40.add(button_29);
		
		JPanel panel_41 = new JPanel();
		panel_4.add(panel_41, "cell 2 2,grow");
		panel_41.setLayout(null);
		
		JButton button_30 = new JButton("");
		button_30.setToolTipText("26");
		button_30.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel_50.setBackground(new java.awt.Color(210,210,210));
			}
		});
		button_30.setBackground(new Color(210, 210, 210));
		button_30.setBounds(0, 0, 14, 14);
		panel_41.add(button_30);
		
		JPanel panel_42 = new JPanel();
		panel_4.add(panel_42, "cell 3 2,grow");
		panel_42.setLayout(null);
		
		JButton button_31 = new JButton("");
		button_31.setToolTipText("27");
		button_31.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel_50.setBackground(new java.awt.Color(210,210,210));
			}
		});
		button_31.setBackground(new Color(210, 210, 210));
		button_31.setBounds(0, 0, 14, 14);
		panel_42.add(button_31);
		
		JPanel panel_43 = new JPanel();
		panel_4.add(panel_43, "cell 4 2,grow");
		panel_43.setLayout(null);
		
		JButton button_32 = new JButton("");
		button_32.setToolTipText("28");
		button_32.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel_50.setBackground(new java.awt.Color(210,210,210));
			}
		});
		button_32.setBackground(new Color(210, 210, 210));
		button_32.setBounds(0, 0, 14, 14);
		panel_43.add(button_32);
		
		JPanel panel_44 = new JPanel();
		panel_4.add(panel_44, "cell 5 2,grow");
		panel_44.setLayout(null);
		
		JButton button_33 = new JButton("");
		button_33.setToolTipText("29");
		button_33.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel_50.setBackground(new java.awt.Color(210,210,210));
			}
		});
		button_33.setBackground(new Color(210, 210, 210));
		button_33.setBounds(0, 0, 14, 14);
		panel_44.add(button_33);
		
		JPanel panel_45 = new JPanel();
		panel_4.add(panel_45, "cell 6 2,grow");
		panel_45.setLayout(null);
		
		JButton button_34 = new JButton("");
		button_34.setToolTipText("30");
		button_34.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel_50.setBackground(new java.awt.Color(210,210,210));
			}
		});
		button_34.setBackground(new Color(210, 210, 210));
		button_34.setBounds(0, 0, 14, 14);
		panel_45.add(button_34);
		
		JPanel panel_46 = new JPanel();
		panel_4.add(panel_46, "cell 7 2,grow");
		panel_46.setLayout(null);
		
		JButton button_35 = new JButton("");
		button_35.setToolTipText("31");
		button_35.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel_50.setBackground(new java.awt.Color(50,50,50));
			}
		});
		button_35.setBackground(new Color(50, 50, 50));
		button_35.setBounds(0, 0, 14, 14);
		panel_46.add(button_35);
		
		JPanel panel_47 = new JPanel();
		panel_4.add(panel_47, "cell 8 2,grow");
		panel_47.setLayout(null);
		
		JButton button_36 = new JButton("");
		button_36.setToolTipText("32");
		button_36.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel_50.setBackground(new java.awt.Color(100,100,100));
			}
		});
		button_36.setBackground(new Color(100, 100, 100));
		button_36.setBounds(0, 0, 14, 14);
		panel_47.add(button_36);
		
		JPanel panel_48 = new JPanel();
		panel_4.add(panel_48, "cell 9 2,grow");
		panel_48.setLayout(null);
		
		JButton button_37 = new JButton("");
		button_37.setToolTipText("33");
		button_37.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel_50.setBackground(new java.awt.Color(150,150,150));
			}
		});
		button_37.setBackground(new Color(150, 150, 150));
		button_37.setBounds(0, 0, 14, 14);
		panel_48.add(button_37);
		
		JPanel panel_49 = new JPanel();
		layeredPane.add(panel_49, "cell 4 0 1 2,grow");
		panel_49.setLayout(null);
		
		
		panel_50.setForeground(SystemColor.window);
		panel_50.setBackground(Color.BLACK);
		panel_50.setBounds(0, 0, 40, 42);
		panel_49.add(panel_50);
		
		JPanel panel_51 = new JPanel();
		panel_51.setBounds(0, 40, 40, 23);
		panel_49.add(panel_51);
		
		JLabel label_1 = new JLabel("\u0426\u0432\u0435\u0442");
		panel_51.add(label_1);
		
		JLayeredPane layeredPane_1 = new JLayeredPane();
		tabbedPane.addTab("\u0412\u0438\u0434", null, layeredPane_1, "");
		tabbedPane.setEnabledAt(1, true);
		layeredPane_1.setLayout(new MigLayout("", "[40px:n:40px,grow][][40px:n:40px,grow][][40px:n:40px,grow][][150px:n:150px,grow][grow][][][::80px]", "[55px:n:55px,grow]"));
		
		JPanel panel_6 = new JPanel();
		layeredPane_1.add(panel_6, "cell 0 0,grow");
		panel_6.setLayout(null);
		
		JButton btnSize = new JButton("");
		btnSize.setToolTipText("\u0423\u0432\u0435\u043B\u0438\u0447\u0438\u0442\u044C");
		btnSize.setIcon(new ImageIcon(work_form.class.getResource("/resource/magnifier+.png")));
		btnSize.setBounds(0, 0, 40, 55);
		panel_6.add(btnSize);
		
		
		JSeparator separator_4 = new JSeparator();
		layeredPane_1.add(separator_4, "cell 1 0");
		
		JPanel panel_7 = new JPanel();
		layeredPane_1.add(panel_7, "cell 2 0,grow");
		panel_7.setLayout(null);
		
		JButton button = new JButton("");
		button.setIcon(new ImageIcon(work_form.class.getResource("/resource/magnifier-.png")));
		button.setToolTipText("\u0423\u043C\u0435\u043D\u044C\u0448\u0438\u0442\u044C");
		button.setBounds(0, 0, 40, 55);
		button.setHorizontalTextPosition(SwingConstants.CENTER);
		panel_7.add(button);
		
		JSeparator separator_5 = new JSeparator();
		layeredPane_1.add(separator_5, "cell 3 0");
		
		JPanel panel_8 = new JPanel();
		layeredPane_1.add(panel_8, "cell 4 0,grow");
		panel_8.setLayout(null);
		
		JButton button_1 = new JButton("");
		button_1.setIcon(new ImageIcon(work_form.class.getResource("/resource/fullscreen.png")));
		button_1.setToolTipText("100%");
		button_1.setBounds(0, 0, 40, 55);
		button_1.setHorizontalTextPosition(SwingConstants.CENTER);
		panel_8.add(button_1);
		
		JSeparator separator_6 = new JSeparator();
		layeredPane_1.add(separator_6, "cell 5 0");
		
		JPanel panel_9 = new JPanel();
		layeredPane_1.add(panel_9, "cell 6 0,grow");
		panel_9.setLayout(null);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("\u0421\u0435\u0442\u043A\u0430");
		chckbxNewCheckBox.setBounds(0, 18, 150, 15);
		chckbxNewCheckBox.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent event) {
		    	picture.isReapaint();
		    }
		});
		panel_9.add(chckbxNewCheckBox);
		
		JCheckBox checkBox_1 = new JCheckBox("\u0421\u0442\u0440\u043E\u043A\u0430 \u0441\u043E\u0441\u0442\u043E\u044F\u043D\u0438\u044F");
		checkBox_1.setSelected(true);
		checkBox_1.setBounds(0, 36, 150, 15);
		checkBox_1.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent event) {
		        if (checkBox_1.isSelected()) {
		            panel.setVisible(true);
		        } else {
		        	panel.setVisible(false);
		        }
		    }
		});
		panel_9.add(checkBox_1);
		
		
		
		JCheckBox checkBox = new JCheckBox("\u041F\u043E\u0434\u0441\u0432\u0435\u0442\u043A\u0430 \u043E\u0431\u043B\u0430\u0441\u0442\u0438");
		checkBox.setBounds(0, 0, 150, 15);
		panel_9.add(checkBox);
		
		
		contentPane.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new MigLayout("", "[86px][][86px][][86px][][][][][][][][][][][][][][][][][][][][][][][][right]", "[20px,grow]"));
		
		JLabel lblNewLabel = new JLabel("0.0; 0.0 \u041F\u041A\u0421");
		lblNewLabel.setIcon(new ImageIcon(work_form.class.getResource("/resource/arrowMany.png")));
		panel.add(lblNewLabel, "cell 0 0");
		
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.BLACK);
		panel.add(separator, "cell 1 0");
		
		
		contentPane.add(panel_58, BorderLayout.CENTER);
		panel_58.setLayout(new BorderLayout(0, 0));
		
		Panel panel_1 = new Panel();
		panel_58.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		
		panel_1.add(scrollPane, BorderLayout.CENTER);
		
		
		
		
/*
		painter p = new painter();
		
		p.init();
		p.height=400;
		p.width=400;
		Applet app = p;
		app.setPreferredSize(new Dimension(640, 480));
		//app.setBounds(10,10,50,50);
		scrollPane.add(app);
*/
		/*
		JPanel p1 = new JPanel();
		p1.setBounds(0, 0, 640, 480);
		
		p1.setLayout(new BoxLayout(p1, BoxLayout.X_AXIS));
		
		JPanel p2 = new JPanel();
		p2.setPreferredSize(new Dimension(640, 480));
		//p2.setLayout(null);
		p2.add(p1);
		
		painter p = new painter();
		p1.add(p);
		p.init();
		p.height=640;
		p.width=480;
		Applet app = p;
		app.setPreferredSize(new Dimension(640, 480));
		scrollPane.add(p2);
		//p1.setLayout(null);
		 */
		 
		
		
/*
		JPanel p2 = new JPanel();
		p2.setPreferredSize(new Dimension(440, 320));
		
		//Запуск рисования
		SwingUtilities.invokeLater(new  Runnable() {
		      public void run() {
		        new  ImageEdit2(p2, menuBar, panel_50, btnNewButton_4, button_5, button_6, button_7);
		      }
		    });
		scrollPane.add(p2);
		p2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
*/
		
		
		JPanel p2 = new JPanel();
		p2.setPreferredSize(new Dimension(415, 415));
		
		scrollPane.add(p2);
		//Запуск рисования
		SwingUtilities.invokeLater(new  Runnable() {
		      public void run() {
		    	  JButton masBt[]= {btnNewButton_4, button_5, button_6, button_7, button_8, button_9, button_10, button_11, button_12, button_13, button_14, button_15, button_16, button_17, button_18, button_19, button_20, button_21, button_22, button_23, button_24, button_25, button_26, button_27, button_28, button_29, button_30, button_31, button_32, button_33, button_34, button_35, button_36, button_37};
		    	  JButton masFunc[]= {btn111, btn114, btn112, btn120, btn117, btn119, btn118, btn115, btn113, btn121, btn122, btnVidelenieButton, btn116, button_3, button_2, button_38, button_39};
		    	  
		    	  JPanel panel_59 = new JPanel();
		    	  panel_58.add(panel_59, BorderLayout.EAST);
		    	  panel_59.setPreferredSize(new Dimension(250, 100));
		    	  panel_59.setMinimumSize(new Dimension(250, 100));
		    	  panel_59.setLayout(new BorderLayout(0, 0));
		    	  
		    	  JPanel panel_62 = new JPanel();
		    	  panel_59.add(panel_62, BorderLayout.WEST);
		    	  panel_62.setLayout(new BorderLayout(0, 0));
		    	  
		    	  JLabel lblNewLabel_1 = new JLabel("::");
		    	  lblNewLabel_1.setPreferredSize(new Dimension(7, 100));
		    	  lblNewLabel_1.setMinimumSize(new Dimension(7, 100));
		    	  lblNewLabel_1.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
		    	  panel_62.add(lblNewLabel_1);
		    	  
    
		  			frame.addMouseMotionListener(new  MouseMotionAdapter() {
		  				//Перемещение
		  				public void mouseDragged(MouseEvent e) {
		  					panel_59.setPreferredSize(new Dimension(frame.getWidth()-e.getX(), panel_59.getY()));
		  					fit_x = panel_59.getX()-e.getX();
		  					panel_59.updateUI();
		  				}
		  		
		  			});
		    	  
		    	  
		  			
		  			
		    	  
		    	  JPanel panel_60 = new JPanel();
		    	  panel_59.add(panel_60, BorderLayout.NORTH);
		    	  
		    	  JScrollPane scrollPane_1 = new JScrollPane();
		    	  panel_59.add(scrollPane_1, BorderLayout.CENTER);
		    	  
		    	  JTextPane textArea = new JTextPane()
		    	  {
		    	      public boolean getScrollableTracksViewportWidth()
		    	      {
		    	          return getUI().getPreferredSize(this).width 
		    	              <= getParent().getSize().width;
		    	      }
		    	  };
		    	  //textArea.setMinimumSize(new Dimension(300,500));
		    	  //textArea.setPreferredSize(new Dimension(300,500));
		    		  
		    	  scrollPane_1.setViewportView(textArea);
		    	  JLabel label = new JLabel("0.0; 0.0 \u041F\u041A\u0421");
		    	  label.setIcon(new ImageIcon(work_form.class.getResource("/resource/stock_draw-rounded-square-unfilled.png")));
		    	  panel.add(label, "cell 2 0");
		    	  
		    	  JLabel label_2 = new JLabel("\u041C\u0430\u0441\u0448\u0442\u0430\u0431: 100%");
		    	  panel.add(label_2, "cell 4 0");
		    	  
		    	  //JTextArea textArea = new JTextArea();
		    	  picture pi = new  picture(p2, menuBar, panel_50, masBt, masFunc, lblNewLabel, textArea, button, btnSize, button_1, scrollPane, btnNewButton_2, label, chckbxNewCheckBox, checkBox, panel_50, frame, tabbedPane, label_2);
		    	  
		    	  
		    	  JSeparator separator_7 = new JSeparator();
		    	  separator_7.setForeground(Color.BLACK);
		    	  panel.add(separator_7, "cell 3 0");
		    	  
		    	  
		    	  
		    	  JSeparator separator_8 = new JSeparator();
		    	  separator_8.setForeground(Color.BLACK);
		    	  panel.add(separator_8, "cell 5 0");
		    	  
		    	  JLabel lblLonLat = new JLabel("\u0414\u043E\u043B\u0433\u043E\u0442\u0430: 0.0; \u0428\u0438\u0440\u043E\u0442\u0430: 0.0");
		    	  lblLonLat.setIcon(new ImageIcon(work_form.class.getResource("/resource/compas_.png")));
		    	  dataBox.newLabel(lblLonLat);
		    	  //lblLonLat.setVisible(false);
		    	  panel.add(lblLonLat, "cell 6 0");
		    	  //label_2.setVisible(false);
		    	  
		    	  
		    	  
		    	  
		    	  
		    	  /*
		    	  JPopupMenu popup = new JPopupMenu();
		    	  popup.setLabel("");
		    	  popup.setToolTipText("");
		    	  addPopup(textArea, popup);
		    	  
		    	  ActionListener copyListener = new ActionListener() {
		    	      public void actionPerformed(ActionEvent event) {
		    	        System.out.println("Popup menu item ["
		    	            + event.getActionCommand() + "] was pressed.");
		    	      }
		    	  };
		    	  
		    	  ActionListener cutlListener = new ActionListener() {
		    	      public void actionPerformed(ActionEvent event) {
		    	        System.out.println("Popup menu item ["
		    	            + event.getActionCommand() + "] was pressed.");
		    	      }
		    	  };
		    	  
		    	  ActionListener pasteListener = new ActionListener() {
		    	      public void actionPerformed(ActionEvent event) {
		    	    	  textArea.insert(,);
		    	      }
		    	  };
		    	  
		    	  
		    	    JMenuItem item, item1, item2;
		    	    popup.add(item = new JMenuItem("Копировать"));
		    	    item.setHorizontalTextPosition(JMenuItem.RIGHT);
		    	    item.addActionListener(copyListener);
		    	    popup.add(item1 = new JMenuItem("Вырезать"));
		    	    item1.setHorizontalTextPosition(JMenuItem.RIGHT);
		    	    item1.addActionListener(cutlListener);
		    	    popup.add(item2 = new JMenuItem("Вставить"));
		    	    item2.setHorizontalTextPosition(JMenuItem.RIGHT);
		    	    item2.addActionListener(pasteListener);
		    	    */
		    	    
		    	    
		    	  JPopupMenu menu = new JPopupMenu();
		          Action cut = new DefaultEditorKit.CutAction();
		          cut.putValue(Action.NAME, "Вырезать");
		          cut.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		          menu.add( cut );

		          Action copy = new DefaultEditorKit.CopyAction();
		          copy.putValue(Action.NAME, "Копировать");
		          copy.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
		          menu.add( copy );

		          Action paste = new DefaultEditorKit.PasteAction();
		          paste.putValue(Action.NAME, "Вставить");
		          paste.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
		          menu.add( paste );

		          Action selectAll = new SelectAllText(textArea);
		          menu.add( selectAll );

		          textArea.setComponentPopupMenu( menu );
		    	    
		    	    
		    	    
		    	    /*
		    	    popup.add(item = new JMenuItem("Full"));
		    	    item.setHorizontalTextPosition(JMenuItem.RIGHT);
		    	    item.addActionListener(menuListener);
		    	    popup.addSeparator();
		    	    popup.add(item = new JMenuItem("Settings . . ."));
		    	    item.addActionListener(menuListener);
		    	    */

		    	    //popup.setLabel("Justification");
		    	    //popup.setBorder(new BevelBorder(BevelBorder.RAISED));
		    	    //popup.addPopupMenuListener(new PopupPrintListener());

		    	    //addMouseListener(new MousePopupListener());

		    	  JLayeredPane layeredPane_2 = new JLayeredPane();
		    	  tabbedPane.addTab("\u041F\u0440\u043E\u0447\u0435\u0435", null, layeredPane_2, null);
		    	  layeredPane_2.setLayout(new MigLayout("", "[40px:n:40px,grow][40px:n:40px,grow]", "[55px:n:55px,grow]"));
		    	  
		    	  JPanel panel_66 = new JPanel();
		    	  layeredPane_2.add(panel_66, "cell 0 0,grow");
		    	  panel_66.setLayout(null);
		    	  
		    	  JButton btnNewButton = new JButton("");
		    	  btnNewButton.setIcon(new ImageIcon(work_form.class.getResource("/resource/Check.png")));
		    	  btnNewButton.setToolTipText("\u041F\u0440\u043E\u0432\u0435\u0440\u043A\u0430 index \u0444\u0430\u0439\u043B\u0430");
		    	  btnNewButton.setBounds(0, 0, 40, 55);
		    	  panel_66.add(btnNewButton);
		    	  
		    	  JPanel panel_67 = new JPanel();
		    	  panel_67.setLayout(null);
		    	  layeredPane_2.add(panel_67, "cell 1 0,grow");
		    	  
		    	  JButton button_4 = new JButton("");
		    	  button_4.setIcon(new ImageIcon(work_form.class.getResource("/resource/page_text.png")));
		    	  button_4.setToolTipText("\u0413\u0435\u043D\u0435\u0440\u0438\u0440\u043E\u0432\u0430\u0442\u044C \u0448\u0430\u0431\u043B\u043E\u043D index \u0444\u0430\u0439\u043B\u0430");
		    	  button_4.setBounds(0, 0, 40, 55);
		    	  panel_67.add(button_4);
		    	  p2.setLayout(new BorderLayout(0, 0));
		    	  
		    	  button_4.addMouseListener(new MouseAdapter() {
		        	  public void mouseClicked(MouseEvent event) {
		        		  //Очистка области
		        		  textArea.selectAll();
		        		  textArea.replaceSelection("");
		        		  //Вставка текста
		        		  textArea.setText(genIndexFile.generate());
		        	  }
		          });
		    	  //scrollPane_1.add(textArea);
		    	 
		    	  //panel_59.add(textArea, BorderLayout.EAST);
		    	  
		    	  
		    	  
		    	  
		    	  //////////Задание размера области или холста//////////////////////
		    	  
		    	  btnNewButton_1.addMouseListener(new MouseAdapter() {
		        	  public void mouseClicked(MouseEvent event) {
		        		  dialog = null;
		        		  if(dialog == null)
							try {
								dialog = new SizeDialog(frame, pi, p2);
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
		        		  dialog.setVisible(true);
		        	  }
		          });
		    	  
		    	  
		    	  btnNewButton.addMouseListener(new MouseAdapter() {
		        	  public void mouseClicked(MouseEvent event) {
		        		  String[] lines_ = textArea.getText().split("\\n");
		        		  String lines__ = "";
		        		  //textArea.selectAll();
			        	  //textArea.replaceSelection("");
		        		  for(int h = 0; h < lines_.length-1; h++) {
			        		 lines__ += lines_[h]+"\n";
		        		  }
		        		  lines__ += lines_[lines_.length-1];
		        		  textArea.setText(lines__);
		        		  /*
		        		  String sts = textArea.getText();
		        		  textArea.selectAll();
		        		  textArea.replaceSelection("");
		        		  textArea.setText(sts);
		        		  */
		        		  
		        		  //Зададим шрифты
		        		    Style heading = null;
		        		    Style normal = null;
		        		    normal = textArea.addStyle("heading", null);		    
		        	        StyleConstants.setFontFamily(normal, "Times New Roman"); 
		        	        StyleConstants.setFontSize(normal, 16);
		        	        // Наследуем свойстdо FontFamily
		        	        heading = textArea.addStyle("normal", normal);
		        	        StyleConstants.setFontSize(heading, 16);
		        	        StyleConstants.setBold(heading, true);
		        		  
		        		  // Изменение стиля части текста
		        		  	SimpleAttributeSet black = new SimpleAttributeSet();
      			        	StyleConstants.setForeground(black, Color.black);
		        			SimpleAttributeSet blue = new SimpleAttributeSet();
		        			StyleConstants.setForeground(blue, Color.blue);
		        			StyledDocument doc = textArea.getStyledDocument();
		        			doc.setCharacterAttributes(0, textArea.getText().length(), black, false);
		        			//doc.setCharacterAttributes(3, 3, blue, false);

		        		  String[] lines = textArea.getText().split("\\n");
		        		  int ch = 0;
		        		  for(int i = 0; i < lines.length; i++)
		        		  {
		        			  Style style = normal;
		        			  int a = -1;
		        			  if(!verifyLine.isVerify(lines[i]))
		        			  {
		        				  style = heading;
		        				  doc.setCharacterAttributes(ch, lines[i].length(), blue, false);
		        			  }
		        				//insertText(textArea, lines[i], style);  
		        				ch+=lines[i].length()+1;
		        		  }
		        		  			
		        	  }
		          });  
		      }   
		    });
		

		
	}
	
	
	
	/**
     * Изменениестиля документа
     */
    private void changeDocumentStyle(JTextPane editor)
    {
        // Изменение стиля части текста
        SimpleAttributeSet blue = new SimpleAttributeSet();
        StyleConstants.setForeground(blue, Color.blue);
        StyledDocument doc = editor.getStyledDocument();
        doc.setCharacterAttributes(10, 9, blue, false);
    }
	
	/**
	     * Добавление в строки определенного стиля
	     * @param editor редактор
	     * @param string строка
	     * @param style стиль
	     */
	public void insertText(JTextPane editor, String string,Style style)
	    {
	            Document doc = editor.getDocument();
	            try {
					doc.insertString(doc.getLength(), string, style);
				} catch (BadLocationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    }
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
	
	public static JFrame getFrame() {
		return frame;
	}
}
