package affichage;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import dao.PharmacienDAO;

public class PharmacienAffichage {
    private static JPanel panel;
    private static JTable table;
    private static JButton deleteButton, modifyButton;
    private static DefaultTableModel tableModel;

    public JPanel afficher() {
        // Panneau principal
        panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(null);
        panel.setBounds(800, 100, 980, 600);

        // Titre
        JLabel titleLabel = new JLabel("Liste des Pharmaciens");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 25));
        titleLabel.setBounds(300, 10, 400, 30);
        panel.add(titleLabel);

        // Tableau
        String[] columnNames = {"ID", "Nom", "Prénom", "Email"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
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

        modifyButton = new JButton(modifyIcon);
        modifyButton.setBounds(300, 500, 90, 50);
        modifyButton.setBackground(Color.WHITE);
        panel.add(modifyButton);

        deleteButton = new JButton(deleteIcon);
        deleteButton.setBounds(500, 500, 90, 50);
        deleteButton.setBackground(Color.WHITE);
        panel.add(deleteButton);

        // Charger les données
        chargerDonnees();

        // Action pour le bouton "Modifier"
        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int id = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
                    String nom = tableModel.getValueAt(selectedRow, 1).toString();
                    String prenom = tableModel.getValueAt(selectedRow, 2).toString();
                    String email = tableModel.getValueAt(selectedRow, 3).toString();

                    modifierPharmacien(id, nom, prenom, email);
                } else {
                    JOptionPane.showMessageDialog(panel, "Veuillez sélectionner un pharmacien.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Action pour le bouton "Supprimer"
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    int id = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());
                    supprimerPharmacien(id);
                } else {
                    JOptionPane.showMessageDialog(panel, "Veuillez sélectionner un pharmacien.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        return panel; // Retourner le panneau principal
    }

    private void chargerDonnees() {
        PharmacienDAO dao = new PharmacienDAO();
        tableModel.setRowCount(0); // Efface les anciennes données
        for (String[] pharmacien : dao.listerPharmaciens()) {
            tableModel.addRow(pharmacien);
        }
    }

    private void modifierPharmacien(int id, String nom, String prenom, String email) {
        JTextField nomField = new JTextField(nom);
        JTextField prenomField = new JTextField(prenom);
        JTextField emailField = new JTextField(email);

        Object[] fields = {
                "Nom:", nomField,
                "Prénom:", prenomField,
                "Email:", emailField
        };

        int option = JOptionPane.showConfirmDialog(panel, fields, "Modifier Pharmacien", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            PharmacienDAO dao = new PharmacienDAO();
            dao.modifierPharmacien(id, nomField.getText(), prenomField.getText(), emailField.getText());
            JOptionPane.showMessageDialog(panel, "Pharmacien modifié avec succès.");
            chargerDonnees();
        }
    }

    private void supprimerPharmacien(int id) {
        int confirm = JOptionPane.showConfirmDialog(panel, "Voulez-vous vraiment supprimer ce pharmacien ?", "Confirmation", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            PharmacienDAO dao = new PharmacienDAO();
            dao.supprimerPharmacien(id);
            JOptionPane.showMessageDialog(panel, "Pharmacien supprimé avec succès.");
            chargerDonnees();
        }
    }
}
