package affichage;

import dao.MedicamentDAO;
import dao.Medicament;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.List;

public class AffichageStatistique {

    private static MedicamentDAO medicamentDAO = new MedicamentDAO();

    public JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        
        // Titre
        JLabel titleLabel = new JLabel("Statistiques des Ventes", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        
        // Tableau des statistiques
        String[] columns = {"Nom du Médicament", "Quantité Vendue", "Montant Total"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        JTable statsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(statsTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Charger les statistiques des ventes (initialement)
        loadSalesData(tableModel);

        // Rafraîchir les données lorsque nécessaire
        JButton refreshButton = new JButton("Rafraîchir");
        refreshButton.addActionListener(e -> loadSalesData(tableModel));
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(refreshButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        return mainPanel;
    }

    private void loadSalesData(DefaultTableModel tableModel) {
        // Vider le tableau avant de le recharger
        tableModel.setRowCount(0);

        try {
            List<Medicament> medicaments = medicamentDAO.getAllMedicaments();
            for (Medicament med : medicaments) {
                // Récupérer les statistiques de vente pour chaque médicament
                double totalVendu = 0;
                int totalQuantiteVendu = 0;
                String query = "SELECT SUM(quantite), SUM(quantite * prix_unitaire) FROM ventes_medicaments " +
                               "WHERE id_medicament = ?";
                
                try (Connection conn = DriverManager.getConnection(MedicamentDAO.DB_URL, MedicamentDAO.DB_USER, MedicamentDAO.DB_PASSWORD);
                     PreparedStatement pstmt = conn.prepareStatement(query)) {
                    pstmt.setInt(1, med.getId());
                    try (ResultSet rs = pstmt.executeQuery()) {
                        if (rs.next()) {
                            totalQuantiteVendu = rs.getInt(1);
                            totalVendu = rs.getDouble(2);
                        }
                    }
                }

                // Ajouter une ligne pour chaque médicament
                tableModel.addRow(new Object[]{
                        med.getNom(),
                        totalQuantiteVendu,
                        totalVendu
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur lors du chargement des statistiques des ventes.");
        }
    }
}
