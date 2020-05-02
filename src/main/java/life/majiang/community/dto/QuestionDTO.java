package life.majiang.community.dto;

import lombok.Data;

/**
 * @author 谭俊彦
 * @version 1.0 2020-04-15 10:30
 * @description
 */
@Data
public class QuestionDTO {
    private int id;
    private String title;
    private String description;
    private Long gmtCreate;
    private Long gmtModified;
    private int creator;
    private int commentCount;
    private int viewCount;
    private int likeCount;
    private String tags;
    private User user;
}
