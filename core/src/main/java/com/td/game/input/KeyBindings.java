package com.td.game.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.td.game.TowerDefenseGame;
import com.td.game.map.GameMap;
import com.td.game.screens.GameScreen;

public class KeyBindings {

    public static boolean handleShortcutKeys(int keycode, TowerDefenseGame game, GameMap.MapType mapType,
            Screen currentScreen) {
        if (keycode == Input.Keys.GRAVE) {
            if (currentScreen instanceof GameScreen) {
                ((GameScreen) currentScreen).toggleConsole();
            }
            return true;
        }
        if (currentScreen instanceof GameScreen) {
            GameScreen gs = (GameScreen) currentScreen;
            if (keycode == Input.Keys.NUM_6) {
                gs.buyOrbByIndex(0);
                return true;
            }
            if (keycode == Input.Keys.NUM_7) {
                gs.buyOrbByIndex(1);
                return true;
            }
            if (keycode == Input.Keys.NUM_8) {
                gs.buyOrbByIndex(2);
                return true;
            }
            if (keycode == Input.Keys.NUM_9) {
                gs.buyOrbByIndex(3);
                return true;
            }
            if (keycode == Input.Keys.NUMPAD_1) {
                gs.selectInventorySlot(0);
                return true;
            }
            if (keycode == Input.Keys.NUMPAD_2) {
                gs.selectInventorySlot(1);
                return true;
            }
            if (keycode == Input.Keys.NUMPAD_3) {
                gs.selectInventorySlot(2);
                return true;
            }
            if (keycode == Input.Keys.NUMPAD_4) {
                gs.selectInventorySlot(3);
                return true;
            }
            if (keycode == Input.Keys.NUMPAD_5) {
                gs.selectInventorySlot(4);
                return true;
            }
            if (keycode == Input.Keys.NUMPAD_6) {
                gs.selectInventorySlot(5);
                return true;
            }
            if (keycode == Input.Keys.M) {
                gs.moveSelectedToMerge();
                return true;
            }
            if (keycode == Input.Keys.C) {
                gs.moveSelectedToCharm();
                return true;
            }
            if (keycode == Input.Keys.SPACE) {
                gs.startNextWaveHotkey();
                return true;
            }
        }
        return false;
    }

}
