package com.backendStudent.demo;

import com.backendStudent.demo.Model.Etudiant;
import com.backendStudent.demo.Model.StatutEtudiant;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class EtudiantTable extends JFrame {

    private JTable table;
    private DefaultTableModel model;

    public EtudiantTable() {
        // Configuration de la fenêtre
        setTitle("Table des Etudiants");
        setSize(1100, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Création du modèle de table
        model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nom");
        model.addColumn("Moyenne");
        model.addColumn("Statut");

        // Récupérer les données des étudiants via l'API
        List<Etudiant> etudiants = getAllEtudiants();

        // Ajouter les étudiants au modèle de table
        for (Etudiant etudiant : etudiants) {
            model.addRow(new Object[]{
                    etudiant.getId(),
                    etudiant.getNom(),
                    etudiant.getMoyenne(),
                    etudiant.getStatut()
            });
        }

        // Créer la table avec le modèle
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Ajout des boutons pour les actions
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 5));  // Ajout d'une colonne supplémentaire

        JButton updateButton = new JButton("Mettre à jour");
        updateButton.addActionListener(e -> updateMoyenne());
        panel.add(updateButton);

        JButton deleteButton = new JButton("Supprimer");
        deleteButton.addActionListener(e -> deleteEtudiant());
        panel.add(deleteButton);

        JButton statsButton = new JButton("Statistiques");
        statsButton.addActionListener(e -> showStats());
        panel.add(statsButton);

        // Ajout du bouton Actualiser
        JButton refreshButton = new JButton("Actualiser");
        refreshButton.addActionListener(e -> refreshTable());
        panel.add(refreshButton);

        // Ajout du bouton Ajouter
        JButton addButton = new JButton("Ajouter un étudiant");
        addButton.addActionListener(e -> addEtudiant());
        panel.add(addButton);

        add(panel, BorderLayout.SOUTH);

        // Affichage de la fenêtre
        setVisible(true);
    }

    public static void main(String[] args) {
        // Lancer l'application
        SwingUtilities.invokeLater(() -> new EtudiantTable());
    }

    // Méthode pour récupérer les étudiants via une requête HTTP GET
    private List<Etudiant> getAllEtudiants() {
        List<Etudiant> etudiants = new ArrayList<>();
        try {
            // URL de l'API (assurez-vous que votre API est en cours d'exécution)
            URL url = new URL("http://localhost:8080/etudiants");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            // Vérifier la réponse du serveur
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // Lire la réponse du serveur
                InputStreamReader in = new InputStreamReader(connection.getInputStream());
                StringBuilder response = new StringBuilder();
                int character;
                while ((character = in.read()) != -1) {
                    response.append((char) character);
                }

                // Convertir la réponse JSON en liste d'objets Etudiant
                JSONArray jsonArray = new JSONArray(response.toString());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Long id = jsonObject.getLong("id");
                    String nom = jsonObject.getString("nom");
                    double moyenne = jsonObject.getDouble("moyenne");
                    String statut = jsonObject.getString("statut");  // Vous pouvez ajuster selon vos besoins

                    // Créer un objet Etudiant et l'ajouter à la liste
                    Etudiant etudiant = new Etudiant(id, nom, moyenne, StatutEtudiant.valueOf(statut));
                    etudiants.add(etudiant);
                }
            } else {
                System.out.println("Erreur lors de la récupération des étudiants.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return etudiants;
    }

    // Fonction pour actualiser la table avec les dernières données
    private void refreshTable() {
        // Supprimer toutes les lignes existantes dans le tableau
        model.setRowCount(0);

        // Récupérer les étudiants actualisés depuis l'API
        List<Etudiant> etudiants = getAllEtudiants();

        // Ajouter les nouveaux étudiants à la table
        for (Etudiant etudiant : etudiants) {
            model.addRow(new Object[]{
                    etudiant.getId(),
                    etudiant.getNom(),
                    etudiant.getMoyenne(),
                    etudiant.getStatut()
            });
        }
    }

    // Fonction de mise à jour de la moyenne
    private void updateMoyenne() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            Long id = (Long) model.getValueAt(selectedRow, 0);
            String newMoyenneString = JOptionPane.showInputDialog(this, "Entrez la nouvelle moyenne : ");
            try {
                double newMoyenne = Double.parseDouble(newMoyenneString);
                updateEtudiantMoyenne(id, newMoyenne);
                model.setValueAt(newMoyenne, selectedRow, 2); // Met à jour la moyenne dans le tableau
                refreshTable();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Veuillez entrer un nombre valide.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un étudiant.");
        }
    }

    // Fonction pour envoyer la requête HTTP de mise à jour de la moyenne
    private void updateEtudiantMoyenne(Long id, double nouvelleMoyenne) {
        try {
            URL url = new URL("http://localhost:8080/etudiants/" + id + "/moyenne");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");

            String jsonBody = "{\"moyenne\": " + nouvelleMoyenne + "}";
            connection.getOutputStream().write(jsonBody.getBytes());
            connection.getOutputStream().flush();


            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                JOptionPane.showMessageDialog(this, "Erreur lors de la mise à jour.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Fonction de suppression de l'étudiant
    private void deleteEtudiant() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            Long id = (Long) model.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Voulez-vous vraiment supprimer cet étudiant?");
            if (confirm == JOptionPane.YES_OPTION) {
                deleteEtudiantFromAPI(id);
                model.removeRow(selectedRow); // Supprime l'étudiant du tableau
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un étudiant.");
        }
    }

    // Fonction pour envoyer la requête HTTP de suppression
    private void deleteEtudiantFromAPI(Long id) {
        try {
            URL url = new URL("http://localhost:8080/etudiants/" + id);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                JOptionPane.showMessageDialog(this, "Erreur lors de la suppression.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Fonction pour afficher les statistiques
    // Fonction pour afficher les statistiques avec un graphique
    private void showStats() {
        try {
            // Connexion à l'API
            URL url = new URL("http://localhost:8080/etudiants/stats");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // Lecture de la réponse
                InputStreamReader in = new InputStreamReader(connection.getInputStream());
                StringBuilder response = new StringBuilder();
                int character;
                while ((character = in.read()) != -1) {
                    response.append((char) character);
                }

                // Parse la réponse JSON
                JSONObject stats = new JSONObject(response.toString());

                // Extraire les statistiques
                double count = stats.getDouble("count");
                double sum = stats.getDouble("sum");
                double min = stats.getDouble("min");
                double max = stats.getDouble("max");
                double average = stats.getDouble("average");

                // Calcul de la moyenne totale des étudiants
                double totalAverage = sum / count; // Moyenne totale

                // Afficher les résultats dans la console
                System.out.println("Moyenne totale des étudiants : " + totalAverage);

                // Liste des statistiques pour le graphique
                List<Double> values = new ArrayList<>();
                List<String> labels = new ArrayList<>();

                // Ajout des statistiques au graphique
                values.add(count);   // Nombre d'étudiants
                values.add(sum);     // Somme des scores
                values.add(min);     // Score minimal
                values.add(max);     // Score maximal
                values.add(average); // Moyenne des scores
                values.add(totalAverage); // Moyenne totale des étudiants

                // Création du graphique
                XYSeriesCollection dataset = new XYSeriesCollection();
                XYSeries series = new XYSeries("Statistiques des Étudiants");

                // Ajouter les données au graphique
                for (int i = 0; i < values.size(); i++) {
                    series.add(i, values.get(i));  // Position X = i, Position Y = valeur
                }
                dataset.addSeries(series);

                // Création du graphique de type XY
                JFreeChart chart = ChartFactory.createXYLineChart(
                        "Statistiques des Étudiants",  // Titre du graphique
                        "Type de Statistique",         // Axe X
                        "Valeur",                      // Axe Y
                        dataset,                       // Données
                        PlotOrientation.VERTICAL,      // Orientation verticale
                        true,                          // Légende
                        true,                          // Tooltips
                        false                          // URLs
                );

                // Affichage du graphique dans une fenêtre
                ChartPanel chartPanel = new ChartPanel(chart);
                chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
                JFrame chartFrame = new JFrame("Graphique des Statistiques");
                chartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                chartFrame.getContentPane().add(chartPanel, BorderLayout.CENTER);
                chartFrame.pack();
                chartFrame.setVisible(true);

            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de la récupération des statistiques.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





    // Fonction pour ajouter un nouvel étudiant
    private void addEtudiant() {
        // Affichage d'un formulaire pour l'ajout d'un étudiant
        JTextField nomField = new JTextField(10);
        JTextField moyenneField = new JTextField(10);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Nom:"));
        panel.add(nomField);
        panel.add(new JLabel("Moyenne:"));
        panel.add(moyenneField);

        int option = JOptionPane.showConfirmDialog(this, panel, "Ajouter un étudiant", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String nom = nomField.getText();
            double moyenne;
            try {
                moyenne = Double.parseDouble(moyenneField.getText());
                // L'objet étudiant sera créé sans avoir à définir manuellement le statut,
                // car la méthode @PrePersist définira le statut automatiquement.
                Etudiant newEtudiant = new Etudiant(null, nom, moyenne, null); // Statut est null, il sera défini par la méthode @PrePersist

                addEtudiantToAPI(newEtudiant);
                refreshTable(); // Actualiser la table pour afficher le nouvel étudiant
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Veuillez entrer une moyenne valide.");
            }
        }
    }

    // Fonction pour envoyer la requête HTTP d'ajout d'étudiant
    private void addEtudiantToAPI(Etudiant etudiant) {
        try {
            URL url = new URL("http://localhost:8080/etudiants");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");

            // Construction du corps de la requête JSON correctement formaté
            String jsonBody = String.format("{\"nom\": \"%s\", \"moyenne\": %s}", etudiant.getNom(), etudiant.getMoyenne());
            System.out.println(jsonBody);
            // Envoi des données
            connection.getOutputStream().write(jsonBody.getBytes());
            connection.getOutputStream().flush();

            // Vérification du code de réponse HTTP
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("Étudiant ajouté avec succès.");
            } else {
                System.out.println("Erreur lors de l'ajout de l'étudiant. Code de réponse : " + responseCode);
                InputStream errorStream = connection.getErrorStream();
                if (errorStream != null) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(errorStream));
                    StringBuilder responseBuilder = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        responseBuilder.append(line);
                    }
                    System.out.println("Réponse d'erreur : " + responseBuilder.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Une erreur s'est produite lors de l'ajout de l'étudiant.");
        }
    }


}
