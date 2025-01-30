package formulaire;

import javax.swing.*;

import affichage.MedicamentAffichage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import dao.MedicamentDAO;

public class MedicamentFormulaire {

    public JPanel demarrer() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(null);
        panel.setBounds(380, 200, 750, 400);

        JLabel titleLabel = new JLabel("Ajout d'un Médicament");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 25));
        titleLabel.setBounds(390, 30, 300, 25);
        panel.add(titleLabel);

        JLabel nomLabel = new JLabel("Nom:");
        nomLabel.setBounds(350, 85, 140, 25);
        JTextField nomField = new JTextField(20);
        nomField.setBounds(500, 85, 250, 30);
        panel.add(nomLabel);
        panel.add(nomField);

        JLabel categorieLabel = new JLabel("Catégorie:");
        categorieLabel.setBounds(350, 125, 140, 25);
        JTextField categorieField = new JTextField(20);
        categorieField.setBounds(500, 125, 250, 30);
        panel.add(categorieLabel);
        panel.add(categorieField);

        JLabel prixLabel = new JLabel("Prix:");
        prixLabel.setBounds(350, 165, 140, 25);
        JTextField prixField = new JTextField(20);
        prixField.setBounds(500, 165, 250, 30);
        panel.add(prixLabel);
        panel.add(prixField);

        JLabel quantiteLabel = new JLabel("Quantité en stock:");
        quantiteLabel.setBounds(350, 205, 140, 25);
        JTextField quantiteField = new JTextField(20);
        quantiteField.setBounds(500, 205, 250, 30);
        panel.add(quantiteLabel);
        panel.add(quantiteField);

        JLabel dateExpirationLabel = new JLabel("Date d'expiration:");
        dateExpirationLabel.setBounds(350, 245, 140, 25);
        JTextField dateExpirationField = new JTextField(20);
        dateExpirationField.setBounds(500, 245, 250, 30);
        panel.add(dateExpirationLabel);
        panel.add(dateExpirationField);

        JLabel descriptionLabel = new JLabel("Description:");
        descriptionLabel.setBounds(350, 285, 140, 25);
        JTextField descriptionField = new JTextField(20);
        descriptionField.setBounds(500, 285, 250, 30);
        panel.add(descriptionLabel);
        panel.add(descriptionField);

        JButton ajouterMedicamentButton = new JButton("Ajouter le Médicament");
        ajouterMedicamentButton.setBounds(500, 325, 250, 35);
        ajouterMedicamentButton.setForeground(Color.WHITE);
        ajouterMedicamentButton.setFont(new Font("Arial", Font.BOLD, 15));
        ajouterMedicamentButton.setBackground(new Color(0, 200, 0));
        panel.add(ajouterMedicamentButton);

        ajouterMedicamentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nom = nomField.getText();
                String categorie = categorieField.getText();
                String prix = prixField.getText();
                String quantite = quantiteField.getText();
                String dateExpiration = dateExpirationField.getText();
                String description = descriptionField.getText();

                if (nom.isEmpty() || categorie.isEmpty() || prix.isEmpty() || quantite.isEmpty() || dateExpiration.isEmpty() || description.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Tous les champs doivent être remplis.", "Erreur", JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        double prixDouble = Double.parseDouble(prix);
                        int quantiteInt = Integer.parseInt(quantite);

                        MedicamentDAO medicamentDAO = new MedicamentDAO();
                        boolean success = medicamentDAO.ajouterMedicament(nom, categorie, prixDouble, quantiteInt, dateExpiration, description);

                        if (success) {
                            JOptionPane.showMessageDialog(null, "Médicament ajouté avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
                            //MedicamentAffichage.chargerDonnees();
                        } else {
                            JOptionPane.showMessageDialog(null, "Erreur lors de l'ajout du médicament.", "Erreur", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Veuillez entrer un prix et une quantité valides.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        return panel;
    }
}
