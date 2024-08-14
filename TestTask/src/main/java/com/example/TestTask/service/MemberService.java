package com.example.TestTask.service;

import com.example.TestTask.model.Member;
import com.example.TestTask.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    public Member createMember(Member member){
        member.setDate(LocalDate.now());
        return memberRepository.save(member);
    }

    public List<Member> getAllMembers(){
        return memberRepository.findAll();
    }

    public void deleteMember(Long id){
        Member member = memberRepository.findById(id).orElseThrow();
        if (member.getAmount() > 0){
            throw new RuntimeException("You cannot delete this member(He borrows books)");
        }else {
            memberRepository.deleteById(id);
        }
    }

    public List<String> findByName(String name){
        Member member = memberRepository.findByName(name).orElseThrow();
        List<String> names = member.getBorrowedBooks();
        return names;
    }
}
