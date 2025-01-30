package affichage;

import javax.swing.*;


import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import dao.FournisseurDAO;

public class FournisseurAffichage {
    private static JFrame frame;
    private static JPanel panel;
    private static JTable table;
    private static JButton deleteButton, modifyButton;
    private static DefaultTableModel tableModel;

 

    public  JPanel afficher() {
        // Panneau principal
        panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(null);
        panel.setBounds(800, 100, 980, 600);

        // Titre
        JLabel titleLabel = new JLabel("Liste des Fournisseurs");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 25));
        titleLabel.setBounds(300, 10, 400, 30);
        panel.add(titleLabel);

        // Tableau
        String[] columnNames = {"ID", "Nom", "Prénom", "Adresse", "Téléphone"};
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

        // Bouton Modifier avec icône
        modifyButton = new JButton(modifyIcon);
        modifyButton.setBounds(310, 500, 90, 50);
        modifyButton.setForeground(Color.WHITE);
        modifyButton.setFont(new Font("Arial", Font.BOLD, 15));
        modifyButton.setBackground(Color.WHITE);
        panel.add(modifyButton);

        // Bouton Supprimer avec icône
        deleteButton = new JButton(deleteIcon);
        deleteButton.setBounds(490, 500, 90, 50);
        deleteButton.setFont(new Font("Arial", Font.BOLD, 15));
        deleteButton.setBackground(Color.WHITE);
        deleteButton.setForeground(Color.WHITE);
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
                    String adresse = tableModel.getValueAt(selectedRow, 3).toString();
                    String telephone = tableModel.getValueAt(selectedRow, 4).toString();

                    modifierFournisseur(id, nom, prenom, adresse, telephone);
                } else {
                    JOptionPane.showMessageDialog(frame, "Veuillez sélectionner un fournisseur.", "Erreur", JOptionPane.ERROR_MESSAGE);
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
                    supprimerFournisseur(id);
                } else {
                    JOptionPane.showMessageDialog(frame, "Veuillez sélectionner un fournisseur.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        return panel;  // Retourne le panel pour utilisation externe
    }

    public static void chargerDonnees() {
        FournisseurDAO dao = new FournisseurDAO();
        tableModel.setRowCount(0); // Efface les anciennes données
        for (String[] fournisseur : dao.listerFournisseurs()) {
            tableModel.addRow(fournisseur);
        }
    }

    private static void modifierFournisseur(int id, String nom, String prenom, String adresse, String telephone) {
        JTextField nomField = new JTextField(nom);
        JTextField prenomField = new JTextField(prenom);
        JTextField adresseField = new JTextField(adresse);
        JTextField telephoneField = new JTextField(telephone);

        Object[] fields = {
                "Nom:", nomField,
                "Prénom:", prenomField,
                "Adresse:", adresseField,
                "Téléphone:", telephoneField
        };

        int option = JOptionPane.showConfirmDialog(frame, fields, "Modifier Fournisseur", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            FournisseurDAO dao = new FournisseurDAO();
            dao.modifierFournisseur(id, nomField.getText(), prenomField.getText(), adresseField.getText(), telephoneField.getText());
            JOptionPane.showMessageDialog(frame, "Fournisseur modifié avec succès.");
            chargerDonnees();
        }
    }

    private static void supprimerFournisseur(int id) {
        int confirm = JOptionPane.showConfirmDialog(frame, "Voulez-vous vraiment supprimer ce fournisseur ?", "Confirmation", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            FournisseurDAO dao = new FournisseurDAO();
            dao.supprimerFournisseur(id);
            JOptionPane.showMessageDialog(frame, "Fournisseur supprimé avec succès.");
            chargerDonnees();
        }
    }
}
