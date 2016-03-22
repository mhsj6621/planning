package link.ebbinghaus.planning.custom.adapter.planning.display.spec;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yurikami.lib.base.BaseFragment;
import com.yurikami.lib.entity.Datetime;
import com.yurikami.lib.util.CachePool;
import com.yurikami.lib.util.DateUtils;
import com.yurikami.lib.util.ViewGroupUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import link.ebbinghaus.planning.custom.other.App;
import link.ebbinghaus.planning.model.PlanningDisplaySpecificModel;
import link.ebbinghaus.planning.model.entity.po.Event;
import link.ebbinghaus.planning.model.impl.PlanningDisplaySpecificModelImpl;
import link.ebbinhaus.planning.R;

/**
 * Created by WINFIELD on 2016/3/2.
 */
public class MonthRecyclerViewAdapter extends RecyclerView.Adapter<MonthRecyclerViewAdapter.ViewHolder>
        implements BaseFragment.OnFragmentDestroyListener {
    private final int ROW_EVENT_COUNT = 3;

    private Context mContext;
    private LayoutInflater mInflater;
    //某一个月所有的Event
    private List<Event> mEvents;
    private Datetime mDatetime;
    //某一个月按日归类的Event
    private List<Event>[] mBlocks;

    private int mDayInMonth;
    //存储这个月的日和星期的集合
    private List<Datetime> mDayWeekListitems = new ArrayList<>();

    private CachePool<Integer, LinearLayout> mViewCachePool = CachePool.getInstance();
    private PlanningDisplaySpecificModel mPlanningDisplaySpecificModel;

    public MonthRecyclerViewAdapter(Context context, List<Event> events, Datetime datetime) {
        this.mContext = context;
        this.mEvents = events;
        this.mDatetime = datetime;
        this.mInflater = LayoutInflater.from(this.mContext);
        this.mDayInMonth = DateUtils.dayInMonth(mDatetime);

        mPlanningDisplaySpecificModel = new PlanningDisplaySpecificModelImpl();

        mPlanningDisplaySpecificModel.makeDayWeekListitems(mDayWeekListitems, mDayInMonth, datetime);
        mBlocks = mPlanningDisplaySpecificModel.eventsToBlocks(mEvents, mDayInMonth);
    }

    @Override
    public MonthRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.listitem_planning_display_spec_month, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MonthRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.blockFl.removeAllViews();

        List<Event> blockEvents = mBlocks[position];
        int blockEventCount = blockEvents.size();

        if (blockEventCount != 0) {
            LinearLayout block = mViewCachePool.getCache(position);
            if (block == null) {
                ViewGroup.LayoutParams lp = ViewGroupUtils.genMatchWrapLP();
                block = ViewGroupUtils.newVerticalLl(mContext, lp);

                //把block里面的所有Event放入block容器中
                LinearLayout row = ViewGroupUtils.newHorizontalLl(mContext, lp);
                for (int i = 1; i <= blockEventCount; i++) {
                    Event event = blockEvents.get(i - 1);
                    TextView eventTv = (TextView) mInflater.inflate(R.layout.textview_planning_display_spec_month_event, row, false);
                    holder.setMonthEventAttrs(eventTv, event);
                    row.addView(eventTv);
                    //每隔LISTITEM_ROW_COUNT个Event,新建一行,并把上一行添加到Row中
                    if ((i % ROW_EVENT_COUNT == 0) || (i == blockEventCount)) {
                        block.addView(row);
                        row = ViewGroupUtils.newHorizontalLl(mContext, lp);
                    }
                }
                mViewCachePool.putCache(position, block);
            }
            ViewGroupUtils.snipParent(block);
            holder.blockFl.addView(block);
        }

        holder.setDayWeek(mDayWeekListitems.get(position));


    }



    @Override
    public int getItemCount() {
        if(mBlocks != null)
            return mBlocks.length;
        return 0;
    }

    @Override
    public void onDestroy() {
        //Fragment的生命周期结束后,释放View缓存池所占用的内存
        mViewCachePool.destroyCachePool();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_planning_display_spec_month_day_of_month) TextView dayOfMonthTv;
        @Bind(R.id.tv_planning_display_spec_month_day_of_week) TextView dayOfWeekTv;
        @Bind(R.id.iv_planning_display_spec_month_add_event) ImageView addEventIv;
        @Bind(R.id.fl_planning_display_spec_month_block) FrameLayout blockFl;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void setDayWeek(Datetime dayWeek){
            Resources res = mContext.getResources();
            dayOfMonthTv.setText(String.format(res.getString(R.string.planning_display_spec_month_listitem_day), dayWeek.getDay()));
            dayOfWeekTv.setText(String.format(res.getString(R.string.planning_display_spec_month_listitem_week), dayWeek.getChnWeek()));
        }

        public void setMonthEventAttrs(TextView eventTv, Event event){
            eventTv.setText(event.getDescription());
            int width = App.getSystemInfo().getWindowWidth() / 4;
            if (event.getEventType() == 2){
                eventTv.setBackgroundResource(R.drawable.planning_display_spec_month_event_normal);
            }
            eventTv.setWidth(width);
        }

    }
}