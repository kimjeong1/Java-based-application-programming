package musicplayer;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

public class FileOperations {
    public static void openFile(JFileChooser fileChooser, List<File> playlist, int currentIndex, MusicPlayer musicPlayer) {
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showOpenDialog(musicPlayer);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            playlist.clear();
            playlist.add(selectedFile);
            musicPlayer.setCurrentIndex(0);
            musicPlayer.playSelectedFile();
        }
    }

    public static void openFolder(JFileChooser fileChooser, List<File> playlist, int currentIndex, MusicPlayer musicPlayer) {
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = fileChooser.showOpenDialog(musicPlayer);
        if (result == JFileChooser.APPROVE_OPTION) {
            File folder = fileChooser.getSelectedFile();
            File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".wav"));
            if (files != null) {
                playlist.clear();
                Collections.addAll(playlist, files);
                musicPlayer.setCurrentIndex(0);
                musicPlayer.playSelectedFile();
            }
        }
    }

    public static void quickOpenFolder1(List<File> playlist, int currentIndex, MusicPlayer musicPlayer) {
        File folder = new File("audio");
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".wav"));
            if (files != null) {
                playlist.clear();
                Collections.addAll(playlist, files);
                musicPlayer.setCurrentIndex(0);
                musicPlayer.playSelectedFile();
            } 
        }
    }

    public static void quickOpenFolder2(List<File> playlist, int currentIndex, MusicPlayer musicPlayer) {
        File folder = new File("audio2");
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".wav"));
            if (files != null) {
                playlist.clear();
                Collections.addAll(playlist, files);
                musicPlayer.setCurrentIndex(0);
                musicPlayer.playSelectedFile();
            } 
        }
    }

    public static void updateAlbumCover(File musicFile, JLabel coverLabel) {
        String imagePath = "album_cover/" + musicFile.getName().replace(".wav", ".jpg");
        String noimagePath = "album_cover/no_album_cover.jpg";
        File imageFile = new File(imagePath);
        if (imageFile.exists()) {
            ImageIcon coverIcon = new ImageIcon(imagePath);
            coverLabel.setIcon(new ImageIcon(coverIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH)));
        } else {
            coverLabel.setIcon(null);
            ImageIcon coverIcon = new ImageIcon(noimagePath);
            coverLabel.setIcon(new ImageIcon(coverIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH)));
        }
    }

    public static void displayLyrics(File selectedFile) {
        String lyricsPath = "lyrics/" + selectedFile.getName().replace(".wav", ".txt");
        File lyricsFile = new File(lyricsPath);
        if (lyricsFile.exists()) {
            try {
                String lyrics = new String(Files.readAllBytes(Paths.get(lyricsFile.getAbsolutePath())), "UTF-8");
                JTextArea textArea = new JTextArea(lyrics);
                textArea.setWrapStyleWord(true);
                textArea.setLineWrap(true);
                textArea.setEditable(false);
                textArea.setCaretPosition(0);

                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setPreferredSize(new Dimension(350, 200));
                scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

                JDialog dialog = new JDialog();
                String fileName = selectedFile.getName();
                if (fileName.contains(".")) {
                    fileName = fileName.substring(0, fileName.lastIndexOf('.'));
                }
                dialog.setTitle("Lyrics - " + fileName);
                dialog.getContentPane().add(scrollPane);
                dialog.pack();
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Lyrics not found", "Lyrics", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
