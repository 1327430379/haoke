package cn.haoke.im.service;

import cn.haoke.common.exception.BusinessException;
import cn.haoke.common.vo.RestResponse;
import cn.haoke.im.pojo.SessionEo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SessionService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public RestResponse<Void> addSession(SessionEo sessionEo) {

        if (sessionEo.getUserId() == null || sessionEo.getTargetId() == null) {
            throw new BusinessException("用户id或目标用户id不能为空！");
        }
        SessionEo data = this.getSession(sessionEo.getUserId(), sessionEo.getTargetId()).getData();
        if (data != null) {
            throw new BusinessException("会话已存在，请勿重复添加！");
        }
        Date date = new Date();
        sessionEo.setBeginTime(date);
        sessionEo.setIsClose(0);
        SessionEo eo = mongoTemplate.insert(sessionEo);
        System.out.println(eo);
        return new RestResponse<>();
    }

    public RestResponse<SessionEo> getSession(Long userId, Long targetId) {
        Criteria criteria = Criteria.where("userId").is(userId).and("targetId").is(targetId);
        Query query = Query.query(criteria);
        List<SessionEo> sessionEos = mongoTemplate.find(query, SessionEo.class);
        SessionEo sessionEo = null;
        if (!CollectionUtils.isEmpty(sessionEos)) {
            sessionEo = sessionEos.get(0);
        }
        return new RestResponse<>(sessionEo);
    }

    public RestResponse<List<SessionEo>> getSessionList(Long userId){
        Criteria criteria = Criteria.where("userId").is(userId);
        Query query = Query.query(criteria);
        List<SessionEo> sessionEos = mongoTemplate.find(query, SessionEo.class);
        if(CollectionUtils.isEmpty(sessionEos)){
            sessionEos = new ArrayList<>();
        }
        return new RestResponse<>(sessionEos);
    }

    public RestResponse<Void> updateSession(SessionEo sessionEo){
        Criteria criteria = Criteria.where("userId").is(sessionEo.getUserId()).and("targetId").is(sessionEo.getTargetId());
        Query query = Query.query(criteria);
        Update update = new Update();
        if(!StringUtils.isEmpty(sessionEo.getAvatar())){
            update.set("avatar",sessionEo.getAvatar());
        }
        if(sessionEo.getIsClose()!=null){
            update.set("isClose",sessionEo.getIsClose());
        }
        if(org.apache.commons.lang3.StringUtils.isNotEmpty(sessionEo.getLastMessage())){
            update.set("lastMessage",sessionEo.getLastMessage());
        }
        if(sessionEo.getEndTime()!=null){
            update.set("endTime",sessionEo.getEndTime());
        }
        if(org.apache.commons.lang3.StringUtils.isNotEmpty(sessionEo.getMobile())){
            update.set("mobile",sessionEo.getMobile());
        }
        if(sessionEo.getLastMessageTime()!=null){
            update.set("lastMessageTIme",sessionEo.getLastMessageTime());
        }
        mongoTemplate.updateFirst(query,update,"sessions");
        return new RestResponse<>();
    }
}
