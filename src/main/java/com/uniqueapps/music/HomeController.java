package com.uniqueapps.music;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML private TextField instrument;
    @FXML private TextField note;
    @FXML private TextField tick;
    @FXML private TextArea orchestraList;
    @FXML private Button play;
    @FXML private Button loopInstruments;
    @FXML private Button loopNotes;
    @FXML private Button loopAll;
    @FXML private Button loopRandom;
    @FXML private CheckBox wait;

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        StringBuilder sb = new StringBuilder();
        sb.append("Instruments:\n");
        try (Synthesizer synthesizer = MidiSystem.getSynthesizer()) {
            synthesizer.open();
            Instrument[] orchestra = synthesizer.getLoadedInstruments();
            for (int i = 0; i < 128; i++) {
                sb.append("\n").append(i).append(") ").append(orchestra[i].getName());
            }
        } catch (MidiUnavailableException e) {
            new Alert(Alert.AlertType.ERROR, "MIDI system is unavailable").showAndWait();
            System.exit(1);
        }
        orchestraList.setText(sb.toString());
    }

    @FXML
    private void playClicked() {
        try {
            Thread.startVirtualThread(new Player(Integer.parseInt(instrument.getText()), Integer.parseInt(note.getText()), Integer.parseInt(tick.getText())));
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Invalid input").showAndWait();
        }
    }

    @FXML
    private void loopInstrumentsClicked() {
        new Thread(() -> {
            try {
                Platform.runLater(() -> {
                    instrument.setDisable(true);
                    play.setDisable(true);
                    loopInstruments.setDisable(true);
                    loopNotes.setDisable(true);
                    loopAll.setDisable(true);
                    loopRandom.setDisable(true);
                });
                for (int i = 0; i <= 127; i++) {
                    final int x = i;
                    if (Integer.parseInt(note.getText()) >= 0 && Integer.parseInt(note.getText()) <= 127) {
                        Platform.runLater(() -> instrument.setText(String.valueOf(x)));
                        Thread thread = Thread.startVirtualThread(new Player(x, Integer.parseInt(note.getText()), Integer.parseInt(tick.getText())));
                        if (wait.isSelected()) thread.join();
                    } else {
                        Platform.runLater(() -> new Alert(Alert.AlertType.ERROR, "Invalid input").showAndWait());
                        break;
                    }
                }
            } catch (NumberFormatException | InterruptedException e) {
                Platform.runLater(() -> new Alert(Alert.AlertType.ERROR, "Invalid input").showAndWait());
            } finally {
                Platform.runLater(() -> {
                    instrument.setDisable(false);
                    play.setDisable(false);
                    loopInstruments.setDisable(false);
                    loopNotes.setDisable(false);
                    loopAll.setDisable(false);
                    loopRandom.setDisable(false);
                });
            }
        }).start();
    }

    @FXML
    private void loopNotesClicked() {
        new Thread(() -> {
            try {
                Platform.runLater(() -> {
                    note.setDisable(true);
                    play.setDisable(true);
                    loopInstruments.setDisable(true);
                    loopNotes.setDisable(true);
                    loopAll.setDisable(true);
                    loopRandom.setDisable(true);
                });
                for (int i = 0; i <= 127; i++) {
                    final int x = i;
                    if (Integer.parseInt(instrument.getText()) >= 0 && Integer.parseInt(instrument.getText()) <= 127) {
                        Platform.runLater(() -> note.setText(String.valueOf(x)));
                        Thread thread = Thread.startVirtualThread(new Player(Integer.parseInt(instrument.getText()), x, Integer.parseInt(tick.getText())));
                        if (wait.isSelected()) thread.join();
                    } else {
                        Platform.runLater(() -> new Alert(Alert.AlertType.ERROR, "Invalid input").showAndWait());
                        break;
                    }
                }
            } catch (NumberFormatException | InterruptedException e) {
                Platform.runLater(() -> new Alert(Alert.AlertType.ERROR, "Invalid input").showAndWait());
            } finally {
                Platform.runLater(() -> {
                    note.setDisable(false);
                    play.setDisable(false);
                    loopInstruments.setDisable(false);
                    loopNotes.setDisable(false);
                    loopAll.setDisable(false);
                    loopRandom.setDisable(false);
                });
            }
        }).start();
    }

    @FXML
    private void loopAllClicked() {
        new Thread(() -> {
            try {
                Platform.runLater(() -> {
                    instrument.setDisable(true);
                    note.setDisable(true);
                    play.setDisable(true);
                    loopInstruments.setDisable(true);
                    loopNotes.setDisable(true);
                    loopAll.setDisable(true);
                    loopRandom.setDisable(true);
                });
                for (int i = 0; i <= 127; i++) {
                    for (int j = 0; j <= 127; j++) {
                        final int x = i;
                        final int y = j;
                        Platform.runLater(() -> note.setText(String.valueOf(x)));
                        Platform.runLater(() -> instrument.setText(String.valueOf(y)));
                        Thread thread = Thread.startVirtualThread(new Player(y, x, Integer.parseInt(tick.getText())));
                        if (wait.isSelected()) thread.join();
                    }
                }
            } catch (NumberFormatException | InterruptedException e) {
                Platform.runLater(() -> new Alert(Alert.AlertType.ERROR, "Invalid input").showAndWait());
            } finally {
                Platform.runLater(() -> {
                    instrument.setDisable(false);
                    note.setDisable(false);
                    play.setDisable(false);
                    loopInstruments.setDisable(false);
                    loopNotes.setDisable(false);
                    loopAll.setDisable(false);
                    loopRandom.setDisable(false);
                });
            }
        }).start();
    }

    @FXML
    private void loopRandomClicked() {
        new Thread(() -> {
            try {
                Platform.runLater(() -> {
                    instrument.setDisable(true);
                    note.setDisable(true);
                    play.setDisable(true);
                    loopInstruments.setDisable(true);
                    loopNotes.setDisable(true);
                    loopAll.setDisable(true);
                    loopRandom.setDisable(true);
                });
                for (int i = 1; i <= 5; i++) {
                    final int x = (int) (Math.random() * 128);
                    final int y = (int) (Math.random() * 128);
                    Platform.runLater(() -> note.setText(String.valueOf(x)));
                    Platform.runLater(() -> instrument.setText(String.valueOf(y)));
                    Thread thread = Thread.startVirtualThread(new Player(y, x, Integer.parseInt(tick.getText())));
                    if (wait.isSelected()) thread.join();
                }
            } catch (NumberFormatException | InterruptedException e) {
                Platform.runLater(() -> new Alert(Alert.AlertType.ERROR, "Invalid input").showAndWait());
            } finally {
                Platform.runLater(() -> {
                    instrument.setDisable(false);
                    note.setDisable(false);
                    play.setDisable(false);
                    loopInstruments.setDisable(false);
                    loopNotes.setDisable(false);
                    loopAll.setDisable(false);
                    loopRandom.setDisable(false);
                });
            }
        }).start();
    }
}