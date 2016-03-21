package link.ebbinghaus.planning.custom.viewholder;

import android.app.Activity;
import android.view.View;

/**
 * Created by WINFIELD on 2016/3/21.
 */
public class BaseActivityViewHolder {
    private Activity mActivity;

    public BaseActivityViewHolder(Activity activity) {
        this.mActivity = activity;
    }

    protected  <T extends View> T find(int viewId) {
        return (T) mActivity.findViewById(viewId);
    }
}
