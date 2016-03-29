package link.ebbinghaus.planning.presenter;

/**
 * Created by WINFIELD on 2016/3/17.
 */
public interface CommonSelectPresenter {

    /**
     * 对toolbar进行设置
     */
    void configureToolbar();

    /**
     * 获取并设置发送者发过来的数据
     */
    void getAndSetSenderData();

    /**
     * 对RecyclerView进行配置
     */
    void configureRecyclerView();

}
