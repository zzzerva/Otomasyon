package tr.com.zerva.Gui;


import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import javax.swing.JLayeredPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Image;
import javax.swing.SwingConstants;
import java.awt.Color;

public class AnaFE extends JFrame{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AnaFE frame = new AnaFE();
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
	public AnaFE() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 727, 495);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(new Font("Yu Gothic", Font.BOLD, 15));
		tabbedPane.setForeground(SystemColor.desktop);
		tabbedPane.setBackground(SystemColor.activeCaption);
		tabbedPane.setBounds(150, 110, 400, 230);
		contentPane.add(tabbedPane);
		
		JLabel back = new JLabel();
        back.setBounds(0, 0, 727, 495);
        contentPane.add(back);
        Image img = new ImageIcon(AnaFE.class.getResource("/2.jpg")).getImage();
        back.setIcon(new ImageIcon(img));
		
		JPanel panel =new JPanel();
		panel.setForeground(SystemColor.desktop);
		tabbedPane.addTab("Giriş", null, panel, null);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Adı Soyadı");
		lblNewLabel.setForeground(SystemColor.desktop);
		lblNewLabel.setBounds(50, 50, 150, 30);
		lblNewLabel.setBackground(SystemColor.activeCaptionText);
		lblNewLabel.setFont(new Font("Yu Gothic", Font.BOLD, 15));
		panel.add(lblNewLabel);
		
        JLabel lblNewLabel_1 = new JLabel("Şifre");
        lblNewLabel_1.setBounds(50, 90, 150, 30);
        lblNewLabel_1.setForeground(SystemColor.desktop);
        lblNewLabel_1.setBackground(SystemColor.activeCaptionText);
        lblNewLabel_1.setFont(new Font("Yu Gothic", Font.BOLD, 15));
        panel.add(lblNewLabel_1);
        
        textField = new JTextField();
        textField.setBounds(200, 50, 150, 30);
        textField.setForeground(SystemColor.desktop);
        panel.add(textField);
        textField.setColumns(10);
        
        passwordField = new JPasswordField();
		passwordField.setBounds(200, 94, 150, 30);
		panel.add(passwordField);
		
		JButton btnNewButton = new JButton("Giriş");
		btnNewButton.setVerticalAlignment(SwingConstants.TOP);
		btnNewButton.setFont(new Font("Yu Gothic", Font.BOLD, 15));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String pass = new String(passwordField.getPassword());
				String useradi = textField.getText();
				String sql = "SELECT * FROM accounts WHERE adiSoyadi = '"+useradi+"'";  
				try {
					ResultSet myRs1 = Bagla.bul(sql);
					while (myRs1.next()) {
						if(useradi.equals(myRs1.getString("adiSoyadi")) && pass.equals(myRs1.getString("sifre"))) {
							System.out.println("Giriş Başarılı..");
							 YetkiliGiris yetkiliGiris = new YetkiliGiris();
						     yetkiliGiris.setVisible(true);
						     dispose();
						}
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(147, 160, 96, 21);
		panel.add(btnNewButton);
                
                JLabel lblNewLabel_4 = new JLabel("GİRİŞ İŞLMLERİ");
                lblNewLabel_4.setHorizontalAlignment(SwingConstants.LEFT);
                lblNewLabel_4.setVerticalAlignment(SwingConstants.TOP);
                lblNewLabel_4.setForeground(SystemColor.desktop);
                lblNewLabel_4.setFont(new Font("Yu Gothic", Font.BOLD, 15));
                lblNewLabel_4.setBounds(10, 10, 150, 20);
                panel.add(lblNewLabel_4);
                
                JPanel panel_1 = new JPanel();
                panel_1.setBackground(SystemColor.menu);
                tabbedPane.addTab("Kayıt", null, panel_1, null);
                panel_1.setLayout(null);
                
                JLabel lblNewLabel_2 = new JLabel("Adı Soyadı");
                lblNewLabel_2.setForeground(SystemColor.desktop);
                lblNewLabel_2.setFont(new Font("Yu Gothic", Font.BOLD, 15));
                lblNewLabel_2.setBounds(50, 50, 150, 30);
                panel_1.add(lblNewLabel_2);
                
                JLabel lblNewLabel_3 = new JLabel("Şifre");
                lblNewLabel_3.setForeground(SystemColor.desktop);
                lblNewLabel_3.setFont(new Font("Yu Gothic", Font.BOLD, 15));
                lblNewLabel_3.setBounds(50, 90, 150, 30);
                panel_1.add(lblNewLabel_3);
                
                textField_1 = new JTextField();
                textField_1.setBounds(200, 50, 150, 30);
                panel_1.add(textField_1);
                textField_1.setColumns(10);
                
                textField_2 = new JTextField();
                textField_2.setBounds(200, 90, 150, 30);
                panel_1.add(textField_2);
                textField_2.setColumns(10);
                		
                JButton btnNewButton_1 = new JButton("Kayıt");
        		btnNewButton_1.addActionListener(new ActionListener() {
        			public void actionPerformed(ActionEvent e) {
        				ResultSet myRs = Bagla.yap();
        				String kayitadi = textField_1.getText();
        				String kayitsifre = textField_2.getText();
        				Integer kayitYetki = 2;
        				String sql1 = "INSERT INTO accounts(yetkiId, AdiSoyadi, sifre) VALUES ("+kayitYetki+", '"+kayitadi+"', '"+kayitsifre+"')";
        				try {
        					Bagla.ekle(sql1);
        					System.out.println("Kayıt yapıldı...");
        				} catch (SQLException e1) {
        					e1.printStackTrace();
        				}
        			}
        		});
                btnNewButton_1.setBounds(145, 147, 96, 21);
                panel_1.add(btnNewButton_1);
                		
                JLabel lblNewLabel_5 = new JLabel("KAYIT İŞLEMLERİ");
                lblNewLabel_5.setHorizontalAlignment(SwingConstants.LEFT);
                lblNewLabel_5.setForeground(SystemColor.desktop);
                lblNewLabel_5.setFont(new Font("Yu Gothic", Font.BOLD, 14));
                lblNewLabel_5.setBounds(10, 10, 150, 20);
                panel_1.add(lblNewLabel_5);
                
	}
}