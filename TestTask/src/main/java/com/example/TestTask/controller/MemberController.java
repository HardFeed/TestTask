package com.example.TestTask.controller;

import com.example.TestTask.model.Member;
import com.example.TestTask.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @PostMapping("/createmember")
    public ResponseEntity<Member> createMember(@Valid @RequestBody Member member){
        return ResponseEntity.ok(memberService.createMember(member));
    }

    @GetMapping("/getmember")
    public List<Member> getAllMembers(){
        return memberService.getAllMembers();
    }

    @DeleteMapping("/deletemember/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id){
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/getname/{name}")
    public ResponseEntity<List> getByNames(@PathVariable String name){
        return ResponseEntity.ok(memberService.findByName(name));
    }
}
