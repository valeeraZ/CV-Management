package com.sylvain.cvmanagement.system.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * Created by Wenzhuo Zhao on 20/10/2021.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(indexName = "cvmanagement")
public class CV {
    @Id
    String id;
    String idUser;
    String name;
    String data;
    Attachment attachment;
}