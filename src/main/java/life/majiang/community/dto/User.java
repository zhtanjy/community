package life.majiang.community.dto;

import lombok.Data;

/**
 * @author 谭俊彦
 * @version 1.0 2020-04-09 9:11
 * @description
 */
@Data
public class User {
    private Integer id;

    private String account_id;

    private String name;

    private String email;

    private String token;

    private Long gmt_create;

    private Long gmt_modified;
}
