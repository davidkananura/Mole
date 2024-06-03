package LibrarySystem;

import java.awt.EventQueue;
import javax.swing.*;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class Dashboard {

    private JFrame frmVuLibrarySystem;
    private JTextField yearTextField;
    private JTextField authorTextField;
    private JTextField titleTextField;
    private JTextField idTextField;
    private JTable table;
    private Connection conn;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Dashboard window = new Dashboard();
                    window.frmVuLibrarySystem.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public Dashboard() {
        initialize();
        connectToDatabase();
        refreshTable();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frmVuLibrarySystem = new JFrame();
        frmVuLibrarySystem.setTitle("VU LIBRARY SYSTEM");
        frmVuLibrarySystem.setResizable(false);
        frmVuLibrarySystem.getContentPane().setBackground(new Color(255, 255, 255));
        frmVuLibrarySystem.setBounds(100, 100, 1002, 602);
        frmVuLibrarySystem.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmVuLibrarySystem.getContentPane().setLayout(null);
        frmVuLibrarySystem.setLocationRelativeTo(frmVuLibrarySystem);
        JPanel panel = new JPanel();
        panel.setBounds(10, 84, 968, 82);
        frmVuLibrarySystem.getContentPane().add(panel);
        panel.setLayout(new GridLayout(0, 5, 0, 0));

        idTextField = new JTextField();
        idTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    titleTextField.requestFocus();
                }
            }
        });
        idTextField.setFont(new Font("Tahoma", Font.PLAIN, 30));
        idTextField.setHorizontalAlignment(SwingConstants.CENTER);
        idTextField.setToolTipText("ENTER BOOK ID");
        panel.add(idTextField);
        idTextField.setColumns(10);

        titleTextField = new JTextField();
        titleTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    authorTextField.requestFocus();
                }
            }
        });
        titleTextField.setToolTipText("ENTER BOOK TITLE");
        titleTextField.setFont(new Font("Tahoma", Font.PLAIN, 30));
        panel.add(titleTextField);
        titleTextField.setColumns(10);

        authorTextField = new JTextField();
        authorTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    yearTextField.requestFocus();
                }
            }
        });
        authorTextField.setToolTipText("ENTER BOOK AUTHOR");
        authorTextField.setFont(new Font("Tahoma", Font.PLAIN, 30));
        panel.add(authorTextField);
        authorTextField.setColumns(10);

        yearTextField = new JTextField();
        yearTextField.setToolTipText("ENTER YEAR OF PUBLICATION");
        yearTextField.setFont(new Font("Tahoma", Font.PLAIN, 30));
        panel.add(yearTextField);
        yearTextField.setColumns(10);

        JButton addButton = new JButton("ADD BOOK");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addNewBook();
            }
        });
        addButton.setBackground(new Color(51, 153, 51));
        addButton.setForeground(new Color(255, 255, 255));
        addButton.setFont(new Font("Tahoma", Font.PLAIN, 30));
        panel.add(addButton);

        JPanel panel_1 = new JPanel();
        panel_1.setBackground(new Color(255, 255, 255));
        panel_1.setBounds(10, 177, 968, 82);
        frmVuLibrarySystem.getContentPane().add(panel_1);
        panel_1.setLayout(new GridLayout(0, 4, 0, 0));

        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setBackground(new Color(255, 255, 255));
        panel_1.add(lblNewLabel);

        JLabel label = new JLabel("");
        label.setBackground(new Color(255, 255, 255));
        panel_1.add(label);

        JButton refreshButton = new JButton("REFRESH ");
        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                refreshTable();
            }
        });
        refreshButton.setBackground(new Color(0, 102, 255));
        refreshButton.setForeground(new Color(255, 255, 255));
        refreshButton.setFont(new Font("Tahoma", Font.PLAIN, 30));
        panel_1.add(refreshButton);

        JButton deleteButton = new JButton("DELETE");
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteSelectedRecord();
            }
        });
        deleteButton.setBackground(new Color(204, 0, 0));
        deleteButton.setForeground(new Color(255, 255, 255));
        deleteButton.setFont(new Font("Tahoma", Font.PLAIN, 30));
        panel_1.add(deleteButton);

        JPanel panel_2 = new JPanel();
        panel_2.setBounds(10, 270, 968, 284);
        frmVuLibrarySystem.getContentPane().add(panel_2);
        panel_2.setLayout(null);

        table = new JTable();
        table.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] {"BOOK ID", "BOOK TITLE", "BOOK AUTHOR", "YEAR"}
        ));
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 11, 948, 262);
        panel_2.add(scrollPane);

        JPanel panel_3 = new JPanel();
        panel_3.setBounds(10, 11, 968, 66);
        frmVuLibrarySystem.getContentPane().add(panel_3);
        panel_3.setLayout(new GridLayout(0, 1, 0, 0));

        JLabel lblNewLabel_1 = new JLabel("VICTORIA UNIVERSITY LIBRARY SYSTEM");
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1.setForeground(new Color(0, 51, 255));
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 40));
        panel_3.add(lblNewLabel_1);
    }

    private void connectToDatabase() {
        try {
            String url = "jdbc:ucanaccess://C://Users/ssebb/eclipse-workspace/LibraryManagementSystem/LibraryDB.accdb";
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addNewBook() {
        try {
            String sql = "INSERT INTO Books (ID, Title, Author, Year) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, Integer.parseInt(idTextField.getText()));
            pstmt.setString(2, titleTextField.getText());
            pstmt.setString(3, authorTextField.getText());
            pstmt.setInt(4, Integer.parseInt(yearTextField.getText()));
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(frmVuLibrarySystem, "Book added successfully!");
            refreshTable();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frmVuLibrarySystem, "Failed to add book.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refreshTable() {
        try {
            String sql = "SELECT * FROM Books";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);
            while (rs.next()) {
                model.addRow(new Object[] {
                    rs.getInt("ID"),
                    rs.getString("Title"),
                    rs.getString("Author"),
                    rs.getInt("Year")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteSelectedRecord() {
        int row = table.getSelectedRow();
        if (row != -1) {
            int bookID = (int) table.getValueAt(row, 0);
            int confirm = JOptionPane.showConfirmDialog(frmVuLibrarySystem, "Are you sure you want to delete this record?", "Delete Confirmation", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    String sql = "DELETE FROM Books WHERE ID = ?";
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    pstmt.setInt(1, bookID);
                    pstmt.executeUpdate();
                    JOptionPane.showMessageDialog(frmVuLibrarySystem, "Record deleted successfully!");
                    refreshTable();
                } catch (SQLException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(frmVuLibrarySystem, "Failed to delete record.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(frmVuLibrarySystem, "Please select a record to delete.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }
}
