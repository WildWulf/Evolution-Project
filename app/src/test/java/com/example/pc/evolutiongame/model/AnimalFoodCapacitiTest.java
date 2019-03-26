package com.example.pc.evolutiongame.model;

import com.example.pc.evolutiongame.logic.CardGiver;
import com.example.pc.evolutiongame.logic.DeckShufler;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class AnimalFoodCapacitiTest {
    @Test
    public void animalFoodCapacityTest() {
        List<Card> deck = DeckShufler.deckShuffle();
        Room room = new Room(deck);

        Assert.assertNotNull(room.getDeck());
        Assert.assertEquals(84, room.getDeck().size());

        for (int i = 0; i < 2; i++) {
            room.addPlayer(new Player());
        }

        Assert.assertTrue(room.canStartGame());

        List<List<Card>> cardsForPlayers = CardGiver.getCardsForPlayers(room.countPlayers(), room.getDeck());
        Assert.assertNotNull(cardsForPlayers);

        for (int i = 0; i < room.countPlayers(); i++) {
            room.getPlayers().get(i).addCards(cardsForPlayers.get(i));
        }
        Assert.assertNotEquals(0, cardsForPlayers.get(0).size());

        room.setAllNotPass();


        do {
            Player currentPlayer = room.getCurrentPlayer();
            Assert.assertNotNull(currentPlayer);

            int localRandomCardNumber = (int) (Math.random() * room.getCurrentPlayer().getCardsCount());

            Card currentCard = currentPlayer.getCard(localRandomCardNumber);
            Assert.assertNotNull(currentCard);

            room.getCurrentPlayer().playAnimal(room.getField(), localRandomCardNumber);

            if (room.getCurrentPlayer().getCardsCount() == 0) {
                room.getCurrentPlayer().setPass(true);
            }
            room.setNextPlayer();
        }
        while (room.allPlayersPass());


        cardsForPlayers = CardGiver.getCardsForPlayers(room.countPlayers(), room.getDeck());

        for (int i = 0; i < room.countPlayers(); i++) {
            room.getPlayers().get(i).addCards(cardsForPlayers.get(i));
        }
        Assert.assertNotEquals(0, cardsForPlayers.get(0).size());

        room.setAllNotPass();


        do {
            Player currentPlayer = room.getCurrentPlayer();
            Assert.assertNotNull(currentPlayer);

            int localRandomCardNumber = (int) (Math.random() * room.getCurrentPlayer().getCardsCount());
            int localRandomAnimalNumber = (int) (Math.random() * room.getCurrentPlayerAnimalsCount(room.getCurrentPlayer()));

            Card currentCard = currentPlayer.getCard(localRandomCardNumber);
            Assert.assertNotNull(currentCard);

            room.getCurrentPlayer().playProperty(room.getField(), localRandomCardNumber, localRandomAnimalNumber, 0);

            if (room.getCurrentPlayer().getCardsCount() == 0) {
                room.getCurrentPlayer().setPass(true);
            }
            room.setNextPlayer();
        }
        while (room.allPlayersPass());


        room.setAllNotPass();


        do {
            for (int i = 0; i < room.getField().getAnimalsCount(); i++) {
                room.getField().getAnimals().get(i).calculateFoodCapacity();
            }
            room.setAllPass();
        }
        while (room.allPlayersPass());
    }
}