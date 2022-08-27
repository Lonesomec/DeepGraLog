package pub.developers.forum.common.support;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

/**
 * @author Qiangqiang.Bian
 * @create 2020/10/22
 * @desc
 **/
@Slf4j
@Component
public class EventBus {

    /**
     * 消息类型枚举
     */
    public enum Topic {
        /**
         *User 的操作 ADMIN 对应也有
         */
        USER_LOGIN,     //登录成功
        USER_REGISTER,  //用户注册
        USER_LOGOUT,    //用户登出
        USER_FOLLOW,    //用户关注
        USER_CANCEL_FOLLOW,     //用户取消关注
        USER_INFO,      //用户详情
        USER_UPDATE,    //用户信息更新
        USER_CHECK_FOLLOWING, //查看用户是否有关注的博主 新增
        USER_FOLLOWER_INFO,   //查看用户粉丝信息
        USER_FOLLOWING_INFO,    //查看关注的博主 新增
        USER_PWD_UPDATE,    //用户密码更新
        USER_FOLLOWED_NOTIFICATION, //用户被关注消息通知
        USER_CHECK_ARTICLE_CLASS,   // 查看文章分类
        USER_CHECK_FOLLOWED_USER_FAQ_LIST,   //用户查看关注的用户的问答列表
        USER_CHECK_FOLLOWED_USER_ARTICLE_LIST,   //用户查看关注的用户的文章列表
        USER_CHECK_FOLLOWED_FAQ_LIST,   //用户查看关注的问答列表
        USER_CHECK_FOLLOWED_ARTICLE_LIST,   //用户查看关注的文章列表
        USER_CHECK_LIKE_ARTICLE_LIST,   //用户查看点赞的文章列表
        USER_CHECK_LIKE_FAQ_LIST,   //用户查看点赞的问答列表
        USER_CHECK_COMMENT_ARTICLE_LIST,   //用户查看评论的文章列表
        USER_CHECK_COMMENT_FAQ_LIST,   //用户查看评论的问答列表
        SEARCH,     //用户搜索
        USER_CHECK_TAG_INFO,  //用户查看标签详情
        USER_FILTER_TAG,  //用户筛选标签
        USER_UPDATE_HEAD_IMG, //用户更新头像



        ARTICLE_CREATE,     //文章创建
        ARTICLE_UPDATE,     //文章更新

        POSTS_INFO,     //posts信息
        POSTS_DELETE,   //posts删除
        ARTICLE_DELETE,     //文章删除
        ARTICLE_COMMENT,    //评论文章
        ARTICLE_LIKE,       //文章点赞
        ARTICLE_UNDO_LIKE,       //文章点赞取消
        CHECK_ARTICLE_HAVE_LIKE, //查看文章是否有点赞
        ARTICLE_INFO,       //文章信息
        ARTICLE_FOLLOW_NOTIFICATION,    //文章被关注提醒
        ARTICLE_COMMENT_NOTIFICATION,   //文章被评论提醒


        FAQ_CREATE,     //问答创建
        FAQ_UPDATE,     //问答更新
        FAQ_DELETE,     //问答删除
        FAQ_COMMENT,    //问答评论
        FAQ_LIKE,       //问答点赞
        FAQ_UNDO_LIKE,       //问答点赞取消
        CHECK_FAQ_HAVE_LIKE, //查看问答是否有点赞
        FAQ_FOLLOW,     //问答关注
        FAQ_BEST_ANSWER,    //问答最佳回答
        FAQ_INFO,       //问答详情
        FAQ_FILTER_SUCCESS,     //筛选问答成功
        FAQ_FILTER_FAILURE,     //筛选问答
        FAQ_FOLLOW_NOTIFICATION,    //问答被关注提醒
        FAQ_COMMENT_NOTIFICATION,   //问答被评论提醒

        STATION_LETTER_READ,   //消息已读
        STATION_LETTER_UNREAD,   //消息未读
        STATION_LETTER_NOTIFICATION,   //站内信通知

        APPROVAL_CREATE,        //授权创建(作者好像指点赞、关注帖子)

        COMMENT_CREATE,     //评论创建

        //管理端功能事件（ADMIN独有）
        ADMIN_SET_ARTICLE_OFFICIAL,     //文章被设置为官方
        ADMIN_CANCEL_ARTICLE_OFFICIAL,  //文章被取消官方
        ADMIN_SET_FAQ_OFFICIAL,     //问答被设置为官方
        ADMIN_CANCEL_FAQ_OFFICIAL,  //问答被取消官方
        ADMIN_SET_ARTICLE_TOP,      //文章被设置为置顶
        ADMIN_CANCEL_ARTICLE_TOP,   //文章被取消置顶
        ADMIN_SET_FAQ_TOP,      //问答被设置为置顶
        ADMIN_CANCEL_FAQ_TOP,   //问答被取消置顶
        ADMIN_SET_ARTICLE_HOT,  //文章被设置为精华
        ADMIN_CANCEL_ARTICLE_HOT,   //文章被取消精华
        ADMIN_SET_FAQ_HOT,  //      问答被设置为精华
        ADMIN_CANCEL_FAQ_HOT,   //问答被取消精华
        ADMIN_SET_ARTICLE_APPROVED,     //设置文章通过
        ADMIN_SET_ARTICLE_UNAPPROVED,   //设置文章不通过
        ADMIN_SET_FAQ_APPROVED,     //设置问答通过
        ADMIN_SET_FAQ_UNAPPROVED,   //设置问答不通过
        ADMIN_SET_TAG_APPROVED,     //设置标签通过
        ADMIN_SET_TAG_UNAPPROVED,   //设置标签不通过


        ADMIN_DELETE_ARTICLE_COMMENT,    //管理员删除文章评论
        ADMIN_CHECK_ARTICLE_COMMENT,    //管理员查看文章评论
        ADMIN_DELETE_FQA_COMMENT,    //管理员删除问答评论
        ADMIN_CHECK_FQA_COMMENT,    //管理员查看问答评论
        ADMIN_SET_USER_AS_USER,     //管理员设置用户为用户
        ADMIN_SET_USER_AS_ADMIN,    //管理员设置用户为管理员
        ADMIN_UNBLOCK_USER,     //管理员把用户拉出小黑屋
        ADMIN_BLOCK_USER,       //管理员把用户拉进小黑屋
        ADMIN_RELEASE_STATION_LETTER,   //管理员发送站内信
        ADMIN_ADD_ARTICLE_TYPE,     //管理员添加文章类别到网站
        ADMIN_ADD_TAG_TYPE,     //管理员添加标签类别到网站
        ADMIN_ARTICLE_TYPE_AUDIT,   //添加文章类型时，进行审核
        ADMIN_CHECK_LOGIN_INFO,     //管理员查看用户登陆记录

        //具体实现类里面的一些事件
        BROWSE_FAQ_ADMIN_PAGE, //查看管理员页面
        BROWSE_FAQ_USER_PAGE,
        BROWSE_FAQ_AUTHOR_PAGE,
        BROWSE_ARTICLE_ADMIN_PAGE, //
        BROWSE_ARTICLE_USER_PAGE,
        BROWSE_ARTICLE_AUTHOR_PAGE,
        BROWSE_USER_ADMIN_PAGE,
        BROWSE_COMMENT_PAGE,
        PAGE_ACTIVE,
        QUERY_ALL_TAG,  //查询所有标签的帖子
        QUERY_BY_TAG_NAME,  //通过名字查询标签
        QUERY_ALL_REF,  //
        QUERY_BY_TAG_IDS,   //通过id集合来查询标签
        PAGE_POSTS, //查看帖子页面
        PAGE_POSTS_BY_TAG_IDS,  //通过标签id来查看帖子
        ADMIN_TAG_PAGE,   //
        UPDATE_IMG,     //上传图片
        QUERY_FAQ_BY_HOTS,     //按照热度查问答
        ADMIN_ADD_CONFIG,
        ADMIN_UPDATE_CONFIG,
        ADMIN_APPROVE_CONFIG,
        ADMIN_CONFIG_PAGE,
        ADMIN_QUERY_CONFIG_AVAILABLE,
        QUERY_ALL_TYPE_ARTICLE,
        QUERY_ADMIN_TYPE_ARTICLE,
        QUERY_ARTICLE_BY_SCOPE,  //根据用户类型查看一定权限的文章







        ;
    }

    /**
     * 事件/事件处理 映射
     */
    private static final Map<Topic, List<EventBus.EventHandler>> EVENT_HANDLER_MAP = new ConcurrentHashMap<>();

    /**
     * 线程池
     */
    private final static ExecutorService executorService = ExecutorFactory.getExecutorService(EventBus.class, 4);

    @PreDestroy
    public void post() {
        executorService.shutdown();
    }

    /**
     * 触发事件，默认异步执行
     * @param eventEn
     * @param message
     */
//    public static void emit(final Topic eventEn, final Object message) {
//        processEmitEvent(eventEn, message, true);
//    }

    /**
     * 同步触发事件
     * @param eventEn
     * @param message
     */
//    public static void emitSync(final Topic eventEn, final Object message) {
//        processEmitEvent(eventEn, message, false);
//    }

    /**
     * 执行处理的事件
     * @param eventEn
     * @param message
     */
    private static void processEmitEvent(Topic eventEn, Object message, Boolean async) {
        log.info("Bus on event=[{}] message=[{}]", eventEn, message);
        List<EventBus.EventHandler> eventHandlers = EVENT_HANDLER_MAP.get(eventEn);
        if (null == eventHandlers) {
            log.warn("emit [{}] event, but not handler.", eventEn);
            return;
        }

        eventHandlers.forEach(eventHandler -> {
            if (async) {
                executorService.submit(() -> {
                    try {
                        eventHandler.onMessage(message);
                    } catch (Exception e) {
                        log.error("handler [{}] async process event [{}] error.", eventHandler.getClass(), eventEn, e);
                    }
                });
            } else {
                try {
                    eventHandler.onMessage(message);
                } catch (Exception e) {
                    log.error("handler [{}] sync process event [{}] error.", eventHandler.getClass(), eventEn, e);
                }
            }
        });
    }

    /**
     * 监听事件
     * @param eventEn
     * @param eventHandler
     */
    private static synchronized void on(Topic eventEn, EventBus.EventHandler eventHandler) {
        List<EventBus.EventHandler> eventHandlers = EVENT_HANDLER_MAP.get(eventEn);

        if (eventHandlers == null) {
            eventHandlers = new ArrayList<>();
        }

        eventHandlers.add(eventHandler);
        EVENT_HANDLER_MAP.put(eventEn, eventHandlers);
    }

    public static abstract class EventHandler<T> implements InitializingBean {

        public abstract Topic topic();

        public abstract void onMessage(T message);

        @Override
        public void afterPropertiesSet() throws Exception {
            EventBus.on(topic(), this);
        }
    }
}
