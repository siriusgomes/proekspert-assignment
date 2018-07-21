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
        this.mockMvc.perform(post("/treeLocal")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":\"2\",\"left\":{\"id\":\"1\",\"left\":{\"id\":\"6\"}},\"right\":{\"id\":\"3\",\"left\":{\"id\":\"5\",\"left\":{\"id\":\"5\",\"right\":{\"id\":\"20\"}}},\"right\":{\"id\":\"10\"}}}"))
                .andExpect(content().string(containsString("{\"id\":2,\"left\":{\"id\":3,\"left\":{\"id\":10},\"right\":{\"id\":5,\"right\":{\"id\":5,\"left\":{\"id\":20}}}},\"right\":{\"id\":1,\"right\":{\"id\":6}}}")))
                .andExpect(status().isOk());

    }
}
