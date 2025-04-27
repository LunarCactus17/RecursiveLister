import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Objects;

public class RecursiveLister extends JFrame {

    private JTextArea fileTextArea;
    private JScrollPane scrollPane;
    private JButton startButton;
    private JButton quitButton;
    private JLabel titleLabel;

    public RecursiveLister() {
        setTitle("Recursive File Lister"); // Set the title of the JFrame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Ensure the application closes on window close
        setSize(600, 400); // Set a decent size for the window
        setLocationRelativeTo(null); // Center the window on the screen
        setLayout(new BorderLayout()); // Use BorderLayout for better organization

        // Create GUI components
        titleLabel = new JLabel("File Lister", SwingConstants.CENTER); // Title at the top
        fileTextArea = new JTextArea();
        scrollPane = new JScrollPane(fileTextArea); // Wrap the JTextArea in a JScrollPane
        startButton = new JButton("Start");
        quitButton = new JButton("Quit");

        // Add components to the JFrame
        add(titleLabel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(); // Panel for buttons to keep them together
        buttonPanel.add(startButton);
        buttonPanel.add(quitButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add ActionListener to the Start button
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // Only allow directory selection
                int result = fileChooser.showOpenDialog(RecursiveLister.this); // Open the file chooser

                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedDirectory = fileChooser.getSelectedFile();
                    fileTextArea.setText(""); // Clear previous text
                    listFilesRecursive(selectedDirectory); // Start the recursive listing
                }
            }
        });

        // Add ActionListener to the Quit button
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Exit the application
            }
        });

        setVisible(true); // Make the JFrame visible
    }

    private void listFilesRecursive(File directory) {
        File[] files = directory.listFiles(); // Get all files and directories in the current directory
        if (files != null) {
            for (File file : files) {
                fileTextArea.append(file.getAbsolutePath() + "\n"); // Display the file path
                if (file.isDirectory()) {
                    listFilesRecursive(file); // Recursive call for subdirectories
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() { // Use SwingUtilities.invokeLater for thread safety
            public void run() {
                new RecursiveLister(); // Create and display the GUI
            }
        });
    }
}