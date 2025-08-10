package com.sky.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;


/**
 * @ApiModel 是 Swagger 框架中的一个注解,生成 API 文档时提供更详细的模型信息
 * 四个属性
 * value：模型的名称（替代类名在文档中显示）
 * description：对模型的详细描述
 * parent：指定父类以继承其属性
 * subTypes：指定子类以展示继承层次结构
 * @ApiModelProperty 是 Swagger 框架中的一个注解，用于对模型类（实体类或 DTO 类）中的字段进行描述和说明
 *
 * @Builder：构建器
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "员工登录返回的数据格式")
public class EmployeeLoginVO implements Serializable {

    @ApiModelProperty("主键值")
    private Long id;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("jwt令牌")
    private String token;

}
