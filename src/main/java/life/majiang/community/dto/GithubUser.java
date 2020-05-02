package life.majiang.community.dto;

import lombok.Data;

/**
 * @author 谭俊彦
 * @version 1.0 2020-04-09 17:18
 * @description
 */
@Data
public class GithubUser {
    private String name;
    private Long id;
    private String bio;
    private String email;
    private String avatar_url;
}
