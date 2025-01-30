package affichage;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.sql.SQLException;
import java.util.List;

import dao.*;

public class MedicamentAffichage {

    private static DefaultTableModel tableModel;

	public JPanel afficher() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(null);

        JLabel titleLabel = new JLabel("Liste des Médicaments", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBounds(300, 10, 400, 30);
        panel.add(titleLabel);

        MedicamentDAO medicamentDAO = new MedicamentDAO();
        List<Medicament> medicaments;
        try {
            medicaments = medicamentDAO.getAllMedicaments();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erreur lors du chargement des médicaments : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            return panel;
        }

        String[] columnNames = {"ID", "Nom", "Catégorie", "Prix", "Quantité en stock", "Date d'expiration", "Description"};
        String[][] data = new String[medicaments.size()][columnNames.length];
        for (int i = 0; i < medicaments.size(); i++) {
            Medicament med = medicaments.get(i);
            data[i] = new String[]{
                    String.valueOf(med.getId()),
                    med.getNom(),
                    med.getCategorie(),
                    String.valueOf(med.getPrix()),
                    String.valueOf(med.getQuantiteEnStock()),
                    med.getDateExpiration().toString(),
                    med.getDescription()
            };
        }

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 60, 880, 400);
        panel.add(scrollPane);

        // Boutons
        ImageIcon modifyIcon = new ImageIcon("sassss/iconeModification.jpg");
        Image modifyImage = modifyIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        modifyIcon = new ImageIcon(modifyImage);

        ImageIcon deleteIcon = new ImageIcon("sassss/suppresion.png");
        Image deleteImage = deleteIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        deleteIcon = new ImageIcon(deleteImage);

        JButton modifyButton = new JButton(modifyIcon);
        modifyButton.setBounds(300, 500, 90, 50);
        modifyButton.setBackground(Color.WHITE);
        panel.add(modifyButton);

        JButton deleteButton = new JButton(deleteIcon);
        deleteButton.setBounds(500, 500, 90, 50);
        deleteButton.setBackground(Color.WHITE);
        panel.add(deleteButton);

        // Actions pour les boutons
        modifyButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String id = (String) table.getValueAt(selectedRow, 0);
                String nom = (String) table.getValueAt(selectedRow, 1);
                String categorie = (String) table.getValueAt(selectedRow, 2);
                String prix = (String) table.getValueAt(selectedRow, 3);
                String quantite = (String) table.getValueAt(selectedRow, 4);
                String expiration = (String) table.getValueAt(selectedRow, 5);
                String description = (String) table.getValueAt(selectedRow, 6);

                modifierMedicament(id, nom, categorie, prix, quantite, expiration, description, table);
            } else {
                JOptionPane.showMessageDialog(panel, "Veuillez sélectionner un médicament.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String id = (String) table.getValueAt(selectedRow, 0);
                supprimerMedicament(id, table);
            } else {
                JOptionPane.showMessageDialog(panel, "Veuillez sélectionner un médicament.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        return panel;
    }

    private void modifierMedicament(String id, String nom, String categorie, String prix, String quantite, String expiration, String description, JTable table) {
        JTextField nomField = new JTextField(nom);
        JTextField categorieField = new JTextField(categorie);
        JTextField prixField = new JTextField(prix);
        JTextField quantiteField = new JTextField(quantite);
        JTextField expirationField = new JTextField(expiration);
        JTextField descriptionField = new JTextField(description);

        Object[] fields = {
                "Nom:", nomField,
                "Catégorie:", categorieField,
                "Prix:", prixField,
                "Quantité:", quantiteField,
                "Date d'expiration:", expirationField,
                "Description:", descriptionField
        };

        int option = JOptionPane.showConfirmDialog(null, fields, "Modifier Médicament", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            MedicamentDAO dao = new MedicamentDAO();
            try {
                dao.modifierMedicament(
                        Integer.parseInt(id),
                        nomField.getText(),
                        categorieField.getText(),
                        Double.parseDouble(prixField.getText()),
                        Integer.parseInt(quantiteField.getText()),
                        expirationField.getText(),
                        descriptionField.getText()
                );
                JOptionPane.showMessageDialog(null, "Médicament modifié avec succès.");
                reloadTable(table); // Recharge le tableau
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erreur lors de la modification : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void supprimerMedicament(String id, JTable table) {
        int confirm = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment supprimer ce médicament ?", "Confirmation", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            MedicamentDAO dao = new MedicamentDAO();
            try {
                dao.supprimerMedicament(Integer.parseInt(id));
                JOptionPane.showMessageDialog(null, "Médicament supprimé avec succès.");
                reloadTable(table); // Recharge le tableau
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erreur lors de la suppression : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void reloadTable(JTable table) {
        MedicamentDAO medicamentDAO = new MedicamentDAO();
        List<Medicament> medicaments;
        try {
            medicaments = medicamentDAO.getAllMedicaments();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erreur lors du rechargement des médicaments : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] columnNames = {"ID", "Nom", "Catégorie", "Prix", "Quantité en stock", "Date d'expiration", "Description"};
        String[][] data = new String[medicaments.size()][columnNames.length];
        for (int i = 0; i < medicaments.size(); i++) {
            Medicament med = medicaments.get(i);
            data[i] = new String[]{
                    String.valueOf(med.getId()),
                    med.getNom(),
                    med.getCategorie(),
                    String.valueOf(med.getPrix()),
                    String.valueOf(med.getQuantiteEnStock()),
                    med.getDateExpiration().toString(),
                    med.getDescription()
            };
        }

        table.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
    }

    public static void chargerDonnees() {
        MedicamentDAO dao = new MedicamentDAO();
        tableModel.setRowCount(0); // Efface les anciennes données
        try {
            for (Medicament medicament : dao.getAllMedicaments()) {
                Object[] row = {
                    medicament.getId(),
                    medicament.getNom(),
                    medicament.getCategorie(),
                    medicament.getPrix(),
                    medicament.getQuantiteEnStock(),
                    medicament.getDateExpiration(),
                    medicament.getDescription()
                };
                tableModel.addRow(row); // Ajoute la ligne au modèle de table
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erreur lors du chargement des médicaments : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    
//    public void chargeDonnee() {
//        MedicamentDAO medicamentDAO = new MedicamentDAO();
//        List<Medicament> medicaments;
//        // Supposons que la table est une variable d'instance ou globalement accessible
//        JTable table = this.table; // Ici, 'this.table' fait référence à la table actuelle
//        DefaultTableModel tableModel = (DefaultTableModel) table.getModel(); // Obtenir le modèle de table
//
//        try {
//            medicaments = medicamentDAO.getAllMedicaments(); // Récupérer la liste des médicaments depuis la base de données
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(null, "Erreur lors du rechargement des médicaments : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//
//        // Effacer les anciennes lignes de la table
//        tableModel.setRowCount(0);
//
//        // Ajouter les nouveaux médicaments dans la table
//        for (Medicament medicament : medicaments) {
//            // Ajouter chaque médicament dans la table sous forme de ligne
//            Object[] rowData = {
//                medicament.getId(),
//                medicament.getNom(),
//                medicament.getCategorie(),
//                medicament.getPrix(),
//                medicament.getQuantiteEnStock(),
//                medicament.getDateExpiration(),
//                medicament.getDescription()
//            };
//            tableModel.addRow(rowData); // Ajouter la ligne au modèle de table
//        }
//    }

}
