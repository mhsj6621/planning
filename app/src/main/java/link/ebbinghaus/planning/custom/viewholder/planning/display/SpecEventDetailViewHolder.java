package link.ebbinghaus.planning.custom.viewholder.planning.display;

import android.app.Activity;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.yurikami.lib.util.DateUtils;
import com.yurikami.lib.util.Utils;

import link.ebbinghaus.planning.custom.constant.config.entity.EventConfig;
import link.ebbinghaus.planning.custom.constant.entity.EventConstant;
import link.ebbinghaus.planning.custom.constant.entity.LearningEventGroupConstant;
import link.ebbinghaus.planning.custom.other.App;
import link.ebbinghaus.planning.custom.viewholder.BaseActivityViewHolder;
import link.ebbinghaus.planning.model.entity.vo.planning.display.SpecEventDetailVo;
import link.ebbinhaus.planning.R;

/**
 * Created by WINFIELD on 2016/3/28.
 */
public class SpecEventDetailViewHolder extends BaseActivityViewHolder {
    public TextView eventTypeTv;
    public TextView eventSubtypeTv;
    public TextView descriptionTv;

    public TextView strategyLabel;
    public TextView strategyTv;

    public TextView sequenceLabel;
    public TextView sequenceTv;

    public TextView expectedFinishDateTv;
    public TextView createTimeTv;
    public TextView finishedTimeTv;
    public TextView processTv;

    public TextView progressLabel;
    public TextView progressTv;

    public TextView knowledgeQuantityLabel;
    public TextView knowledgeQuantityTv;

    public TextView workloadLabel;
    public TextView workloadTv;

    public TextView remindTimeTv;

    public TextView showSequenceLabel;
    public Switch showSequenceSwitch;

    public Switch greekAlphabetSwitch;
    public TextView eventGroupTv;
    public TextView summaryTv;

    public SpecEventDetailViewHolder(Activity activity) {
        super(activity);
        eventTypeTv = find(R.id.tv_planning_display_event_detail_event_type);
        eventSubtypeTv = find(R.id.tv_planning_display_event_detail_event_subtype);
        descriptionTv = find(R.id.tv_planning_display_event_detail_description);

        strategyLabel = find(R.id.label_planning_display_event_detail_strategy);
        strategyTv = find(R.id.tv_planning_display_event_detail_strategy);

        sequenceLabel = find(R.id.label_planning_display_event_detail_sequence);
        sequenceTv = find(R.id.tv_planning_display_event_detail_sequence);

        expectedFinishDateTv = find(R.id.tv_planning_display_event_detail_expected_date);
        createTimeTv = find(R.id.tv_planning_display_event_detail_create_time);
        finishedTimeTv = find(R.id.tv_planning_display_event_detail_finished_time);
        processTv = find(R.id.tv_planning_display_event_detail_process);

        progressLabel = find(R.id.label_planning_display_event_detail_progress);
        progressTv = find(R.id.tv_planning_display_event_detail_progress);

        knowledgeQuantityLabel = find(R.id.label_planning_display_event_detail_knowledge_quantity);
        knowledgeQuantityTv = find(R.id.tv_planning_display_event_detail_knowledge_quantity);

        workloadLabel = find(R.id.label_planning_display_event_detail_workload);
        workloadTv = find(R.id.tv_planning_display_event_detail_workload);

        remindTimeTv = find(R.id.tv_planning_display_event_detail_remind_time);

        showSequenceLabel = find(R.id.label_planning_display_event_detail_is_show_sequence);
        showSequenceSwitch = find(R.id.switch_planning_display_event_detail_is_show_sequence);

        greekAlphabetSwitch = find(R.id.switch_planning_display_event_detail_is_greek_alphabet_marked);
        eventGroupTv = find(R.id.tv_planning_display_event_detail_event_group);
        summaryTv = find(R.id.tv_planning_display_event_detail_summary);
    }

    /**
     * 显示学习计划模式(即全部控件,默认显示全部控件,所以不用调用此方法,因此为空方法)
     */
    public void learningMode(){
//        strategyLabel.setVisibility(View.VISIBLE);
//        strategyTv.setVisibility(View.VISIBLE);
//
//        sequenceLabel.setVisibility(View.VISIBLE);
//        sequenceTv.setVisibility(View.VISIBLE);
//
//        progressLabel.setVisibility(View.VISIBLE);
//        progressTv.setVisibility(View.VISIBLE);
//
//        knowledgeQuantityLabel.setVisibility(View.VISIBLE);
//        knowledgeQuantityTv.setVisibility(View.VISIBLE);
//
//        workloadLabel.setVisibility(View.VISIBLE);
//        workloadTv.setVisibility(View.VISIBLE);
//
//        showSequenceLabel.setVisibility(View.VISIBLE);
//        showSequenceSwitch.setVisibility(View.VISIBLE);
    }

    /**
     * 显示普通计划模式(将会屏蔽掉一些控件)
     */
    public void normalMode(){
        strategyLabel.setVisibility(View.GONE);
        strategyTv.setVisibility(View.GONE);

        sequenceLabel.setVisibility(View.GONE);
        sequenceTv.setVisibility(View.GONE);

        progressLabel.setVisibility(View.GONE);
        progressTv.setVisibility(View.GONE);

        knowledgeQuantityLabel.setVisibility(View.GONE);
        knowledgeQuantityTv.setVisibility(View.GONE);

        workloadLabel.setVisibility(View.GONE);
        workloadTv.setVisibility(View.GONE);

        showSequenceLabel.setVisibility(View.GONE);
        showSequenceSwitch.setVisibility(View.GONE);
    }
    
    public void setData(SpecEventDetailVo vo){

        //计划组
        if (vo.eventGroup != null){
            eventGroupTv.setText(vo.eventGroup.getDescription());
        }else {
            eventGroupTv.setText(R.string.common_none);
        }

        //子类型
        if (vo.eventSubtype != null){
            eventSubtypeTv.setText(vo.eventSubtype.getEventSubtype());
        }else {
            eventSubtypeTv.setText(R.string.common_none);
        }

        //学习计划组
        if (vo.learningEventGroup != null){
            strategyTv.setText(LearningEventGroupConstant.STRATEGY[vo.learningEventGroup.getStrategy() - 1]);
            progressTv.setText(String.format("%d/%d", vo.learningEventGroup.getLearningEventFinishedCount(), vo.learningEventGroup.getLearningEventTotal()));
            knowledgeQuantityTv.setText(String.format("%d", vo.learningEventGroup.getKnowledgeQuantity()));
            workloadTv.setText(String.format(App.getContext().getString(R.string.learning_event_group_workload), vo.learningEventGroup.getWorkload()));
            sequenceTv.setText(String.format("%d", vo.event.getEventSequence()));
        }else {
            //控件会被遮住,所以不用设置默认值
        }

        //计划
        eventTypeTv.setText( EventConstant.EVENT_TYPE[vo.event.getEventType() - 1] );
        descriptionTv.setText(vo.event.getDescription());
        expectedFinishDateTv.setText(DateUtils.formatTimestamp2ChnDate(vo.event.getEventExpectedFinishedDate()));
        createTimeTv.setText(DateUtils.formatTimestamp2Datetime(vo.event.getCreateTime()));
        processTv.setText( vo.event.getEventType() == EventConfig.TYPE_SPEC_LEARNING ?
                EventConstant.PROCESS_LEARNING[vo.event.getEventProcess() - 1] :
                EventConstant.PROCESS_NORMAL[vo.event.getEventProcess() - 1]);

        if (vo.event.getIsEventFinished()) {
            finishedTimeTv.setText(DateUtils.formatTimestamp2Datetime(vo.event.getEventFinishedTime()));
        }else {
            finishedTimeTv.setText(R.string.planning_display_spec_event_detail_unfinished);
        }

        if (vo.event.getIsRemind()){
            remindTimeTv.setText(DateUtils.formatTimestamp2HourMinute(vo.event.getRemindTime()));
        }else {
            remindTimeTv.setText(R.string.planning_display_spec_event_detail_no_prompt);
        }
        showSequenceSwitch.setChecked(Utils.equals(true, vo.event.getIsShowEventSequence()));
        greekAlphabetSwitch.setChecked(Utils.equals(true, vo.event.getIsGreekAlphabetMarked()));
        summaryTv.setText(vo.event.getSummary());
    }
}
