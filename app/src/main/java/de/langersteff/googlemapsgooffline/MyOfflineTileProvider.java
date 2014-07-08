package de.langersteff.googlemapsgooffline;

import android.os.Environment;

import com.google.android.gms.maps.model.Tile;
import com.google.android.gms.maps.model.TileProvider;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by stefanlanger on 24.06.14.
 */
public class MyOfflineTileProvider implements TileProvider {

    private static final String FILE_FORMAT;
    private static final String DIR_FORMAT;
    private static final int TILE_WIDTH = 256;
    private static final int TILE_HEIGHT = 256;
    private static final int BUFFER_SIZE = 16 * 1024;

    static {
        DIR_FORMAT = "/Android/data/de.langersteff.googlemapsgooffline/map/%d/%d/";
        FILE_FORMAT = "%d.png";
    }


    public MyOfflineTileProvider() {
    }

    @Override
    public Tile getTile(int x, int y, int zoom) {

        try {
            byte[] image = readTileImage(x, y, zoom);
            return image == null ? null : new Tile(TILE_WIDTH, TILE_HEIGHT, image);
        } catch (IOException e) {
            return NO_TILE;
        }
    }

    private byte[] readTileImage(int x, int y, int zoom) throws IOException {
        FileInputStream in = null;
        ByteArrayOutputStream buffer = null;

        try {
            File tileFile = new File(getTileFilename(x, y, zoom));

            in = new FileInputStream(tileFile);

            buffer = new ByteArrayOutputStream();

            int nRead;
            byte[] data = new byte[BUFFER_SIZE];

            while ((nRead = in.read(data, 0, BUFFER_SIZE)) != -1) {
                buffer.write(data, 0, nRead);
            }
            buffer.flush();

            return buffer.toByteArray();
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        } finally {
            if (in != null) try { in.close(); } catch (Exception ignored) {}
            if (buffer != null) try { buffer.close(); } catch (Exception ignored) {}
        }
    }

    private String getTileFilename(int x, int y, int zoom) {
        return  offlineFolder(zoom, x) + String.format(FILE_FORMAT, y);
    }

    private String offlineFolder(int zoom, int x) {
        return Environment.getExternalStorageDirectory().getPath() + String.format(DIR_FORMAT, zoom, x);
    }


}
