package formulaire;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import dao.PharmacienDAO;

public class PharmacienFormulaire {

    // Déclaration des variables globales pour les composants
    private JTextField nameField, prenomField, emailField;
    private JPasswordField passwordField, confirmepasswordField;
    private JButton createAccountButton;

    public JPanel creerPanelFormulaire() {
        // Panneau principal
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(null);
        panel.setBounds(380, 200, 750, 400); // Positionner le panneau

        JLabel titleLabel = new JLabel("Création De Compte");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 25));
        titleLabel.setBounds(390, 30, 300, 25);
        panel.add(titleLabel);

        ImageIcon imageIcon = new ImageIcon("sassss/pharmacie1.png");
        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setBounds(20, 90, 300, imageIcon.getIconHeight());
        panel.add(imageLabel);

        // Champ Nom
        JLabel nameLabel = new JLabel("Nom:");
        nameLabel.setBounds(350, 85, 200, 25);
        nameField = new JTextField(20);
        nameField.setBounds(500, 85, 250, 30);
        panel.add(nameLabel);
        panel.add(nameField);

        // Champ Prénom
        JLabel prenomLabel = new JLabel("Prénom:");
        prenomLabel.setBounds(350, 125, 140, 25);
        prenomField = new JTextField(20);
        prenomField.setBounds(500, 125, 250, 30);
        panel.add(prenomLabel);
        panel.add(prenomField);

        // Champ Email
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(350, 165, 140, 25);
        emailField = new JTextField(20);
        emailField.setBounds(500, 165, 250, 30);
        panel.add(emailLabel);
        panel.add(emailField);

        // Champ Mot de Passe
        JLabel passwordLabel = new JLabel("Mot de passe:");
        passwordLabel.setBounds(350, 205, 140, 25);
        passwordField = new JPasswordField(20);
        passwordField.setBounds(500, 205, 250, 30);
        panel.add(passwordLabel);
        panel.add(passwordField);

        // Champ Confirmation Mot de Passe
        JLabel confirmeLabel = new JLabel("Confirmer Mot de passe:");
        confirmeLabel.setBounds(350, 245, 140, 25);
        confirmepasswordField = new JPasswordField(20);
        confirmepasswordField.setBounds(500, 245, 250, 30);
        panel.add(confirmeLabel);
        panel.add(confirmepasswordField);

        // Bouton Créer un Compte
        createAccountButton = new JButton("Créer un Compte");
        createAccountButton.setBounds(500, 285, 250, 35);
        createAccountButton.setForeground(Color.WHITE);
        createAccountButton.setFont(new Font("Arial", Font.BOLD, 15));
        createAccountButton.setBackground(new Color(0, 200, 0));
        panel.add(createAccountButton);

        // Ajouter l'action du bouton
        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String prenom = prenomField.getText();
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());
                String confirmepassword = new String(confirmepasswordField.getPassword());

                // Validation basique
                if (name.isEmpty() || prenom.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "Tous les champs doivent être remplis.", "Erreur", JOptionPane.ERROR_MESSAGE);
                } else if (!password.equals(confirmepassword)) {
                    JOptionPane.showMessageDialog(panel, "Les mots de passe ne correspondent pas.", "Erreur", JOptionPane.ERROR_MESSAGE);
                } else {
                    PharmacienDAO pf = new PharmacienDAO();
                    if (pf.verifierEmailExistant(email)) {
                        JOptionPane.showMessageDialog(panel, "Cet email existe déjà.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    } else {
                        pf.creerCompte(name, prenom, email, confirmepassword);
                        JOptionPane.showMessageDialog(panel, "Compte créé avec succès pour " + name + ".", "Succès", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });

        return panel;
    }


}
