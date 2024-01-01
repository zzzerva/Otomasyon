package tr.com.zerva.Gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Image;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class YetkiliGiris extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public static JButton btnYetkiliIlemleri = new JButton("YETKİ İŞLEMLERİ");
	public static JButton btnrnIlemleri = new JButton("ÜRÜN İŞLEMLERİ");
	public static YetkiliGiris yetkiliGirisInstance;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					YetkiliGiris frame = new YetkiliGiris();
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
	public YetkiliGiris() {
		yetkiliGirisInstance = this;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 441, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 102));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("SATIŞ İŞLEMLERİ");
		Image img = new ImageIcon(YetkiliGiris.class.getResource("/satis.png")).getImage();
		Image scaledImg = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		btnNewButton.setIcon(new ImageIcon(scaledImg));
		btnNewButton.setFont(new Font("Yu Gothic", Font.BOLD, 12));
		btnNewButton.setBounds(10, 10, 196, 113);
		contentPane.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            SatisIslem yetki = new SatisIslem();
	            yetki.setVisible(true);
	            dispose();
	        }
	    });
		
		//btnrnIlemleri = new JButton("ÜRÜN İŞLEMLERİ");
		Image img1 = new ImageIcon(YetkiliGiris.class.getResource("/ürün.png")).getImage();
		Image scaledImg1 = img1.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		btnrnIlemleri.setIcon(new ImageIcon(scaledImg1));
		btnrnIlemleri.setFont(new Font("Yu Gothic", Font.BOLD, 12));
		btnrnIlemleri.setBounds(216, 10, 196, 113);
		contentPane.add(btnrnIlemleri);
		
		btnrnIlemleri.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        	UrunIslem yetki = new UrunIslem();
	            yetki.setVisible(true);
	            dispose();
	        }
	    });
		
		//btnYetkiliIlemleri = new JButton("YETKİ İŞLEMLERİ");
		Image img2 = new ImageIcon(YetkiliGiris.class.getResource("/yetkili.png")).getImage();
		Image scaledImg2 = img2.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		btnYetkiliIlemleri.setIcon(new ImageIcon(scaledImg2));
		btnYetkiliIlemleri.setFont(new Font("Yu Gothic", Font.BOLD, 12));
		btnYetkiliIlemleri.setBounds(10, 140, 196, 113);
		contentPane.add(btnYetkiliIlemleri);
		
		btnYetkiliIlemleri.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        	edit_member yetki = new edit_member();
	            yetki.setVisible(true);
	            dispose();
	        }
	    });
		
		JButton btnk = new JButton("ÇIKIŞ");
		Image img3 = new ImageIcon(YetkiliGiris.class.getResource("/cik.png")).getImage();
		Image scaledImg3 = img3.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		btnk.setIcon(new ImageIcon(scaledImg3));
		btnk.setFont(new Font("Yu Gothic", Font.BOLD, 12));
		btnk.setBounds(216, 140, 196, 113);
		contentPane.add(btnk);
		
		btnk.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            AnaFE cik = new AnaFE();
	            cik.setVisible(true);
	            dispose();
	        }
	    });
	}
	
	public static YetkiliGiris getYetkiliGirisInstance() {
        return yetkiliGirisInstance;
    }
	public void gizleButonlar() {
	    btnYetkiliIlemleri.setVisible(false);
	    btnrnIlemleri.setVisible(false);
	}
}
