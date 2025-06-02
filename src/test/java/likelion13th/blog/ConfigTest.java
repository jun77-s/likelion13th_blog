package likelion13th.blog;

import org.junit.jupiter.api.Test;


public class ConfigTest {

    @Test
    void apiKeyTest() {
        System.out.println(System.getenv("OPENAI_API_KEY"));
    }
}
