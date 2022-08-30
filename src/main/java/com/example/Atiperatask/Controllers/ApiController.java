package com.example.Atiperatask.Controllers;

import com.example.Atiperatask.Models.RepositoryInfo;
import com.example.Atiperatask.Exceptions.NotExistingLoginException;
import com.example.Atiperatask.Service.GithubInfoDownloader;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ApiController {

    private final GithubInfoDownloader githubInfoDownloader;
    private final Gson gson;

    @GetMapping(value = "/getRepos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getReposJson(@RequestParam String login){
        try {
            List<RepositoryInfo> repositoriesInfo = githubInfoDownloader.downloadRepositoriesInfo(login);
            return ResponseEntity
                    .ok()
                    .body(gson.toJson(repositoriesInfo));
        } catch (NotExistingLoginException e) {
            String body = new JSONObject()
                    .put("status", 404)
                    .put("message", e.getMessage())
                    .toString();
            return ResponseEntity
                    .status(404)
                    .body(body);
        } catch (Exception e){
            String body = new JSONObject()
                    .put("status", 500)
                    .put("message", "Internal Server Error")
                    .toString();
            return ResponseEntity
                    .status(500)
                    .body(body);
        }
    }

    @GetMapping(value = "/getRepos", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> getReposXml(@RequestParam String login){
            String body = new JSONObject()
                    .put("status", 406)
                    .put("message", "Xml not supported")
                    .toString();
            return ResponseEntity
                    .status(406)
                    .body(body);
    }
}
