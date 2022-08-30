package com.example.Atiperatask.Controllers;
import com.example.Atiperatask.Models.BranchInfo;
import com.example.Atiperatask.Models.Commit;
import com.example.Atiperatask.Models.Owner;
import com.example.Atiperatask.Models.RepositoryInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

class ApiControllerTest {
    private final String LOGIN = "Rafal-Bobrowski";
    private final String WRONG_LOGIN = "Rafal-Bowski";
    private final Gson gson = new GsonBuilder().create();

    @Test
    public void shouldResponseOkAndListRepositories(){
        String repositories = given()
                .param("login", LOGIN)
                .when()
                .header("Accept", MediaType.APPLICATION_JSON_VALUE)
                .get("getRepos")
                .then()
                .statusCode(200)
                .extract().asString();
        System.out.println(repositories);
        List<RepositoryInfo> repositoryInfos = List.of(gson.fromJson(repositories, RepositoryInfo[].class));
        List<RepositoryInfo> expectedRepositoryInfos = getExpectedRepositoryInfos();

        assertThat(repositoryInfos)
                .isNotNull()
                .hasSize(3)
                .containsAll(expectedRepositoryInfos);
    }

    @Test
    public void shouldResponseNotFoundWhenNotExistingUser(){
        var retrievedFailureResponseS = given()
                .param("login", WRONG_LOGIN)
                .when()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .get("getRepos")
                .then()
                .statusCode(404)
                .extract().asString();

        FailureResponseDTO retrievedFailureResponse = gson.fromJson(retrievedFailureResponseS, FailureResponseDTO.class);

        var expectedFailureResponse = new FailureResponseDTO()
                .setMessage("There is no such user")
                .setStatus(404);

        assertThat(retrievedFailureResponse)
                .isNotNull()
                .hasFieldOrPropertyWithValue("status", expectedFailureResponse.getStatus())
                .hasFieldOrPropertyWithValue("message", expectedFailureResponse.getMessage());
    }

    @Test
    public void shouldResponseXmlNotSupported(){
        var retrievedFailureResponseS = given()
                .param("login", LOGIN)
                .when()
                .header("Accept", MediaType.APPLICATION_XML_VALUE)
                .get("getRepos")
                .then()
                .statusCode(406)
                .extract().asString();

        FailureResponseDTO retrievedFailureResponse = gson.fromJson(retrievedFailureResponseS, FailureResponseDTO.class);

        var expectedFailureResponse = new FailureResponseDTO()
                .setMessage("Xml not supported")
                .setStatus(406);

        assertThat(retrievedFailureResponse)
                .isNotNull()
                .hasFieldOrPropertyWithValue("status", expectedFailureResponse.getStatus())
                .hasFieldOrPropertyWithValue("message", expectedFailureResponse.getMessage());
    }

    private List<RepositoryInfo> getExpectedRepositoryInfos(){
        Owner owner = new Owner();
        owner.setLogin(LOGIN);

        BranchInfo issBranchInfo = new BranchInfo();
        issBranchInfo.setName("main");
        issBranchInfo.setCommit(new Commit("45d12d2c826d582e1d4c65dc4433d34289ce5a6b"));

        RepositoryInfo repositoryIss = new RepositoryInfo()
                .setName("IssTrackerApp")
                .setFork(false)
                .setOwner(owner)
                .setBranchInfos(List.of(issBranchInfo));

        BranchInfo javaBranchInfo = new BranchInfo();
        javaBranchInfo.setName("main");
        javaBranchInfo.setCommit(new Commit("4ac6ad69fa00062977600ea25ba2c9b09dd7923c"));

        RepositoryInfo repositoryJava = new RepositoryInfo()
                .setName("java-zaawansowana")
                .setFork(false)
                .setOwner(owner)
                .setBranchInfos(List.of(javaBranchInfo));

        List<RepositoryInfo> repositoriesList = new ArrayList<>();
        repositoriesList.add(repositoryIss);
        repositoriesList.add(repositoryJava);
        return repositoriesList;
    }


}