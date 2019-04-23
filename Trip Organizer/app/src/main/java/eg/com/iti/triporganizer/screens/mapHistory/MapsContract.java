package eg.com.iti.triporganizer.screens.mapHistory;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;

public interface MapsContract {

    interface MapsPresenter
    {
        public String getRequestedUrl(LatLng origin, LatLng dest);
    }
}
