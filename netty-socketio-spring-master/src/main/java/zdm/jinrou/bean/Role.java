package zdm.jinrou.bean;

/**
 * @author zengdongming
 * @create 2018-03-19 上午 11:14
 **/
public enum Role {
  WOLF("狼",0.3),MAN("村民",0.3),
  GOD_S("预言家",0.1),GOD_M("猴子",0.1),GOD_C("乌鸦",0.1),GOD_G("守卫",0.1),
  BETRAY("狂人",0.1);

  private String name;
  private double chance;

  Role(String name,double chance) {
    this.name = name;
    this.chance = chance;
  }

  public String getName() {
    return name;
  }

  public double getChance() {
    return chance;
  }

  public static Role getRandRole(){
    double chance = 0;
    for (Role role : Role.values()) {
      if(chance==1) return role;
      chance = role.chance / (1 - chance);
      if(Math.random()<chance) return role;
    }
    return Role.BETRAY;
  }
}
