package app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import dao.PharmacienDAO;

public class LoginApp {

    // Déclaration des variables globales pour les composants
    private JPanel panel;
    private JTextField nameField;
    private JPasswordField passwordField;

    public LoginApp() {
        // Créer la fenêtre principale
        JFrame frame = new JFrame("Login Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLayout(null);

        // Créer et configurer le panneau principal
        panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(null);
        panel.setBounds(350, 150, 800, 500);

        // Ajouter le panneau principal à la fenêtre
        frame.add(panel);

        // Titre
        JLabel titleLabel = new JLabel("Login");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
        titleLabel.setBounds(320, 30, 400, 50);
        panel.add(titleLabel);

        // Ajout d'une image
        ImageIcon imageIcon = new ImageIcon("sassss/pharmacie1.png"); // Chemin de l'image
        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setBounds(20, 90, 300, imageIcon.getIconHeight());
        panel.add(imageLabel);

        // Champ Nom
        JLabel nameLabel = new JLabel("Nom:");
        nameLabel.setBounds(350, 150, 200, 25);
        nameField = new JTextField(20);
        nameField.setBounds(500, 150, 250, 30);
        panel.add(nameLabel);
        panel.add(nameField);

        // Champ Mot de Passe
        JLabel passwordLabel = new JLabel("Mot de passe:");
        passwordLabel.setBounds(350, 205, 140, 25);
        passwordField = new JPasswordField(20);
        passwordField.setBounds(500, 205, 250, 30);
        panel.add(passwordLabel);
        panel.add(passwordField);

        // Bouton Connexion
        JButton createAccountButton = new JButton("Login");
        createAccountButton.setBounds(500, 275, 250, 35);
        createAccountButton.setForeground(Color.WHITE);
        createAccountButton.setFont(new Font("Arial", Font.BOLD, 15));
        createAccountButton.setBackground(new Color(0, 200, 0));
        panel.add(createAccountButton);

        // Lien "Je n'ai pas un compte"
        JLabel loginLabel = new JLabel("<html><a href='#'>Je n'ai pas un compte</a></html>");
        loginLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginLabel.setBounds(500, 320, 200, 30);
        panel.add(loginLabel);

        // Action sur le lien
        loginLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(null, "Redirection vers la création de compte");
            }
        });

        // Action sur le bouton Connexion
        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String password = new String(passwordField.getPassword());

                if (name.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Créer une instance de PharmacienDAO et vérifier l'authentification
                    PharmacienDAO pharmacienDAO = new PharmacienDAO();
                    boolean authentifie = pharmacienDAO.authentifierPharmacien(name, password);

                    if (authentifie) {
                        JOptionPane.showMessageDialog(null, "Connexion réussie !");
                        // Rediriger vers une autre fenêtre ou effectuer une action
                        App p = new App();
                        p.createAndShowGUI();
                        
                        frame.dispose(); // Fermer la fenêtre de connexion
                    } else {
                        JOptionPane.showMessageDialog(null, "Nom ou mot de passe incorrect.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }

                    // Fermer la connexion à la base de données
                    pharmacienDAO.fermerConnexion();
                }
            }
        });

        // Afficher la fenêtre
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        // Lancer l'application
        new LoginApp();
    }
}
