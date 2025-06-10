package com.backendStudent.demo;

import com.backendStudent.demo.Model.Etudiant;
import com.backendStudent.demo.Model.StatutEtudiant;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FrontOffice extends JFrame {
    private JTable tableStudent;
    private DefaultTableModel modelStudent;
    JFreeChart chart;

    public FrontOffice() {
        setTitle("");
        setSize(1366, 728);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setUndecorated(true);

        //use for add the components in panel
        JPanel panelBody = new JPanel();
        panelBody.setLayout(null);
        panelBody.setBackground(new Color(2, 2, 17, 245));
        panelBody.setBounds(0, 0, 1000, 650);
        add(panelBody);

        JButton fermerButon = new JButton("X");
        fermerButon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        fermerButon.setContentAreaFilled(true);
        fermerButon.setFocusPainted(false);
        fermerButon.setBorderPainted(false);
        fermerButon.setForeground(Color.white);
        fermerButon.setBackground(new Color(2, 2, 17, 255));
        fermerButon.addActionListener(e -> System.exit(0));
        fermerButon.setBounds(1310, 10, 50, 20);
        fermerButon.setFont(new Font("Serif", Font.PLAIN, 18));
        panelBody.add(fermerButon);
        fermerButon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                fermerButon.setBackground(new Color(183, 47, 36, 247));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                fermerButon.setForeground(Color.white);
                fermerButon.setBackground(new Color(2, 2, 17, 255));

            }
        });

        JButton reduireButon = new JButton("-");
        reduireButon.setFocusPainted(false);
        reduireButon.setBorderPainted(false);
        reduireButon.setForeground(Color.white);
        reduireButon.setBackground(new Color(2, 2, 17, 255));
        reduireButon.addActionListener(e -> setState(Frame.ICONIFIED));
        reduireButon.setBounds(1310, 40, 50, 20);
        reduireButon.setFont(new Font("Serif", Font.PLAIN, 40));
        panelBody.add(reduireButon);

        reduireButon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                reduireButon.setBackground(new Color(255, 144, 33, 255));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                reduireButon.setForeground(Color.white);
                reduireButon.setBackground(new Color(2, 2, 17, 255));
            }
        });

        //add image logo
        ImageIcon imageIcon = new ImageIcon(Objects.requireNonNull(FrontOffice.class.getResource("/digitalisation-removebg-preview.png")));
        Image image = imageIcon.getImage();
        Image scaledImage = image.getScaledInstance(115, 70, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel imageLabel = new JLabel(scaledIcon);
        imageLabel.setBounds(30, 15, 115, 70);
        panelBody.add(imageLabel);
        //use for add the components in header panel
        JPanel head = new JPanel();
        head.setLayout(null);
        head.setBackground(new Color(2, 2, 17, 255));
        head.setBounds(0, 2, 1000, 100);
        panelBody.add(head);
        panelBody.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                head.setBounds(0, 2, panelBody.getWidth(), 100);
            }
        });

        //use for add the navBar buttons in navbarLeft
        JPanel navBarLeft = new JPanel();
        navBarLeft.setLayout(null);
        navBarLeft.setBackground(new Color(2, 2, 17, 240));
        navBarLeft.setBounds(10, 130, 350, 100);
        panelBody.add(navBarLeft);
        navBarLeft.setBorder(new MatteBorder(0, 2, 2, 2, new Color(2, 2, 17, 240)));
        panelBody.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent event) {
                navBarLeft.setBounds(10, 130, 400, 100);
            }
        });

        JPanel navBarLeft2 = new JPanel();
        navBarLeft2.setLayout(null);
        navBarLeft2.setBackground(new Color(2, 2, 17, 240));
        navBarLeft2.setBounds(10, 250, 350, 100);
        panelBody.add(navBarLeft2);
        panelBody.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent event) {
                navBarLeft2.setBounds(10, 250, 400, 100);
            }
        });

        JPanel containerPanel = new JPanel();
        containerPanel.setLayout(null);
        containerPanel.setBackground(new Color(2, 2, 17, 255));
        containerPanel.setBounds(420, 130, 890, 540);
        panelBody.add(containerPanel);

        panelBody.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent event) {
                containerPanel.setBounds(420, 130, 938, 590);
            }
        });

        JLabel titleInHead = new JLabel("🎓 DIG Sekoly ");
        titleInHead.setFont(new Font("Serif", Font.PLAIN, 26));
        titleInHead.setBounds(495, -80, 200, 200);
        titleInHead.setForeground(Color.white);
        head.add(titleInHead);
        panelBody.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent event) {
                titleInHead.setLocation((panelBody.getWidth() - 30) / 2, -70);
            }
        });

        JLabel titleInBarLeft = new JLabel("Liste , Ajouter");
        titleInBarLeft.setFont(new Font("Serif", Font.PLAIN, 16));
        titleInBarLeft.setBounds(10, -30, 100, 100);
        titleInBarLeft.setForeground(Color.white);
        navBarLeft.add(titleInBarLeft);

        JLabel titleInBarLeft1 = new JLabel("liste, Statistiques");
        titleInBarLeft1.setFont(new Font("Serif", Font.PLAIN, 16));
        titleInBarLeft1.setBounds(10, -30, 100, 100);
        titleInBarLeft1.setForeground(Color.white);
        navBarLeft2.add(titleInBarLeft1);

        JPanel panelInNavBarLeft = new JPanel();
        panelInNavBarLeft.setLayout(null);
        panelInNavBarLeft.setBounds(1, 39, 398, 60);
        panelInNavBarLeft.setBackground(new Color(241, 241, 241));
        navBarLeft.add(panelInNavBarLeft);

        JPanel panelInNavBarLeft2 = new JPanel();
        panelInNavBarLeft2.setLayout(null);
        panelInNavBarLeft2.setBounds(1, 39, 398, 60);
        panelInNavBarLeft2.setBackground(new Color(241, 241, 241));
        navBarLeft2.add(panelInNavBarLeft2);

        //use for add users
        JButton addUser = new JButton("👨‍🎓Ajouter un élève");
        addUser.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addUser.setSelected(false);
        addUser.setOpaque(true);
        addUser.setFocusPainted(false);
        addUser.setBounds(15, 10, 170, 39);
        addUser.setFont(new Font("Serif", Font.PLAIN, 17));
        addUser.setBackground(new Color(2, 2, 17, 255));
        addUser.setForeground(Color.white);
        panelInNavBarLeft.add(addUser);

        JButton listUser = new JButton("📃 Liste des élèves");
        listUser.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        listUser.setFocusPainted(false);
        listUser.setBounds(210, 10, 170, 39);
        listUser.setFont(new Font("Serif", Font.PLAIN, 17));
        listUser.setBackground(new Color(2, 2, 17, 255));
        listUser.setForeground(Color.white);
        panelInNavBarLeft.add(listUser);

        JButton stat = new JButton(" Statistiques");
        stat.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        stat.setFocusPainted(false);
        stat.setBounds(15, 10, 365, 39);
        stat.setFont(new Font("Serif", Font.PLAIN, 17));
        stat.setBackground(new Color(2, 2, 17, 255));
        stat.setForeground(Color.white);
        panelInNavBarLeft2.add(stat);

        //panel for register student
        JPanel panelStudent = new JPanel();
        panelStudent.setLayout(null);
        panelStudent.setBounds(18, 10, 900, 570);
        panelStudent.setBackground(new Color(2, 2, 17, 255));
        containerPanel.add(panelStudent);

        JPanel panelListStudent = new JPanel();
        panelListStudent.setLayout(null);
        panelListStudent.setBounds(1, 2, 938, 590);
        panelListStudent.setBackground(new Color(2, 2, 17, 255));
        containerPanel.add(panelListStudent);
        panelListStudent.setVisible(false);

        JPanel panelStatistiques = new JPanel();
        panelStatistiques.setLayout(null);
        panelStatistiques.setBounds(1, 2, 938, 590);
        panelStatistiques.setBackground(new Color(2, 2, 17, 255));
        containerPanel.add(panelStatistiques);
        panelStatistiques.setVisible(false);




//        ChartPanel chartPanel = new ChartPanel(chart);
//        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
//        JFrame chartFrame = new JFrame("Graphique des Statistiques");
//        chartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        chartFrame.getContentPane().add(chartPanel, BorderLayout.CENTER);
//        chartFrame.pack();
//        chartFrame.setVisible(true);



        //student register form
        JLabel titleForm = new JLabel("📝 Formulaire de Registration 📝");
        titleForm.setBounds(350, -100, 300, 300);
        titleForm.setForeground(Color.white);
        titleForm.setFont(new Font("Serif", Font.PLAIN, 19));
        panelStudent.add(titleForm);

        JLabel nameLabel = new JLabel("Nom       ➡");
        nameLabel.setBounds(230, 90, 200, 20);
        nameLabel.setForeground(Color.white);
        nameLabel.setFont(new Font("Serif", Font.PLAIN, 17));
        panelStudent.add(nameLabel);

        JLabel firstNameLabel = new JLabel("Prénom   ➡");
        firstNameLabel.setBounds(230, 150, 200, 20);
        firstNameLabel.setForeground(Color.white);
        firstNameLabel.setFont(new Font("Serif", Font.PLAIN, 17));
        panelStudent.add(firstNameLabel);

        JLabel classLabel = new JLabel("Class       ➡");
        classLabel.setBounds(230, 210, 200, 20);
        classLabel.setForeground(Color.white);
        classLabel.setFont(new Font("Serif", Font.PLAIN, 17));
        panelStudent.add(classLabel);

        JLabel moyenneLabel = new JLabel("moyenne         ");
        moyenneLabel.setBounds(230, 270, 200, 20);
        moyenneLabel.setForeground(Color.white);
        moyenneLabel.setFont(new Font("Serif", Font.PLAIN, 17));
        panelStudent.add(moyenneLabel);

        JTextField nameValues = new JTextField();
        nameValues.setBounds(350, 90, 245, 25);
        nameValues.setForeground(new Color(2, 2, 17, 240));
        nameValues.setFont(new Font("Serif", Font.BOLD, 17));
        panelStudent.add(nameValues);

        JTextField firstNameValues = new JTextField();
        firstNameValues.setBounds(350, 150, 245, 25);
        firstNameValues.setForeground(new Color(2, 2, 17, 240));
        firstNameValues.setFont(new Font("Serif", Font.BOLD, 17));
        panelStudent.add(firstNameValues);

        String[] itemsCl = {"6ème A", "6ème B", "6ème C", "6ème D",
                "",
                "5ème A", "5ème B", "5ème C", "5ème D",
                "",
                "4ème A", "4ème B", "4ème C", "4ème D",
                "",
                "3ème A", "3ème B", "3ème C", "3ème D",
                "",
                "2nd A", "2nd B", "2nd C", "2nd D",
                "",
                "Première A", "Première B", "Première C", "Première D",
                "",
                "Première.S A", "Première.S B", "Première.S C", "Première.S D",
                "",
                "Première.L A", "Première.L B", "Première.L C", "Première.L D",
                "",
                "Terminal A", "Terminal B", "Terminal C", "Terminal D",
                "",
                "Terminal.S A", "Terminal.S B", "Terminal.S C", "Terminal.S D", "" +
                "",
                "Terminal.L A", "Terminal.L B", "Terminal.L C", "Terminal.L D",};
        JComboBox<String> comboBoxItemsCla = new JComboBox<>(itemsCl);
        comboBoxItemsCla.setFont(new Font("Serif", Font.BOLD, 17));
        comboBoxItemsCla.setLightWeightPopupEnabled(false);
        comboBoxItemsCla.setBackground(Color.WHITE);
        comboBoxItemsCla.setForeground(new Color(2, 2, 17, 240));
        comboBoxItemsCla.setBounds(350, 210, 245, 25);
        panelStudent.add(comboBoxItemsCla);


        JTextField moyenneValues = new JTextField();
        moyenneValues.setBounds(350, 270, 245, 25);
        moyenneValues.setForeground(new Color(2, 2, 17, 240));
        moyenneValues.setFont(new Font("Serif", Font.BOLD, 17));
        panelStudent.add(moyenneValues);

        JButton insertUser = new JButton("Ajouter ➕");
        insertUser.setBounds(410, 350, 130, 30);
        insertUser.setBackground(Color.WHITE);
        insertUser.setForeground(new Color(2, 2, 17, 240));
        insertUser.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        insertUser.setFont(new Font("Serif", Font.BOLD, 16));
        insertUser.setOpaque(true);
        insertUser.setFocusPainted(false);

        panelStudent.add(insertUser);

        JLabel icon = new JLabel("👨‍🎓");
        icon.setBounds(200, 210, 400, 400);
        icon.setForeground(Color.white);
        icon.setFont(new Font("Serif", Font.BOLD, 70));
        panelStudent.add(icon);

        addUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelStudent.setVisible(true);
                panelListStudent.setVisible(false);
                panelStatistiques.setVisible(false);
            }
        });

        listUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelStudent.setVisible(false);
                panelStatistiques.setVisible(false);
                panelListStudent.setVisible(true);
            }
        });

        stat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelStudent.setVisible(false);
                panelListStudent.setVisible(false);
                panelStatistiques.setVisible(true);
                showStats(panelStatistiques);


            }
        });


        //table
        String[] itemsCla = {"Class",
                "",
                "6ème A", "6ème B", "6ème C", "6ème D",
                "",
                "5ème A", "5ème B", "5ème C", "5ème D",
                "",
                "4ème A", "4ème B", "4ème C", "4ème D",
                "",
                "3ème A", "3ème B", "3ème C", "3ème D",
                "",
                "2nd A", "2nd B", "2nd C", "2nd D",
                "",
                "Première A", "Première B", "Première C", "Première D",
                "",
                "Première.S A", "Première.S B", "Première.S C", "Première.S D",
                "",
                "Première.L A", "Première.L B", "Première.L C", "Première.L D",
                "",
                "Terminal A", "Terminal B", "Terminal C", "Terminal D",
                "",
                "Terminal.S A", "Terminal.S B", "Terminal.S C", "Terminal.S D", "" +
                "",
                "Terminal.L A", "Terminal.L B", "Terminal.L C", "Terminal.L D",};
        JComboBox<String> comboBoxItemsClass = new JComboBox<>(itemsCla);
        comboBoxItemsClass.setFont(new Font("Serif", Font.BOLD, 15));
        comboBoxItemsClass.setLightWeightPopupEnabled(false);
        comboBoxItemsClass.setBackground(Color.WHITE);
        comboBoxItemsClass.setForeground(new Color(2, 2, 17, 240));
        comboBoxItemsClass.setBounds(1, 4, 135, 20);
        comboBoxItemsClass.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panelListStudent.add(comboBoxItemsClass);


        JButton search = new JButton("🔎");
        search.setBackground(Color.WHITE);
        search.setBorderPainted(false);
        search.setFocusPainted(false);
        search.setForeground(new Color(2, 2, 17, 240));
        search.setFont(new Font("Serif", Font.BOLD, 15));
        search.setBounds(150, 4, 50, 20);
        search.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panelListStudent.add(search);
        search.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                search.setBackground(new Color(2, 2, 17, 251));
                search.setForeground(Color.white);
                System.out.println("io " + getAllEtudiants().get(0).getNom());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                search.setForeground(new Color(2, 2, 17, 255));
                search.setBackground(Color.WHITE);

            }
        });

        JButton refresh = new JButton("🔃");
        refresh.setBackground(Color.WHITE);
        refresh.setBorderPainted(false);
        refresh.setFocusPainted(false);
        refresh.setForeground(new Color(2, 2, 17, 240));
        refresh.setFont(new Font("Serif", Font.BOLD, 15));
        refresh.setBounds(200, 4, 50, 20);
        refresh.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panelListStudent.add(refresh);
        refresh.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                refresh.setBackground(new Color(2, 2, 17, 251));
                refresh.setForeground(Color.white);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                refresh.setForeground(new Color(2, 2, 17, 255));
                refresh.setBackground(Color.WHITE);

            }
        });

        JButton delete = new JButton("Suprimer");
        delete.addActionListener(e -> deleteEtudiant());
        delete.setBackground(Color.WHITE);
        delete.setBorderPainted(false);
        delete.setFocusPainted(false);
        delete.setForeground(new Color(2, 2, 17, 240));
        delete.setFont(new Font("Serif", Font.BOLD, 15));
        delete.setBounds(250, 4, 125, 20);
        delete.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panelListStudent.add(delete);
        delete.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                delete.setBackground(new Color(2, 2, 17, 251));
                delete.setForeground(Color.white);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                delete.setForeground(new Color(2, 2, 17, 255));
                delete.setBackground(Color.WHITE);
            }
        });

        JButton updateButton = new JButton("Mettre à jour");
        updateButton.addActionListener(e -> updateEtudiant());
        updateButton.setBackground(Color.WHITE);
        updateButton.setBorderPainted(false);
        updateButton.setFocusPainted(false);
        updateButton.setForeground(new Color(2, 2, 17, 240));
        updateButton.setFont(new Font("Serif", Font.BOLD, 15));
        updateButton.setBounds(370, 4, 125, 20);
        updateButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        panelListStudent.add(updateButton);
        updateButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                updateButton.setBackground(new Color(2, 2, 17, 251));
                updateButton.setForeground(Color.white);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                updateButton.setForeground(new Color(2, 2, 17, 255));
                updateButton.setBackground(Color.WHITE);
            }
        });


        String[] columnNameStudent = {"Id", "Nom", "Prénom", "Class", "Moyenne", "statut"};
        modelStudent = new DefaultTableModel(columnNameStudent, 0);
        tableStudent = new JTable(modelStudent) {
            public boolean isCellEditable(int row, int column) {
                return true;
            }
        };
        JTableHeader header = tableStudent.getTableHeader();
        header.setBackground(Color.WHITE);
        header.setFont(new Font("Serif", Font.BOLD, 17));
        header.setForeground(new Color(2, 2, 17, 255));

        tableStudent.setForeground(Color.white);
        tableStudent.setFont(new Font("Serif", Font.BOLD, 14));
        tableStudent.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        tableStudent.setSelectionBackground(new Color(255, 253, 251, 224));
        tableStudent.setSelectionForeground(new Color(2, 2, 17, 255));

        // Center align text in all columns
        DefaultTableCellRenderer centerRendererStudent = new DefaultTableCellRenderer();
        centerRendererStudent.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < tableStudent.getColumnCount(); i++) {
            tableStudent.getColumnModel().getColumn(i).setCellRenderer(centerRendererStudent);
        }
        tableStudent.setRowHeight(40);

        // Adjust column widths
        TableColumnModel columnModelStudent = tableStudent.getColumnModel();
        for (int i = 0; i < columnModelStudent.getColumnCount(); i++) {
            columnModelStudent.getColumn(i).setPreferredWidth(150);
        }

        JScrollPane scrollPaneStudent = new JScrollPane(tableStudent);
        scrollPaneStudent.setBounds(1, 36, 935, 550);
        scrollPaneStudent.setBackground(new Color(2, 2, 17, 255));
        scrollPaneStudent.setBorder(BorderFactory.createLineBorder(Color.white));
        scrollPaneStudent.getViewport().setOpaque(false);
        tableStudent.setBackground(new Color(2, 2, 17, 255));
        panelListStudent.add(scrollPaneStudent);

        //endTable
        //end of panel add student


        List<Etudiant> students = getAllEtudiants();
        updateTableStudent(students);

        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Etudiant> updatedStudents = getAllEtudiants(); // Récupérer la dernière version
                updateTableStudent(updatedStudents); // Mettre à jour le tableau
                System.out.println("io");
                comboBoxItemsClass.setSelectedItem(comboBoxItemsClass.getItemAt(0));
            }
        });


//for insert student
        insertUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameValues.getText();
                String firstName = firstNameValues.getText();
                String grade = (String) comboBoxItemsCla.getSelectedItem();
                double moyenne;


                try {
                    moyenne = Double.parseDouble(moyenneValues.getText());
                    // L'objet étudiant sera créé sans avoir à définir manuellement le statut,
                    // car la méthode @PrePersist définira le statut automatiquement.
                    Etudiant newEtudiant = new Etudiant(null, name, firstName, grade, moyenne, null); // Statut est null, il sera défini par la méthode @PrePersist

                    addEtudiantToAPI(newEtudiant);
//                        refreshTable();
                    JOptionPane.showMessageDialog(panelStudent, "Success.");
                    List<Etudiant> updatedStudents = getAllEtudiants();
                    updateTableStudent(updatedStudents);


                    nameValues.setText("");
                    firstNameValues.setText("");
                    moyenneValues.setText("");

                } catch (NumberFormatException ee) {
                    JOptionPane.showMessageDialog(panelStudent, "Veuillez entrer une moyenne valide.");
                }

            }


        });


    }

    public void refreshTable() {
        // Supprimer toutes les lignes existantes dans le tableau
        modelStudent.setRowCount(0);

        // Récupérer les étudiants actualisés depuis l'API
        List<Etudiant> etudiants = getAllEtudiants();

        // Ajouter les nouveaux étudiants à la table
        for (Etudiant etudiant : etudiants) {
            modelStudent.addRow(new Object[]{
                    etudiant.getId(),
                    etudiant.getNom(),
                    etudiant.getPrenom(),
                    etudiant.getClasse(),
                    etudiant.getMoyenne(),
                    etudiant.getStatut()
            });
        }
    }


    // Fonction pour envoyer la requête HTTP de mise à jour du nom et prenom
    private void updateEtudiantNomPrenom(Long id, String nom, String prenom) {
        try {
            String urlStr = "http://localhost:8080/etudiants/" + id + "/identite?nom="
                    + URLEncoder.encode(nom, "UTF-8")
                    + "&prenom=" + URLEncoder.encode(prenom, "UTF-8");

            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json"); // Pas nécessaire ici mais OK

            // Ici on n'a pas besoin d'écrire dans le corps
            connection.connect();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                JOptionPane.showMessageDialog(null, "Erreur lors de la mise à jour de l'identité.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void updateEtudiant() {
        int selectedRow = tableStudent.getSelectedRow();
        if (selectedRow != -1) {
            Long id = (Long) modelStudent.getValueAt(selectedRow, 0);

            String[] options = {"Modifier la moyenne", "Modifier le nom et prénom"};
            int choice = JOptionPane.showOptionDialog(
                    this,
                    "Que voulez-vous modifier ?",
                    "Mise à jour Étudiant",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            if (choice == 0) { // Modifier la moyenne
                String newMoyenneString = JOptionPane.showInputDialog(this, "Entrez la nouvelle moyenne : ");
                try {
                    double newMoyenne = Double.parseDouble(newMoyenneString);
                    updateEtudiantMoyenne(id, newMoyenne);
                    modelStudent.setValueAt(newMoyenne, selectedRow, 2); // Colonne moyenne
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Veuillez entrer un nombre valide.");
                }
            } else if (choice == 1) { // Modifier nom et prénom
                String newNom = JOptionPane.showInputDialog(this, "Entrez le nouveau nom : ");
                String newPrenom = JOptionPane.showInputDialog(this, "Entrez le nouveau prénom : ");
                if (newNom != null && newPrenom != null && !newNom.isBlank() && !newPrenom.isBlank()) {
                    updateEtudiantNomPrenom(id, newNom, newPrenom);
                    modelStudent.setValueAt(newNom, selectedRow, 1);
                    modelStudent.setValueAt(newPrenom, selectedRow, 3);
                } else {
                    JOptionPane.showMessageDialog(this, "Nom ou prénom invalide.");
                }
            }

            refreshTable();

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
                    String prenom = jsonObject.getString("prenom");
                    String classe = jsonObject.getString("classe");
                    double moyenne = jsonObject.getDouble("moyenne");
                    String statut = jsonObject.getString("statut");  // Vous pouvez ajuster selon vos besoins

                    // Créer un objet Etudiant et l'ajouter à la liste
                    Etudiant etudiant = new Etudiant(id, nom, prenom, classe, moyenne, StatutEtudiant.valueOf(statut));
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


    // Fonction pour envoyer la requête HTTP d'ajout d'étudiant
    private void addEtudiantToAPI(Etudiant etudiant) {
        try {
            URL url = new URL("http://localhost:8080/etudiants");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");

            // Construction du corps de la requête JSON correctement formaté
            String jsonBody = String.format("{\"nom\": \"%s\",\"prenom\": \"%s\",\"classe\": \"%s\", \"moyenne\": %s}", etudiant.getNom(), etudiant.getPrenom(), etudiant.getClasse(), etudiant.getMoyenne());
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

    // Fonction de suppression de l'étudiant
    private void deleteEtudiant() {
        int selectedRow = tableStudent.getSelectedRow();
        if (selectedRow != -1) {
            Long id = (Long) modelStudent.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Voulez-vous vraiment supprimer cet étudiant?");
            if (confirm == JOptionPane.YES_OPTION) {
                deleteEtudiantFromAPI(id);
                modelStudent.removeRow(selectedRow); // Supprime l'étudiant du tableau
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

    private void updateTableStudent(List<Etudiant> students) {
        modelStudent.setRowCount(0);
        for (Etudiant s : students) {
            modelStudent.addRow(new Object[]{
                    s.getId(), s.getNom(), s.getPrenom(),
                    s.getClasse(), s.getMoyenne(), s.getStatut()
            });
        }
    }

    // Fonction pour afficher les statistiques
    // Fonction pour afficher les statistiques avec un graphique
    private void showStats(JPanel panelStatistiques) {
        try {
            URL url = new URL("http://localhost:8080/etudiants/stats");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStreamReader in = new InputStreamReader(connection.getInputStream());
                StringBuilder response = new StringBuilder();
                int character;
                while ((character = in.read()) != -1) {
                    response.append((char) character);
                }

                JSONObject stats = new JSONObject(response.toString());

                double min = stats.getDouble("min");
                double max = stats.getDouble("max");
                double average = stats.getDouble("average");

                DefaultCategoryDataset dataset = new DefaultCategoryDataset();
                dataset.addValue(min, "Notes", "Note minimale");
                dataset.addValue(max, "Notes", "Note maximale");
                dataset.addValue(average, "Notes", "Moyenne");

                JFreeChart chart = ChartFactory.createBarChart(
                        "Statistiques des Étudiants",
                        "Type de Note",
                        "Valeur",
                        dataset,
                        PlotOrientation.VERTICAL,
                        false,  // pas de légende
                        true,
                        false
                );

                CategoryPlot plot = chart.getCategoryPlot();
                plot.setBackgroundPaint(Color.WHITE);
                plot.setRangeGridlinePaint(Color.GRAY);

                // Personnalisation du renderer pour afficher les labels au-dessus des barres
                BarRenderer renderer = (BarRenderer) plot.getRenderer();
                renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
                renderer.setDefaultItemLabelsVisible(true);
                renderer.setDefaultItemLabelFont(new Font("SansSerif", Font.BOLD, 14));
                renderer.setBarPainter(new org.jfree.chart.renderer.category.StandardBarPainter()); // pour avoir un rendu classique
                renderer.setSeriesPaint(0, new Color(79, 129, 189)); // bleu sympa pour les barres

                ChartPanel chartPanel = new ChartPanel(chart);
                chartPanel.setBounds(20, 20, 880, 550);

                panelStatistiques.removeAll();
                panelStatistiques.setLayout(null);
                panelStatistiques.add(chartPanel);
                panelStatistiques.revalidate();
                panelStatistiques.repaint();

            } else {
                JOptionPane.showMessageDialog(null, "Erreur lors de la récupération des statistiques.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {
        JFrame f = new FrontOffice();
        f.setVisible(true);
    }

}
