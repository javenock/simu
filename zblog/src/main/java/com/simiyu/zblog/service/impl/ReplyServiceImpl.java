package com.simiyu.zblog.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simiyu.zblog.model.Comment;
import com.simiyu.zblog.model.Reply;
import com.simiyu.zblog.repository.ReplyRepository;
import com.simiyu.zblog.service.ReplyService;
/**
 * 
 * @author simiyu
 *
 */
@Service
public class ReplyServiceImpl implements ReplyService{

	@Autowired
	private ReplyRepository replyRepository;
	@Override
	public Long save(Reply replies, Comment comment, Long parentId) {
		replies.setParentComment(comment);
		replies.setDateTime(LocalDateTime.now());
		replyRepository.saveAndFlush(replies);
		 return replies.getId(); 
	}

}
