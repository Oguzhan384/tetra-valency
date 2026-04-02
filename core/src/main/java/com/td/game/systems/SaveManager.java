package com.td.game.systems;

import java.io.File;

public class SaveManager {

    private static final String LEGACY_SAVE_DIR = "Documents/TetraValency/";
    private static final String SAVE_DIR_NAME = "saves";

    public static final String SAVE_FILE_ELEMENTAL_CASTLE = "save_elemental_castle.json";
    public static final String SAVE_FILE_DESERT_OASIS = "save_desert_oasis.json";

    private static String getSaveFileName(com.td.game.map.GameMap.MapType mapType) {
        return mapType == com.td.game.map.GameMap.MapType.DESERT_OASIS
                ? SAVE_FILE_DESERT_OASIS
                : SAVE_FILE_ELEMENTAL_CASTLE;
    }

    private static File findProjectRootDir() {
        File current = new File(System.getProperty("user.dir", ".")).getAbsoluteFile();
        File cursor = current;
        while (cursor != null) {
            boolean hasSettings = new File(cursor, "settings.gradle").exists();
            boolean hasCore = new File(cursor, "core").isDirectory();
            if (hasSettings || hasCore) {
                return cursor;
            }
            cursor = cursor.getParentFile();
        }
        return current;
    }

    private static com.badlogic.gdx.files.FileHandle getProjectSaveDir() {
        File saveDir = new File(findProjectRootDir(), SAVE_DIR_NAME);
        com.badlogic.gdx.files.FileHandle dir = com.badlogic.gdx.Gdx.files.absolute(saveDir.getAbsolutePath());
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    public static com.badlogic.gdx.files.FileHandle getSharedSaveFile(String fileName) {
        return getProjectSaveDir().child(fileName);
    }

    private static com.badlogic.gdx.files.FileHandle getLocalSaveFile(com.td.game.map.GameMap.MapType mapType) {
        return getProjectSaveDir().child(getSaveFileName(mapType));
    }

    private static com.badlogic.gdx.files.FileHandle getLegacySaveFile(com.td.game.map.GameMap.MapType mapType) {
        return com.badlogic.gdx.Gdx.files.external(LEGACY_SAVE_DIR).child(getSaveFileName(mapType));
    }

    private static void migrateLegacySaveIfNeeded(com.td.game.map.GameMap.MapType mapType) {
        com.badlogic.gdx.files.FileHandle targetFile = getLocalSaveFile(mapType);
        if (targetFile.exists()) {
            return;
        }

        com.badlogic.gdx.files.FileHandle oldLocalFile = com.badlogic.gdx.Gdx.files.local("saves/" + getSaveFileName(mapType));
        if (oldLocalFile.exists()) {
            targetFile.writeString(oldLocalFile.readString(), false, "UTF-8");
            com.badlogic.gdx.Gdx.app.log("SaveManager",
                    "Migrated local save to " + targetFile.file().getAbsolutePath());
            return;
        }

        com.badlogic.gdx.files.FileHandle oldInternalFile = com.badlogic.gdx.Gdx.files.internal("saves/" + getSaveFileName(mapType));
        if (oldInternalFile.exists()) {
            targetFile.writeString(oldInternalFile.readString(), false, "UTF-8");
            com.badlogic.gdx.Gdx.app.log("SaveManager",
                    "Migrated internal save to " + targetFile.file().getAbsolutePath());
            return;
        }

        com.badlogic.gdx.files.FileHandle oldAssetsInternalFile = com.badlogic.gdx.Gdx.files.internal("assets/saves/" + getSaveFileName(mapType));
        if (oldAssetsInternalFile.exists()) {
            targetFile.writeString(oldAssetsInternalFile.readString(), false, "UTF-8");
            com.badlogic.gdx.Gdx.app.log("SaveManager",
                    "Migrated assets save to " + targetFile.file().getAbsolutePath());
            return;
        }

        com.badlogic.gdx.files.FileHandle legacyFile = getLegacySaveFile(mapType);
        if (legacyFile.exists()) {
            targetFile.writeString(legacyFile.readString(), false, "UTF-8");
            com.badlogic.gdx.Gdx.app.log("SaveManager",
                    "Migrated legacy save to " + targetFile.file().getAbsolutePath());
        }
    }

    public static void save(SaveData data, com.td.game.map.GameMap.MapType mapType) {
        try {
            com.badlogic.gdx.files.FileHandle file = getLocalSaveFile(mapType);
            file.writeString(data.toJson(), false);
            com.badlogic.gdx.Gdx.app.log("SaveManager", "Game saved successfully to " + file.file().getAbsolutePath());
        } catch (Exception e) {
            com.badlogic.gdx.Gdx.app.error("SaveManager", "Failed to save game", e);
        }
    }

    public static SaveData load(com.td.game.map.GameMap.MapType mapType) {
        try {
            migrateLegacySaveIfNeeded(mapType);
            com.badlogic.gdx.files.FileHandle file = getLocalSaveFile(mapType);
            if (file.exists()) {
                String jsonStr = file.readString();
                SaveData data = SaveData.fromJson(jsonStr);
                com.badlogic.gdx.Gdx.app.log("SaveManager", "Game loaded successfully from " + file.file().getAbsolutePath());
                return data;
            }
        } catch (Exception e) {
            com.badlogic.gdx.Gdx.app.error("SaveManager", "Failed to load game", e);
        }
        return null;
    }

    public static boolean hasSave(com.td.game.map.GameMap.MapType mapType) {
        migrateLegacySaveIfNeeded(mapType);
        return getLocalSaveFile(mapType).exists();
    }

    public static void deleteSave(com.td.game.map.GameMap.MapType mapType) {
        com.badlogic.gdx.files.FileHandle localFile = getLocalSaveFile(mapType);
        if (localFile.exists()) {
            localFile.delete();
            com.badlogic.gdx.Gdx.app.log("SaveManager", "Deleted save file " + localFile.file().getAbsolutePath());
        }
        com.badlogic.gdx.files.FileHandle legacyFile = getLegacySaveFile(mapType);
        if (legacyFile.exists()) {
            legacyFile.delete();
            com.badlogic.gdx.Gdx.app.log("SaveManager", "Deleted legacy save file " + legacyFile.file().getAbsolutePath());
        }
    }
}
