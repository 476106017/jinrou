package zdm.jinrou.service;

import zdm.jinrou.bean.PInfo;
import zdm.jinrou.util.IMap;

import java.util.Map;

public interface PlayerInfoService {

    PInfo getPInfoByToken(String token);

    IMap login(String uname, String pwd);
}
