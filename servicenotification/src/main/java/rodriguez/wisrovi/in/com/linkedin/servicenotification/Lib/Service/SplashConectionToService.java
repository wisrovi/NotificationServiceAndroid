package rodriguez.wisrovi.in.com.linkedin.servicenotification.Lib.Service;

import android.content.Context;

import rodriguez.wisrovi.in.com.linkedin.servicenotification.Lib.Broadcast.UtilBroadcast;

public class SplashConectionToService {
    public void StartServiceIfThisIsClose(Context thisContext){
        new UtilBroadcast().StartServiceIfIsClose(thisContext);
    }
}
