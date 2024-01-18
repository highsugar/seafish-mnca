package la.seafish.mnca.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;


@TableName("mnca_user")
@Data
public class MncaUser implements Serializable {
    @TableId(type=IdType.AUTO)
    private String mncaId;

    private String password;

}
