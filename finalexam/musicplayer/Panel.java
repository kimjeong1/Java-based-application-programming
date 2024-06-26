package musicplayer;

import javax.sound.sampled.FloatControl;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class Panel {

    public static JPanel createLikedSongsPanel(HashMap<String, Boolean> likeMap) {
        JPanel likedSongsPanel = new JPanel(new BorderLayout());
        JTextArea likedSongsArea = new JTextArea();
        likedSongsArea.setEditable(false);
        updateLikedSongsArea(likedSongsArea, likeMap);
        JScrollPane scrollPane = new JScrollPane(likedSongsArea);
        likedSongsPanel.add(scrollPane, BorderLayout.CENTER);
        return likedSongsPanel;
    }

    private static void updateLikedSongsArea(JTextArea likedSongsArea, HashMap<String, Boolean> likeMap) {
        StringBuilder likedSongsContent = new StringBuilder();
        for (String fileName : likeMap.keySet()) {
            if (likeMap.get(fileName)) {
                if (fileName.endsWith(".wav")) {
                    fileName = fileName.substring(0, fileName.lastIndexOf('.'));
                }
                likedSongsContent.append(fileName).append("\n");
            }
        }
        likedSongsArea.setText(likedSongsContent.toString());
    }

    public static void updateLikedSongsPanel(HashMap<String, Boolean> likeMap, JFrame frame) {
        JPanel likedSongsPanel = (JPanel) ((JTabbedPane) frame.getContentPane().getComponent(0)).getComponentAt(1);
        JTextArea likedSongsArea = (JTextArea) ((JScrollPane) likedSongsPanel.getComponent(0)).getViewport().getView();
        updateLikedSongsArea(likedSongsArea, likeMap);
    }

    public static JPanel createDislikedSongsPanel(HashMap<String, Boolean> dislikeMap) {
        JPanel dislikedSongsPanel = new JPanel(new BorderLayout());
        JTextArea dislikedSongsArea = new JTextArea();
        dislikedSongsArea.setEditable(false);
        updateDislikedSongsArea(dislikedSongsArea, dislikeMap);
        JScrollPane scrollPane = new JScrollPane(dislikedSongsArea);
        dislikedSongsPanel.add(scrollPane, BorderLayout.CENTER);
        return dislikedSongsPanel;
    }

    private static void updateDislikedSongsArea(JTextArea dislikedSongsArea, HashMap<String, Boolean> dislikeMap) {
        StringBuilder dislikedSongsContent = new StringBuilder();
        for (String fileName : dislikeMap.keySet()) {
            if (dislikeMap.get(fileName)) {
                if (fileName.endsWith(".wav")) {
                    fileName = fileName.substring(0, fileName.lastIndexOf('.'));
                }
                dislikedSongsContent.append(fileName).append("\n");
            }
        }
        dislikedSongsArea.setText(dislikedSongsContent.toString());
    }

    public static void updateDislikedSongsPanel(HashMap<String, Boolean> dislikeMap, JFrame frame) {
        JPanel dislikedSongsPanel = (JPanel) ((JTabbedPane) frame.getContentPane().getComponent(0)).getComponentAt(2);
        JTextArea dislikedSongsArea = (JTextArea) ((JScrollPane) dislikedSongsPanel.getComponent(0)).getViewport().getView();
        updateDislikedSongsArea(dislikedSongsArea, dislikeMap);
    }


    public static void setupButtons(JPanel controlsPanel, GridBagConstraints gbc, JButton playPauseButton, JButton nextButton,
    		JButton prevButton, JButton shuffleButton, JButton likeButton, JButton dislikeButton, JButton volumeButton,
    		JButton lyricsButton, JButton playlistButton, JLabel shuffleStatusLabel, JPanel progressPanel, JSlider volumeSlider) {
      
    	// Playlist button
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        controlsPanel.add(playlistButton, gbc);

        // Lyrics button
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        controlsPanel.add(lyricsButton, gbc);

        // Like button
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        controlsPanel.add(likeButton, gbc);

        // Dislike button
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        controlsPanel.add(dislikeButton, gbc);

        // Previous button
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        controlsPanel.add(prevButton, gbc);

        // Play/Pause button
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        controlsPanel.add(playPauseButton, gbc);

        // Next button
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        controlsPanel.add(nextButton, gbc);

        // Shuffle button
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        controlsPanel.add(shuffleButton, gbc);

        // Shuffle status label
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        controlsPanel.add(shuffleStatusLabel, gbc);

        // Progress slider
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        controlsPanel.add(progressPanel, gbc);

        // Volume button
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        controlsPanel.add(volumeButton, gbc);

        // Volume slider
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 3;
        gbc.weightx = 1;
        controlsPanel.add(volumeSlider, gbc);
    }

    public static JPanel createProgressPanel(JLabel currentTimeLabel, JSlider progressSlider, JLabel totalTimeLabel) {
        JPanel progressPanel = new JPanel(new BorderLayout());
        progressPanel.setOpaque(false);
        progressPanel.add(currentTimeLabel, BorderLayout.WEST);
        progressPanel.add(progressSlider, BorderLayout.CENTER);
        progressPanel.add(totalTimeLabel, BorderLayout.EAST);
        return progressPanel;
    }

    public static JPanel createVolumePanel(JButton volumeButton, JSlider volumeSlider, FloatControl volumeControl) {
        JPanel volumePanel = new JPanel();
        volumePanel.setLayout(new BorderLayout());
        volumePanel.setOpaque(false);
        volumePanel.add(volumeButton, BorderLayout.WEST);
        volumePanel.add(volumeSlider, BorderLayout.CENTER);
        volumeSlider.addChangeListener(e -> {
            if (volumeControl != null) {
                float volume = volumeSlider.getValue() / 100.0f;
                volumeControl.setValue(volumeControl.getMinimum() + (volumeControl.getMaximum() - volumeControl.getMinimum()) * volume);
            }
        });
        return volumePanel;
    }
}
