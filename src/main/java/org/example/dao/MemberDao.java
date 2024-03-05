package org.example.dao;

import org.example.dto.Member;

import java.util.ArrayList;
import java.util.List;

public class MemberDao extends Dao {
    public List<Member> members;

    public MemberDao() {
        members = new ArrayList<Member>();
    }

    public void add(Member member) {
        members.add(member);
        lastId++;
    }
}
