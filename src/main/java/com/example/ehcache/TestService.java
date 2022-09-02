package com.example.ehcache;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;


@Service
public class TestService {


    /**
     *@Cacheable : 캐시에 값이 있다면 메소드 실행하지 않고 바로 리턴
     * value : ehcache.xml에 설정한 캐시 이름 등록
     * key :해당 파라미터가 캐시의 키가 되도록 설정
     */
    @Cacheable(value = "memberCache", key="#memberId")
    public Member getMember(@PathVariable String memberId) {
        slowQuery(2000);
        return findByMember(memberId);
    }

    @Cacheable(value = "memberCache2", key="#memberId")
    public Member getMember2(@PathVariable String memberId) {
        slowQuery(2000);
        return findByMember(memberId);
    }


    /*
    같은 key 값의 캐시를 한꺼번에 삭제하는 경우
        @Caching(evict = {
            @CacheEvict(cacheNames="memberCache", key="#memberId"),
            @CacheEvict(cacheNames="memberCache2", key="#memberId"),
    })
     */
    @CacheEvict(cacheNames="memberCache", key="#memberId")
    public void refresh (String memberId) {
        System.out.println("memberId 의 캐시를 삭제합니다");
    }


    private Member findByMember (String memberId) {
        return Member.builder().loginId(memberId).build();
    }

    // 빅 쿼리를 돌린다는 가정
    private void slowQuery(long seconds) {
        try {
            Thread.sleep(seconds);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }
}
