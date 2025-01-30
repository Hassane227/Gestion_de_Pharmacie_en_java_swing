package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PharmacienDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/gestion_pharmacie";
    private static final String USER = "root"; // Remplacez par votre utilisateur MySQL
    private static final String PASSWORD = ""; // Remplacez par votre mot de passe MySQL

    private Connection connection;

    public PharmacienDAO() {
        try {
            // Initialisation de la connexion
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connexion à la base de données réussie !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la connexion à la base de données : " + e.getMessage());
        }
    }

    // Méthode pour créer un compte
    public boolean creerCompte(String nom, String prenom, String email, String password) {
        String query = "INSERT INTO pharmacienn (nom, prenom, email, mot_de_passe) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, nom);
            preparedStatement.setString(2, prenom);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, password);

            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            System.err.println("Erreur lors de la création du compte : " + e.getMessage());
            return false;
        }
    }

    // Vérification de l'existence d'un email
    public boolean verifierEmailExistant(String email) {
        String query = "SELECT COUNT(*) FROM pharmacienn WHERE email = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la vérification de l'email : " + e.getMessage());
        }
        return false;
    }

    // Suppression d'un pharmacien
    public void supprimerPharmacien(int id) {
        String query = "DELETE FROM pharmacienn WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Pharmacien supprimé avec succès.");
            } else {
                System.out.println("Aucun pharmacien trouvé avec l'ID fourni.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression du pharmacien : " + e.getMessage());
        }
    }

    // Modification d'un pharmacien
    public void modifierPharmacien(int id, String nom, String prenom, String email) {
        String query = "UPDATE pharmacienn SET nom = ?, prenom = ?, email = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, nom);
            pstmt.setString(2, prenom);
            pstmt.setString(3, email);
            pstmt.setInt(4, id);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Pharmacien modifié avec succès.");
            } else {
                System.out.println("Aucun pharmacien trouvé avec l'ID fourni.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la modification du pharmacien : " + e.getMessage());
        }
    }
// authentification
    public boolean authentifierPharmacien(String nom, String motDePasse) {
        String query = "SELECT COUNT(*) FROM pharmacienn WHERE nom = ? AND mot_de_passe = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, nom);
            preparedStatement.setString(2, motDePasse);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1) > 0; // Retourne true si un utilisateur est trouvé
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'authentification : " + e.getMessage());
        }
        return false;
    }

    // Liste des pharmaciens
    public List<String[]> listerPharmaciens() {
        String query = "SELECT id, nom, prenom, email FROM pharmacienn";
        List<String[]> pharmaciens = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String[] pharmacien = new String[4];
                pharmacien[0] = String.valueOf(rs.getInt("id"));
                pharmacien[1] = rs.getString("nom");
                pharmacien[2] = rs.getString("prenom");
                pharmacien[3] = rs.getString("email");
                pharmaciens.add(pharmacien);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des pharmaciens : " + e.getMessage());
        }
        return pharmaciens;
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
