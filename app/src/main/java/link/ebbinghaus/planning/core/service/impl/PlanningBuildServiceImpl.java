package link.ebbinghaus.planning.core.service.impl;

import com.yurikami.lib.util.DateUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import link.ebbinghaus.planning.app.constant.config.entity.LearningEventGroupConfig;
import link.ebbinghaus.planning.app.constant.module.PlanningBuildConstant;
import link.ebbinghaus.planning.core.db.decorator.impl.DefaultInputValueDaoDecorator;
import link.ebbinghaus.planning.core.db.decorator.impl.EventDaoDecorator;
import link.ebbinghaus.planning.core.db.decorator.impl.EventGroupDaoDecorator;
import link.ebbinghaus.planning.core.db.decorator.impl.EventSubtypeDaoDecorator;
import link.ebbinghaus.planning.core.db.decorator.impl.FastTemplateDaoDecorator;
import link.ebbinghaus.planning.core.model.local.po.DefaultInputValue;
import link.ebbinghaus.planning.core.model.local.po.Event;
import link.ebbinghaus.planning.core.model.local.po.EventGroup;
import link.ebbinghaus.planning.core.model.local.po.EventSubtype;
import link.ebbinghaus.planning.core.model.local.po.FastTemplate;
import link.ebbinghaus.planning.core.model.local.sys.Tab;
import link.ebbinghaus.planning.core.model.local.vo.planning.build.InputEventVo;
import link.ebbinghaus.planning.core.service.PlanningBuildService;
import link.ebbinghaus.planning.ui.view.planning.build.fragment.PlanningBuildAbstractFragment;
import link.ebbinghaus.planning.ui.view.planning.build.fragment.PlanningBuildSpecificFragment;

/**
 * Created by WINFIELD on 2016/3/14.
 */
public class PlanningBuildServiceImpl implements PlanningBuildService {

    @Override
    public List<Tab> makePlanningBuildTabs() {
        List<Tab> tabs = new ArrayList<>();
        tabs.add(new Tab(PlanningBuildConstant.TAB_NAME_SPECIFIC, new PlanningBuildSpecificFragment()));
        tabs.add(new Tab(PlanningBuildConstant.TAB_NAME_ABSTRACT, new PlanningBuildAbstractFragment()));
        return tabs;
    }

    @Override
    public List<Long> addLearningEvent(InputEventVo inputEvent) {
        EventDaoDecorator eventDao = new EventDaoDecorator();
        List<Long> eventIds = eventDao.insertLearningEvents(inputEvent, LearningEventGroupConfig.STRATEGY[inputEvent.getStrategy() - 1]);
        eventDao.closeDB();
        return eventIds;
    }

    @Override
    public Long addNormalEvent(Event event) {
        EventDaoDecorator dao = new EventDaoDecorator();
        Long id = dao.insertNormalEvent(event);
        dao.closeDB();
        return id;
    }

    @Override
    public void addAbstractEvent(Event event) {
        EventDaoDecorator dao = new EventDaoDecorator();
        dao.insertAbstractEvent(event);
        dao.closeDB();
    }

    @Override
    public DefaultInputValue findDefaultInputValue() {
        DefaultInputValueDaoDecorator dao = new DefaultInputValueDaoDecorator();
        DefaultInputValue defaultInputValue = dao.selectAll().get(0);
        dao.closeDB();
        return defaultInputValue;
    }

    @Override
    public TreeMap<Long, Integer> find30daysStatistics(long startDateTimestamp) {
        TreeMap<Long, Integer> statistics = new TreeMap<>();
        EventDaoDecorator dao = new EventDaoDecorator();
        List<Event> events = dao.select30daysSpecEventsFrom(startDateTimestamp);
        dao.closeDB();
        //将30天中每天的数量初始化为0
        for (int dayOffset = 0; dayOffset < 30 ;dayOffset++){
            statistics.put(DateUtils.timestampAfter(startDateTimestamp,dayOffset), 0);
        }
        //统计数量
        for (Event event : events) {
            Long key = event.getEventExpectedFinishedDate();
            statistics.put(key, statistics.get(key) + 1);
        }
        return statistics;
    }

    @Override
    public List<EventSubtype> findAllEventSubtype() {
        EventSubtypeDaoDecorator dao = new EventSubtypeDaoDecorator();
        List<EventSubtype> eventSubtypes = dao.selectAll();
        dao.closeDB();
        return eventSubtypes;
    }

    @Override
    public List<FastTemplate> findAllSpecLearningFastTemplate() {
        FastTemplateDaoDecorator dao = new FastTemplateDaoDecorator();
        List<FastTemplate> fastTemplates = dao.selectSpecLearningAll();
        dao.closeDB();
        return fastTemplates;
    }

    @Override
    public List<FastTemplate> findAllSpecNormalFastTemplate() {
        FastTemplateDaoDecorator dao = new FastTemplateDaoDecorator();
        List<FastTemplate> fastTemplates = dao.selectSpecNormalAll();
        dao.closeDB();
        return fastTemplates;
    }

    @Override
    public List<FastTemplate> findAllAbstFastTemplate() {
        FastTemplateDaoDecorator dao = new FastTemplateDaoDecorator();
        List<FastTemplate> fastTemplates = dao.selectAbstractAll();
        dao.closeDB();
        return fastTemplates;
    }

    @Override
    public List<EventGroup> findAllEventGroup() {
        EventGroupDaoDecorator dao = new EventGroupDaoDecorator();
        List<EventGroup> eventGroups = dao.selectAll();
        dao.closeDB();
        return eventGroups;
    }

}
