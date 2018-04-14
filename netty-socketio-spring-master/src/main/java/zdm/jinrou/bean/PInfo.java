package zdm.jinrou.bean;

import java.io.Serializable;
import java.util.Date;

public class PInfo implements Serializable {
    private Long id;

    private String pName;

    private String pLoginName;

    private String pLoginPwd;

    private Integer pScore;

    private Date createAt;

    public static PInfo loginInstance(String pLoginName) {
        PInfo  pInfo = new PInfo();
        pInfo.pLoginName = pLoginName;
        return pInfo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName == null ? null : pName.trim();
    }

    public String getpLoginName() {
        return pLoginName;
    }

    public void setpLoginName(String pLoginName) {
        this.pLoginName = pLoginName == null ? null : pLoginName.trim();
    }

    public String getpLoginPwd() {
        return pLoginPwd;
    }

    public void setpLoginPwd(String pLoginPwd) {
        this.pLoginPwd = pLoginPwd == null ? null : pLoginPwd.trim();
    }

    public Integer getpScore() {
        return pScore;
    }

    public void setpScore(Integer pScore) {
        this.pScore = pScore;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
}