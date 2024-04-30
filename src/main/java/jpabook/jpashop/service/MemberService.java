package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // 읽기 전용
@RequiredArgsConstructor // final이 있는 필드만 가지고 생성자를 만들어 준다.
public class MemberService {

    private final MemberRepository memberRepository;

    /* @RequiredArgsConstructor를 주입하면 생성자를 만들지 않아도 된다.
    // @Autowired 생성자가 1개만 있는 경우엔 Spring이 @Autowired가 없어도 자동으로 인잭션 해준다.
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }*/

    // 회원 가입
    @Transactional // 디폴트 false 쓰기(데이터 변경) 가능
    public Long join(Member member) {
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        //EXCEPTION
        List<Member> findMembers = memberRepository.findByName(member.getName());// 같은 name이 있는지 찾아본다.
        if (!findMembers.isEmpty()) { //findMembers가 공백이 아니라면 중복회원이 있다는 뜻
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
