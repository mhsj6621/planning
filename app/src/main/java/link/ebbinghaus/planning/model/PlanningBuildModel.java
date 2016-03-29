package link.ebbinghaus.planning.model;

import java.util.List;

import link.ebbinghaus.planning.model.entity.po.DefaultInputValue;
import link.ebbinghaus.planning.model.entity.po.Event;
import link.ebbinghaus.planning.model.entity.po.EventGroup;
import link.ebbinghaus.planning.model.entity.po.EventSubtype;
import link.ebbinghaus.planning.model.entity.po.FastTemplate;
import link.ebbinghaus.planning.model.entity.sys.Tab;
import link.ebbinghaus.planning.model.entity.vo.planning.build.InputEventVo;

/**
 * Created by WINFIELD on 2016/3/14.
 */
public interface PlanningBuildModel {

    /**
     * 创建PlanningBuild的最顶层tabs
     * @return PlanningBuild的tabs
     */
    List<Tab> makePlanningBuildTabs();

    /**
     * 根据参数里的策略,向数据库添加相应的Event和LearningEventGroup
     * @param inputEvent 学习计划分项
     */
    void addLearningEvent(InputEventVo inputEvent);

    /**
     * 向数据库保存一个制定好的普通计划,会把和普通计划无关的属性设null
     * @param event 普通计划
     */
    void addNormalEvent(Event event);

    /**
     * 向数据库保存一个制定好的模糊计划
     * @param event
     */
    void addAbstractEvent(Event event);

    /**
     * 获取数据库中保存的默认输入值
     */
    DefaultInputValue findDefaultInputValue();

    /**
     * 查找所有子类型
     * @return 所有子类型
     */
    List<EventSubtype> findAllEventSubtype();

    /**
     * 查找具体学习计划的所有快速模板
     * @return 具体学习计划的所有快速模板
     */
    List<FastTemplate> findAllSpecLearningFastTemplate();

    /**
     * 查找具体普通计划的所有快速模板
     * @return 普通计划的所有快速模板
     */
    List<FastTemplate> findAllSpecNormalFastTemplate();

    /**
     * 查找模糊计划的所有快速模板
     * @return 模糊计划的所有快速模板
     */
    List<FastTemplate> findAllAbstFastTemplate();

    /**
     * 查找所有计划组
     * @return 所有计划组
     */
    List<EventGroup> findAllEventGroup();
}
