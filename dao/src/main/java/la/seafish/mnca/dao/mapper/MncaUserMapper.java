package la.seafish.mnca.dao.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import la.seafish.mnca.dao.entity.MncaUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

@Service
@Mapper
public interface MncaUserMapper extends BaseMapper<MncaUser> {
}
