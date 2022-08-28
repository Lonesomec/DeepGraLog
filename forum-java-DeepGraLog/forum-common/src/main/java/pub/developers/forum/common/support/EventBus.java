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
     * Message type enumeration
     */
    public enum Topic {
        /**
         *
         */
        USER_LOGIN,
        USER_REGISTER,
        USER_LOGOUT,
        USER_FOLLOW,
        USER_CANCEL_FOLLOW,
        USER_INFO,
        USER_UPDATE,
        USER_CHECK_FOLLOWING,
        USER_FOLLOWER_INFO,
        USER_FOLLOWING_INFO,
        USER_PWD_UPDATE,
        USER_FOLLOWED_NOTIFICATION,
        USER_CHECK_ARTICLE_CLASS,
        USER_CHECK_FOLLOWED_USER_FAQ_LIST,
        USER_CHECK_FOLLOWED_USER_ARTICLE_LIST,
        USER_CHECK_FOLLOWED_FAQ_LIST,
        USER_CHECK_FOLLOWED_ARTICLE_LIST,
        USER_CHECK_LIKE_ARTICLE_LIST,
        USER_CHECK_LIKE_FAQ_LIST,
        USER_CHECK_COMMENT_ARTICLE_LIST,
        USER_CHECK_COMMENT_FAQ_LIST,
        SEARCH,
        USER_CHECK_TAG_INFO,
        USER_FILTER_TAG,
        USER_UPDATE_HEAD_IMG,



        ARTICLE_CREATE,
        ARTICLE_UPDATE,

        POSTS_INFO,
        POSTS_DELETE,
        ARTICLE_DELETE,
        ARTICLE_COMMENT,
        ARTICLE_LIKE,
        ARTICLE_UNDO_LIKE,
        CHECK_ARTICLE_HAVE_LIKE,
        ARTICLE_INFO,
        ARTICLE_FOLLOW_NOTIFICATION,
        ARTICLE_COMMENT_NOTIFICATION,


        FAQ_CREATE,
        FAQ_UPDATE,
        FAQ_DELETE,
        FAQ_COMMENT,
        FAQ_LIKE,
        FAQ_UNDO_LIKE,
        CHECK_FAQ_HAVE_LIKE,
        FAQ_FOLLOW,
        FAQ_BEST_ANSWER,
        FAQ_INFO,
        FAQ_FILTER_SUCCESS,
        FAQ_FILTER_FAILURE,
        FAQ_FOLLOW_NOTIFICATION,
        FAQ_COMMENT_NOTIFICATION,

        STATION_LETTER_READ,
        STATION_LETTER_UNREAD,
        STATION_LETTER_NOTIFICATION,

        APPROVAL_CREATE,

        COMMENT_CREATE,

        //ADMIN function events (ADMIN only)
        ADMIN_SET_ARTICLE_OFFICIAL,
        ADMIN_CANCEL_ARTICLE_OFFICIAL,
        ADMIN_SET_FAQ_OFFICIAL,
        ADMIN_CANCEL_FAQ_OFFICIAL,
        ADMIN_SET_ARTICLE_TOP,
        ADMIN_CANCEL_ARTICLE_TOP,
        ADMIN_SET_FAQ_TOP,
        ADMIN_CANCEL_FAQ_TOP,
        ADMIN_SET_ARTICLE_HOT,
        ADMIN_CANCEL_ARTICLE_HOT,
        ADMIN_SET_FAQ_HOT,  //
        ADMIN_CANCEL_FAQ_HOT,
        ADMIN_SET_ARTICLE_APPROVED,
        ADMIN_SET_ARTICLE_UNAPPROVED,
        ADMIN_SET_FAQ_APPROVED,
        ADMIN_SET_FAQ_UNAPPROVED,
        ADMIN_SET_TAG_APPROVED,
        ADMIN_SET_TAG_UNAPPROVED,


        ADMIN_DELETE_ARTICLE_COMMENT,
        ADMIN_CHECK_ARTICLE_COMMENT,
        ADMIN_DELETE_FQA_COMMENT,
        ADMIN_CHECK_FQA_COMMENT,
        ADMIN_SET_USER_AS_USER,
        ADMIN_SET_USER_AS_ADMIN,
        ADMIN_UNBLOCK_USER,
        ADMIN_BLOCK_USER,
        ADMIN_RELEASE_STATION_LETTER,
        ADMIN_ADD_ARTICLE_TYPE,
        ADMIN_ADD_TAG_TYPE,
        ADMIN_ARTICLE_TYPE_AUDIT,
        ADMIN_CHECK_LOGIN_INFO,

        //Concrete implementation of some events inside the class
        BROWSE_FAQ_ADMIN_PAGE,
        BROWSE_FAQ_USER_PAGE,
        BROWSE_FAQ_AUTHOR_PAGE,
        BROWSE_ARTICLE_ADMIN_PAGE, //
        BROWSE_ARTICLE_USER_PAGE,
        BROWSE_ARTICLE_AUTHOR_PAGE,
        BROWSE_USER_ADMIN_PAGE,
        BROWSE_COMMENT_PAGE,
        PAGE_ACTIVE,
        QUERY_ALL_TAG,
        QUERY_BY_TAG_NAME,
        QUERY_ALL_REF,
        QUERY_BY_TAG_IDS,
        PAGE_POSTS,
        PAGE_POSTS_BY_TAG_IDS,
        ADMIN_TAG_PAGE,
        UPDATE_IMG,
        QUERY_FAQ_BY_HOTS,
        ADMIN_ADD_CONFIG,
        ADMIN_UPDATE_CONFIG,
        ADMIN_APPROVE_CONFIG,
        ADMIN_CONFIG_PAGE,
        ADMIN_QUERY_CONFIG_AVAILABLE,
        QUERY_ALL_TYPE_ARTICLE,
        QUERY_ADMIN_TYPE_ARTICLE,
        QUERY_ARTICLE_BY_SCOPE,







        ;
    }

    /**
     * Event/event processing mapping
     */
    private static final Map<Topic, List<EventBus.EventHandler>> EVENT_HANDLER_MAP = new ConcurrentHashMap<>();

    /**
     * thread pool
     */
    private final static ExecutorService executorService = ExecutorFactory.getExecutorService(EventBus.class, 4);

    @PreDestroy
    public void post() {
        executorService.shutdown();
    }

    /**
     * triggered event . By default, the event is executed asynchronously
     * @param eventEn
     * @param message
     */
//    public static void emit(final Topic eventEn, final Object message) {
//        processEmitEvent(eventEn, message, true);
//    }

    /**
     * Synchronous triggering event
     * @param eventEn
     * @param message
     */
//    public static void emitSync(final Topic eventEn, final Object message) {
//        processEmitEvent(eventEn, message, false);
//    }

    /**
     * Execute processed events
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
     * Listen for an event
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
