package affichage;

import dao.Medicament;
import dao.MedicamentDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VenteMedicamentsInterface {
    private static MedicamentDAO medicamentDAO = new MedicamentDAO();
    private static List<Medicament> panier = new ArrayList<>();
    private static JLabel totalLabel;
    private static DefaultTableModel panierTableModel;

    public JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new GridLayout(1, 2));
        mainPanel.setBackground(Color.WHITE);

        // Panneau de gauche : Tableau des médicaments
        JPanel leftPanel = new JPanel(new BorderLayout());
        JLabel listLabel = new JLabel("Liste des Médicaments");
        listLabel.setFont(new Font("Arial", Font.BOLD, 16));
        leftPanel.add(listLabel, BorderLayout.NORTH);
        leftPanel.setBackground(Color.WHITE);

        String[] columnsMed = {"Nom", "Prix", "Stock", "Catégorie", "Expiration"};
        DefaultTableModel medTableModel = new DefaultTableModel(columnsMed, 0);
        JTable medTable = new JTable(medTableModel);
        JScrollPane medScrollPane = new JScrollPane(medTable);
        leftPanel.add(medScrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new FlowLayout());
        JTextField quantiteField = new JTextField(5);
        JButton ajouterButton = new JButton("Ajouter au panier");
        inputPanel.add(new JLabel("Quantité :"));
        inputPanel.add(quantiteField);
        inputPanel.add(ajouterButton);
        leftPanel.add(inputPanel, BorderLayout.SOUTH);

        mainPanel.add(leftPanel);

        // Charger les données des médicaments dans le tableau
        try {
            List<Medicament> medicaments = medicamentDAO.getAllMedicaments();
            for (Medicament med : medicaments) {
                medTableModel.addRow(new Object[]{
                        med.getNom(),
                        med.getPrix(),
                        med.getQuantiteEnStock(),
                        med.getCategorie(),
                        med.getDateExpiration()
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erreur lors du chargement des médicaments.");
        }

        // Panneau de droite : Tableau du panier
        JPanel rightPanel = new JPanel(new BorderLayout());
        JLabel panierLabel = new JLabel("Panier");
        panierLabel.setFont(new Font("Arial", Font.BOLD, 16));
        rightPanel.setBackground(Color.WHITE);
        rightPanel.add(panierLabel, BorderLayout.NORTH);

        String[] columnsPanier = {"Nom", "Prix Unitaire", "Quantité", "Total"};
        panierTableModel = new DefaultTableModel(columnsPanier, 0);
        JTable panierTable = new JTable(panierTableModel);
        panierTable.setBackground(Color.WHITE);

        JScrollPane panierScrollPane = new JScrollPane(panierTable);
        rightPanel.add(panierScrollPane, BorderLayout.CENTER);

        JPanel actionPanel = new JPanel(new FlowLayout());
        totalLabel = new JLabel("Total : 0.00 $");
        JButton vendreButton = new JButton("Vendre");
        actionPanel.add(totalLabel);
        actionPanel.add(vendreButton);
        rightPanel.add(actionPanel, BorderLayout.SOUTH);

        mainPanel.add(rightPanel);

        // Ajouter au panier
        ajouterButton.addActionListener(e -> {
            int selectedRow = medTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "Veuillez sélectionner un médicament.");
                return;
            }

            try {
                List<Medicament> medicaments = medicamentDAO.getAllMedicaments();
                Medicament selectedMed = medicaments.get(selectedRow);

                int quantite = Integer.parseInt(quantiteField.getText());
                if (quantite > selectedMed.getQuantiteEnStock()) {
                    JOptionPane.showMessageDialog(null, "Stock insuffisant !");
                    return;
                }

                // Mise à jour du stock
                selectedMed.setQuantiteEnStock(selectedMed.getQuantiteEnStock() - quantite);
                medicamentDAO.updateStock(selectedMed.getId(), selectedMed.getQuantiteEnStock());

                // Ajouter au panier
                double total = selectedMed.getPrix() * quantite;
                panier.add(new Medicament(selectedMed.getId(), selectedMed.getNom(), selectedMed.getCategorie(), selectedMed.getPrix(), quantite, selectedMed.getDateExpiration(), selectedMed.getDescription()));
                panierTableModel.addRow(new Object[]{selectedMed.getNom(), selectedMed.getPrix(), quantite, total});

                // Mettre à jour le total
                double totalPanier = panier.stream().mapToDouble(m -> m.getPrix() * m.getQuantiteEnStock()).sum();
                totalLabel.setText("Total : " + totalPanier + " $");

                // Rafraîchir le tableau des médicaments
                medTableModel.setValueAt(selectedMed.getQuantiteEnStock(), selectedRow, 2);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Erreur : " + ex.getMessage());
            }
        });

        // Vendre
        vendreButton.addActionListener(e -> {
            if (panier.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Le panier est vide !");
                return;
            }

            StringBuilder details = new StringBuilder("Détails de la vente :\n");
            double total = 0;

            // Iterate through the items in the cart
            for (Medicament med : panier) {
                double itemTotal = med.getPrix() * med.getQuantiteEnStock();
                details.append(med.getNom()).append(" - Quantité : ").append(med.getQuantiteEnStock()).append(" - Total : ").append(itemTotal).append(" $\n");
                total += itemTotal;
            }

            details.append("\nTotal général : ").append(total).append(" $");
            JOptionPane.showMessageDialog(null, details.toString(), "Vente confirmée", JOptionPane.INFORMATION_MESSAGE);

            // Call addVente for each item in the cart
            try {
                for (Medicament med : panier) {
                    medicamentDAO.addVente(med.getId(), med.getQuantiteEnStock(), med.getPrix());
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erreur lors de l'ajout de la vente : " + ex.getMessage());
            }

            // Clear the cart after the sale
            panier.clear();
            panierTableModel.setRowCount(0);
            totalLabel.setText("Total : 0.00 $");
        });

        return mainPanel;
    }
}
