package eg.com.iti.triporganizer.utils;

import android.graphics.Color;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public class MapColors {
    public static int[] color = {
            Color.YELLOW , Color.RED,  Color.MAGENTA ,Color.BLUE, Color.GREEN, Color.CYAN
    };
   //  BitmapDescriptorFactory used for marker icons and ground overlays.
    public static float[] markerColor =
            {
                    BitmapDescriptorFactory.HUE_YELLOW,
                    BitmapDescriptorFactory.HUE_RED,
                    BitmapDescriptorFactory.HUE_MAGENTA ,
                    BitmapDescriptorFactory.HUE_BLUE,
                    BitmapDescriptorFactory.HUE_GREEN,
                    BitmapDescriptorFactory.HUE_CYAN,
                    BitmapDescriptorFactory.HUE_ORANGE,
                    BitmapDescriptorFactory.HUE_ROSE,
                    BitmapDescriptorFactory.HUE_VIOLET,
                    BitmapDescriptorFactory.HUE_AZURE
            };
                /*HUE_MAGENTA
                 HUE_RED --------> Constant Value: 0.0
                 HUE_BLUE -------> Constant Value: 240.0
                 HUE_GREEN ------> Constant Value: 120.0
                 HUE_CYAN -------> Constant Value: 180.0
                 HUE_MAGENTA --->  Constant Value: 300.0
                 HUE_ORANGE -----> Constant Value: 30.0
                 HUE_ROSE -------> Constant Value: 330.0
                 HUE_VIOLET -----> Constant Value: 270.0
                 HUE_YELLOW -----> Constant Value: 60.0
                 HUE_AZURE  -----> Constant Value: 210.0
                */
    public static int colorIndex = 0;
}
