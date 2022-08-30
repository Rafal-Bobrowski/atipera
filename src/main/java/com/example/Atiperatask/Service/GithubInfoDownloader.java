package com.example.Atiperatask.Service;

import com.example.Atiperatask.Models.BranchInfo;
import com.example.Atiperatask.Models.RepositoryInfo;
import com.example.Atiperatask.Exceptions.NotExistingLoginException;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class GithubInfoDownloader {

    private final HttpClient httpClient;
    private final String REPOS_URL = "https://api.github.com/users/{login}/repos";
    private final String BRANCHES_URL = "https://api.github.com/repos/{owner}/{repo}/branches";
    private final Gson gson;

    public List<RepositoryInfo> downloadRepositoriesInfo(String login) throws Exception {
        List<RepositoryInfo> repositories = downloadRepositoriesWithoutBranches(login);
        for (RepositoryInfo repository : repositories){
            downloadAndInjectBranchesInfoToRepository(repository, login);
        }
        return repositories;
    }

    private List<RepositoryInfo> downloadRepositoriesWithoutBranches(String login) throws Exception {
        URI reposURI = new URI(REPOS_URL.replace("{login}", login));
        HttpResponse<String> response = sendGetRequestWithURI(reposURI);
        return Stream.of(gson.fromJson(response.body(), RepositoryInfo[].class))
                .filter(info -> !info.isFork())
                .collect(Collectors.toList());
    }

    private void downloadAndInjectBranchesInfoToRepository(RepositoryInfo repositoryInfo, String login) throws URISyntaxException, IOException, InterruptedException {
        URI branchURI = new URI(BRANCHES_URL.replace("{owner}", login).replace("{repo}", repositoryInfo.getName()));
        HttpResponse<String> response = sendGetRequestWithURI(branchURI);
        List<BranchInfo> branchesInfo = List.of(gson.fromJson(response.body(), BranchInfo[].class));
        repositoryInfo.setBranchInfos(branchesInfo);
    }

    private HttpResponse<String> sendGetRequestWithURI(URI uri) throws IOException, InterruptedException {
        HttpRequest request = createGetRequestWithURI(uri);
        return pickStringResponse(request);
    }

    private HttpRequest createGetRequestWithURI(URI uri){
        return HttpRequest
                .newBuilder()
                .GET()
                .header("Accept", "application/vnd.github+json")
                .uri(uri)
                .build();
    }

    private HttpResponse<String> pickStringResponse(HttpRequest request) throws IOException, InterruptedException {
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if(response.statusCode() == 404){
            throw new NotExistingLoginException("There is no such user");
        }
        return response;
    }
}
