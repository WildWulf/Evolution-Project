
package com.example.pc.evolutiongame.wifidirect.discovery;

import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.ActionListener;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.net.wifi.p2p.WifiP2pManager.ConnectionInfoListener;
import android.net.wifi.p2p.WifiP2pManager.DnsSdServiceResponseListener;
import android.net.wifi.p2p.WifiP2pManager.DnsSdTxtRecordListener;
import android.net.wifi.p2p.nsd.WifiP2pDnsSdServiceInfo;
import android.net.wifi.p2p.nsd.WifiP2pDnsSdServiceRequest;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pc.evolutiongame.BoardFragment;
import com.example.pc.evolutiongame.GameMode;
import com.example.pc.evolutiongame.HandFragment;
import com.example.pc.evolutiongame.core.Connectable;
import com.example.pc.evolutiongame.core.EvolutionContext;
import com.example.pc.evolutiongame.core.client.TcpClient;
import com.example.pc.evolutiongame.model.Room;

import java.util.HashMap;
import java.util.Map;

import static android.os.SystemClock.sleep;
import static com.example.pc.evolutiongame.Configuration.getBotConfiguration;
import static com.example.pc.evolutiongame.Configuration.getHumanConfiguration;
import static com.example.pc.evolutiongame.Configuration.getServerConfiguration;
import static com.example.pc.evolutiongame.core.server.TcpServer.SERVER_PORT;
import static java.lang.String.format;

public class WiFiServiceDiscoveryActivity extends Activity
        implements WiFiDirectServicesList.DeviceClickListener, Handler.Callback, ConnectionInfoListener,
        Connectable {

    private static final int NUMBER_PLAYER = 2;
    public static final String TAG = "evolutiongame";

    public static final String TXTRECORD_PROP_AVAILABLE = "available";
    public static final String SERVICE_INSTANCE = "_evolution_game";
    public static final String SERVICE_REG_TYPE = "_evolution._tcp";

    public static final int SET_ID = 0x400 + 1;
    public static final int ROOM_READ = 0x400 + 2;
    public static final int ENDGAME = 0x400 + 3;
    private WifiP2pManager manager;

    private final IntentFilter intentFilter = new IntentFilter();
    private Channel channel;
    private BroadcastReceiver receiver = null;
    private WifiP2pDnsSdServiceRequest serviceRequest;

    private Handler handler = new Handler(this);
    private BoardFragment boardFragment;

    private TextView statusTxtView;
    private HandFragment handFragment;
    private String playerId;
    private Room room;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main);
        statusTxtView = (TextView) findViewById(R.id.status_text);

        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        intentFilter
                .addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        intentFilter
                .addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

        manager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        channel = manager.initialize(this, getMainLooper(), null);
        startRegistrationAndDiscovery();

        WiFiDirectServicesList servicesList = new WiFiDirectServicesList();
        getFragmentManager().beginTransaction()
                .add(R.id.container_root, servicesList, "services").commit();

        sleep(10);
    }

    @Override
    protected void onRestart() {
        Fragment frag = getFragmentManager().findFragmentByTag("services");
        if (frag != null) {
            getFragmentManager().beginTransaction().remove(frag).commit();
        }
        super.onRestart();
    }

    @Override
    protected void onStop() {
        if (manager != null && channel != null) {
            manager.removeGroup(channel, new ActionListener() {

                @Override
                public void onFailure(int reasonCode) {
                    Log.d(TAG, "Disconnect failed. Reason :" + reasonCode);
                }

                @Override
                public void onSuccess() {
                }

            });
        }
        super.onStop();
    }

    /**
     * Registers a local service and then initiates a service discovery
     */
    private void startRegistrationAndDiscovery() {
        Map<String, String> record = new HashMap<String, String>();
        record.put(TXTRECORD_PROP_AVAILABLE, "visible");

        WifiP2pDnsSdServiceInfo service = WifiP2pDnsSdServiceInfo.newInstance(
                SERVICE_INSTANCE, SERVICE_REG_TYPE, record);
        manager.addLocalService(channel, service, new ActionListener() {

            @Override
            public void onSuccess() {
                appendStatus("Added Local Service");
            }

            @Override
            public void onFailure(int error) {
                appendStatus("Failed to add a service");
            }
        });

        discoverService();

    }

    private void discoverService() {

        /*
         * Register listeners for DNS-SD services. These are callbacks invoked
         * by the system when a service is actually discovered.
         */

        manager.setDnsSdResponseListeners(channel,
                new DnsSdServiceResponseListener() {

                    @Override
                    public void onDnsSdServiceAvailable(String instanceName,
                                                        String registrationType, WifiP2pDevice srcDevice) {

                        // A service has been discovered. Is this our app?

                        if (instanceName.equalsIgnoreCase(SERVICE_INSTANCE)) {

                            // update the UI and add the item the discovered
                            // device.
                            WiFiDirectServicesList fragment = (WiFiDirectServicesList) getFragmentManager()
                                    .findFragmentByTag("services");
                            if (fragment != null) {
                                WiFiDirectServicesList.WiFiDevicesAdapter adapter = ((WiFiDirectServicesList.WiFiDevicesAdapter) fragment
                                        .getListAdapter());
                                WiFiP2pService service = new WiFiP2pService();
                                service.device = srcDevice;
                                service.instanceName = instanceName;
                                service.serviceRegistrationType = registrationType;
                                adapter.add(service);
                                adapter.notifyDataSetChanged();
                                Log.d(TAG, "onBonjourServiceAvailable "
                                        + instanceName);
                            }
                        }

                    }
                }, new DnsSdTxtRecordListener() {

                    /**
                     * A new TXT record is available. Pick up the advertised
                     * buddy name.
                     */
                    @Override
                    public void onDnsSdTxtRecordAvailable(
                            String fullDomainName, Map<String, String> record,
                            WifiP2pDevice device) {
                        Log.d(TAG,
                                device.deviceName + " is "
                                        + record.get(TXTRECORD_PROP_AVAILABLE));
                    }
                });

        // After attaching listeners, create a service request and initiate
        // discovery.
        serviceRequest = WifiP2pDnsSdServiceRequest.newInstance();
        manager.addServiceRequest(channel, serviceRequest,
                new ActionListener() {

                    @Override
                    public void onSuccess() {
                        appendStatus("Added service discovery request");
                    }

                    @Override
                    public void onFailure(int arg0) {
                        appendStatus("Failed adding service discovery request");
                    }
                });
        manager.discoverServices(channel, new ActionListener() {

            @Override
            public void onSuccess() {
                appendStatus("Service discovery initiated");
            }

            @Override
            public void onFailure(int arg0) {
                appendStatus("Service discovery failed");

            }
        });
    }

    @Override
    public void connectP2p(WiFiP2pService service) {
        WifiP2pConfig config = new WifiP2pConfig();
        config.deviceAddress = service.device.deviceAddress;
        config.wps.setup = WpsInfo.PBC;
        if (serviceRequest != null)
            manager.removeServiceRequest(channel, serviceRequest,
                    new ActionListener() {

                        @Override
                        public void onSuccess() {
                        }

                        @Override
                        public void onFailure(int arg0) {
                        }
                    });

        manager.connect(channel, config, new ActionListener() {

            @Override
            public void onSuccess() {
                appendStatus("Connecting to service");
            }

            @Override
            public void onFailure(int errorCode) {
                appendStatus("Failed connecting to service");
            }
        });
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case SET_ID:
                playerId = (String) msg.obj;
                break;

            case ROOM_READ:
                room = (Room) msg.obj;
                boardFragment.refreshRoom(playerId, room);
                handFragment.refreshRoom(playerId, room);
                break;

            case ENDGAME:
                room = (Room) msg.obj;
                String winnerId = room.getWinnerId();
                if (playerId.equals(winnerId)) {
                    Toast.makeText(this, "You are winner!", Toast.LENGTH_LONG).show();
                    break;
                }

                Toast.makeText(this, format("Winner is %s", winnerId), Toast.LENGTH_LONG).show();
                break;
        }
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        receiver = new WiFiDirectBroadcastReceiver(manager, channel, this);
        registerReceiver(receiver, intentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        boardFragment.refreshRoom(playerId, room);
    }

    @Override
    public void onConnectionInfoAvailable(WifiP2pInfo p2pInfo) {
        Intent intent = getIntent();
        String gameMode = intent.getStringExtra("gameMode");

        /*
         * The group owner accepts connections using a server socket and then spawns a
         * client socket for every client. This is handled by {@code
         * GroupOwnerSocketHandler}
         */
        if (p2pInfo.isGroupOwner) {
            Log.d(TAG, "Connected as group owner");
            getServerConfiguration(handler, gameMode, this).start(SERVER_PORT);
        } else {
            Log.d(TAG, "Connected as peer");
            String serverAddress = p2pInfo.groupOwnerAddress.getHostAddress();

            TcpClient player = getPlayer(gameMode);
            player.start(serverAddress, SERVER_PORT);

            boardFragment = new BoardFragment();
            handFragment = new HandFragment();
            handFragment.setSender(player.getContext().getSender());
            boardFragment.setSender(player.getContext().getSender());
            boardFragment.setHandFragment(handFragment);
            getFragmentManager().beginTransaction().replace(R.id.container_root, boardFragment).commit();
        }


        statusTxtView.setVisibility(View.GONE);
    }

    private TcpClient getPlayer(String gameMode) {
        if (GameMode.valueOf(gameMode.toUpperCase()) == GameMode.PLAYER) {
            return getHumanConfiguration(handler);
        }
        if (GameMode.valueOf(gameMode.toUpperCase()) == GameMode.BOT) {
            return getBotConfiguration(handler);
        }
        return null;
    }

    public void appendStatus(String status) {
        String current = statusTxtView.getText().toString();
        statusTxtView.setText(format("%s\n%s", current, status));
    }

    @Override
    public void started(EvolutionContext context) {
        System.out.println("Server is started");

        context.setRoom(new Room(NUMBER_PLAYER));

        TcpClient player = getPlayer(context.getGameMode());
        player.start(context.getAddress().getHostAddress(), context.getPort());

        boardFragment = new BoardFragment();
        handFragment = new HandFragment();
        handFragment.setSender(player.getContext().getSender());
        boardFragment.setSender(player.getContext().getSender());
        boardFragment.setHandFragment(handFragment);
        getFragmentManager().beginTransaction().replace(R.id.container_root, boardFragment).commit();
    }
}
