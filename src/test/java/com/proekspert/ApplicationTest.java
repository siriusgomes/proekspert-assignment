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
    public void test01_shouldRevertTree() throws Exception {
        this.mockMvc.perform(post("/treeLocal")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":\"2\",\"left\":{\"id\":\"1\",\"left\":{\"id\":\"6\"}},\"right\":{\"id\":\"3\",\"left\":{\"id\":\"5\",\"left\":{\"id\":\"5\",\"right\":{\"id\":\"20\"}}},\"right\":{\"id\":\"10\"}}}"))
                .andExpect(content().string(containsString("{\"id\":2,\"left\":{\"id\":3,\"left\":{\"id\":10},\"right\":{\"id\":5,\"right\":{\"id\":5,\"left\":{\"id\":20}}}},\"right\":{\"id\":1,\"right\":{\"id\":6}}}")))
                .andExpect(status().isOk());
    }

    @Test
    public void test02_shouldRevertTree() throws Exception {
        this.mockMvc.perform(post("/treeLocal")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":\"50\",\"left\":{\"id\":\"46\",\"left\":{\"id\":\"22\",\"left\":{\"id\":\"12\",\"left\":{\"id\":\"1\"},\"right\":{\"id\":\"15\"}},\"right\":{\"id\":\"25\",\"right\":{\"id\":\"30\",\"left\":{\"id\":\"28\",\"left\":{\"id\":\"26\"}},\"right\":{\"id\":\"40\"}}}},\"right\":{\"id\":\"48\",\"right\":{\"id\":\"49\"}}},\"right\":{\"id\":\"59\",\"left\":{\"id\":\"55\"},\"right\":{\"id\":\"100\",\"left\":{\"id\":\"78\",\"left\":{\"id\":\"67\"},\"right\":{\"id\":\"86\",\"right\":{\"id\":\"92\"}}}}}}"))
                .andExpect(content().string(containsString("{\"id\":50,\"left\":{\"id\":59,\"left\":{\"id\":100,\"right\":{\"id\":78,\"left\":{\"id\":86,\"left\":{\"id\":92}},\"right\":{\"id\":67}}},\"right\":{\"id\":55}},\"right\":{\"id\":46,\"left\":{\"id\":48,\"left\":{\"id\":49}},\"right\":{\"id\":22,\"left\":{\"id\":25,\"left\":{\"id\":30,\"left\":{\"id\":40},\"right\":{\"id\":28,\"right\":{\"id\":26}}}},\"right\":{\"id\":12,\"left\":{\"id\":15},\"right\":{\"id\":1}}}}}")))
                .andExpect(status().isOk());
    }
}
