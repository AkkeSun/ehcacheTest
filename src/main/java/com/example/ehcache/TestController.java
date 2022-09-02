package com.example.ehcache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/cacheTest")
public class TestController {

    @Autowired private TestService testService;

    private int cnt = 1;

    @GetMapping
    public void getMember(String memberId) {

        long start = System.currentTimeMillis();
        Member member = testService.getMember(memberId);
        long end = System.currentTimeMillis();

        System.out.println(cnt + " : " + member.getLoginId()+ "의 수행시간 : "+ Long.toString(end-start));
        cnt ++ ;
    }

    @GetMapping("/refresh")
    public void cacheRefresh(String memberId) {
        testService.refresh(memberId);
    }

}
