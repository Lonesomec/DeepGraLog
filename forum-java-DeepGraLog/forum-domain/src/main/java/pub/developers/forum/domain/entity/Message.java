package pub.developers.forum.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pub.developers.forum.common.enums.MessageChannelEn;
import pub.developers.forum.common.enums.MessageContentTypeEn;
import pub.developers.forum.common.enums.MessageReadEn;
import pub.developers.forum.common.enums.MessageTypeEn;
import pub.developers.forum.domain.entity.value.IdValue;

/**
 * @author Qiangqiang.Bian
 * @create 2020/7/30
 * @desc Message
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Message extends BaseEntity {

    /**
     * Message channel
     */
    private MessageChannelEn channel;

    /**
     *
     */
    private MessageTypeEn type;

    /**
     * read/unread
     */
    private MessageReadEn read;

    /**
     *
     */
    private IdValue sender;

    /**
     *
     */
    private IdValue receiver;

    /**
     *
     */
    private String title;

    /**
     *
     */
    private MessageContentTypeEn contentType;

    /**
     *
     */
    private String content;

}
