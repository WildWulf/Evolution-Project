package com.example.pc.evolutiongame;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pc.evolutiongame.core.Sendable;
import com.example.pc.evolutiongame.core.control.Action;
import com.example.pc.evolutiongame.core.control.Game;
import com.example.pc.evolutiongame.core.control.Phase;
import com.example.pc.evolutiongame.model.Animal;
import com.example.pc.evolutiongame.model.Player;
import com.example.pc.evolutiongame.model.Property;
import com.example.pc.evolutiongame.model.Room;
import com.example.pc.evolutiongame.wifidirect.discovery.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import static com.example.pc.evolutiongame.Utils.getEnemyPlayers;
import static com.example.pc.evolutiongame.Utils.getPlayer;

public class BoardFragment extends Fragment {

    public static final String TAG = "evolutiongame";

    Button hand, food, open;

    Button playerAnimal1, playerAnimal2, playerAnimal3, playerAnimal4, playerAnimal5, playerAnimal6;
    Button enemyAnimal1, enemyAnimal2, enemyAnimal3, enemyAnimal4, enemyAnimal5, enemyAnimal6;

    TextView gamePhase;
    TextView playerFoodCapacity1, playerFoodCapacity2, playerFoodCapacity3, playerFoodCapacity4, playerFoodCapacity5, playerFoodCapacity6;
    TextView enemyFoodCapacity1, enemyFoodCapacity2, enemyFoodCapacity3, enemyFoodCapacity4, enemyFoodCapacity5, enemyFoodCapacity6;

    TableLayout playerTable1, playerTable2, playerTable3, playerTable4, playerTable5, playerTable6;
    ImageView playerImageView_1_1, playerImageView_1_2, playerImageView_1_3, playerImageView_1_4, playerImageView_1_5, playerImageView_1_6;
    ImageView playerImageView_2_1, playerImageView_2_2, playerImageView_2_3, playerImageView_2_4, playerImageView_2_5, playerImageView_2_6;
    ImageView playerImageView_3_1, playerImageView_3_2, playerImageView_3_3, playerImageView_3_4, playerImageView_3_5, playerImageView_3_6;
    ImageView playerImageView_4_1, playerImageView_4_2, playerImageView_4_3, playerImageView_4_4, playerImageView_4_5, playerImageView_4_6;
    ImageView playerImageView_5_1, playerImageView_5_2, playerImageView_5_3, playerImageView_5_4, playerImageView_5_5, playerImageView_5_6;
    ImageView playerImageView_6_1, playerImageView_6_2, playerImageView_6_3, playerImageView_6_4, playerImageView_6_5, playerImageView_6_6;

    TableLayout enemyTable1, enemyTable2, enemyTable3, enemyTable4, enemyTable5, enemyTable6;
    ImageView enemyImageView_1_1, enemyImageView_1_2, enemyImageView_1_3, enemyImageView_1_4, enemyImageView_1_5, enemyImageView_1_6;
    ImageView enemyImageView_2_1, enemyImageView_2_2, enemyImageView_2_3, enemyImageView_2_4, enemyImageView_2_5, enemyImageView_2_6;
    ImageView enemyImageView_3_1, enemyImageView_3_2, enemyImageView_3_3, enemyImageView_3_4, enemyImageView_3_5, enemyImageView_3_6;
    ImageView enemyImageView_4_1, enemyImageView_4_2, enemyImageView_4_3, enemyImageView_4_4, enemyImageView_4_5, enemyImageView_4_6;
    ImageView enemyImageView_5_1, enemyImageView_5_2, enemyImageView_5_3, enemyImageView_5_4, enemyImageView_5_5, enemyImageView_5_6;
    ImageView enemyImageView_6_1, enemyImageView_6_2, enemyImageView_6_3, enemyImageView_6_4, enemyImageView_6_5, enemyImageView_6_6;
    private HandFragment handFragment;
    private View view;
    private String playerId;
    private Room room;
    private Player player;
    private Sendable sender;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.board_fragment, container, false);

        hand = (Button) view.findViewById(R.id.showDeckButton);
        food = (Button) view.findViewById(R.id.foodCapacityButton);
        open = (Button) view.findViewById(R.id.passButton);

        gamePhase = (TextView) view.findViewById(R.id.gamePhase);

        playerFoodCapacity1 = (TextView) view.findViewById(R.id.playerFoodCapacity1);
        playerFoodCapacity2 = (TextView) view.findViewById(R.id.playerFoodCapacity2);
        playerFoodCapacity3 = (TextView) view.findViewById(R.id.playerFoodCapacity3);
        playerFoodCapacity4 = (TextView) view.findViewById(R.id.playerFoodCapacity4);
        playerFoodCapacity5 = (TextView) view.findViewById(R.id.playerFoodCapacity5);
        playerFoodCapacity6 = (TextView) view.findViewById(R.id.playerFoodCapacity6);

        enemyFoodCapacity1 = (TextView) view.findViewById(R.id.enemyFoodCapacity1);
        enemyFoodCapacity2 = (TextView) view.findViewById(R.id.enemyFoodCapacity2);
        enemyFoodCapacity3 = (TextView) view.findViewById(R.id.enemyFoodCapacity3);
        enemyFoodCapacity4 = (TextView) view.findViewById(R.id.enemyFoodCapacity4);
        enemyFoodCapacity5 = (TextView) view.findViewById(R.id.enemyFoodCapacity5);
        enemyFoodCapacity6 = (TextView) view.findViewById(R.id.enemyFoodCapacity6);

        playerTable1 = (TableLayout) view.findViewById(R.id.playerPropertyTable1);
        {
            playerImageView_1_1 = (ImageView) view.findViewById(R.id.imageView7_1_1);
            playerImageView_1_2 = (ImageView) view.findViewById(R.id.imageView7_1_2);
            playerImageView_1_3 = (ImageView) view.findViewById(R.id.imageView7_2_1);
            playerImageView_1_4 = (ImageView) view.findViewById(R.id.imageView7_2_2);
            playerImageView_1_5 = (ImageView) view.findViewById(R.id.imageView7_3_1);
            playerImageView_1_6 = (ImageView) view.findViewById(R.id.imageView7_3_2);
        }
        playerTable2 = (TableLayout) view.findViewById(R.id.playerPropertyTable2);
        {
            playerImageView_2_1 = (ImageView) view.findViewById(R.id.imageView8_1_1);
            playerImageView_2_2 = (ImageView) view.findViewById(R.id.imageView8_1_2);
            playerImageView_2_3 = (ImageView) view.findViewById(R.id.imageView8_2_1);
            playerImageView_2_4 = (ImageView) view.findViewById(R.id.imageView8_2_2);
            playerImageView_2_5 = (ImageView) view.findViewById(R.id.imageView8_3_1);
            playerImageView_2_6 = (ImageView) view.findViewById(R.id.imageView8_3_2);
        }
        playerTable3 = (TableLayout) view.findViewById(R.id.playerPropertyTable3);
        {
            playerImageView_3_1 = (ImageView) view.findViewById(R.id.imageView9_1_1);
            playerImageView_3_2 = (ImageView) view.findViewById(R.id.imageView9_1_2);
            playerImageView_3_3 = (ImageView) view.findViewById(R.id.imageView9_2_1);
            playerImageView_3_4 = (ImageView) view.findViewById(R.id.imageView9_2_2);
            playerImageView_3_5 = (ImageView) view.findViewById(R.id.imageView9_3_1);
            playerImageView_3_6 = (ImageView) view.findViewById(R.id.imageView9_3_2);
        }
        playerTable4 = (TableLayout) view.findViewById(R.id.playerPropertyTable4);
        {
            playerImageView_4_1 = (ImageView) view.findViewById(R.id.imageView10_1_1);
            playerImageView_4_2 = (ImageView) view.findViewById(R.id.imageView10_1_2);
            playerImageView_4_3 = (ImageView) view.findViewById(R.id.imageView10_2_1);
            playerImageView_4_4 = (ImageView) view.findViewById(R.id.imageView10_2_2);
            playerImageView_4_5 = (ImageView) view.findViewById(R.id.imageView10_3_1);
            playerImageView_4_6 = (ImageView) view.findViewById(R.id.imageView10_3_2);
        }
        playerTable5 = (TableLayout) view.findViewById(R.id.playerPropertyTable5);
        {
            playerImageView_5_1 = (ImageView) view.findViewById(R.id.imageView11_1_1);
            playerImageView_5_2 = (ImageView) view.findViewById(R.id.imageView11_1_2);
            playerImageView_5_3 = (ImageView) view.findViewById(R.id.imageView11_2_1);
            playerImageView_5_4 = (ImageView) view.findViewById(R.id.imageView11_2_2);
            playerImageView_5_5 = (ImageView) view.findViewById(R.id.imageView11_3_1);
            playerImageView_5_6 = (ImageView) view.findViewById(R.id.imageView11_3_2);
        }
        playerTable6 = (TableLayout) view.findViewById(R.id.playerPropertyTable6);
        {
            playerImageView_6_1 = (ImageView) view.findViewById(R.id.imageView12_1_1);
            playerImageView_6_2 = (ImageView) view.findViewById(R.id.imageView12_1_2);
            playerImageView_6_3 = (ImageView) view.findViewById(R.id.imageView12_2_1);
            playerImageView_6_4 = (ImageView) view.findViewById(R.id.imageView12_2_2);
            playerImageView_6_5 = (ImageView) view.findViewById(R.id.imageView12_3_1);
            playerImageView_6_6 = (ImageView) view.findViewById(R.id.imageView12_3_2);
        }


        enemyTable1 = (TableLayout) view.findViewById(R.id.enemyPropertyTable1);
        {
            enemyImageView_1_1 = (ImageView) view.findViewById(R.id.imageView1_1_1);
            enemyImageView_1_2 = (ImageView) view.findViewById(R.id.imageView1_1_2);
            enemyImageView_1_3 = (ImageView) view.findViewById(R.id.imageView1_2_1);
            enemyImageView_1_4 = (ImageView) view.findViewById(R.id.imageView1_2_2);
            enemyImageView_1_5 = (ImageView) view.findViewById(R.id.imageView1_3_1);
            enemyImageView_1_6 = (ImageView) view.findViewById(R.id.imageView1_3_2);
        }
        enemyTable2 = (TableLayout) view.findViewById(R.id.enemyPropertyTable2);
        {
            enemyImageView_2_1 = (ImageView) view.findViewById(R.id.imageView2_1_1);
            enemyImageView_2_2 = (ImageView) view.findViewById(R.id.imageView2_1_2);
            enemyImageView_2_3 = (ImageView) view.findViewById(R.id.imageView2_2_1);
            enemyImageView_2_4 = (ImageView) view.findViewById(R.id.imageView2_2_2);
            enemyImageView_2_5 = (ImageView) view.findViewById(R.id.imageView2_3_1);
            enemyImageView_2_6 = (ImageView) view.findViewById(R.id.imageView2_3_2);
        }
        enemyTable3 = (TableLayout) view.findViewById(R.id.enemyPropertyTable3);
        {
            enemyImageView_3_1 = (ImageView) view.findViewById(R.id.imageView3_1_1);
            enemyImageView_3_2 = (ImageView) view.findViewById(R.id.imageView3_1_2);
            enemyImageView_3_3 = (ImageView) view.findViewById(R.id.imageView3_2_1);
            enemyImageView_3_4 = (ImageView) view.findViewById(R.id.imageView3_2_2);
            enemyImageView_3_5 = (ImageView) view.findViewById(R.id.imageView3_3_1);
            enemyImageView_3_6 = (ImageView) view.findViewById(R.id.imageView3_3_2);
        }
        enemyTable4 = (TableLayout) view.findViewById(R.id.enemyPropertyTable4);
        {
            enemyImageView_4_1 = (ImageView) view.findViewById(R.id.imageView4_1_1);
            enemyImageView_4_2 = (ImageView) view.findViewById(R.id.imageView4_1_2);
            enemyImageView_4_3 = (ImageView) view.findViewById(R.id.imageView4_2_1);
            enemyImageView_4_4 = (ImageView) view.findViewById(R.id.imageView4_2_2);
            enemyImageView_4_5 = (ImageView) view.findViewById(R.id.imageView4_3_1);
            enemyImageView_4_6 = (ImageView) view.findViewById(R.id.imageView4_3_2);
        }
        enemyTable5 = (TableLayout) view.findViewById(R.id.enemyPropertyTable5);
        {
            enemyImageView_5_1 = (ImageView) view.findViewById(R.id.imageView5_1_1);
            enemyImageView_5_2 = (ImageView) view.findViewById(R.id.imageView5_1_2);
            enemyImageView_5_3 = (ImageView) view.findViewById(R.id.imageView5_2_1);
            enemyImageView_5_4 = (ImageView) view.findViewById(R.id.imageView5_2_2);
            enemyImageView_5_5 = (ImageView) view.findViewById(R.id.imageView5_3_1);
            enemyImageView_5_6 = (ImageView) view.findViewById(R.id.imageView5_3_2);
        }
        enemyTable6 = (TableLayout) view.findViewById(R.id.enemyPropertyTable6);
        {
            enemyImageView_6_1 = (ImageView) view.findViewById(R.id.imageView6_1_1);
            enemyImageView_6_2 = (ImageView) view.findViewById(R.id.imageView6_1_2);
            enemyImageView_6_3 = (ImageView) view.findViewById(R.id.imageView6_2_1);
            enemyImageView_6_4 = (ImageView) view.findViewById(R.id.imageView6_2_2);
            enemyImageView_6_5 = (ImageView) view.findViewById(R.id.imageView6_3_1);
            enemyImageView_6_6 = (ImageView) view.findViewById(R.id.imageView6_3_2);
        }


        playerAnimal1 = (Button) view.findViewById(R.id.playerMinion1);
        playerAnimal2 = (Button) view.findViewById(R.id.playerMinion2);
        playerAnimal3 = (Button) view.findViewById(R.id.playerMinion3);
        playerAnimal4 = (Button) view.findViewById(R.id.playerMinion4);
        playerAnimal5 = (Button) view.findViewById(R.id.playerMinion5);
        playerAnimal6 = (Button) view.findViewById(R.id.playerMinion6);

        enemyAnimal1 = (Button) view.findViewById(R.id.enemyMinion1);
        enemyAnimal2 = (Button) view.findViewById(R.id.enemyMinion2);
        enemyAnimal3 = (Button) view.findViewById(R.id.enemyMinion3);
        enemyAnimal4 = (Button) view.findViewById(R.id.enemyMinion4);
        enemyAnimal5 = (Button) view.findViewById(R.id.enemyMinion5);
        enemyAnimal6 = (Button) view.findViewById(R.id.enemyMinion6);

        final Gson gson = new GsonBuilder().create();

        playerAnimal1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!player.gotUsedCards()) {
                    if (room.getPhase() == Phase.POWER && room.getCurrentPlayer().getId().equals(playerId)) {
                        player.giveFood(room, 0);
                        sender.sendMessage(gson.toJson(new Game(Action.REFRESH_STATE, room.getPhase(), room)));
                    } else {
                        Toast.makeText(getActivity(), "You can not do this", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (room.getPhase() == Phase.EVOLUTION && room.getCurrentPlayer().getId().equals(playerId)) {
                        player.playProperty(room.getField(), player.getUsedCardIndex(), 0, player.getUsedCardState());
                        sender.sendMessage(gson.toJson(new Game(Action.REFRESH_STATE, room.getPhase(), room)));
                    } else {
                        Toast.makeText(getActivity(), "You can not do this", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        playerAnimal2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!player.gotUsedCards()) {
                    if (room.getPhase() == Phase.POWER && room.getCurrentPlayer().getId().equals(playerId)) {
                        player.giveFood(room, 1);
                        sender.sendMessage(gson.toJson(new Game(Action.REFRESH_STATE, room.getPhase(), room)));
                    } else {
                        Toast.makeText(getActivity(), "You can not do this", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (room.getPhase() == Phase.EVOLUTION && room.getCurrentPlayer().getId().equals(playerId)) {
                        player.playProperty(room.getField(), player.getUsedCardIndex(), 1, player.getUsedCardState());
                        sender.sendMessage(gson.toJson(new Game(Action.REFRESH_STATE, room.getPhase(), room)));
                    } else {
                        Toast.makeText(getActivity(), "You can not do this", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        playerAnimal3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!player.gotUsedCards()) {
                    if (room.getPhase() == Phase.POWER && room.getCurrentPlayer().getId().equals(playerId)) {
                        player.giveFood(room, 2);
                        sender.sendMessage(gson.toJson(new Game(Action.REFRESH_STATE, room.getPhase(), room)));
                    } else {
                        Toast.makeText(getActivity(), "You can not do this", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (room.getPhase() == Phase.EVOLUTION && room.getCurrentPlayer().getId().equals(playerId)) {
                        player.playProperty(room.getField(), player.getUsedCardIndex(), 2, player.getUsedCardState());
                        sender.sendMessage(gson.toJson(new Game(Action.REFRESH_STATE, room.getPhase(), room)));
                    } else {
                        Toast.makeText(getActivity(), "You can not do this", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        playerAnimal4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!player.gotUsedCards()) {
                    if (room.getPhase() == Phase.POWER && room.getCurrentPlayer().getId().equals(playerId)) {
                        player.giveFood(room, 3);
                        sender.sendMessage(gson.toJson(new Game(Action.REFRESH_STATE, room.getPhase(), room)));
                    } else {
                        Toast.makeText(getActivity(), "You can not do this", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (room.getPhase() == Phase.EVOLUTION && room.getCurrentPlayer().getId().equals(playerId)) {
                        player.playProperty(room.getField(), player.getUsedCardIndex(), 3, player.getUsedCardState());
                        sender.sendMessage(gson.toJson(new Game(Action.REFRESH_STATE, room.getPhase(), room)));
                    } else {
                        Toast.makeText(getActivity(), "You can not do this", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        playerAnimal5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!player.gotUsedCards()) {
                    if (room.getPhase() == Phase.POWER && room.getCurrentPlayer().getId().equals(playerId)) {
                        player.giveFood(room, 4);
                        sender.sendMessage(gson.toJson(new Game(Action.REFRESH_STATE, room.getPhase(), room)));
                    } else {
                        Toast.makeText(getActivity(), "You can not do this", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (room.getPhase() == Phase.EVOLUTION && room.getCurrentPlayer().getId().equals(playerId)) {
                        player.playProperty(room.getField(), player.getUsedCardIndex(), 4, player.getUsedCardState());
                        sender.sendMessage(gson.toJson(new Game(Action.REFRESH_STATE, room.getPhase(), room)));
                    } else {
                        Toast.makeText(getActivity(), "You can not do this", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        playerAnimal6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!player.gotUsedCards()) {
                    if (room.getPhase() == Phase.POWER && room.getCurrentPlayer().getId().equals(playerId)) {
                        player.giveFood(room, 5);
                        sender.sendMessage(gson.toJson(new Game(Action.REFRESH_STATE, room.getPhase(), room)));
                    } else {
                        Toast.makeText(getActivity(), "You can not do this", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (room.getPhase() == Phase.EVOLUTION && room.getCurrentPlayer().getId().equals(playerId)) {
                        player.playProperty(room.getField(), player.getUsedCardIndex(), 5, player.getUsedCardState());
                        sender.sendMessage(gson.toJson(new Game(Action.REFRESH_STATE, room.getPhase(), room)));
                    } else {
                        Toast.makeText(getActivity(), "You can not do this", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        hand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.container_root, handFragment, "handFragment").addToBackStack("handFragment").commit();
            }
        });

        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        return view;
    }

    private void renderAnimalPropertes(String playerId, Room room) {
        List<Animal> playerAnimals = new ArrayList<>();
        List<Animal> enemyAnimals = new ArrayList<>();

        Player player = new Player();
        Player enemy = new Player();

        for (int i = 0; i < room.getPlayers().size(); i++) {
            if (playerId.equals(room.getPlayers().get(0).getId())) {
                player = room.getPlayers().get(0);
                enemy = room.getPlayers().get(1);
            } else {
                player = room.getPlayers().get(1);
                enemy = room.getPlayers().get(0);
            }
        }

        for (int i = 0; i < room.getAnimals().size(); i++) {
            playerAnimals = room.getField().getAnimals(player);
            enemyAnimals = room.getField().getAnimals(enemy);
        }

        setAllImageViewINVISIBLE();

        renderPlayerAnimals(playerAnimals);
        renderEnemyAnimals(enemyAnimals);
    }

    private void setAllImageViewINVISIBLE() {
        enemyImageView_1_1.setVisibility(View.INVISIBLE);
        enemyImageView_1_2.setVisibility(View.INVISIBLE);
        enemyImageView_1_3.setVisibility(View.INVISIBLE);
        enemyImageView_1_4.setVisibility(View.INVISIBLE);
        enemyImageView_1_5.setVisibility(View.INVISIBLE);
        enemyImageView_1_6.setVisibility(View.INVISIBLE);
        enemyImageView_2_1.setVisibility(View.INVISIBLE);
        enemyImageView_2_2.setVisibility(View.INVISIBLE);
        enemyImageView_2_3.setVisibility(View.INVISIBLE);
        enemyImageView_2_4.setVisibility(View.INVISIBLE);
        enemyImageView_2_5.setVisibility(View.INVISIBLE);
        enemyImageView_2_6.setVisibility(View.INVISIBLE);
        enemyImageView_3_1.setVisibility(View.INVISIBLE);
        enemyImageView_3_2.setVisibility(View.INVISIBLE);
        enemyImageView_3_3.setVisibility(View.INVISIBLE);
        enemyImageView_3_4.setVisibility(View.INVISIBLE);
        enemyImageView_3_5.setVisibility(View.INVISIBLE);
        enemyImageView_3_6.setVisibility(View.INVISIBLE);
        enemyImageView_4_1.setVisibility(View.INVISIBLE);
        enemyImageView_4_2.setVisibility(View.INVISIBLE);
        enemyImageView_4_3.setVisibility(View.INVISIBLE);
        enemyImageView_4_4.setVisibility(View.INVISIBLE);
        enemyImageView_4_5.setVisibility(View.INVISIBLE);
        enemyImageView_4_6.setVisibility(View.INVISIBLE);
        enemyImageView_5_1.setVisibility(View.INVISIBLE);
        enemyImageView_5_2.setVisibility(View.INVISIBLE);
        enemyImageView_5_3.setVisibility(View.INVISIBLE);
        enemyImageView_5_4.setVisibility(View.INVISIBLE);
        enemyImageView_5_5.setVisibility(View.INVISIBLE);
        enemyImageView_5_6.setVisibility(View.INVISIBLE);
        enemyImageView_6_1.setVisibility(View.INVISIBLE);
        enemyImageView_6_2.setVisibility(View.INVISIBLE);
        enemyImageView_6_3.setVisibility(View.INVISIBLE);
        enemyImageView_6_4.setVisibility(View.INVISIBLE);
        enemyImageView_6_5.setVisibility(View.INVISIBLE);
        enemyImageView_6_6.setVisibility(View.INVISIBLE);

        playerImageView_1_1.setVisibility(View.INVISIBLE);
        playerImageView_1_2.setVisibility(View.INVISIBLE);
        playerImageView_1_3.setVisibility(View.INVISIBLE);
        playerImageView_1_4.setVisibility(View.INVISIBLE);
        playerImageView_1_5.setVisibility(View.INVISIBLE);
        playerImageView_1_6.setVisibility(View.INVISIBLE);
        playerImageView_2_1.setVisibility(View.INVISIBLE);
        playerImageView_2_2.setVisibility(View.INVISIBLE);
        playerImageView_2_3.setVisibility(View.INVISIBLE);
        playerImageView_2_4.setVisibility(View.INVISIBLE);
        playerImageView_2_5.setVisibility(View.INVISIBLE);
        playerImageView_2_6.setVisibility(View.INVISIBLE);
        playerImageView_3_1.setVisibility(View.INVISIBLE);
        playerImageView_3_2.setVisibility(View.INVISIBLE);
        playerImageView_3_3.setVisibility(View.INVISIBLE);
        playerImageView_3_4.setVisibility(View.INVISIBLE);
        playerImageView_3_5.setVisibility(View.INVISIBLE);
        playerImageView_3_6.setVisibility(View.INVISIBLE);
        playerImageView_4_1.setVisibility(View.INVISIBLE);
        playerImageView_4_2.setVisibility(View.INVISIBLE);
        playerImageView_4_3.setVisibility(View.INVISIBLE);
        playerImageView_4_4.setVisibility(View.INVISIBLE);
        playerImageView_4_5.setVisibility(View.INVISIBLE);
        playerImageView_4_6.setVisibility(View.INVISIBLE);
        playerImageView_5_1.setVisibility(View.INVISIBLE);
        playerImageView_5_2.setVisibility(View.INVISIBLE);
        playerImageView_5_3.setVisibility(View.INVISIBLE);
        playerImageView_5_4.setVisibility(View.INVISIBLE);
        playerImageView_5_5.setVisibility(View.INVISIBLE);
        playerImageView_5_6.setVisibility(View.INVISIBLE);
        playerImageView_6_1.setVisibility(View.INVISIBLE);
        playerImageView_6_2.setVisibility(View.INVISIBLE);
        playerImageView_6_3.setVisibility(View.INVISIBLE);
        playerImageView_6_4.setVisibility(View.INVISIBLE);
        playerImageView_6_5.setVisibility(View.INVISIBLE);
        playerImageView_6_6.setVisibility(View.INVISIBLE);
    }

    private void renderPlayerAnimals(List<Animal> playerAnimals) {
        if (playerAnimals.size() >= 1) {
            renderProps(playerAnimals, playerImageView_1_1, playerImageView_1_2, playerImageView_1_3, playerImageView_1_4, playerImageView_1_5, playerImageView_1_6, 0);
            if (playerAnimals.size() >= 2) {
                renderProps(playerAnimals, playerImageView_2_1, playerImageView_2_2, playerImageView_2_3, playerImageView_2_4, playerImageView_2_5, playerImageView_2_6, 1);
                if (playerAnimals.size() >= 3) {
                    renderProps(playerAnimals, playerImageView_3_1, playerImageView_3_2, playerImageView_3_3, playerImageView_3_4, playerImageView_3_5, playerImageView_3_6, 2);
                    if (playerAnimals.size() >= 4) {
                        renderProps(playerAnimals, playerImageView_4_1, playerImageView_4_2, playerImageView_4_3, playerImageView_4_4, playerImageView_4_5, playerImageView_4_6, 3);
                        if (playerAnimals.size() >= 5) {
                            renderProps(playerAnimals, playerImageView_5_1, playerImageView_5_2, playerImageView_5_3, playerImageView_5_4, playerImageView_5_5, playerImageView_4_6, 4);
                            if (playerAnimals.size() >= 6) {
                                renderProps(playerAnimals, playerImageView_6_1, playerImageView_6_2, playerImageView_6_3, playerImageView_6_4, playerImageView_4_5, playerImageView_6_6, 5);
                            }
                        }
                    }
                }
            }
        }
    }

    private void renderProps(List<Animal> animals, ImageView imageView_1, ImageView
            imageView_2, ImageView imageView_3, ImageView imageView_4, ImageView imageView_5, ImageView
                                     imageView_6, int i) {
        if (animals.get(i).getProperty().size() >= 1) {
            imageView_1.setVisibility(View.VISIBLE);
            renderProp(animals.get(i).getProperty().get(0), imageView_1);
            if (animals.get(i).getProperty().size() >= 2) {
                imageView_2.setVisibility(View.VISIBLE);
                renderProp(animals.get(i).getProperty().get(1), imageView_2);
                if (animals.get(i).getProperty().size() >= 3) {
                    imageView_3.setVisibility(View.VISIBLE);
                    renderProp(animals.get(i).getProperty().get(2), imageView_3);
                    if (animals.get(i).getProperty().size() >= 4) {
                        imageView_4.setVisibility(View.VISIBLE);
                        renderProp(animals.get(i).getProperty().get(3), imageView_4);
                        if (animals.get(i).getProperty().size() >= 5) {
                            imageView_5.setVisibility(View.VISIBLE);
                            renderProp(animals.get(i).getProperty().get(4), imageView_5);
                            if (animals.get(i).getProperty().size() >= 6) {
                                imageView_6.setVisibility(View.VISIBLE);
                                renderProp(animals.get(i).getProperty().get(5), imageView_6);
                            } else {
                                imageView_6.setVisibility(View.INVISIBLE);
                            }
                        } else {
                            imageView_5.setVisibility(View.INVISIBLE);
                            imageView_6.setVisibility(View.INVISIBLE);
                        }
                    } else {
                        imageView_4.setVisibility(View.INVISIBLE);
                        imageView_5.setVisibility(View.INVISIBLE);
                        imageView_6.setVisibility(View.INVISIBLE);
                    }
                } else {
                    imageView_3.setVisibility(View.INVISIBLE);
                    imageView_4.setVisibility(View.INVISIBLE);
                    imageView_5.setVisibility(View.INVISIBLE);
                    imageView_6.setVisibility(View.INVISIBLE);
                }
            } else {
                imageView_2.setVisibility(View.INVISIBLE);
                imageView_3.setVisibility(View.INVISIBLE);
                imageView_4.setVisibility(View.INVISIBLE);
                imageView_5.setVisibility(View.INVISIBLE);
                imageView_6.setVisibility(View.INVISIBLE);
            }
        } else {
            imageView_1.setVisibility(View.INVISIBLE);
            imageView_2.setVisibility(View.INVISIBLE);
            imageView_3.setVisibility(View.INVISIBLE);
            imageView_4.setVisibility(View.INVISIBLE);
            imageView_5.setVisibility(View.INVISIBLE);
            imageView_6.setVisibility(View.INVISIBLE);
        }
    }

    private void renderEnemyAnimals(List<Animal> enemyAnimals) {
        if (enemyAnimals.size() >= 1) {
            renderProps(enemyAnimals, enemyImageView_1_1, enemyImageView_1_2, enemyImageView_1_3, enemyImageView_1_4, enemyImageView_1_5, enemyImageView_1_6, 0);
            if (enemyAnimals.size() >= 2) {
                renderProps(enemyAnimals, enemyImageView_2_1, enemyImageView_2_2, enemyImageView_2_3, enemyImageView_2_4, enemyImageView_2_5, enemyImageView_2_6, 1);
                if (enemyAnimals.size() >= 3) {
                    renderProps(enemyAnimals, enemyImageView_3_1, enemyImageView_3_2, enemyImageView_3_3, enemyImageView_3_4, enemyImageView_3_5, enemyImageView_3_6, 2);
                    if (enemyAnimals.size() >= 4) {
                        renderProps(enemyAnimals, enemyImageView_4_1, enemyImageView_4_2, enemyImageView_4_3, enemyImageView_4_4, enemyImageView_4_5, enemyImageView_4_6, 3);
                        if (enemyAnimals.size() >= 5) {
                            renderProps(enemyAnimals, enemyImageView_5_1, enemyImageView_5_2, enemyImageView_5_3, enemyImageView_5_4, enemyImageView_5_5, enemyImageView_4_6, 4);
                            if (enemyAnimals.size() >= 6) {
                                renderProps(enemyAnimals, enemyImageView_6_1, enemyImageView_6_2, enemyImageView_6_3, enemyImageView_6_4, enemyImageView_4_5, enemyImageView_6_6, 5);
                            }
                        }
                    }
                }
            }
        }
    }

    private void renderProp(Property property, ImageView imageView) {
        switch (property.getValue()) {
            case BURROWING: {
                imageView.setImageResource(R.drawable.burrowing_prp);
                System.out.println("" + property + " " + imageView.getId());
                break;
            }
            case COMMOUFLAGE: {
                imageView.setImageResource(R.drawable.commouflage_prp);
                System.out.println("" + property + " " + imageView.getId());
                break;
            }
            case CARNIVOROUS: {
                imageView.setImageResource(R.drawable.carnivorous_prp);
                System.out.println("" + property + " " + imageView.getId());
                break;
            }
            case COMMUNICATION: {
                imageView.setImageResource(R.drawable.communication_prp);
                System.out.println("" + property + " " + imageView.getId());
                break;
            }
            case COOPERATION: {
                imageView.setImageResource(R.drawable.cooperation_prp);
                System.out.println("" + property + " " + imageView.getId());
                break;
            }
            case GRAZING: {
                imageView.setImageResource(R.drawable.grazing_prp);
                System.out.println("" + property + " " + imageView.getId());
                break;
            }
            case HIBERNATION_ABILITY: {
                imageView.setImageResource(R.drawable.hibernation_ability_prp);
                System.out.println("" + property + " " + imageView.getId());
                break;
            }
            case HIGH_BODY_WEIGHT: {
                imageView.setImageResource(R.drawable.high_body_weight_prp);
                System.out.println("" + property + " " + imageView.getId());
                break;
            }
            case MIMICRY: {
                imageView.setImageResource(R.drawable.mimicry_prp);
                System.out.println("" + property + " " + imageView.getId());
                break;
            }
            case PARASITE: {
                imageView.setImageResource(R.drawable.parasite_prp);
                System.out.println("" + property + " " + imageView.getId());
                break;
            }
            case PIRACY: {
                imageView.setImageResource(R.drawable.pirasy_prp);
                System.out.println("" + property + " " + imageView.getId());
                break;
            }
            case POISONOUS: {
                imageView.setImageResource(R.drawable.poisonous_prp);
                System.out.println("" + property + " " + imageView.getId());
                break;
            }
            case RUNNING: {
                imageView.setImageResource(R.drawable.running_prp);
                System.out.println("" + property + " " + imageView.getId());
                break;
            }
            case SCAVENGER: {
                imageView.setImageResource(R.drawable.scavenger_prp);
                System.out.println("" + property + " " + imageView.getId());
                break;
            }
            case SHARP_VISION: {
                imageView.setImageResource(R.drawable.sharp_vision_prp);
                System.out.println("" + property + " " + imageView.getId());
                break;
            }
            case SWIMMING: {
                imageView.setImageResource(R.drawable.swimming_prp);
                System.out.println("" + property + " " + imageView.getId());
                break;
            }
            case SYMBIOSYS: {
                imageView.setImageResource(R.drawable.symbiosis_prp);
                System.out.println("" + property + " " + imageView.getId());
                break;
            }
            case TAIL_LOSS: {
                imageView.setImageResource(R.drawable.tail_loss_prp);
                System.out.println("" + property + " " + imageView.getId());
                break;
            }
            case FAT_TISSUE: {
                imageView.setImageResource(R.drawable.fat_tissue_prp);
                System.out.println("" + property + " " + imageView.getId());
                break;
            }
            default: {
                imageView.setImageResource(R.drawable.evolution_prp);
                System.out.println("" + property + " " + imageView.getId());
                break;
            }
        }
    }

    public void refreshRoom(String playerId, Room room) {
        this.playerId = playerId;
        this.room = room;
        System.out.printf("Trying board for room->%s", room);

        renderAnimalPropertes(playerId, room);

        ViewGroup playerLayout = view.findViewById(R.id.playerLayout);
        player = getPlayer(playerId, room);
        Utils.renderPlayerAnimals(player, room, playerLayout);

        ViewGroup enemyLayout = view.findViewById(R.id.enemyLayout);
        List<Player> enemyPlayers = getEnemyPlayers(playerId, room);
        Utils.renderPlayerAnimals(enemyPlayers.get(0), room, enemyLayout);

        gamePhase.setText(room.getPhase().name());

        if (room.getCapacityFood() > 0) {
            String food_ = String.valueOf(room.getCapacityFood());
            food.setText(food_);
        } else {
            food.setText("Food");
        }

        setAllFoodInvisible();
//        renderFoodCapacity();
    }

    private void setAllFoodInvisible() {
        enemyFoodCapacity1.setVisibility(View.INVISIBLE);
        enemyFoodCapacity2.setVisibility(View.INVISIBLE);
        enemyFoodCapacity3.setVisibility(View.INVISIBLE);
        enemyFoodCapacity4.setVisibility(View.INVISIBLE);
        enemyFoodCapacity5.setVisibility(View.INVISIBLE);
        enemyFoodCapacity6.setVisibility(View.INVISIBLE);

        playerFoodCapacity1.setVisibility(View.INVISIBLE);
        playerFoodCapacity2.setVisibility(View.INVISIBLE);
        playerFoodCapacity3.setVisibility(View.INVISIBLE);
        playerFoodCapacity4.setVisibility(View.INVISIBLE);
        playerFoodCapacity5.setVisibility(View.INVISIBLE);
        playerFoodCapacity6.setVisibility(View.INVISIBLE);
    }

//    private void renderFoodCapacity() {
//        List<Animal> playerAnimals = new ArrayList<>();
//        List<Animal> enemyAnimals = new ArrayList<>();
//
//        Player player = new Player();
//        Player enemy = new Player();
//
//        for (int i = 0; i < room.getPlayers().size(); i++) {
//            if (playerId.equals(room.getPlayers().get(0).getId())) {
//                player = room.getPlayers().get(0);
//                enemy = room.getPlayers().get(1);
//            } else {
//                player = room.getPlayers().get(1);
//                enemy = room.getPlayers().get(0);
//            }
//        }
//
//        for (int i = 0; i < room.getAnimals().size(); i++) {
//            playerAnimals = room.getField().getAnimals(player);
//            enemyAnimals = room.getField().getAnimals(enemy);
//        }
//
//        setAllImageViewINVISIBLE();
//
////        renderPlayerFood(playerAnimals);
////        renderEnemyFood(enemyAnimals);
//    }

//    private void renderEnemyFood(List<Animal> enemyAnimals) {
//        enemyFoodCapacity1.setText(enemyAnimals.get(0).getCapacityFood());
//        enemyFoodCapacity2.setText(enemyAnimals.get(1).getCapacityFood());
//        enemyFoodCapacity3.setText(enemyAnimals.get(2).getCapacityFood());
//        enemyFoodCapacity4.setText(enemyAnimals.get(3).getCapacityFood());
//        enemyFoodCapacity5.setText(enemyAnimals.get(4).getCapacityFood());
//        enemyFoodCapacity6.setText(enemyAnimals.get(5).getCapacityFood());
//
//        switch (enemyAnimals.size()){
//            case 1: {
//                enemyFoodCapacity1.setVisibility(View.VISIBLE);
//                break;
//            }
//            case 2: {
//                enemyFoodCapacity1.setVisibility(View.VISIBLE);
//                enemyFoodCapacity2.setVisibility(View.VISIBLE);
//                break;
//            }
//            case 3: {
//                enemyFoodCapacity1.setVisibility(View.VISIBLE);
//                enemyFoodCapacity2.setVisibility(View.VISIBLE);
//                enemyFoodCapacity3.setVisibility(View.VISIBLE);
//                break;
//            }
//            case 4: {
//                enemyFoodCapacity1.setVisibility(View.VISIBLE);
//                enemyFoodCapacity2.setVisibility(View.VISIBLE);
//                enemyFoodCapacity3.setVisibility(View.VISIBLE);
//                enemyFoodCapacity4.setVisibility(View.VISIBLE);
//                break;
//            }
//            case 5: {
//                enemyFoodCapacity1.setVisibility(View.VISIBLE);
//                enemyFoodCapacity2.setVisibility(View.VISIBLE);
//                enemyFoodCapacity3.setVisibility(View.VISIBLE);
//                enemyFoodCapacity4.setVisibility(View.VISIBLE);
//                enemyFoodCapacity5.setVisibility(View.VISIBLE);
//                break;
//            }
//            case 6: {
//                enemyFoodCapacity1.setVisibility(View.VISIBLE);
//                enemyFoodCapacity2.setVisibility(View.VISIBLE);
//                enemyFoodCapacity3.setVisibility(View.VISIBLE);
//                enemyFoodCapacity4.setVisibility(View.VISIBLE);
//                enemyFoodCapacity5.setVisibility(View.VISIBLE);
//                enemyFoodCapacity6.setVisibility(View.VISIBLE);
//                break;
//            }
//        }
//    }
//
//    private void renderPlayerFood(List<Animal> playerAnimals) {
//        playerFoodCapacity1.setText(playerAnimals.get(0).getCapacityFood());
//        playerFoodCapacity2.setText(playerAnimals.get(1).getCapacityFood());
//        playerFoodCapacity3.setText(playerAnimals.get(2).getCapacityFood());
//        playerFoodCapacity4.setText(playerAnimals.get(3).getCapacityFood());
//        playerFoodCapacity5.setText(playerAnimals.get(4).getCapacityFood());
//        playerFoodCapacity6.setText(playerAnimals.get(5).getCapacityFood());
//
//        switch (playerAnimals.size()){
//            case 1: {
//                playerFoodCapacity1.setVisibility(View.VISIBLE);
//                break;
//            }
//            case 2: {
//                playerFoodCapacity1.setVisibility(View.VISIBLE);
//                playerFoodCapacity2.setVisibility(View.VISIBLE);
//                break;
//            }
//            case 3: {
//                playerFoodCapacity1.setVisibility(View.VISIBLE);
//                playerFoodCapacity2.setVisibility(View.VISIBLE);
//                playerFoodCapacity3.setVisibility(View.VISIBLE);
//                break;
//            }
//            case 4: {
//                playerFoodCapacity1.setVisibility(View.VISIBLE);
//                playerFoodCapacity2.setVisibility(View.VISIBLE);
//                playerFoodCapacity3.setVisibility(View.VISIBLE);
//                playerFoodCapacity4.setVisibility(View.VISIBLE);
//                break;
//            }
//            case 5: {
//                playerFoodCapacity1.setVisibility(View.VISIBLE);
//                playerFoodCapacity2.setVisibility(View.VISIBLE);
//                playerFoodCapacity3.setVisibility(View.VISIBLE);
//                playerFoodCapacity4.setVisibility(View.VISIBLE);
//                playerFoodCapacity5.setVisibility(View.VISIBLE);
//                break;
//            }
//            case 6: {
//                playerFoodCapacity1.setVisibility(View.VISIBLE);
//                playerFoodCapacity2.setVisibility(View.VISIBLE);
//                playerFoodCapacity3.setVisibility(View.VISIBLE);
//                playerFoodCapacity4.setVisibility(View.VISIBLE);
//                playerFoodCapacity5.setVisibility(View.VISIBLE);
//                playerFoodCapacity6.setVisibility(View.VISIBLE);
//                break;
//            }
//        }
//    }

    public void setHandFragment(HandFragment handFragment) {
        this.handFragment = handFragment;
    }

    public void setSender(Sendable sender) {
        this.sender = sender;
    }
}
