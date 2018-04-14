package zdm.jinrou.util;

import org.springframework.util.LinkedMultiValueMap;

import javax.swing.*;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public class IMap extends LinkedHashMap<String ,Object> implements Serializable {
    public IMap(boolean suc, String msg, Object data){
        this.set("suc",suc).set("msg",msg).set("data",data);
    }

    private IMap set(String k,Object v){
        this.put(k,v);
        return this;
    }

    public boolean isSuc(){
        return (boolean) this.get("suc");
    }
    public String getMsg(){
        return (String) this.get("msg");
    }
    public Object getData(){
        return this.get("data");
    }

    public static IMap infoSuccess(String msg){
        return new IMap(true,msg,null);
    }
    public static IMap infoFail(String msg){
        return new IMap(false,msg,null);
    }

    public static IMap returnData(Object data){
        return new IMap(true,"",data);
    }
}
