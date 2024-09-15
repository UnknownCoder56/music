package com.uniqueapps.music;

import javafx.application.Platform;
import javafx.scene.control.Alert;

import javax.sound.midi.*;

public class Player implements Runnable {

    private final int instrument;
    private final int note;
    private final int tick;

    public Player(int instrument, int note, int tick) {
        this.instrument = instrument;
        this.note = note;
        this.tick = tick;
    }

    @Override
    public void run() {
        try {
            Sequencer player = MidiSystem.getSequencer();
            player.open();

            Sequence seq = new Sequence(Sequence.PPQ, 4);
            Track track = seq.createTrack();

            ShortMessage first = new ShortMessage();
            first.setMessage(192, 1, instrument, 0);
            MidiEvent changeInstrument = new MidiEvent(first, 1);
            track.add(changeInstrument);

            ShortMessage a = new ShortMessage();
            a.setMessage(144, 1, note, 100);
            MidiEvent noteOn = new MidiEvent(a, 1);
            track.add(noteOn);

            ShortMessage b = new ShortMessage();
            b.setMessage(128, 1, note, 100);
            MidiEvent noteOff = new MidiEvent(b, tick);
            track.add(noteOff);

            player.setSequence(seq);
            player.start();

            while (true) {
                if (!player.isRunning()) {
                    player.close();
                    return;
                }
            }
        } catch (InvalidMidiDataException e) {
            Platform.runLater(() -> new Alert(Alert.AlertType.ERROR, "Invalid input").showAndWait());
        } catch (MidiUnavailableException e) {
            Platform.runLater(() -> {
                new Alert(Alert.AlertType.ERROR, "MIDI system is unavailable").showAndWait();
                System.exit(1);
            });
        }
    }
}
