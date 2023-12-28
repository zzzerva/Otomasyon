package tr.com.zerva.Gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class YetkiIslemi extends JFrame {

    private DefaultTableModel model;
    private JTable table;
    private JPanel contentPane;
    private DefaultListModel<String> sonucListModel;
    private JList<String> sonucList;
    private JScrollPane sonucScrollPane;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                YetkiIslemi frame = new YetkiIslemi();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public YetkiIslemi() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 717, 492);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(135, 206, 250));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        model = new DefaultTableModel();
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(5, 5, 497, 383);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(SystemColor.activeCaption);
        buttonPanel.setBounds(5, 398, 497, 52);
        contentPane.setLayout(null);
        getContentPane().add(scrollPane);
        getContentPane().add(buttonPanel);

        buttonPanel.setLayout(null);

        JButton saveButton = new JButton("Değişiklikleri Kaydet");
        saveButton.setBounds(32, 10, 204, 33);
        buttonPanel.add(saveButton);
        saveButton.setFont(new Font("Yu Gothic", Font.BOLD, 15));

        JButton refreshButton = new JButton("Yenile");
        refreshButton.setBounds(295, 9, 137, 33);
        buttonPanel.add(refreshButton);
        refreshButton.addActionListener(e -> refreshTable());
        refreshButton.setVerticalAlignment(SwingConstants.TOP);
        refreshButton.setFont(new Font("Yu Gothic", Font.BOLD, 15));

        sonucListModel = new DefaultListModel<>();
        sonucList = new JList<>(sonucListModel);
        sonucScrollPane = new JScrollPane(sonucList);
        sonucScrollPane.setBounds(512, 113, 191, 81);
        buttonPanel.add(sonucScrollPane);

        refreshButton.addActionListener(e -> refreshTable());
        saveButton.addActionListener(e -> saveChanges());

        JPanel aramaPanel = new JPanel(new FlowLayout());

        JTextField aramaText = new JTextField(15);
        JButton araButton = new JButton("Ara");
        araButton.addActionListener(e -> aramaYap(aramaText.getText()));

        aramaPanel.add(new JLabel("Aranacak Kelime: "));
        aramaPanel.add(aramaText);
        aramaPanel.add(araButton);

        aramaPanel.setBounds(512, 103, 181, 101);
        contentPane.add(aramaPanel);
        
                JButton backButton = new JButton("Geri");
                backButton.setBounds(512, 312, 181, 33);
                contentPane.add(backButton);
                backButton.setFont(new Font("Yu Gothic", Font.BOLD, 15));
                backButton.addActionListener(e -> yetkiliGiris());
    }

    private void yetkiliGiris() {
    	YetkiliGiris yetkiliGiris = new YetkiliGiris();
	    yetkiliGiris.setVisible(true);
	    dispose();
    }

    private void refreshTable() {
        model.setRowCount(0);
        try (Connection connection = DriverManager.getConnection(Bagla.getJdbcUrl(), Bagla.getUsername(), Bagla.getPassword())) {
            String query = "SELECT * FROM accounts";
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

    private void saveChanges() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(Bagla.getJdbcUrl(), Bagla.getUsername(), Bagla.getPassword());
            connection.setAutoCommit(false);

            String updateQuery = "UPDATE accounts SET yetkiId=?, adiSoyadi=?, sifre=? WHERE id=?";

            try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                for (int row = 0; row < model.getRowCount(); row++) {
                    updateStatement.setObject(1, model.getValueAt(row, 1)); //yetkiId sütunu
                    updateStatement.setObject(2, model.getValueAt(row, 2)); // adiSoyadi sütunu
                    updateStatement.setObject(3, model.getValueAt(row, 3)); // sifre sütunu
                    updateStatement.setObject(4, model.getValueAt(row, 0)); // id sütunu

                    updateStatement.addBatch();
                }

                updateStatement.executeBatch();
                connection.commit();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException closeEx) {
                    closeEx.printStackTrace();
                }
            }
        }
        JOptionPane.showMessageDialog(this, "Değişiklik başarıyla kaydedildi.");
    }

    private void aramaYap(String arananKelime) {
        sonucListModel.clear();
        try (Connection connection = DriverManager.getConnection(Bagla.getJdbcUrl(), Bagla.getUsername(), Bagla.getPassword())) {
            String query = "SELECT * FROM accounts WHERE adiSoyadi LIKE ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, "%" + arananKelime + "%");

                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        String sonuc = resultSet.getString("adiSoyadi");
                        sonucListModel.addElement(sonuc);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sonucList.setModel(sonucListModel);
    }
}