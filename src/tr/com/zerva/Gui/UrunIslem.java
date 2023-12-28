package tr.com.zerva.Gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class UrunIslem extends JFrame {

    private DefaultListModel<String> urunListModel;
    private JList<String> urunList;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new UrunIslem().setVisible(true);
            }
        });
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
                if (urunAdi != null && !urunAdi.trim().isEmpty()) {
                    urunListModel.addElement(urunAdi);
                    // Burada MySQL tablosuna da ekleyebilirsiniz.
                    urunEkle(urunAdi);
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
                    // Burada MySQL tablosundan da silebilirsiniz.
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
                        // Burada MySQL tablosundaki kaydı da güncelleyebilirsiniz.
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

        // MySQL bağlantısı ve tablo verilerini al
        baglanVeTabloyuDoldur();
    }

    private void urunEkle(String urunAdi) {
        // MySQL tablosuna ekleme işlemleri burada gerçekleştirilecek.
        // Örneğin: INSERT INTO urunler (urun_adi) VALUES ('urunAdi');
    }

    private void urunSil(String urunAdi) {
        // MySQL tablosundan silme işlemleri burada gerçekleştirilecek.
        // Örneğin: DELETE FROM urunler WHERE urun_adi = 'urunAdi';
    }

    private void urunGuncelle(String eskiAd, String yeniAd) {
        // MySQL tablosunda güncelleme işlemleri burada gerçekleştirilecek.
        // Örneğin: UPDATE urunler SET urun_adi = 'yeniAd' WHERE urun_adi = 'eskiAd';
    }

    private void baglanVeTabloyuDoldur() {
        // MySQL bağlantısı ve tablo verilerini almak için burada işlemler yapılacak.
        try {
            // MySQL bağlantısı
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/otomasyon","root","root");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT urun_adi FROM urunler");

            // Tabloyu doldur
            while (resultSet.next()) {
                String urunAdi = resultSet.getString("urun_adi");
                urunListModel.addElement(urunAdi);
            }

            // Bağlantıyı kapat
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}