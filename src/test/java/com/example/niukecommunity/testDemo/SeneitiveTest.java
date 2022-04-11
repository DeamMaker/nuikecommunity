package com.example.niukecommunity.testDemo;

import com.example.niukecommunity.util.SensitiveFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SeneitiveTest {
    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Test
    public void testSensitiveFilter(){
        String text="这里可以&赌$$博、吸^^毒、开@票))))哦";
        String filter = sensitiveFilter.filter(text);
        System.out.println(filter);
    }
}
