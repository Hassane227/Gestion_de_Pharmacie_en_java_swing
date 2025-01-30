# Gestion de Pharmacie en Java Swing

## 📌 Description
Ce projet est une application de gestion de pharmacie développée en Java Swing. Il permet aux pharmaciens de gérer les médicaments, les ventes et les fournisseurs de manière efficace.

## 🛠️ Fonctionnalités
- **Gestion des médicaments** : Ajout, modification, suppression et recherche de médicaments.
- **Gestion des ventes** : Enregistrement des ventes avec calcul automatique du total.
- **Gestion des fournisseurs** : Ajout et suivi des fournisseurs de médicaments.
- **Interface utilisateur en Java Swing** : Interface graphique intuitive et interactive.
- **Base de données** : Stockage des informations dans une base de données MySQL.

## 📦 Technologies utilisées
- **Langage** : Java (Swing, JDBC)
- **Base de données** : MySQL
- **IDE recommandé** : NetBeans, Eclipse ou IntelliJ IDEA
- **Outils de gestion de projet** : Git & GitHub

## 📥 Installation et exécution
### 1️⃣ Prérequis
- Java JDK 8 ou plus
- MySQL installé et configuré
- Un IDE Java (NetBeans, Eclipse, IntelliJ IDEA...)

### 2️⃣ Cloner le projet
```sh
git clone https://github.com/Hassane227/Gestion_de_Pharmacie_en_java_swing.git
cd Gestion_de_Pharmacie_en_java_swing
```

### 3️⃣ Configurer la base de données
1. Ouvrir MySQL et créer une base de données :
   ```sql
   CREATE DATABASE pharmacie_db;
   ```
2. Importer le fichier SQL fourni (`pharmacie_db.sql`).
3. Modifier les paramètres de connexion dans le fichier `Database.java` :
   ```java
   String url = "jdbc:mysql://localhost:3306/pharmacie_db";
   String user = "root";
   String password = ""; // Modifier si nécessaire
   ```

### 4️⃣ Exécuter le projet
- Ouvrir le projet dans un IDE Java
- Compiler et exécuter `Main.java`

## 📸 Captures d'écran
_Ajoutez ici des captures d'écran de votre application_

## 📝 Auteurs
- **Adamou Moussa Hassane** - Développeur principal

## 📜 Licence
Ce projet est sous licence MIT - voir le fichier [LICENSE](LICENSE) pour plus de détails.

## 🎯 Objectifs futurs
- Ajout d’un module de gestion des commandes
- Intégration d’un tableau de bord statistique
- Amélioration de l’interface utilisateur

---

🚀 **N’hésitez pas à contribuer et à donner votre avis !**
