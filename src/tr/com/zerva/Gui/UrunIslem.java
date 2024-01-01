package tr.com.zerva.Gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class UrunIslem extends JFrame {

	private JPanel contentPane;
    private DefaultListModel<String> urunListModel = new DefaultListModel<>();
    private JList<String> urunList = new JList<>(urunListModel);
    private DefaultTableModel model = new DefaultTableModel();
    private JTable table = new JTable(model);

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UrunIslem().UrunListelemeFrame());
    }

    public UrunIslem() {
    	setBackground(Color.LIGHT_GRAY);
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 727, 495);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.inactiveCaption);
		contentPane.setForeground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
        setTitle("Ürün İşlemleri");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(433, 300);
        setLocationRelativeTo(null);

        //urunListModel = new DefaultListModel<>();
        contentPane.setLayout(null);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 50, 212, 203);
        getContentPane().add(scrollPane);
        //urunList = new JList<>(urunListModel);
        scrollPane.setViewportView(urunList);

        JButton backButton = new JButton("Geri");
        backButton.setBackground(new Color(240, 240, 240));
        backButton.setBounds(10, 10, 88, 33);
        backButton.setForeground(new Color(0, 51, 204));
        contentPane.add(backButton);
        backButton.setFont(new Font("Yu Gothic", Font.BOLD, 15));
        backButton.addActionListener(e -> YetkiliGiris());
        
        JButton refreshButton = new JButton("Yenile");
        refreshButton.setVerticalAlignment(SwingConstants.BOTTOM);
        refreshButton.setFont(new Font("Yu Gothic", Font.BOLD, 13));
        refreshButton.setForeground(new Color(0, 51, 204));
        refreshButton.setBounds(124, 10, 88, 33);
        refreshButton.addActionListener(e -> refreshTable());
        contentPane.add(refreshButton);
        
        
        JButton ekleButton = new JButton("Ürün Ekle");
        ekleButton.setBackground(new Color(240, 240, 240));
        
        Image img = new ImageIcon(YetkiliGiris.class.getResource("/5.jpg")).getImage();
		Image scaledImg = img.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
		ekleButton.setIcon(new ImageIcon(scaledImg));
		ekleButton.setFont(new Font("Yu Gothic", Font.BOLD, 10));
		ekleButton.setBounds(240, 101, 154, 61);
		contentPane.add(ekleButton);
		
        ekleButton.setBounds(222, 109, 164, 61);
        ekleButton.setForeground(new Color(0, 51, 204));
        ekleButton.setFont(new Font("Yu Gothic", Font.BOLD, 15));
        ekleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String adi = JOptionPane.showInputDialog(UrunIslem.this, "Ürün Adı:");
                String kategoriAdi = JOptionPane.showInputDialog(UrunIslem.this, "Kategori Adı:");
                String fiyatStr = JOptionPane.showInputDialog(UrunIslem.this, "Fiyat:");

                if (adi != null && !adi.trim().isEmpty() &&
                        kategoriAdi != null && !kategoriAdi.trim().isEmpty() &&
                        fiyatStr != null && !fiyatStr.trim().isEmpty()) {
                    try {
                        float fiyat = Float.parseFloat(fiyatStr);

                        urunListModel.addElement("Ürün Adı: " + adi + ", Kategori Adı: " + kategoriAdi + ", Fiyat: " + fiyat);
                        urunEkle(adi, kategoriAdi, fiyat);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(UrunIslem.this, "Geçerli bir fiyat giriniz.", "Hata", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        JButton silButton = new JButton("Ürün Sil");
        silButton.setBackground(new Color(240, 240, 240));
        
        Image img1 = new ImageIcon(YetkiliGiris.class.getResource("/3.jpg")).getImage();
		Image scaledImg1 = img1.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
		silButton.setIcon(new ImageIcon(scaledImg1));
		silButton.setFont(new Font("Yu Gothic", Font.BOLD, 12));
		silButton.setBounds(245, 180, 154, 61);
		contentPane.add(silButton);
		
        silButton.setBounds(222, 192, 164, 61);
        silButton.setForeground(new Color(0, 51, 204));
        silButton.setFont(new Font("Yu Gothic", Font.BOLD, 15));
        silButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	urun_delete sil = new urun_delete();
	            sil.setVisible(true);
	            dispose();
            }
        });

        JButton duzenleButton = new JButton("Ürün Düzenle");
        duzenleButton.setBackground(new Color(240, 240, 240));
        
        Image img2 = new ImageIcon(YetkiliGiris.class.getResource("/4.jpg")).getImage();
		Image scaledImg2 = img2.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
		duzenleButton.setIcon(new ImageIcon(scaledImg2));
		duzenleButton.setFont(new Font("Yu Gothic", Font.BOLD, 11));
		duzenleButton.setBounds(245, 30, 176, 61);
		contentPane.add(duzenleButton);
		
        duzenleButton.setBounds(222, 30, 164, 61);
        duzenleButton.setForeground(new Color(0, 51, 204));
        duzenleButton.setFont(new Font("Yu Gothic", Font.BOLD, 14));
        duzenleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	urun_duzenle duzenle = new urun_duzenle();
	            duzenle.setVisible(true);
	            dispose();
            }
        });

        contentPane.add(ekleButton);
        contentPane.add(silButton);
        contentPane.add(duzenleButton);
        
    }

    
    public void UrunListelemeFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Ürün Listesi");
        setSize(600, 400);
        setLocationRelativeTo(null);

       // model = new DefaultTableModel();
      //  table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        getContentPane().add(scrollPane);

        refreshTable();
        setVisible(true);
    }

	private void YetkiliGiris() {
		YetkiliGiris yetkiliGiris = new YetkiliGiris();
	    yetkiliGiris.setVisible(true);
	    dispose();
	}

	private void refreshTable() {
        model.setRowCount(0);

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/otomasyon","root","root")) {
            String query = "SELECT * FROM urunler";
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {

                int columnCount = resultSet.getMetaData().getColumnCount();
                Vector<String> columns = new Vector<>();
                for (int i = 1; i <= columnCount; i++) {
                    columns.add(resultSet.getMetaData().getColumnName(i));
                }
                model.setColumnIdentifiers(columns);

                while (resultSet.next()) {
                    Vector<Object> row = new Vector<>();
                    for (int i = 1; i <= columnCount; i++) {
                        row.add(resultSet.getObject(i));
                    }
                    model.addRow(row);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void urunEkle(String urunAdi, String kategoriAdi, float fiyat) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/otomasyon","root","root")) {
            String query = "INSERT INTO urunler (adi, kategoriAdi, fiyat) VALUES (?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, urunAdi);
                statement.setString(2, kategoriAdi);
                statement.setFloat(3, fiyat);
                statement.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    private void loadMembers() {
		model.setRowCount(0);

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/otomasyon","root","root")) {
            String query = "SELECT * FROM urunler";
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {
                int columnCount = resultSet.getMetaData().getColumnCount();
                for (int i = 1; i <= columnCount; i++) {
                    model.addColumn(resultSet.getMetaData().getColumnName(i));
                }

                while (resultSet.next()) {
                    Vector<Object> row = new Vector<>();
                    for (int i = 1; i <= columnCount; i++) {
                        row.add(resultSet.getObject(i));
                    }
                    model.addRow(row);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
	}
}