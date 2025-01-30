package dao;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FournisseurDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/gestion_pharmacie";
    private static final String USER = "root"; // Remplacez par votre utilisateur MySQL
    private static final String PASSWORD = ""; // Remplacez par votre mot de passe MySQL

    private Connection connection;

    public FournisseurDAO() {
        try {
            // Initialisation de la connexion
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connexion à la base de données réussie !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la connexion à la base de données : " + e.getMessage());
        }
    }

    // Méthode pour ajouter un fournisseur
    public boolean ajouterFournisseur(String nom, String prenom, String adresse, String telephone,String mail) {
        String query = "INSERT INTO fournisseur (nom, prenom, adresse, telephone,email) VALUES (?, ?, ?, ?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, nom);
            preparedStatement.setString(2, prenom);
            preparedStatement.setString(3, adresse);
            preparedStatement.setString(4, telephone);
            preparedStatement.setString(5, mail);


            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout du fournisseur : " + e.getMessage());
            return false;
        }
    }

    // Suppression d'un fournisseur
    public void supprimerFournisseur(int id) {
        String query = "DELETE FROM fournisseur WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Fournisseur supprimé avec succès.");
            } else {
                System.out.println("Aucun fournisseur trouvé avec l'ID fourni.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression du fournisseur : " + e.getMessage());
        }
    }

    // Modification d'un fournisseur
    public void modifierFournisseur(int id, String nom, String prenom, String adresse, String telephone) {
        String query = "UPDATE fournisseur SET nom = ?, prenom = ?, adresse = ?, telephone = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, nom);
            pstmt.setString(2, prenom);
            pstmt.setString(3, adresse);
            pstmt.setString(4, telephone);
            pstmt.setInt(5, id);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Fournisseur modifié avec succès.");
            } else {
                System.out.println("Aucun fournisseur trouvé avec l'ID fourni.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la modification du fournisseur : " + e.getMessage());
        }
    }

    // Liste des fournisseurs
    public List<String[]> listerFournisseurs() {
        String query = "SELECT id, nom, prenom, adresse, telephone FROM fournisseur";
        List<String[]> fournisseurs = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String[] fournisseur = new String[5];
                fournisseur[0] = String.valueOf(rs.getInt("id"));
                fournisseur[1] = rs.getString("nom");
                fournisseur[2] = rs.getString("prenom");
                fournisseur[3] = rs.getString("adresse");
                fournisseur[4] = rs.getString("telephone");
                fournisseurs.add(fournisseur);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des fournisseurs : " + e.getMessage());
        }
        return fournisseurs;
    }

    // Fermeture de la connexion
    public void fermerConnexion() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Connexion fermée.");
            } catch (SQLException e) {
                System.err.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
            }
        }
    }
}
