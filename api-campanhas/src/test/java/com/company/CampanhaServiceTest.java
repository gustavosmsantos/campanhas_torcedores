package com.company;

import org.junit.Ignore;

@Ignore
public class CampanhaServiceTest {

//    @ClassRule
//    public static DockerComposeRule docker = DockerComposeRule.builder()
//            .file("src/test/resources/docker-compose.yml")
//            .saveLogsTo("build/docker/logs")
//            .build();
//
//    @Test
//    public void validaInclusaoCampanhaDataInvalida() throws IOException {
//
//        String url = "http://localhost:4567/campanhas";
//
//        HttpClient client = HttpClientBuilder.create().build();
//        HttpPost request = new HttpPost(url);
//
//        ObjectNode node = mapper.createObjectNode();
//        node.put("nome", "Uma campanha");
//        node.put("inicio", "2018-03-20");
//        node.put("fim", "2017-11-12");
//        node.put("timeId", "times/1012");
//        request.setEntity(new StringEntity(node.toString()));
//
//        request.setHeader("Content-Type", "application/json");
//
//        HttpResponse response = client.execute(request);
//
//        assertThat(response.getStatusLine().getStatusCode(), Matchers.is(400));
//
//    }

}
