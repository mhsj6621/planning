package link.ebbinghaus.planning.app.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import link.ebbinghaus.planning.app.service.AmendDatabaseProcessAndRelatedService;

public class AmendDatabaseProcessAndRelatedReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, AmendDatabaseProcessAndRelatedService.class);
        context.startService(i);
    }
}
