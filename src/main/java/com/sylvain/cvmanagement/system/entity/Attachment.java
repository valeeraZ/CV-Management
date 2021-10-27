package com.sylvain.cvmanagement.system.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Wenzhuo Zhao on 25/10/2021.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Attachment {
    public String content_type;
    public String author;
    public String title;
    public String language;
    public String content;
}
