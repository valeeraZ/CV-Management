package com.sylvain.cvmanagement.system.controller;

import com.sylvain.cvmanagement.system.entity.CVShort;
import com.sylvain.cvmanagement.system.service.CVService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

/**
 * Created by Wenzhuo Zhao on 20/10/2021.
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/api")
@Slf4j
@Validated
public class CVController {
    private final CVService cvService;

    @PostMapping("/cvs")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public ResponseEntity<String> addCV(@NotNull(message = "{file.blank}") MultipartFile file) throws IOException {
        String id = cvService.save(file);
        return ResponseEntity.ok().body(id);
    }

    @GetMapping("/cvs")
    public ResponseEntity<List<CVShort>> getAllCV(){
        return ResponseEntity.ok().body(cvService.query());
    }

    @GetMapping("/cvs/search")
    public ResponseEntity<List<CVShort>> queryInContent(@RequestParam("keyword") @NotNull(message = "{keyword.blank}") String keyword)throws IOException {
        return ResponseEntity.ok().body(cvService.queryInContent(keyword));
    }
}
