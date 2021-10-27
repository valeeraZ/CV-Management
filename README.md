# Description
A Spring Boot Application for using Elastic Search with Spring Boot to manage, index and search CVs.  
This application could be used for recruitment of job, each candidate could post their CV with an user account, the HR could view a list of CV and the corresponding candidats with an admin account.

# Tech
* The authentication with authorization and role is realised by `Spring Security`.
* Credentials are needed for authentication, once login successfully you will get a token (JWT). Use this token for the API who needs a pre authorization.
* The account is saved in a MySQL database and operated with `Spring Data`.  
* A CV file in pdf or docx is saved into `Elastic Search` cluster which is encoded to Base64 with a text of contents.
* With `Spring Boot Admin Client`, you can view the logs and check the health of application with the actuators exposed.

# Documentation of API
See https://documenter.getpostman.com/view/10263827/UV5deaVm for usage of endpoints.

# Usage
You should have a MySQL and ElasticSearch instance to use this application

## Modify `application.properties` about connection to datasource
1. Delete the first line `spring.profiles.active=dev`
2. Modify the information about datasource of MySQL and ElasticSearch
   ```
    spring.datasource.url=jdbc:mysql://<ip-mysql>:<port>/<database>?serverTimezone=UTC
    spring.datasource.username=<mysql-username>
    spring.datasource.password=<mysql-password>
   
    spring.elasticsearch.rest.uris=<elasticsearch-ip:port>
    spring.elasticsearch.rest.username=<username-elasticsearch>
    spring.elasticsearch.rest.password=<password-elasticsearch>
   ```
   
## Optional: Use Spring Boot Admin Client
If you want to use Spring Boot Admin Client, set the information about Spring Security and Spring Boot Admin.  

```
# Spring Security
spring.security.user.name=<username>
spring.security.user.password=<password>

# Spring Boot Admin Client Side
spring.boot.admin.client.url=http://localhost:8888
spring.boot.admin.client.instance.name=cvmanagement
spring.boot.admin.client.instance.service-base-url=http://localhost:8080
management.endpoints.web.exposure.include=*
management.endpoint.health.show-details=always
spring.boot.admin.client.username=<username>
spring.boot.admin.client.password=<password>
spring.boot.admin.client.instance.metadata.user.name=${spring.security.user.name}
spring.boot.admin.client.instance.metadata.user.password=${spring.security.user.password}
```

Otherwise, delete these properties if you don't need Spring Boot Admin.
## Optional: set up SSH connection
If your Elastic Search/MySQL is hosted on a remote VPS and not accessible from Internet, you might need to configure SSH connection in this project.
* modify the SSH connection.
    ```
    ssh.enabled=false
    ssh.host=<remote-host>
    ssh.port=22
    ssh.username=<username>
    ssh.privatekey=<id_rsa>
    ```
      
* copy your private key `id_rsa` of SSH in the project
* If your private key begins with `BEGIN OPENSSH PRIVATE KEY` which is not supported by JSch, you might need to convert the key to RSA format.
    * copy your private key
        ```
        cp id_rsa id_rsa-new
        ```
    * convert the new private key
        ```
        ssh-keygen -p -f id_rsa-new -m pem 
        ```
    * then modify the path to private key...

## Set up Elastic Search
1. Install "ingest attachment plugin" on every node of your Elastic Search Cluster, see https://www.elastic.co/guide/en/elasticsearch/plugins/current/ingest-attachment.html

2. Using the Attachment Processor in a Pipeline
   ```
    PUT _ingest/pipeline/attachment
    {
        "description" : "Extract attachment information",
        "processors" : [
            {
                "attachment" : {
                    "field" : "data"
                }
            }
        ]
    }
   ```
## Set up MySQL
Create a database in your MySQL instance, with the name that you give in `spring.datasource.url=jdbc:mysql://<ip-mysql>:<port>/<database>?serverTimezone=UTC`
