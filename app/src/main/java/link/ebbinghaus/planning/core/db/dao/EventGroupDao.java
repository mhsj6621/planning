package link.ebbinghaus.planning.core.db.dao;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import link.ebbinghaus.planning.app.constant.config.DBConfig;
import link.ebbinghaus.planning.core.model.local.po.EventGroup;

/**
 * Created by WINFIELD on 2016/3/14.
 */
public class EventGroupDao extends BaseDao<EventGroup> implements DBConfig.EventGroupColumn{

    public EventGroupDao() {
        super(PK_EVENT_GROUP_ID,DBConfig.Table.EVENT_GROUP);
    }

    @Override
    protected List<EventGroup> _select(String querySql,String[] selectionArgs) {
        Cursor cursor = db.rawQuery(querySql, selectionArgs);
        List<EventGroup> eventGroups = new ArrayList<>();
        while (cursor.moveToNext()){
            EventGroup eventGroup = new EventGroup();
            eventGroup.filledByCursor(cursor);
            eventGroups.add(eventGroup);
        }
        return eventGroups;
    }

    @Override
    protected long _insert(EventGroup eventGroup) {
        ContentValues values = new ContentValues();
        eventGroup.convertToContentValues(values);
        return db.insert(mTableName, null, values);
    }

    @Override
    protected int _update(EventGroup eventGroup, String where, String[] args) {
        ContentValues values = new ContentValues();
        eventGroup.convertToContentValues(values);
        return db.update(mTableName, values, where, args);
    }

    @Override
    public long insert(EventGroup eventGroup) {
        eventGroup.setPkEventGroupId(_insert(eventGroup));
        return eventGroup.getPkEventGroupId();
    }

    @Override
    public void updateByPrimaryKey(EventGroup eventGroup) {
        String whereClause = mPkColumn + " = ?";
        _update(eventGroup, whereClause, new String[]{eventGroup.getPkEventGroupId().toString()});
    }

    @Override
    public void insertSome(List<EventGroup> eventGroups) {
        for (EventGroup eventGroup : eventGroups) {
            eventGroup.setPkEventGroupId(_insert(eventGroup));
        }
    }

    /* 以下方法为非通用方法 */

    /**
     * 更新(增加)学习计划个数
     * @param pk 计划组主键
     * @param increment 增加的个数
     */
    public void updateLearningEventCount(Long pk, int increment){
        String updateSql = "UPDATE "+ mTableName
                +" SET "+ LEARNING_EVENT_COUNT +" = ("+ LEARNING_EVENT_COUNT +" + ?) "
                + "WHERE "+ PK_EVENT_GROUP_ID +" = ?";
        db.execSQL(updateSql,new Object[]{increment,pk});
    }

    /**
     * 更新(增加)普通计划个数
     * @param pk 计划组主键
     * @param increment 增加的个数
     */
    public void updateNormalEventCount(Long pk, int increment){
        String updateSql = "UPDATE "+ mTableName
                +" SET "+ NORMAL_EVENT_COUNT +" = ("+ NORMAL_EVENT_COUNT +" + ?) "
                + "WHERE "+ PK_EVENT_GROUP_ID +" = ?";
        db.execSQL(updateSql,new Object[]{increment,pk});
    }

    /**
     * 更新(增加)模糊计划个数
     * @param pk 计划组主键
     * @param increment 增加的个数
     */
    public void updateAbstractEventCount(Long pk, int increment){
        String updateSql = "UPDATE "+ mTableName
                +" SET "+ ABSTRACT_EVENT_COUNT +" = ("+ ABSTRACT_EVENT_COUNT +" + ?) "
                + "WHERE "+ PK_EVENT_GROUP_ID +" = ?";
        db.execSQL(updateSql,new Object[]{increment,pk});
    }

    /**
     * 查找所有的具体计划组
     * @return 所有的具体计划组
     */
    public List<EventGroup> selectAllSpecEventGroup(){
        String querySql = "SELECT * FROM "+ mTableName
                +" WHERE ("+ LEARNING_EVENT_COUNT +" + "+ NORMAL_EVENT_COUNT +") > 0";
        return _select(querySql);
    }

    /**
     * 查找所有的模糊计划组
     * @return 所有的模糊计划组
     */
    public List<EventGroup> selectAllAbstEventGroup(){
        String querySql = "SELECT * FROM "+ mTableName
                +" WHERE "+ ABSTRACT_EVENT_COUNT +" > 0";
        return _select(querySql);
    }

}
