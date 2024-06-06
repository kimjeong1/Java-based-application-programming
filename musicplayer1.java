import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class musicplayer extends JFrame {
    private JButton playButton, pauseButton, stopButton;
    private JLabel statusLabel;
    private JSlider volumeSlider;
    private JSlider progressSlider;
    private JFileChooser fileChooser;
    private File selectedFile;
    private Clip audioClip;
    private boolean isPlaying = false;
    private boolean isPaused = false;
    private boolean isAdjustingProgress = false;
    private long clipTimePosition = 0;
    private FloatControl volumeControl;
    private Timer timer;

    public musicplayer() {
        setTitle("Simple Music Player");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        // Create Menu Bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem openMenuItem = new JMenuItem("Open");
        fileMenu.add(openMenuItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        playButton = new JButton("Play");
        pauseButton = new JButton("Pause");
        stopButton = new JButton("Stop");

        statusLabel = new JLabel("No file selected");

        fileChooser = new JFileChooser();

        volumeSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        volumeSlider.setMajorTickSpacing(10);
        volumeSlider.setPaintTicks(true);
        volumeSlider.setPaintLabels(true);
        volumeSlider.addChangeListener(e -> {
            if (volumeControl != null) {
                float volume = volumeSlider.getValue() / 100.0f;
                volumeControl.setValue(volumeControl.getMinimum() + (volumeControl.getMaximum() - volumeControl.getMinimum()) * volume);
            }
        });

        progressSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
        progressSlider.setMajorTickSpacing(10);
        progressSlider.setPaintTicks(true);
        progressSlider.setPaintLabels(true);
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
            }
        });

        add(playButton);
        add(pauseButton);
        add(stopButton);
        add(volumeSlider);
        add(progressSlider);
        add(statusLabel);

        playButton.addActionListener(new PlayAction());
        pauseButton.addActionListener(new PauseAction());
        stopButton.addActionListener(new StopAction());
        openMenuItem.addActionListener(new OpenAction());

        timer = new Timer(1000, e -> {
            if (audioClip != null && isPlaying) {
                isAdjustingProgress = true;
                int progress = (int) ((audioClip.getMicrosecondPosition() / (double) audioClip.getMicrosecondLength()) * 100);
                progressSlider.setValue(progress);
                isAdjustingProgress = false;
            }
        });
        timer.start();

        setVisible(true);
    }

    private class PlayAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (selectedFile != null) {
                if (isPaused) {
                    audioClip.setMicrosecondPosition(clipTimePosition);
                    audioClip.start();
                    isPaused = false;
                    statusLabel.setText("Playing: " + selectedFile.getName());
                } else if (!isPlaying) {
                    try {
                        AudioInputStream audioStream = AudioSystem.getAudioInputStream(selectedFile);
                        audioClip = AudioSystem.getClip();
                        audioClip.open(audioStream);
                        volumeControl = (FloatControl) audioClip.getControl(FloatControl.Type.MASTER_GAIN);
                        audioClip.start();
                        isPlaying = true;
                        statusLabel.setText("Playing: " + selectedFile.getName());
                    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                        ex.printStackTrace();
                        statusLabel.setText("Error playing file.");
                    }
                }
            } else {
                statusLabel.setText("No file selected");
            }
        }
    }

    private class PauseAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (audioClip != null && isPlaying) {
                clipTimePosition = audioClip.getMicrosecondPosition();
                audioClip.stop();
                isPaused = true;
                statusLabel.setText("Paused: " + selectedFile.getName());
            }
        }
    }

    private class StopAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (audioClip != null && (isPlaying || isPaused)) {
                audioClip.stop();
                audioClip.close();
                isPlaying = false;
                isPaused = false;
                clipTimePosition = 0;
                progressSlider.setValue(0);
                statusLabel.setText("Stopped: " + selectedFile.getName());
            }
        }
    }

    private class OpenAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                selectedFile = fileChooser.getSelectedFile();
                statusLabel.setText("Selected: " + selectedFile.getName());
                if (isPlaying || isPaused) {
                    audioClip.stop();
                    audioClip.close();
                    isPlaying = false;
                    isPaused = false;
                    clipTimePosition = 0;
                    progressSlider.setValue(0);
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(musicplayer::new);
    }
}
