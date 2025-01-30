package app;

import javax.swing.*;


import java.awt.*;
import affichage.*;
import formulaire.*;



public class App {

    private JPanel mainPanel; // Panneau principal qui change dynamiquement

   
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new App().createAndShowGUI());
    }

    public void createAndShowGUI() {
        // Fenêtre principale
        JFrame frame = new JFrame("Dashboard Fournisseurs");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximiser la fenêtre
        frame.setLayout(new BorderLayout());

        // En-tête
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
;
        headerPanel.setBackground(Color.white); // Couleur de fond
        JLabel headerLabel = new JLabel("Pharmacie OUMAR-HASSANE"); // Nom de la pharmacie
        headerLabel.setFont(new Font("Arial", Font.BOLD, 30));
        headerLabel.setForeground(Color.black);
        headerPanel.add(headerLabel);

        // Section de navigation (à gauche)
        JPanel navigationPanel = new JPanel();
        navigationPanel.setLayout(null); // Positionnement manuel des composants
        navigationPanel.setPreferredSize(new Dimension(350, 0)); // Largeur fixe
        navigationPanel.setBackground(new Color(0, 200, 0));
        // Image
        ImageIcon imageIcon = new ImageIcon("sassss/logoPhar3.jpg");
        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setBounds(20, 5,286, 150);
        navigationPanel.add(imageLabel);

        // Boutons dans la section de navigation
        JButton btnAfficherFournisseurs = new JButton("Afficher Fournisseurs");
        JButton btnAjouterFournisseur = new JButton("Ajouter Fournisseur");
        JButton btnVente = new JButton("Vendre Medicament");
        JButton btnMedocAffich = new JButton("Afficher Medicament");
        JButton btnAjoutPharmacien = new JButton("Ajouter Pharmacien");
        JButton btnAffichPharmacien = new JButton("Afficher Pharmacien");
        JButton btnAffichStat = new JButton("Afficher Statistique");
        JButton btnAjoutMedoc = new JButton("Ajouter Medoc");
        btnVente.setBackground(Color.WHITE);
        btnAfficherFournisseurs.setBackground(Color.WHITE);
        btnAjouterFournisseur.setBackground(Color.WHITE);
        btnMedocAffich.setBackground(Color.WHITE);
        btnAjoutPharmacien.setBackground(Color.WHITE);
        btnAffichPharmacien.setBackground(Color.WHITE);
        btnAffichStat.setBackground(Color.WHITE);
        btnAjoutMedoc.setBackground(Color.WHITE);

        // Définir les tailles et positions des boutons avec setBounds
        btnVente.setBounds(75, 170, 200, 35);  // Premier bouton Vente
        btnAfficherFournisseurs.setBounds(75, 215, 200, 35);  // Affichage des fournisseurs
        btnMedocAffich.setBounds(75, 260, 200, 35);  // Affichage des médicaments
        btnAffichPharmacien.setBounds(75, 305, 200, 35);  // Affichage des pharmaciens
        btnAffichStat.setBounds(75, 350, 200, 35);  // Affichage des statistiques

        btnAjouterFournisseur.setBounds(75, 395, 200, 35);  // Ajout d'un fournisseur
        btnAjoutMedoc.setBounds(75, 440, 200, 35);  // Ajout d'un médicament
        btnAjoutPharmacien.setBounds(75, 485, 200, 35);  // Ajout d'un pharmacien




        // Ajouter les boutons au panneau de navigation
        navigationPanel.add(btnAfficherFournisseurs);
        navigationPanel.add(btnAjouterFournisseur);
        navigationPanel.add(btnVente);
        navigationPanel.add(btnMedocAffich);
        navigationPanel.add(btnAjoutPharmacien);
        navigationPanel.add(btnAffichPharmacien);
        navigationPanel.add(btnAffichStat);
        navigationPanel.add(btnAjoutMedoc);

        // Section principale (panneau qui change dynamiquement)
        mainPanel = new JPanel(new BorderLayout());
        FournisseurAffichage fournisseurAffichage = new FournisseurAffichage();
        JPanel afficherPanel = fournisseurAffichage.afficher();

//        login log = new login();
//        JPanel loginPanel = log.demarrer();
        MedicamentAffichage medocAffich = new MedicamentAffichage();
        JPanel medocAffichPanel = medocAffich.afficher();
        
        VenteMedicamentsInterface vente = new VenteMedicamentsInterface(); 
        JPanel VentePanel = vente.createMainPanel();
        
        fournisseurFormulaire form = new fournisseurFormulaire();
        JPanel Form = form.demarrer();

        JPanel ajouterPanel = new JPanel();
        ajouterPanel.setBackground(Color.LIGHT_GRAY);
        ajouterPanel.add(new JLabel("Ajouter un fournisseur (non implémenté)"));
        
        PharmacienFormulaire pha = new PharmacienFormulaire();
        JPanel AjoutPharmacienPanel = pha.creerPanelFormulaire();
        
        PharmacienAffichage AffichPha =  new PharmacienAffichage();
        JPanel AffichPharPanel = AffichPha.afficher();
        
        AffichageStatistique AffichStat = new AffichageStatistique();
        JPanel AffichStatPanel = AffichStat.createMainPanel();
        
        MedicamentFormulaire mf = new MedicamentFormulaire();
        JPanel AjoutMedocPanel = mf.demarrer();
        

        // Ajouter le panneau initial
        mainPanel.add(VentePanel);
//        mainPanel.add(AjoutPharmacienPanel);

        // ActionListener pour changer de panneau
        btnAfficherFournisseurs.addActionListener(e -> switchPanel(afficherPanel));
        btnAjouterFournisseur.addActionListener(e -> switchPanel(Form ));
        btnVente.addActionListener(e -> switchPanel(VentePanel));
        btnMedocAffich.addActionListener(e -> switchPanel(medocAffichPanel));
        btnAjoutPharmacien.addActionListener(e -> switchPanel(AjoutPharmacienPanel));
        btnAffichPharmacien.addActionListener(e -> switchPanel(AffichPharPanel));
        btnAffichStat.addActionListener(e -> switchPanel(AffichStatPanel));
        btnAjoutMedoc.addActionListener(e -> switchPanel(AjoutMedocPanel));

        // Footer
        JPanel footerPanel = new JPanel();
        footerPanel.setLayout(new BorderLayout());
        footerPanel.setBackground(new Color(0, 200, 0));
        JLabel footerLabel = new JLabel("Créé par Hassane", JLabel.CENTER);
        footerLabel.setForeground(Color.WHITE);
        footerPanel.add(footerLabel, BorderLayout.CENTER);

        // Panneau vide à droite
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.WHITE);

        // Ajouter les sections à la fenêtre principale
        frame.add(headerPanel, BorderLayout.NORTH); // En-tête en haut
        frame.add(navigationPanel, BorderLayout.WEST); // Navigation fixe
        frame.add(mainPanel, BorderLayout.CENTER);     // Contenu dynamique
        frame.add(footerPanel, BorderLayout.SOUTH);   // Footer en bas
        frame.add(rightPanel, BorderLayout.EAST);     // Panneau vide à droite

        // Afficher la fenêtre
        frame.setVisible(true);
    }

    /**
     * Méthode pour changer dynamiquement le panneau principal.
     *
     * @param panel Le panneau à afficher.
     */
    private void switchPanel(JPanel panel) {
        mainPanel.removeAll();        // Supprimer le contenu actuel
        mainPanel.add(panel);         // Ajouter le nouveau panneau
        mainPanel.revalidate();       // Revalider pour appliquer les changements
        mainPanel.repaint();          // Repeindre l'interface utilisateur
    }
}
