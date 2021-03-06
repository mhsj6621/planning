package link.ebbinghaus.planning.core.db.dao;

import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import link.ebbinghaus.planning.core.db.DBManager;
import link.ebbinghaus.planning.core.db.decorator.IBaseDaoDecorator;

/**
 * <strong>此基类定义和实现了一些通用方法,Dao底层方法</strong><br>
 * <br>
 * 通用方法分别为:<br>
 * <ul>
 * <li>①对单记录以主键为依据的增、删、改、查</li>
 * <li>②批量对记录进行增、删</li>
 * </ul>
 *
 * Dao的操作术语为增insert、删delete、改update、查select,<br>
 * 这个系列的通用方法的实现,可以调用Dao的底层方法_insert...系列,也可以直接操作db,也可以互相调用,<br>
 * 其中_insert系列的底层方法最优先被调用,其次是互相调用,再其次是db(从效率和简洁性综合考虑);<br>
 *
 * <ul>
 * <li
 * <li>!Dao系列的方法都作为底层类,不直接与外部交互(至于相应的DaoDecorator交互)</li>
 * <li>!一个Dao对应一个DaoDecorator包装类</li>
 * <li>!Dao系列的类不使用事务,事务在Dao所对应的DaoDecorator以方法为范畴使用事务</li>
 * <li>!protected表示将被子类访问或设置,public则表示是用来暴露给外部(对象的DaoDecorator)使用的</li>
 * </ul>
 * @param <T> Dao所对应的实体类型
 *
 */
public abstract class BaseDao<T> implements IBaseDaoDecorator<T> {
    protected SQLiteDatabase db;
    protected String mTableName;
    protected String mPkColumn;

    public BaseDao(String pkColumn,String table) {
        db = DBManager.getInstance().openDB();
        this.mTableName = table;
        this.mPkColumn = pkColumn;
    }

    /**
     * 底层抽象方法增删改,由子类实现后可以确定对哪张表进行操作;<br>
     * 这个系列的底层方法可以由insert delete update select开头的系列方法调用,<br>
     * 比起直接使用db,操作要简化一些
     */
    protected abstract List<T> _select(String querySql,String[] selectionArgs);
    protected List<T> _select(String querySql){ return _select(querySql, new String[]{}); }
    protected abstract long _insert(T t);
    protected int _delete(String where, String[] args){ return db.delete(mTableName, where, args); }
    protected abstract int _update(T t, String where, String[] args);

    protected  List<T> _select(String querySql,int... selectionArgs){
        String[] realSelectionArgs = new String[selectionArgs.length];
        for (int i = 0; i < selectionArgs.length; i++) {
            realSelectionArgs[i] = selectionArgs[i] + "";
        }
        return _select(querySql,realSelectionArgs);
    }
    /**
     * 以下按顺序为单条记录操作,批量操作,所有记录操作,<br>
     * 能实现则直接实现,不能实现则设置为抽象方法
     */
     /* 单条记录操作 */

    /**
     * 增方法,并把添加后的id填入实体中
     * @param t 欲添加的实体
     * @return 插入记录的id
     */
    @Override
    public abstract long insert(T t);

    /**
     * 删方法
     * @param pk 要删除记录的主键
     */
    @Override
    public void deleteByPrimaryKey(Long pk){
        String whereClause = mPkColumn + " = ?";
        _delete(whereClause, new String[]{pk.toString()});
    }

    /**
     * 修改方法
     * @param t 修改信息,包含主键
     */
    @Override
    public abstract void updateByPrimaryKey(T t);

    /**
     * 查找一条记录的方法
     * @param pk 要查找记录的主键
     * @return 返回查找到的结果的实体
     */
    @Override
    public T selectByPrimaryKey(Long pk){
        String querySql = "SELECT * FROM " + mTableName + " WHERE " +
                mPkColumn + " = " + pk;
        List<T> ts = _select(querySql);
        return ts.size() == 0 ? null : ts.get(0);
    }

    /* 批量记录操作 */

    /**
     * 批量增加,并把添加后的id添加的各自的实体中
     * @param ts 要增加记录的实体集
     */
    @Override
    public abstract void insertSome(List<T> ts);

    /**
     * 根据主键批量删除
     * @param pks 要删除记录的主键集
     */
    @Override
    public void deleteSomeByPrimaryKeys(List<Long> pks){
        for (Long pk: pks){
            deleteByPrimaryKey(pk);
        }
    }

    /**
     * 根据主键批量修改
     * @param ts 要修改记录的实体集
     */
    @Override
    public void updateSomeByPrimaryKeys(List<T> ts){
        for (T t:ts){
            updateByPrimaryKey(t);
        }
    }

    /* 所有记录操作 */

    /**
     * 删除所有记录
     */
    @Override
    public int deleteAll(){
         return _delete("1", null);
    }

    /**
     * 查找所有记录
     */
    @Override
    public List<T> selectAll() {
        String querySql = "SELECT * FROM " + mTableName;
        return _select(querySql);
    }

    /* --- */


    /**
     * Dao使用后必须调用此方法
     */
    @Override
    public void closeDB(){
        DBManager.getInstance().closeDB();
    }

    public void beginTransaction(){
        db.beginTransaction();
    }
    public void setTransactionSuccessful(){
        db.setTransactionSuccessful();
    }
    public void endTransaction(){
        db.endTransaction();
    }



}
