package musicplayer;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class MusicPlayer extends JFrame {
    private JButton playPauseButton, nextButton, prevButton, shuffleButton,
    	likeButton, dislikeButton, volumeButton, lyricsButton, playlistButton;
    private JLabel statusLabel, coverLabel, shuffleStatusLabel, currentTimeLabel, totalTimeLabel;
    private JSlider volumeSlider, progressSlider;
    private JFileChooser fileChooser;
    
    private File selectedFile;
    private List<File> playlist;
    private Clip audioClip;
    private FloatControl volumeControl;
    private Timer timer;
    
    private int currentIndex = 0;
    private long clipTimePosition = 0;
    private boolean isPlaying = false;
    private boolean isPaused = false;
    private boolean isAdjustingProgress = false;
    private boolean isShuffle = false;
    private boolean isLiked = false;
    private boolean isDisliked = false;
    
    private HashMap<String, Boolean> likeMap = new HashMap<>();
    private HashMap<String, Boolean> dislikeMap = new HashMap<>();

    private final String playIconPath = "image/play.jpg";
    private final String pauseIconPath = "image/pause.jpg";
    private final String nextIconPath = "image/next.jpg";
    private final String prevIconPath = "image/prev.jpg";
    private final String shuffleIconPath = "image/shuffle.jpg";
    private final String likeIconPath = "image/like.jpg";
    private final String dislikeIconPath = "image/dislike.jpg";
    private final String likePressedIconPath = "image/like_pressed.jpg";
    private final String dislikePressedIconPath = "image/dislike_pressed.jpg";
    private final String soundIconPath = "image/sound.jpg";
    private final String soundLowIconPath = "image/sound_low.jpg";
    private final String soundHighIconPath = "image/sound_high.jpg";
    private final String soundZeroIconPath = "image/sound_zero.jpg";
    private final String lyricsIconPath = "image/lyrics.jpg";
    private final String playlistIconPath = "image/playlist.jpg";

    public MusicPlayer() {
        setTitle("Music Player");
        setSize(350, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.LIGHT_GRAY);

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Open");
        JMenuItem openMenuItem = new JMenuItem("Open File");
        JMenuItem openFolderMenuItem = new JMenuItem("Open Folder");
        JMenuItem quickOpenMenuItem1 = new JMenuItem("Quick Open1");
        JMenuItem quickOpenMenuItem2 = new JMenuItem("Quick Open2");
        fileMenu.add(openMenuItem);
        fileMenu.add(openFolderMenuItem);
        fileMenu.add(quickOpenMenuItem1);
        fileMenu.add(quickOpenMenuItem2);

        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        JTabbedPane tabbedPane = new JTabbedPane();
        JPanel mainPanel = new JPanel(new BorderLayout());

        ImageIcon playIcon = resizeIcon(new ImageIcon(playIconPath), 50, 50);
        ImageIcon pauseIcon = resizeIcon(new ImageIcon(pauseIconPath), 50, 50);
        ImageIcon nextIcon = resizeIcon(new ImageIcon(nextIconPath), 50, 50);
        ImageIcon prevIcon = resizeIcon(new ImageIcon(prevIconPath), 50, 50);
        ImageIcon shuffleIcon = resizeIcon(new ImageIcon(shuffleIconPath), 50, 50);
        ImageIcon likeIcon = resizeIcon(new ImageIcon(likeIconPath), 35, 35);
        ImageIcon dislikeIcon = resizeIcon(new ImageIcon(dislikeIconPath), 35, 35);
        ImageIcon likePressedIcon = resizeIcon(new ImageIcon(likePressedIconPath), 35, 35);
        ImageIcon dislikePressedIcon = resizeIcon(new ImageIcon(dislikePressedIconPath), 35, 35);
        ImageIcon soundIcon = resizeIcon(new ImageIcon(soundIconPath), 50, 50);
        ImageIcon soundLowIcon = resizeIcon(new ImageIcon(soundLowIconPath), 50, 50);
        ImageIcon soundHighIcon = resizeIcon(new ImageIcon(soundHighIconPath), 50, 50);
        ImageIcon soundZeroIcon = resizeIcon(new ImageIcon(soundZeroIconPath), 50, 50);
        ImageIcon lyricsIcon = resizeIcon(new ImageIcon(lyricsIconPath), 50, 50);
        ImageIcon playlistIcon = resizeIcon(new ImageIcon(playlistIconPath), 50, 50);

        Dimension buttonSize = new Dimension(60, 60);

        playPauseButton = new JButton(playIcon);
        playPauseButton.setPreferredSize(buttonSize);
        makeButtonTransparent(playPauseButton);

        nextButton = new JButton(nextIcon);
        nextButton.setPreferredSize(buttonSize);
        makeButtonTransparent(nextButton);

        prevButton = new JButton(prevIcon);
        prevButton.setPreferredSize(buttonSize);
        makeButtonTransparent(prevButton);

        shuffleButton = new JButton(shuffleIcon);
        shuffleButton.setPreferredSize(buttonSize);
        makeButtonTransparent(shuffleButton);

        likeButton = new JButton(likeIcon);
        likeButton.setPreferredSize(buttonSize);
        makeButtonTransparent(likeButton);

        dislikeButton = new JButton(dislikeIcon);
        dislikeButton.setPreferredSize(buttonSize);
        makeButtonTransparent(dislikeButton);

        volumeButton = new JButton(soundIcon);
        volumeButton.setPreferredSize(buttonSize);
        makeButtonTransparent(volumeButton);

        lyricsButton = new JButton(lyricsIcon);
        lyricsButton.setPreferredSize(buttonSize);
        makeButtonTransparent(lyricsButton);

        playlistButton = new JButton(playlistIcon);
        playlistButton.setPreferredSize(buttonSize);
        makeButtonTransparent(playlistButton);

        statusLabel = new JLabel("No file selected");
        statusLabel.setForeground(Color.BLACK);
        coverLabel = new JLabel();
        coverLabel.setPreferredSize(new Dimension(200, 200));
        coverLabel.setHorizontalAlignment(JLabel.CENTER);

        shuffleStatusLabel = new JLabel("Shuffle: Off");
        shuffleStatusLabel.setForeground(Color.BLACK);

        fileChooser = new JFileChooser();

        volumeSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 70);
        volumeSlider.setMajorTickSpacing(10);
        volumeSlider.setPaintTicks(true);
        volumeSlider.setPaintLabels(true);
        volumeSlider.setOpaque(false);
        volumeSlider.setVisible(false);

        progressSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
        progressSlider.setPaintTicks(false);
        progressSlider.setPaintLabels(false);
        progressSlider.setOpaque(false);

        currentTimeLabel = new JLabel("00:00");
        totalTimeLabel = new JLabel("00:00");

        JPanel progressPanel = Panel.createProgressPanel(currentTimeLabel, progressSlider, totalTimeLabel);

        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.LIGHT_GRAY);
        topPanel.setLayout(new BorderLayout());
        topPanel.add(coverLabel, BorderLayout.CENTER);

        JPanel statusPanel = new JPanel();
        statusPanel.setBackground(Color.LIGHT_GRAY);
        statusPanel.setLayout(new FlowLayout());
        statusPanel.add(statusLabel);
        topPanel.add(statusPanel, BorderLayout.SOUTH);

        JPanel controlsPanel = new JPanel();
        controlsPanel.setBackground(Color.LIGHT_GRAY);
        controlsPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        Panel.setupButtons(controlsPanel, gbc, playPauseButton, nextButton, prevButton, shuffleButton, likeButton,
        		dislikeButton, volumeButton, lyricsButton, playlistButton, shuffleStatusLabel, progressPanel, volumeSlider);

        mainPanel.add(topPanel, BorderLayout.CENTER);
        mainPanel.add(controlsPanel, BorderLayout.SOUTH);

        tabbedPane.addTab("Player", mainPanel);
        tabbedPane.addTab("Liked Songs", Panel.createLikedSongsPanel(likeMap));
        tabbedPane.addTab("Disliked Songs", Panel.createDislikedSongsPanel(dislikeMap));

        add(tabbedPane, BorderLayout.CENTER);

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (audioClip != null && isPlaying) {
                    long currentMicroseconds = audioClip.getMicrosecondPosition();
                    long totalMicroseconds = audioClip.getMicrosecondLength();
                    int progress = (int) ((currentMicroseconds * 100) / totalMicroseconds);
                    isAdjustingProgress = true;
                    progressSlider.setValue(progress);
                    isAdjustingProgress = false;

                    currentTimeLabel.setText(formatTime(currentMicroseconds));
                    totalTimeLabel.setText(formatTime(totalMicroseconds));

                    if (audioClip.getFramePosition() == audioClip.getFrameLength()) {
                        playNext();
                    }
                }
            }
        });
        
        
        playlist = new ArrayList<>();
        
        openMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileOperations.openFile(fileChooser, playlist, currentIndex, MusicPlayer.this);
            }
        });

        openFolderMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileOperations.openFolder(fileChooser, playlist, currentIndex, MusicPlayer.this);
            }
        });

        quickOpenMenuItem1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileOperations.quickOpenFolder1(playlist, currentIndex, MusicPlayer.this);
            }
        });

        quickOpenMenuItem2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileOperations.quickOpenFolder2(playlist, currentIndex, MusicPlayer.this);
            }
        });

        playPauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isPlaying) {
                    pause();
                } else {
                    play();
                }
            }
        });

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedFile == null) {
                    JOptionPane.showMessageDialog(MusicPlayer.this, "No file selected", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    playNext();
                }
            }
        });

        prevButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedFile == null) {
                    JOptionPane.showMessageDialog(MusicPlayer.this, "No file selected", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    playPrevious();
                }
            }
        });

        shuffleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleShuffle();
            }
        });

        likeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedFile == null) {
                    JOptionPane.showMessageDialog(MusicPlayer.this, "No file selected", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    toggleLike();
                    Panel.updateLikedSongsPanel(likeMap, MusicPlayer.this);
                }
            }
        });

        dislikeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedFile == null) {
                    JOptionPane.showMessageDialog(MusicPlayer.this, "No file selected", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    toggleDislike();
                    Panel.updateDislikedSongsPanel(dislikeMap, MusicPlayer.this);
                }
            }
        });

        volumeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                volumeSlider.setVisible(!volumeSlider.isVisible());
                controlsPanel.revalidate();
                controlsPanel.repaint();
            }
        });

        lyricsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayLyrics();
            }
        });

        playlistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayPlaylist();
            }
        });

        volumeSlider.addChangeListener(e -> {
            if (volumeControl != null) {
                float volume = volumeSlider.getValue() / 100.0f;
                volumeControl.setValue(volumeControl.getMinimum() + (volumeControl.getMaximum() - volumeControl.getMinimum()) * volume);
                if (volumeSlider.getValue() == 0) {
                    volumeButton.setIcon(resizeIcon(new ImageIcon(soundZeroIconPath), 50, 50));
                } else if (volumeSlider.getValue() < 50) {
                    volumeButton.setIcon(resizeIcon(new ImageIcon(soundLowIconPath), 50, 50));
                } else {
                    volumeButton.setIcon(resizeIcon(new ImageIcon(soundHighIconPath), 50, 50));
                }
            }
        });

        progressSlider.addChangeListener(e -> {
            if (audioClip != null && !isAdjustingProgress) {
                if (isPlaying || isPaused) {
                    int progress = progressSlider.getValue();
                    long newPosition = (long) (audioClip.getMicrosecondLength() * (progress / 100.0));
                    audioClip.setMicrosecondPosition(newPosition);
                    if (isPlaying) {
                        clipTimePosition = newPosition;
                        audioClip.start();
                    }
                }
                if (audioClip != null && audioClip.getMicrosecondPosition() >= audioClip.getMicrosecondLength()) {
                    playNext();
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MusicPlayer().setVisible(true);
            }
        });
    }

    private ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        Image image = icon.getImage();
        Image resizedImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }

    private void makeButtonTransparent(JButton button) {
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
    }

    private void toggleShuffle() {
        isShuffle = !isShuffle;
        shuffleStatusLabel.setText("Shuffle: " + (isShuffle ? "On" : "Off"));
    }

    private void toggleLike() {
        if (isLiked) {
            isLiked = false;
            likeButton.setIcon(resizeIcon(new ImageIcon(likeIconPath), 35, 35));
        } else {
            isLiked = true;
            isDisliked = false;
            likeButton.setIcon(resizeIcon(new ImageIcon(likePressedIconPath), 35, 35));
            dislikeButton.setIcon(resizeIcon(new ImageIcon(dislikeIconPath), 35, 35));
        }
        if (selectedFile != null) {
            likeMap.put(selectedFile.getName(), isLiked);
        }
    }

    private void toggleDislike() {
        if (isDisliked) {
            isDisliked = false;
            dislikeButton.setIcon(resizeIcon(new ImageIcon(dislikeIconPath), 35, 35));
        } else {
            int result = JOptionPane.showConfirmDialog(this, "Are you sure?", "think about it again :(", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                isDisliked = true;
                isLiked = false;
                dislikeButton.setIcon(resizeIcon(new ImageIcon(dislikePressedIconPath), 35, 35));
                likeButton.setIcon(resizeIcon(new ImageIcon(likeIconPath), 35, 35));
            }
        }
        if (selectedFile != null) {
            dislikeMap.put(selectedFile.getName(), isDisliked);
        }
    }

    private String formatTime(long microseconds) {
        long totalSeconds = microseconds / 1_000_000;
        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    private void playNext() {
        if (playlist.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No file selected", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (isShuffle) {
            currentIndex = (int) (Math.random() * playlist.size());
        } else {
            currentIndex = (currentIndex + 1) % playlist.size();
        }
        playSelectedFile();
        
    }

    public void playSelectedFile() {
        if (currentIndex >= 0 && currentIndex < playlist.size()) {
            selectedFile = playlist.get(currentIndex);
            resetLikeDislike();
            playAudioFile(selectedFile);
        }
    }

    private void playAudioFile(File file) {
        try {
            if (audioClip != null && audioClip.isOpen()) {
                audioClip.close();
            }
            audioClip = AudioSystem.getClip();
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            audioClip.open(audioStream);
            volumeControl = (FloatControl) audioClip.getControl(FloatControl.Type.MASTER_GAIN);
            setVolume(volumeSlider.getValue());
            FileOperations.updateAlbumCover(file, coverLabel);
            audioClip.start();
            isPlaying = true;
            isPaused = false;
            playPauseButton.setIcon(resizeIcon(new ImageIcon(pauseIconPath), 50, 50));
            String fileName = file.getName();
            if (fileName.contains(".")) {
                fileName = fileName.substring(0, fileName.lastIndexOf('.'));
            }
            statusLabel.setText("Playing: " + fileName);
            timer.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            ex.printStackTrace();
        }
    }

    private void play() {
        if (selectedFile == null) {
            JOptionPane.showMessageDialog(this, "No file selected", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (isPaused) {
            audioClip.setMicrosecondPosition(clipTimePosition);
            audioClip.start();
            isPlaying = true;
            isPaused = false;
            playPauseButton.setIcon(resizeIcon(new ImageIcon(pauseIconPath), 50, 50));
            String fileName = selectedFile.getName();
            if (fileName.contains(".")) {
                fileName = fileName.substring(0, fileName.lastIndexOf('.'));
            }
            statusLabel.setText("Playing: " + fileName);
        } else if (selectedFile != null) {
            playAudioFile(selectedFile);
        }
    }

    private void pause() {
        clipTimePosition = audioClip.getMicrosecondPosition();
        audioClip.stop();
        isPlaying = false;
        isPaused = true;
        playPauseButton.setIcon(resizeIcon(new ImageIcon(playIconPath), 50, 50));
        String fileName = selectedFile.getName();
        if (fileName.contains(".")) {
            fileName = fileName.substring(0, fileName.lastIndexOf('.'));
        }
        statusLabel.setText("Paused: " + fileName);
    }

    private void playPrevious() {
        if (playlist.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No file selected", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (isShuffle) {
            currentIndex = (int) (Math.random() * playlist.size());
        } else {
            currentIndex = (currentIndex - 1 + playlist.size()) % playlist.size();
        }
        playSelectedFile();
    }

    private void displayLyrics() {
        if (selectedFile != null) {
            FileOperations.displayLyrics(selectedFile);
        }
    }

    private void displayPlaylist() {
        StringBuilder playlistContent = new StringBuilder();
        if (playlist.size() == 0) {
            JOptionPane.showMessageDialog(this, "No file selected", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        for (int i = 0; i < playlist.size(); i++) {
            String fileName = playlist.get(i).getName();
            if (fileName.endsWith(".wav")) {
                fileName = fileName.substring(0, fileName.lastIndexOf('.'));
            }
            playlistContent.append((i + 1)).append(". ").append(fileName).append("\n");
        }
        JOptionPane.showMessageDialog(this, playlistContent.toString(), "Playlist", JOptionPane.INFORMATION_MESSAGE);
    }

    private void resetLikeDislike() {
        isLiked = false;
        isDisliked = false;
        likeButton.setIcon(resizeIcon(new ImageIcon(likeIconPath), 35, 35));
        dislikeButton.setIcon(resizeIcon(new ImageIcon(dislikeIconPath), 35, 35));
        if (selectedFile != null) {
            String fileName = selectedFile.getName();
            if (likeMap.containsKey(fileName)) {
                isLiked = likeMap.get(fileName);
                likeButton.setIcon(resizeIcon(new ImageIcon(isLiked ? likePressedIconPath : likeIconPath), 35, 35));
            }
            if (dislikeMap.containsKey(fileName)) {
                isDisliked = dislikeMap.get(fileName);
                dislikeButton.setIcon(resizeIcon(new ImageIcon(isDisliked ? dislikePressedIconPath : dislikeIconPath), 35, 35));
            }
        }
    }

    private void setVolume(int volume) {
        if (volumeControl != null) {
            float volumeLevel = volume / 100.0f;
            volumeControl.setValue(volumeControl.getMinimum() + (volumeControl.getMaximum() - volumeControl.getMinimum()) * volumeLevel);
        }
    }
    
    public void setCurrentIndex(int index) {
        this.currentIndex = index;
    }
}
