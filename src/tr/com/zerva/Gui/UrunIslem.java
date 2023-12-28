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
                String urunAdi = JOptionPane.showInputDialog(UrunIslem.this, "Ürün Adı:");
                String kategoriAdi = JOptionPane.showInputDialog(UrunIslem.this, "Kategori Adı:");
                String fiyatStr = JOptionPane.showInputDialog(UrunIslem.this, "Fiyat:");

                if (urunAdi != null && !urunAdi.trim().isEmpty() &&
                        kategoriAdi != null && !kategoriAdi.trim().isEmpty() &&
                        fiyatStr != null && !fiyatStr.trim().isEmpty()) {
                    try {
                        float fiyat = Float.parseFloat(fiyatStr);

                        urunListModel.addElement("Ürün Adı: " + urunAdi + ", Kategori Adı: " + kategoriAdi + ", Fiyat: " + fiyat);
                        urunEkle(urunAdi, kategoriAdi, fiyat);
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
                int selectedIndex = urunList.getSelectedIndex();
                if (selectedIndex != -1) {
                    String secilenUrun = urunListModel.getElementAt(selectedIndex);
                    urunListModel.remove(selectedIndex);
                    urunSil(secilenUrun);
                } else {
                    JOptionPane.showMessageDialog(UrunIslem.this, "Lütfen bir ürün seçin.");
                }
            }
        });

        JButton duzenleButton = new JButton("Ürün Düzenle");
        duzenleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = urunList.getSelectedIndex();
                if (selectedIndex != -1) {
                    String secilenUrun = urunListModel.getElementAt(selectedIndex);
                    String yeniAd = JOptionPane.showInputDialog(UrunIslem.this, "Yeni Ürün Adı:", secilenUrun);
                    if (yeniAd != null && !yeniAd.trim().isEmpty()) {
                        urunListModel.setElementAt(yeniAd, selectedIndex);
                        urunGuncelle(secilenUrun, yeniAd);
                    }
                } else {
                    JOptionPane.showMessageDialog(UrunIslem.this, "Lütfen bir ürün seçin.");
                }
            }
        });

        buttonPanel.add(ekleButton);
        buttonPanel.add(silButton);
        buttonPanel.add(duzenleButton);

        getContentPane().add(buttonPanel, BorderLayout.EAST);
        baglanVeTabloyuDoldur();
    }

    public void UrunListelemeFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Ürün Listesi");
        setSize(600, 400);
        setLocationRelativeTo(null);

        model = new DefaultTableModel();
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane);

        JButton refreshButton = new JButton("Yenile");
        refreshButton.addActionListener(e -> refreshTable());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(refreshButton);

        add(buttonPanel, BorderLayout.SOUTH);

        refreshTable();

        setVisible(true);
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

    private void urunSil(String urunAdi) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/otomasyon","root","root")) {
            String query = "DELETE FROM urunler WHERE adi = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, urunAdi);
                statement.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void urunGuncelle(String eskiAd, String yeniAd) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/otomasyon","root","root")) {
            String query = "UPDATE urunler SET adi = ? WHERE adi = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, yeniAd);
                statement.setString(2, eskiAd);
                statement.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void baglanVeTabloyuDoldur() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/otomasyon","root","root");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT adi FROM urunler");

            while (resultSet.next()) {
                String urunAdi = resultSet.getString("adi");
                urunListModel.addElement(urunAdi);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}