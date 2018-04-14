package zdm.jinrou.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import zdm.jinrou.bean.PInfo;
import zdm.jinrou.dao.PInfoMapper;
import zdm.jinrou.service.PlayerInfoService;
import zdm.jinrou.util.IMap;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
class PlayerInfoServiceImpl implements PlayerInfoService {
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    PInfoMapper pInfoMapper;

    public PInfo getPInfoByToken(String token){
        String pinfoId = stringRedisTemplate.opsForValue().get("pinfo:"+token);
        PInfo pInfo = pInfoMapper.selectByPrimaryKey(Long.getLong(pinfoId));
        return pInfo;
    }

    public IMap login(String uname, String pwd){
        PInfo pInfo = PInfo.loginInstance(uname);
        List<PInfo> pInfos = pInfoMapper.selectByQuery(pInfo);
        if(CollectionUtils.isEmpty(pInfos))
            return IMap.infoFail("用户不存在!");
        if(pInfos.size()>1)
            return IMap.infoFail("用户名存在多个!");
        if(!pInfos.get(0).getpLoginPwd().equals(pwd))
            return IMap.infoFail("用户名或密码错误!");

        String token = UUID.randomUUID().toString().replace("-","");
        stringRedisTemplate.opsForValue().set("pinfo:"+token,pInfo.getId()+"",6*60*60);

        return IMap.returnData(token);
    }
}
