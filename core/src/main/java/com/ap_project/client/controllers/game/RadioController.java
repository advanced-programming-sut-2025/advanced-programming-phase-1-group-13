package com.ap_project.client.controllers.game;

import com.ap_project.common.models.App;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.awt.*;

public class RadioController {
    public void handleFileSelection(Runnable onFileAdded) {
        Gdx.app.postRunnable(() -> chooseMusicFile(onFileAdded));
    }

    public void chooseMusicFile(Runnable onFileAdded) {
        FileDialog dialog = new FileDialog((Frame) null, "Upload Music", FileDialog.LOAD);
        dialog.setVisible(true);

        if (dialog.getFile() != null) {
            String selectedFilePath = dialog.getDirectory() + dialog.getFile();
            addMusicFromFile(selectedFilePath, onFileAdded);
        }

        dialog.dispose();
    }

    public void addMusicFromFile(String filePath, Runnable onSuccess) {
        if (filePath == null) return;

        try {
            FileHandle sourceFile = Gdx.files.absolute(filePath);
            FileHandle musicDir = Gdx.files.local("Music/");
            if (!musicDir.exists()) musicDir.mkdirs();

            FileHandle destFile = musicDir.child(sourceFile.name());
            sourceFile.copyTo(destFile);

            App.getLoggedIn().getMusic().add("Music/" + sourceFile.name());

            if (onSuccess != null) {
                Gdx.app.postRunnable(onSuccess);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
