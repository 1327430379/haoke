package cn.haoke.center.house.apiimpl;

import cn.haoke.center.house.api.ApiNoticeMessageService;
import cn.haoke.center.house.constants.enums.MessageTitleEnum;
import cn.haoke.center.house.constants.enums.TargetUserType;
import cn.haoke.center.house.mapper.NoticeMessageMapper;
import cn.haoke.center.house.pojo.NoticeMessageEo;
import cn.haoke.common.vo.RestResponse;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service(version = "1.0.0")
public class ApiNoticeMessageServiceImpl implements ApiNoticeMessageService {
    @Autowired
    private NoticeMessageMapper noticeMessageMapper;

    @Override
    public RestResponse<Long> addNoticeMessage(NoticeMessageEo eo) {
        noticeMessageMapper.insert(eo);
        return new RestResponse<>(eo.getId());
    }

    @Override
    public RestResponse<Void> saveNoticeMessage(NoticeMessageEo eo) {
        if (eo.getId() != null) {
            noticeMessageMapper.updateById(eo);
        } else {
            noticeMessageMapper.insert(eo);
        }

        return RestResponse.VOID;
    }

    @Override
    public RestResponse<List<NoticeMessageEo>> queryByUserId(Long userId) {
        NoticeMessageEo eo = NoticeMessageEo.builder().userId(userId).build();
        Wrapper<NoticeMessageEo> wrapper = new QueryWrapper<>(eo);
        return new RestResponse<>(noticeMessageMapper.selectList(wrapper));
    }

    @Override
    public RestResponse<NoticeMessageEo> queryById(Long id) {
        NoticeMessageEo messageEo = noticeMessageMapper.selectById(id);
        return new RestResponse<>(messageEo);
    }

    @Override
    public RestResponse<Void> updateSeeHouseStatus(Long id, Integer seeHouseStatus) {
        NoticeMessageEo eo = new NoticeMessageEo();
        eo.setId(id);
        eo.setSeeHouseStatus(seeHouseStatus);
        noticeMessageMapper.updateById(eo);
        return RestResponse.VOID;
    }
}
