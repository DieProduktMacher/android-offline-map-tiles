package de.langersteff.googlemapsgooffline;

import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.TileProvider;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap;
    private MyUrlTileProvider mUrlTileProvider;
    private MyOfflineTileProvider mOfflineTileProvider;
    private Button mBtnGoOffline;
    private Boolean isOffline = false;
    private TileOverlay mTileOverlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mBtnGoOffline = (Button) findViewById(R.id.go_offline);
        mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                .getMap();


        mBtnGoOffline.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isOffline) {
                    goOffline();
                } else {
                    goOnline();
                }
            }
        });

        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(48.144527, 11.565928))
                .title("A Marker"));
    }

    private void goOffline() {
        mOfflineTileProvider = new MyOfflineTileProvider();
        setUpMap(mOfflineTileProvider);
        isOffline = true;
        mBtnGoOffline.setText(getString(R.string.go_online));
        mMap.setMapType(GoogleMap.MAP_TYPE_NONE);
    }

    private void goOnline() {
        if (mTileOverlay != null) {
            mTileOverlay.remove();
            isOffline = false;
            mBtnGoOffline.setText(getString(R.string.go_offline));
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    private void setUpMap(TileProvider tileProvider) {
        mTileOverlay = mMap.addTileOverlay( new TileOverlayOptions().tileProvider( tileProvider ) );
    }
}
