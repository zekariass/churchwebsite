package com.churchwebsite.churchwebsite.services;

import com.churchwebsite.churchwebsite.entities.Member;
import com.churchwebsite.churchwebsite.repositories.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Page<Member> findAll(int page, Integer pageSize, String sortBy) {
        if(sortBy.isEmpty()){
            sortBy = "membershipDate";
        }

        Pageable pageable;
        if(sortBy == "newsPostTime"){
            pageable = PageRequest.of(page-1, pageSize, Sort.by(Sort.Order.desc(sortBy)));
        }else{
            pageable = PageRequest.of(page-1, pageSize, Sort.by(Sort.Order.asc(sortBy)));
        }
        return memberRepository.findAll(pageable);
    }


    public List<Member> findAll(){
        return memberRepository.findAll();
    }



    public Member save(Member member) {
        return memberRepository.save(member);
    }

    public Member findById(int memberId) {

        return memberRepository.findById(memberId).orElseThrow(
                () -> new RuntimeException("No Member Found!")
        );
    }

    public void deleteById(int memberId) {
        memberRepository.deleteById(memberId);
    }
}
