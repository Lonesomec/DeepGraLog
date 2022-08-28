package pub.developers.forum.domain.service;

import pub.developers.forum.common.enums.CacheBizTypeEn;

/**
 * @author Qiangqiang.Bian
 * @create 20/7/23
 * @desc
 **/
public interface CacheService {

    /**
     * store
     * @param key
     * @param value
     * @return
     */
    boolean set(CacheBizTypeEn bizType, String key, String value);

    /**
     * Store and set Timeout period (unit: second)
     * @param key
     * @param value
     * @param seconds
     * @return
     */
    boolean setAndExpire(CacheBizTypeEn bizType, String key, String value, Long seconds);

    /**
     * get key
     * @param key
     * @return
     */
    String get(CacheBizTypeEn bizType, String key);
    String got(CacheBizTypeEn bizType, String key);

    String get_error(CacheBizTypeEn bizType, String key);

    /**
     * check if it does exist
     * @param key
     * @return
     */
    Boolean exists(CacheBizTypeEn bizType, String key);

    /**
     * delete
     * @param key
     * @return
     */
    Boolean del(CacheBizTypeEn bizType, String key);

}
