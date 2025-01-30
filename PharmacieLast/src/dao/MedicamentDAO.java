package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicamentDAO {
    public static final String DB_URL = "jdbc:mysql://localhost:3306/gestion_pharmacie";
    public static final String DB_USER = "root";
    public static final String DB_PASSWORD = "";

    // Méthode pour récupérer tous les médicaments
    public List<Medicament> getAllMedicaments() throws SQLException {
        List<Medicament> medicaments = new ArrayList<>();
        String query = "SELECT * FROM medicament";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Medicament medicament = new Medicament(
                        rs.getInt("id_medicament"),
                        rs.getString("nom"),
                        rs.getString("categorie"),
                        rs.getDouble("prix"),
                        rs.getInt("quantite_en_stock"),
                        rs.getDate("date_expiration"),
                        rs.getString("description")
                );
                medicaments.add(medicament);
            }
        }
        return medicaments;
    }

    // Méthode pour mettre à jour le stock
    public void updateStock(int medicamentId, int quantitySold) throws SQLException {
        String query = "UPDATE medicament SET quantite_en_stock = quantite_en_stock - ? WHERE id_medicament = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, quantitySold);
            pstmt.setInt(2, medicamentId);
            pstmt.executeUpdate();
        }
    }
    
 // Méthode pour ajouter un médicament avec un retour de succès
    public boolean ajouterMedicament(String nom, String categorie, double prix, int quantiteEnStock, String dateExpiration, String description) {
        String query = "INSERT INTO medicament (nom, categorie, prix, quantite_en_stock, date_expiration, description) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, nom);
            pstmt.setString(2, categorie);
            pstmt.setDouble(3, prix);
            pstmt.setInt(4, quantiteEnStock);
            pstmt.setDate(5, Date.valueOf(dateExpiration));
            pstmt.setString(6, description);
            
            int rowsAffected = pstmt.executeUpdate();
            
            // Si une ligne a été affectée, cela signifie que l'ajout a réussi
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;  // Retourner false en cas d'erreur
        }
    }


    // Méthode pour supprimer un médicament
    public void supprimerMedicament(int medicamentId) throws SQLException {
        String query = "DELETE FROM medicament WHERE id_medicament = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, medicamentId);
            pstmt.executeUpdate();
        }
    }

    // Méthode pour modifier un médicament
    public void modifierMedicament(int medicamentId, String nom, String categorie, double prix, int quantiteEnStock, String dateExpiration, String description) throws SQLException {
        String query = "UPDATE medicament SET nom = ?, categorie = ?, prix = ?, quantite_en_stock = ?, date_expiration = ?, description = ? WHERE id_medicament = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, nom);
            pstmt.setString(2, categorie);
            pstmt.setDouble(3, prix);
            pstmt.setInt(4, quantiteEnStock);
            pstmt.setDate(5, Date.valueOf(dateExpiration));
            pstmt.setString(6, description);
            pstmt.setInt(7, medicamentId);
            pstmt.executeUpdate();
        }
    }

    // Méthode pour ajouter une vente
    public void addVente(int idMedicament, int quantity, double prixUnitaire) throws SQLException {
        String queryVente = "INSERT INTO ventes (date_vente, montant_total) VALUES (?, ?)";
        String queryVenteMedicament = "INSERT INTO ventes_medicaments (id_ventes, id_medicament, quantite, prix_unitaire) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmtVente = conn.prepareStatement(queryVente, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement pstmtVenteMedicament = conn.prepareStatement(queryVenteMedicament)) {

            // Insérer la vente
            pstmtVente.setTimestamp(1, new Timestamp(System.currentTimeMillis())); // Date actuelle
            double montantTotal = quantity * prixUnitaire;
            pstmtVente.setDouble(2, montantTotal);
            pstmtVente.executeUpdate();
            System.out.println("add vente");

            // Récupérer l'ID de la vente insérée
            try (ResultSet generatedKeys = pstmtVente.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int idVente = generatedKeys.getInt(1);

                    // Insérer le lien entre la vente et le médicament
                    pstmtVenteMedicament.setInt(1, idVente);
                    pstmtVenteMedicament.setInt(2, idMedicament);
                    pstmtVenteMedicament.setInt(3, quantity);
                    pstmtVenteMedicament.setDouble(4, prixUnitaire);
                    pstmtVenteMedicament.executeUpdate();

                    // Mettre à jour le stock
                    updateStock(idMedicament, quantity);
                }
            }
        }
    }
}
