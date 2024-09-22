package develop.grassserver.member.check;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class MemberCheckIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("성공 - 멤버 ID 중복 및 형식 체크")
    void test1() throws Exception {
        String memberId = "aaaa";
        ResultActions result = mockMvc.perform(
                get("/api/v1/members/check/id")
                        .param("memberId", memberId)
        );

        result.andExpect(
                        jsonPath("$.success").value("true"))
                .andDo(print());
    }

    @Test
    @DisplayName("실패 - 멤버 ID 길이 미충족")
    void test2() throws Exception {
        String memberId = "aaa";
        ResultActions result = mockMvc.perform(
                get("/api/v1/members/check/id")
                        .param("memberId", memberId)
        );

        result.andExpect(
                        jsonPath("$.success").value("false"))
                .andDo(print());
    }

    @Test
    @DisplayName("성공 - 멤버 이름 중복 및 길이 충족")
    void test3() throws Exception {
        String name = "aaa";
        ResultActions result = mockMvc.perform(
                get("/api/v1/members/check/name")
                        .param("name", name));

        result.andExpect(
                        jsonPath("$.success").value("true"))
                .andDo(print());
    }
}