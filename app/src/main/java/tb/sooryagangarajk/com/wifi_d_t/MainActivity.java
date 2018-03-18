package tb.sooryagangarajk.com.wifi_d_t;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    WifiP2pManager mManager;
    IntentFilter mIntentFilter;
    BroadcastReceiver mReceiver;
    public static String TAG="app";
    WifiP2pManager.Channel mChannel;
    public static WifiP2pManager.PeerListListener myPeerListListener;
    public static ListView listView;
    public static ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final List<String> deviceNameList = new ArrayList<String>();
        final List<String> deviceIpList = new ArrayList<String>();
        final CustomListAdapter whatever = new CustomListAdapter(this, deviceIpList, deviceIpList);
        listView = (ListView) findViewById(R.id.listid);
        listView.setAdapter(whatever);

        myPeerListListener = new WifiP2pManager.PeerListListener() {
            @Override
            public void onPeersAvailable(WifiP2pDeviceList peers) {
                for (WifiP2pDevice wifiP2pDevice : peers.getDeviceList()) {
                    Toast.makeText(MainActivity.this, wifiP2pDevice.deviceName, Toast.LENGTH_SHORT).show();
                    deviceNameList.add(wifiP2pDevice.deviceName);
                    deviceIpList.add(wifiP2pDevice.deviceAddress);
                    listView.setAdapter(whatever);
                }
            }
        };

        mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel = mManager.initialize(this, getMainLooper(), null);
        mReceiver = new WiFiDirectBroadcastReceiver(mManager, mChannel, this, myPeerListListener);
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
        mManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "discoverPeers Success");
            }
            @Override
            public void onFailure(int reasonCode) {
                Log.d(TAG, "discoverPeers onFailure");
            }
        });
    }

    protected void onResume() {
        super.onResume();
        registerReceiver(mReceiver, mIntentFilter);
    }
    /* unregister the broadcast receiver */
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mReceiver);
    }
}
