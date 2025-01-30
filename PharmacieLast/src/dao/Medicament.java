package dao;
import java.util.Date;

public class Medicament{
    private int id;
    private String nom;
    private String categorie;
    private double prix;
    private int quantiteEnStock;
    private Date dateExpiration;
    private String description;

    public Medicament(int id, String nom, String categorie, double prix, int quantiteEnStock, Date dateExpiration, String description) {
        this.id = id;
        this.nom = nom;
        this.categorie = categorie;
        this.prix = prix;
        this.quantiteEnStock = quantiteEnStock;
        this.dateExpiration = dateExpiration;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getCategorie() {
        return categorie;
    }

    public double getPrix() {
        return prix;
    }

    public int getQuantiteEnStock() {
        return quantiteEnStock;
    }

    public Date getDateExpiration() {
        return dateExpiration;
    }

    public String getDescription() {
        return description;
    }

    public void setQuantiteEnStock(int quantiteEnStock) {
        this.quantiteEnStock = quantiteEnStock;
    }
}