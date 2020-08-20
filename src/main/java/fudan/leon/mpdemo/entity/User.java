package fudan.leon.mpdemo.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @Author: liyang27
 * @Date: 2020/6/20 10:07
 * @Description:
 */
@Data
@TableName("mp_user")
@EqualsAndHashCode(callSuper = false)
public class User extends Model {

    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.NONE)
    private Long userId;
    @TableField("name")
    private String realName;

    //通过entity查询时设为<
    @TableField(condition = "%s&lt;#{%s}")
    private Integer age;
    private String email;
    private Long managerId;
    private LocalDateTime createTime;
    @TableField(exist = false)
    private String remark;
}
