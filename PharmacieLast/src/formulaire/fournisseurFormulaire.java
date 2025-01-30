package formulaire;

import javax.swing.*;

import affichage.FournisseurAffichage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import dao.FournisseurDAO;

public class fournisseurFormulaire {

    // Déclaration des variables globales pour les composants
    private static JFrame frame;
    private static JPanel panel;
    private static JTextField entrepriseField, adresseField, telephoneField, emailField;
    private static JButton ajouterFournisseurButton;

//    public static void main(String[] args) {
//        // Initialiser la fenêtre et les composants
//        // Juste pour afficher la fenêtre, vous pouvez appeler cette fonction sans avoir à afficher le JPanel ici.
//        demarrer();
//    }

    public  JPanel demarrer() {
        // Créer le panneau principal
        panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(null);
        panel.setBounds(380, 200, 750, 400);  // Positionner le panneau dans la fenêtre principale

        // Titre
        JLabel titleLabel = new JLabel("Ajout d'un Fournisseur");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 25));
        titleLabel.setBounds(390, 30, 300, 25);
        panel.add(titleLabel);

        // Image
        ImageIcon imageIcon = new ImageIcon("sassss/fournisseur3.jpg");
        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setBounds(20, 90, 300, imageIcon.getIconHeight());
        panel.add(imageLabel);

        // Champ Nom de l'entreprise
        JLabel entrepriseLabel = new JLabel("Nom");
        entrepriseLabel.setBounds(350, 85, 200, 25);
        entrepriseField = new JTextField(20);
        entrepriseField.setBounds(500, 85, 250, 30);
        panel.add(entrepriseLabel);
        panel.add(entrepriseField);

        // Champ Adresse
        JLabel adresseLabel = new JLabel("Adresse:");
        adresseLabel.setBounds(350, 125, 140, 25);
        adresseField = new JTextField(20);
        adresseField.setBounds(500, 125, 250, 30);
        panel.add(adresseLabel);
        panel.add(adresseField);

        // Champ Numéro de téléphone
        JLabel telephoneLabel = new JLabel("Téléphone:");
        telephoneLabel.setBounds(350, 165, 140, 25);
        telephoneField = new JTextField(20);
        telephoneField.setBounds(500, 165, 250, 30);
        panel.add(telephoneLabel);
        panel.add(telephoneField);

        // Champ Email
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(350, 205, 140, 25);
        emailField = new JTextField(20);
        emailField.setBounds(500, 205, 250, 30);
        panel.add(emailLabel);
        panel.add(emailField);

        // Bouton Ajouter Fournisseur
        ajouterFournisseurButton = new JButton("Ajouter le Fournisseur");
        ajouterFournisseurButton.setBounds(500, 245, 250, 35);
        ajouterFournisseurButton.setForeground(Color.WHITE);
        ajouterFournisseurButton.setFont(new Font("Arial", Font.BOLD, 15));
        ajouterFournisseurButton.setBackground(new Color(0, 200, 0));
        panel.add(ajouterFournisseurButton);

        // Ajouter l'action du bouton
        ajouterFournisseurButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String entreprise = entrepriseField.getText();
                String adresse = adresseField.getText();
                String telephone = telephoneField.getText();
                String email = emailField.getText();

                // Validation basique
                if (entreprise.isEmpty() || adresse.isEmpty() || telephone.isEmpty() || email.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Tous les champs doivent être remplis.", "Erreur", JOptionPane.ERROR_MESSAGE);
                } else {
                    FournisseurDAO fournisseurDAO = new FournisseurDAO();
                    fournisseurDAO.ajouterFournisseur(entreprise, "sass", adresse, telephone, email);
                    JOptionPane.showMessageDialog(frame, "Fournisseur ajouté avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
                    
//                    FournisseurAffichage p = new FournisseurAffichage();
                    FournisseurAffichage.chargerDonnees();
                    
                    
                }
            }
        });

        // Retourner le panneau
        return panel;
    }
}
