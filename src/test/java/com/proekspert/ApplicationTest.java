package com.proekspert;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello World")));
    }

    @Test
    public void test00_shouldRevertTree() throws Exception {
        this.mockMvc.perform(post("/tree")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"id\": \"2\",\n" +
                        "  \"left\" : {\n" +
                        "  \t\"id\": \"1\"\n" +
                        "  },\n" +
                        "  \"right\" : {\n" +
                        "  \t\"id\": \"3\",\n" +
                        "  \t \"left\" : {\n" +
                        "\t  \t\"id\": \"5\",\n" +
                        "\t\t  \t\"left\" : {\n" +
                        "\t\t  \t\"id\": \"5\"\n" +
                        "\t\t  }\n" +
                        "\t  }\n" +
                        "  }\n" +
                        "}\n"))
                .andExpect(content().string(containsString("Node: 2 - Left: 3 - Right: 1\n" +
                        "Node: 3 - Right: 5\n" +
                        "Node: 5 - Right: 5\n" +
                        "Node: 5\n" +
                        "Node: 1\n")))
                .andExpect(status().isOk());

    }
}
