package com.devthink.devthink_server.application;

import com.devthink.devthink_server.domain.Reply;
import com.devthink.devthink_server.dto.ReplyResponseData;
import com.devthink.devthink_server.errors.ReplyNotFoundException;
import com.devthink.devthink_server.infra.ReplyRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ReplyService {
    private final ReplyRepository replyRepository;

    public ReplyService(ReplyRepository replyRepository) {
        this.replyRepository = replyRepository;
    }

    /**
     * 모든 Reply를 조회합니다.
     * @return 조회된 모든 Reply
     */
    public List<ReplyResponseData> getReplies() {
        return getReplyResponseDataList(replyRepository.findAll());
    }

    /**
     * 특정 Reply를 조회합니다.
     * @param replyId 조회할 대댓글의 식별자
     * @return 조회된 Reply
     */
    public Reply getReply(Long replyId) {
        return replyRepository.findById(replyId)
                .orElseThrow(() -> new ReplyNotFoundException(replyId));
    }

    /**
     * 특정 사용자가 등록한 Reply를 모두 조회합니다.
     * @param userIdx 대댓글을 조회할 사용자의 식별자
     * @return 특정 사용자가 작성한 Reply 리스트
     */
    public List<ReplyResponseData> getUserReplies(Long userIdx) {
        List<Reply> userReplies = replyRepository.findByUserId(userIdx);
        if (userReplies.isEmpty())
            throw new ReplyNotFoundException();
        return getReplyResponseDataList(userReplies);
    }

    /**
     * entity List를 받아 dto List 데이터로 변환하여 반환합니다.
     * @param replies entity List
     * @return 입력된 dto 데이터로 변환된 list
     */
    private List<ReplyResponseData> getReplyResponseDataList(List<Reply> replies) {
        List<ReplyResponseData> replyResponseData = new ArrayList<>();

        for (Reply reply : replies)
            replyResponseData.add(reply.toReplyResponseData());
        return replyResponseData;
    }

}
