package com.sylvain.cvmanagement.system.service;


import com.google.common.collect.ImmutableMap;
import com.sylvain.cvmanagement.security.utils.CurrentUserUtils;
import com.sylvain.cvmanagement.system.entity.CVShort;
import com.sylvain.cvmanagement.system.entity.User;
import com.sylvain.cvmanagement.system.exception.BadFormatException;
import com.sylvain.cvmanagement.system.exception.EmptyFileException;
import com.sylvain.cvmanagement.system.exception.EmptyKeywordException;
import com.sylvain.cvmanagement.system.repository.CVRepository;
import com.sylvain.cvmanagement.system.repository.CVShortRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Wenzhuo Zhao on 20/10/2021.
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class CVService {
    private final CVRepository cvRepository;
    private final CVShortRepository cvShortRepository;
    private final RestHighLevelClient highLevelClient;
    private final CurrentUserUtils currentUserUtils;
    private final ElasticsearchRestTemplate elasticsearchRestTemplate;

    public String save(MultipartFile file) throws IOException {
        User currentUser = currentUserUtils.getCurrentUser();

        String name;
        if (!file.isEmpty()) {
            name = file.getOriginalFilename();
            Tika tika = new Tika();
            String detectedType = tika.detect(file.getBytes());
            if (!(detectedType.equals("application/pdf") || detectedType.equals("application/x-tika-ooxml"))) {
                throw new BadFormatException(ImmutableMap.of("filename", name, "extension", detectedType));
            }
        } else {
            throw new EmptyFileException(ImmutableMap.of("filename", "empty"));
        }

        String contents = DatatypeConverter.printBase64Binary(file.getBytes());
        IndexRequest request = new IndexRequest("cvmanagement")
                .source("data", contents,
                        "idUser", currentUser.getId(),
                        "name", currentUser.getName())
                .setPipeline("attachment");

        IndexResponse response = highLevelClient.index(request, RequestOptions.DEFAULT);
        String index = response.getIndex();
        String id = response.getId();
        log.info("Save document " + name + " of user " + currentUser.getName() + " in " + index + " with id " + id);
        return id;
    }

    public List<CVShort> query(){
        Iterator<CVShort> ite = cvShortRepository.findAll().iterator();
        List<CVShort> res = new ArrayList<>();
        while (ite.hasNext()){
            res.add(ite.next());
        }
        return res;
    }

    public List<CVShort> queryInContent(String keyword){
        List<CVShort> cvs = new ArrayList<>();
        if(!keyword.isEmpty()) {
            NativeSearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(QueryBuilders.matchQuery("attachment.content", keyword)).build();
            SearchHits<CVShort> search = elasticsearchRestTemplate.search(searchQuery, CVShort.class);
            for (SearchHit<CVShort> searchHit : search) {
                cvs.add(searchHit.getContent());
            }
        }else{
            throw new EmptyKeywordException(ImmutableMap.of("keyword", "empty"));
        }
        return cvs;
    }
}
