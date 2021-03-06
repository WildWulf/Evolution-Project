package com.example.pc.evolutiongame;

import android.view.View;
import android.view.ViewGroup;

import com.example.pc.evolutiongame.model.Animal;
import com.example.pc.evolutiongame.model.Player;
import com.example.pc.evolutiongame.model.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class Utils {
    private static Random rand = new Random(System.currentTimeMillis());

    public static String getNewId() {
        return String.format("%04X", rand.nextInt());
    }

    public static List<Player> getEnemyPlayers(String playerId, Room room) {
        List<Player> players = new ArrayList<>();
        for (Player player : room.getPlayers()) {
            if (!playerId.equals(player.getId())) {
                players.add(player);
            }
        }
        return players;
    }

    public static Player getPlayer(String playerId, Room room) {
        for (Player player : room.getPlayers()) {
            if (playerId.equals(player.getId())) {
                return player;
            }
        }
        return null;
    }

    public static List<Animal> getPlayerAnimals(Player player, Room room) {
        List<Animal> playerAnimals = new CopyOnWriteArrayList<>();
        for (Animal animal : room.getAnimals()) {
            if (player.getId().equals(animal.getPlayer().getId())) {
                playerAnimals.add(animal);
            }
        }
        return playerAnimals;
    }

    public static void renderPlayerAnimals(Player player, Room room, ViewGroup playerLayout) {
        List<Animal> playerAnimals = getPlayerAnimals(player, room);

        for (int i = 0; i < playerLayout.getChildCount(); i++) {
            View animalView = playerLayout.getChildAt(i);
            if (animalView != null) {
                animalView.setVisibility(View.INVISIBLE);
            }
        }

        for (int i = 0; i < playerAnimals.size(); i++) {
            View animalView = playerLayout.getChildAt(i);
            if (animalView != null) {
                animalView.setVisibility(View.VISIBLE);
            }
        }
    }
}
