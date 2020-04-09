package life.majiang.community.dto;

import lombok.Data;

/**
 * @author 谭俊彦
 * @version 1.0 2020-04-09 16:42
 * @description
 */
@Data
public class AccessTokenDTO {

    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;
}
