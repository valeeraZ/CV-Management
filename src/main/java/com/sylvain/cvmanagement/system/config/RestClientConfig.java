package com.sylvain.cvmanagement.system.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.util.List;

/**
 * Created by Wenzhuo Zhao on 20/10/2021.
 */
@EnableElasticsearchRepositories
public class RestClientConfig extends AbstractElasticsearchConfiguration {

    @Value("${spring.elasticsearch.rest.uris}")
    private List<String> uris;

    @Value("${spring.elasticsearch.rest.password}")
    private String userName;

    @Value("${spring.elasticsearch.rest.username}")
    private String password;

    @Bean
    public RestHighLevelClient elasticsearchClient() {

        final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(uris.get(0))
                .withBasicAuth(userName, password)
                .build();

        return RestClients.create(clientConfiguration).rest();
    }
}
