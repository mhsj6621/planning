package link.ebbinghaus.planning.app.constant.config;

import com.yurikami.lib.db.SqlBuilder;
import com.yurikami.lib.util.DateUtils;

/**
 * Created by WINFIELD on 2016/2/28.
 */
public class DBConfig {
    public static final String DB_NAME = "planning.db3";
    public static final int DB_VERSION = 1;

    public static final String CREATE_TABLE_EVENT = SqlBuilder.build().create(Table.EVENT)
            .pk(EventColumn.PK_EVENT_ID)
            .integer(EventColumn.LEARNING_EVENT_GROUP_ID)
            .integer(EventColumn.EVENT_GROUP_ID)
            .text(EventColumn.DESCRIPTION)
            .text(EventColumn.SUMMARY)
            .integer(EventColumn.EVENT_TYPE)
            .integer(EventColumn.EVENT_SUBTYPE_ID)
            .integer(EventColumn.EVENT_SEQUENCE)
            .integer(EventColumn.IS_SHOW_EVENT_SEQUENCE)
            .integer(EventColumn.CREATE_TIME).defaultCurrTimestamp()
            .integer(EventColumn.EVENT_EXPECTED_FINISHED_DATE)
            .integer(EventColumn.EVENT_FINISHED_TIME)
            .integer(EventColumn.IS_EVENT_FINISHED)._default(0)
            .integer(EventColumn.GREEK_ALPHABET_ID)
            .integer(EventColumn.IS_GREEK_ALPHABET_MARKED)
            .integer(EventColumn.IS_REMIND)
            .integer(EventColumn.REMIND_TIME)
            .integer(EventColumn.EVENT_PROCESS)._default(1)
            .sql();
    public static final String CREATE_TABLE_GREEK_ALPHABET = SqlBuilder.build().create(Table.GREEK_ALPHABET)
            .pk(GreekAlphabetColumn.PK_GREEK_ALPHABET_ID)
            .text(GreekAlphabetColumn.GREEK_ALPHABET)
            .integer(GreekAlphabetColumn.USAGE)._default(0)
            .sql();
    public static final String CREATE_TABLE_LEARNING_EVENT_GROUP = SqlBuilder.build().create(Table.LEARNING_EVENT_GROUP)
            .pk(LearningEventGroupColumn.PK_LEARNING_EVENT_GROUP_ID)
            .integer(LearningEventGroupColumn.KNOWLEDGE_QUANTITY)._default(0)
            .integer(LearningEventGroupColumn.STRATEGY)
            .integer(LearningEventGroupColumn.LEARNING_EVENT_TOTAL)
            .integer(LearningEventGroupColumn.LEARNING_EVENT_FINISHED_COUNT)._default(0)
            .integer(LearningEventGroupColumn.LEARNING_DURATION)
            .real(LearningEventGroupColumn.EFFICIENCY)
            .real(LearningEventGroupColumn.UNDERSTANDING_DEGREE)
            .sql();
    public static final String CREATE_TABLE_EVENT_GROUP = SqlBuilder.build().create(Table.EVENT_GROUP)
            .pk(EventGroupColumn.PK_EVENT_GROUP_ID)
            .integer(EventGroupColumn.CREATE_TIME).defaultCurrTimestamp()
            .text(EventGroupColumn.DESCRIPTION)
            .integer(EventGroupColumn.LEARNING_EVENT_COUNT)._default(0)
            .integer(EventGroupColumn.NORMAL_EVENT_COUNT)._default(0)
            .integer(EventGroupColumn.ABSTRACT_EVENT_COUNT)._default(0)
            .sql();
    public static final String CREATE_TABLE_EVENT_SUBTYPE = SqlBuilder.build().create(Table.EVENT_SUBTYPE)
            .pk(EventSubtypeColumn.PK_EVENT_SUBTYPE_ID)
            .text(EventSubtypeColumn.EVENT_SUBTYPE)
            .sql();
    public static final String CREATE_TABLE_FAST_TEMPLATE = SqlBuilder.build().create(Table.FAST_TEMPLATE)
            .pk(FastTemplateColumn.PK_FAST_TEMPLATE_ID)
            .text(FastTemplateColumn.TEMPLATE)
            .integer(FastTemplateColumn.EVENT_TYPE)
            .sql();
    public static final String CREATE_TABLE_DEFAULT_INPUT_VALUE = SqlBuilder.build().create(Table.DEFAULT_INPUT_VALUE)
            .pk(DefaultInputValueColumn.PK_DEFAULT_INPUT_VALUE_ID)._default(1)
            .integer(DefaultInputValueColumn.MAX_WIDTH)._default(5)
            .integer(DefaultInputValueColumn.IS_GREEK_ALPHABET_MARKED)._default(0)
            .integer(DefaultInputValueColumn.IS_REMIND)._default(0)
            .integer(DefaultInputValueColumn.REMIND_TIME)._default(DateUtils.getHourMinuteMilliseconds(19,0))
            .integer(DefaultInputValueColumn.STRATEGY)._default(1)
            .integer(DefaultInputValueColumn.IS_SHOW_EVENT_SEQUENCE)._default(0)
            .sql();
    public static final String CREATE_TABLE_ACHIEVEMENT = SqlBuilder.build().create(Table.ACHIEVEMENT)
            .pk(AchievementColumn.PK_ACHIEVEMENT_ID)
            .text(AchievementColumn.NAME)
            .text(AchievementColumn.DESCRIPTION)
            .text(AchievementColumn.IMAGE_URL)
            .integer(AchievementColumn.RARITY)
            .integer(AchievementColumn.IS_ACHIEVED)._default(0)
            .sql();

    public interface Table{
        String EVENT = "event";
        String GREEK_ALPHABET = "greek_alphabet";
        String LEARNING_EVENT_GROUP = "learning_event_group";
        String EVENT_GROUP = "event_group";
        String EVENT_SUBTYPE = "event_subtype";
        String FAST_TEMPLATE = "fast_template";
        String DEFAULT_INPUT_VALUE = "default_input_value";
        String ACHIEVEMENT = "achievement";
    }
    public interface EventColumn{
        String PK_EVENT_ID = "PK_EVENT_ID";
        String LEARNING_EVENT_GROUP_ID = "LEARNING_EVENT_GROUP_ID";
        String EVENT_GROUP_ID = "EVENT_GROUP_ID";
        String DESCRIPTION = "DESCRIPTION";
        String SUMMARY = "SUMMARY";
        String EVENT_TYPE = "EVENT_TYPE";   //1:学习型 2:普通型 3:模糊型
        String EVENT_SUBTYPE_ID = "EVENT_SUBTYPE_ID";
        String EVENT_SEQUENCE = "EVENT_SEQUENCE";
        String IS_SHOW_EVENT_SEQUENCE = "IS_SHOW_EVENT_SEQUENCE";   //1:true 2:false
        String CREATE_TIME = "CREATE_TIME"; //精确到秒
        String EVENT_EXPECTED_FINISHED_DATE = "EVENT_EXPECTED_FINISHED_DATE";   //精确到日
        String EVENT_FINISHED_TIME = "EVENT_FINISHED_TIME";
        String IS_EVENT_FINISHED = "IS_EVENT_FINISHED";
        String GREEK_ALPHABET_ID = "GREEK_ALPHABET_ID";
        String IS_GREEK_ALPHABET_MARKED = "IS_GREEK_ALPHABET_MARKED";
        String IS_REMIND = "IS_REMIND";
        String REMIND_TIME = "REMIND_TIME";
        String EVENT_PROCESS = "EVENT_PROCESS"; //1:未完成 2:完成 3:过期
    }
    public interface GreekAlphabetColumn{
        String PK_GREEK_ALPHABET_ID = "PK_GREEK_ALPHABET_ID";
        String GREEK_ALPHABET = "GREEK_ALPHABET";
        String USAGE = "USAGE";
    }
    public interface EventSubtypeColumn{
        String PK_EVENT_SUBTYPE_ID = "PK_EVENT_SUBTYPE_ID";
        String EVENT_SUBTYPE = "EVENT_SUBTYPE";
    }
    public interface LearningEventGroupColumn{
        String PK_LEARNING_EVENT_GROUP_ID = "PK_LEARNING_EVENT_GROUP_ID";
        String KNOWLEDGE_QUANTITY = "KNOWLEDGE_QUANTITY";
        String STRATEGY = "STRATEGY";   //1:理解型 2:记忆型 3:强记型 4:永久型
        String LEARNING_EVENT_TOTAL = "LEARNING_EVENT_TOTAL";   //这个学习计划组的总数量
        String LEARNING_EVENT_FINISHED_COUNT = "LEARNING_EVENT_FINISHED_COUNT";
        String LEARNING_DURATION = "LEARNING_DURATION";   //学习时长,单位:分 最短10分钟
        String EFFICIENCY = "EFFICIENCY";   //效率 0.3:差 0.6:一般 0.9:高效 1:非常高效
        String UNDERSTANDING_DEGREE = "UNDERSTANDING_DEGREE";   //理解情况 0.3:不太理解 0.7:大致理解 1:完全理解
    }
    public interface EventGroupColumn{
        String PK_EVENT_GROUP_ID = "PK_EVENT_GROUP_ID";
        String CREATE_TIME = "CREATE_TIME";
        String DESCRIPTION = "DESCRIPTION";
        String LEARNING_EVENT_COUNT = "LEARNING_EVENT_COUNT";
        String NORMAL_EVENT_COUNT = "NORMAL_EVENT_COUNT";
        String ABSTRACT_EVENT_COUNT = "ABSTRACT_EVENT_COUNT";
    }
    public interface FastTemplateColumn{
        String PK_FAST_TEMPLATE_ID = "PK_FAST_TEMPLATE_ID";
        String TEMPLATE = "TEMPLATE";
        String EVENT_TYPE = "EVENT_TYPE"; //1:学习型 2:普通型 3:模糊型
    }
    public interface DefaultInputValueColumn{
        String PK_DEFAULT_INPUT_VALUE_ID = "PK_DEFAULT_INPUT_VALUE_ID";
        String MAX_WIDTH = "MAX_WIDTH";
        String IS_GREEK_ALPHABET_MARKED = "IS_GREEK_ALPHABET_MARKED";
        String IS_REMIND = "IS_REMIND";
        String REMIND_TIME = "REMIND_TIME";
        String STRATEGY = "STRATEGY";   //1:理解型 2:记忆型 3:强记型 4:永久型
        String IS_SHOW_EVENT_SEQUENCE = "IS_SHOW_EVENT_SEQUENCE";
    }
    public interface AchievementColumn{
        String PK_ACHIEVEMENT_ID = "PK_ACHIEVEMENT_ID";
        String NAME = "NAME";
        String DESCRIPTION = "DESCRIPTION";
        String IMAGE_URL = "IMAGE_URL";
        String RARITY = "RARITY";
        String IS_ACHIEVED = "IS_ACHIEVED";
    }

}
