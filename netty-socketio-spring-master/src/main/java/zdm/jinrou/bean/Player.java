package zdm.jinrou.bean;

import java.util.UUID;

/**
 * @author zengdongming
 * @create 2018-03-19 上午 11:13
 **/
public class Player {

  private UUID uuid;
  private Role role;
  private String name;
  private Long pInfoId;
  private boolean banned;

  public Player(UUID uuid,Long pInfoId) {
    this.uuid = uuid;
    this.pInfoId = pInfoId;
    this.banned = false;
    this.role = Role.getRandRole();
  }

  public UUID getUuid() {
    return uuid;
  }

  public void setUuid(UUID uuid) {
    this.uuid = uuid;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isBanned() {
    return banned;
  }

  public void setBanned(boolean banned) {
    this.banned = banned;
  }

  public Long getpInfoId() {
    return pInfoId;
  }

  public void setpInfoId(Long pInfoId) {
    this.pInfoId = pInfoId;
  }
}
