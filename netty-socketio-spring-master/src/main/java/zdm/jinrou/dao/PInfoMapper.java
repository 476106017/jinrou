package zdm.jinrou.dao;

import org.apache.ibatis.annotations.Mapper;
import zdm.jinrou.bean.PInfo;

import java.util.List;

@Mapper
public interface PInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PInfo record);

    int insertSelective(PInfo record);

    PInfo selectByPrimaryKey(Long id);
    List<PInfo> selectByQuery(PInfo record);

    int updateByPrimaryKeySelective(PInfo record);

    int updateByPrimaryKey(PInfo record);
}