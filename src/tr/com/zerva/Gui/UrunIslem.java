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

    private DefaultListModel<String> urunListModel;
    private JList<String> urunList;
    private DefaultTableModel model;
    private JTable table;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UrunIslem().UrunListelemeFrame());
    }

    public UrunIslem() {
        setTitle("Ürün İşlemleri");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        urunListModel = new DefaultListModel<>();
        urunList = new JList<>(urunListModel);

        JScrollPane scrollPane = new JScrollPane(urunList);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1));

        JButton ekleButton = new JButton("Ürün Ekle");
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
        silButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	urun_delete sil = new urun_delete();
	            sil.setVisible(true);
	            dispose();
            }
        });

        JButton duzenleButton = new JButton("Ürün Düzenle");
        duzenleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	urun_duzenle duzenle = new urun_duzenle();
	            duzenle.setVisible(true);
	            dispose();
            }
        });

        buttonPanel.add(ekleButton);
        buttonPanel.add(silButton);
        buttonPanel.add(duzenleButton);

        getContentPane().add(buttonPanel, BorderLayout.EAST);
    }

    public void UrunListelemeFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Ürün Listesi");
        setSize(600, 400);
        setLocationRelativeTo(null);

        model = new DefaultTableModel();
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        getContentPane().add(scrollPane);

        JButton refreshButton = new JButton("Yenile");
        refreshButton.addActionListener(e -> refreshTable());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(refreshButton);

        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        refreshTable();

        JButton backButton = new JButton("Geri");
        backButton.addActionListener(e -> yetkiliGiris());

        JPanel buttonPanel1 = new JPanel(new FlowLayout());
        buttonPanel1.add(backButton);

        getContentPane().add(buttonPanel1, BorderLayout.NORTH);
        refreshTable();

        setVisible(true);
    }

    private void yetkiliGiris() {
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
}