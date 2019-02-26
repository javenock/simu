package com.simiyu.zblog.service;

import com.simiyu.zblog.model.Comment;
import com.simiyu.zblog.model.Reply;
/**
 * 
 * @author simiyu
 *
 */
public interface ReplyService {

	public Long save(Reply replies,Comment comment,Long parentId);
}
