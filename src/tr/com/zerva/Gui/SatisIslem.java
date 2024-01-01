package tr.com.zerva.Gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.SystemColor;

public class SatisIslem extends JFrame {
    private static final String DB_URL = "jdbc:mysql://localhost:3307/otomasyon";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    private JPanel contentPane = new JPanel();
    private int mouseX, mouseY;
    private JTextField AdiField, kategoriAdiField, fiyatField, adetField;

    public SatisIslem() {
        super("Satış Uygulaması");

        JPanel panel = new JPanel();
        panel.setBackground(SystemColor.inactiveCaption);
        panel.setForeground(SystemColor.activeCaptionText);
        panel.setLayout(null);
        JPanel panel_1 = new JPanel();
        panel_1.setBounds(0, 0, 450, 40);
        panel_1.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int x = e.getXOnScreen();
                int y = e.getYOnScreen();
                setLocation(x - mouseX, y - mouseY);
            }
        });
        panel_1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });
        panel.setLayout(null);
        panel_1.setBackground(SystemColor.activeCaption);
        panel.add(panel_1);
        contentPane.setLayout(null);
        panel_1.setLayout(null);
        JLabel lbl_exit = new JLabel("X");
        lbl_exit.setForeground(new Color(0, 51, 204));
        lbl_exit.setVerticalAlignment(SwingConstants.BOTTOM);
        lbl_exit.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_exit.setFont(new Font("Yu Gothic", Font.BOLD, 17));
        lbl_exit.setBounds(332, 0, 85, 40);
        panel_1.add(lbl_exit);
        
        JLabel lblSatIlemi = new JLabel("Satış İşlemi");
        lblSatIlemi.setHorizontalAlignment(SwingConstants.LEFT);
        lblSatIlemi.setVerticalAlignment(SwingConstants.BOTTOM);
        lblSatIlemi.setForeground(new Color(0, 51, 204));
        lblSatIlemi.setFont(new Font("Yu Gothic", Font.BOLD, 20));
        lblSatIlemi.setBounds(10, 7, 142, 35);
        panel_1.add(lblSatIlemi);
        lbl_exit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (JOptionPane.showConfirmDialog(null, "Formu kapatmak istediğinizden emin misiniz?", "Uyarı",
                        JOptionPane.YES_NO_OPTION) == 0) {
                    dispose();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                lbl_exit.setForeground(Color.red);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                lbl_exit.setForeground(new Color(51, 153, 204));
            }
        });
        JButton backButton = new JButton("Geri");
        backButton.setForeground(new Color(0, 51, 204));
        backButton.setBounds(279, 257, 81, 25);
        panel.add(backButton);
        backButton.setFont(new Font("Yu Gothic", Font.BOLD, 15));

        backButton.addActionListener(e -> YetkiliGiris());

        JLabel urunAdiLabel = new JLabel("Ürün Adı:");
        urunAdiLabel.setForeground(new Color(0, 51, 204));
        urunAdiLabel.setFont(new Font("Yu Gothic", Font.BOLD, 15));
        urunAdiLabel.setBounds(10, 55, 80, 25);
        panel.add(urunAdiLabel);

        AdiField = new JTextField(20);
        AdiField.setBounds(179, 53, 165, 25);
        panel.add(AdiField);

        JLabel kategoriAdiLabel = new JLabel("Kategori Adı:");
        kategoriAdiLabel.setForeground(new Color(0, 51, 204));
        kategoriAdiLabel.setFont(new Font("Yu Gothic", Font.BOLD, 15));
        kategoriAdiLabel.setBounds(10, 107, 118, 25);
        panel.add(kategoriAdiLabel);

        kategoriAdiField = new JTextField(20);
        kategoriAdiField.setBounds(179, 105, 165, 25);
        panel.add(kategoriAdiField);

        JLabel fiyatLabel = new JLabel("Fiyat:");
        fiyatLabel.setForeground(new Color(0, 51, 204));
        fiyatLabel.setFont(new Font("Yu Gothic", Font.BOLD, 15));
        fiyatLabel.setBounds(10, 153, 80, 25);
        panel.add(fiyatLabel);

        fiyatField = new JTextField(20);
        fiyatField.setBounds(179, 151, 165, 25);
        panel.add(fiyatField);

        JLabel adetLabel = new JLabel("Adet:");
        adetLabel.setForeground(new Color(0, 51, 204));
        adetLabel.setVerticalAlignment(SwingConstants.TOP);
        adetLabel.setFont(new Font("Yu Gothic", Font.BOLD, 15));
        adetLabel.setBounds(10, 205, 80, 25);
        panel.add(adetLabel);

        adetField = new JTextField(20);
        adetField.setBounds(179, 203, 165, 25);
        panel.add(adetField);

        JButton satisButton = new JButton("Satış Yap");
        satisButton.setForeground(new Color(0, 51, 204));
        satisButton.setVerticalAlignment(SwingConstants.TOP);
        satisButton.setFont(new Font("Yu Gothic", Font.BOLD, 13));
        satisButton.setBounds(50, 255, 150, 25);
        satisButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	SatisIslem();
            }
        });
        panel.add(satisButton);
        getContentPane().add(panel);
        setSize(429, 385);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void YetkiliGiris() {
    	YetkiliGiris yetkiliGiris = new YetkiliGiris();
	    yetkiliGiris.setVisible(true);
	    dispose();
	}

	private void SatisIslem() {
    	    String Adi = AdiField.getText();
    	    String kategoriAdi = kategoriAdiField.getText();
    	    double fiyat = Double.parseDouble(fiyatField.getText());
    	    int adet = Integer.parseInt(adetField.getText());

    	    try {
    	        Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);

    	        String selectQuery = "SELECT * FROM urunler WHERE adi = ? AND kategoriAdi = ?";
    	        try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {
    	            selectStatement.setString(1, Adi);
    	            selectStatement.setString(2, kategoriAdi);

    	            try (ResultSet resultSet = selectStatement.executeQuery()) {
    	                if (resultSet.next()) {
    	                    double kdvOrani = 0.02;
    	                    double toplamFiyat = (fiyat * adet) * (1 + kdvOrani);

    	                    int id = resultSet.getInt("id");
    	                    System.out.println("Ürün ID: " + id);
    	                    System.out.println("Toplam Fiyat: " + toplamFiyat);

    	                    String satisQuery = "INSERT INTO satislar (id, adet, toplamFiyat) VALUES (?, ?, ?)";
    	                    try (PreparedStatement satisStatement = connection.prepareStatement(satisQuery)) {
    	                        satisStatement.setInt(1, id);
    	                        satisStatement.setInt(2, adet);
    	                        satisStatement.setDouble(3, toplamFiyat);

    	                        satisStatement.executeUpdate();

    	                        String updateQuery = "UPDATE urunler SET adet = adet - ? WHERE id = ?";
    	                        try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
    	                            updateStatement.setInt(1, adet);
    	                            updateStatement.setInt(2, id);

    	                            updateStatement.executeUpdate();
    	                        }

    	                        System.out.println("Satış başarıyla gerçekleştirildi.");
    	                    }
    	                } else {
    	                    System.out.println("Ürün bulunamadı.");
    	                }
    	            }
    	        }

    	        connection.close();
    	    } catch (SQLException ex) {
    	        ex.printStackTrace();
    	        System.out.println("Hata Mesajı: " + ex.getMessage());
    	    }
    	}

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
        	SatisIslem uygulama = new SatisIslem();
            uygulama.setVisible(true);
        });
    }
}