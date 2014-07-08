package de.langersteff.googlemapsgooffline;

import android.util.Log;

import com.google.android.gms.maps.model.UrlTileProvider;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by stefanlanger on 24.06.14.
 */
public class MyUrlTileProvider extends UrlTileProvider {


    private static final String FORMAT;

    static {
        FORMAT = "http://otile1.mqcdn.com/tiles/1.0.0/map/%d/%d/%d.png";
    }

    // ------------------------------------------------------------------------
    // Instance Variables
    // ------------------------------------------------------------------------

    private String mMapIdentifier;

    // ------------------------------------------------------------------------
    // Constructors
    // ------------------------------------------------------------------------

    public MyUrlTileProvider() {
        super(256, 256);
    }

    // ------------------------------------------------------------------------
    // Public Methods
    // ------------------------------------------------------------------------

    @Override
    public URL getTileUrl(int x, int y, int z) {
        try {
            return new URL(String.format(FORMAT, z, x, y));

        } catch (MalformedURLException e) {
            return null;
        }
    }


}
