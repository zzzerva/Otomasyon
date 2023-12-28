package tr.com.zerva.Gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import java.awt.SystemColor;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.awt.Font;
import javax.swing.SwingConstants;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ScrollPaneConstants;
import java.awt.Color;

public class YetkiIslemi extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
    private DefaultTableModel model;
    
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					YetkiIslemi frame = new YetkiIslemi();
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
                        refreshButton.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {
								// TODO Auto-generated method stub
								
							}
                        });
                        refreshButton.setVerticalAlignment(SwingConstants.TOP);
                        refreshButton.setFont(new Font("Yu Gothic", Font.BOLD, 15));
                        
                                refreshButton.addActionListener(e -> refreshTable());
                saveButton.addActionListener(e -> saveChanges());

        refreshTable();
		
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
	}