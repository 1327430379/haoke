package cn.haoke.im.service;

import cn.haoke.center.user.api.ApiUserService;
import cn.haoke.center.user.pojo.UserEo;
import cn.haoke.common.exception.BusinessException;
import cn.haoke.common.vo.RestResponse;
import cn.haoke.im.dao.MessageDAO;
import cn.haoke.im.pojo.Message;
import cn.haoke.im.pojo.SessionEo;
import org.apache.dubbo.config.annotation.Reference;
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

    @Reference(version = "1.0.0")
    private ApiUserService apiUserService;
    @Autowired
    private MessageDAO messageDAO;

    /***
     * 添加会话
     * @param sessionEo
     * @return
     */
    public RestResponse addSession(SessionEo sessionEo) {

        if (sessionEo.getUserId() == null || sessionEo.getTargetId() == null) {
            throw new BusinessException("用户id或目标用户id不能为空！");
        }
        if(sessionEo.getUserId().equals(sessionEo.getTargetId())){
            throw new BusinessException("您就是房东哦！");
        }
        SessionEo data = this.getSession(sessionEo.getUserId(), sessionEo.getTargetId()).getData();
        if (data != null) {
            return new RestResponse("会话已存在！");
        }
        //查出房东信息
        UserEo userEo = apiUserService.queryById(sessionEo.getTargetId()).getData();
        if(userEo==null){
            throw new BusinessException("目标用户不存在！");
        }
        sessionEo.setTargetName(!StringUtils.isEmpty(userEo.getName())?userEo.getName():userEo.getUsername());
        sessionEo.setAvatar(userEo.getAvatar());
        sessionEo.setMobile(userEo.getMobile());
        Date date = new Date();
        sessionEo.setBeginTime(date);
        sessionEo.setIsClose(0);
        //查出用户信息
        UserEo data1 = apiUserService.queryById(sessionEo.getUserId()).getData();
        if(data1==null){
            throw new BusinessException("用户不存在！");
        }
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
        List<SessionEo> allSessions = new ArrayList<>();
        if(!CollectionUtils.isEmpty(sessionEos)){
            allSessions.addAll(sessionEos);
        }
        //如果对方建立的会话给自己发了消息的也需展示
//        Criteria criteria2= Criteria.where("targetId").is(userId);
//        Query query2 = Query.query(criteria2);
//        List<SessionEo> sessionEos2 = mongoTemplate.find(query2, SessionEo.class);
//        if(!CollectionUtils.isEmpty(sessionEos2)){
//            for (SessionEo sessionEo : sessionEos2) {
//
//                List<Message> list = messageDAO.findListByFromAndTo(sessionEo.getUserId(), userId, 1, 10);
//                if(!CollectionUtils.isEmpty(list)){
//                    String userName = sessionEo.getUserName();
//                    sessionEo.setTargetName(userName);
//                    allSessions.add(sessionEo);
//                }
//            }
//        }

        return new RestResponse<>(allSessions);
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
