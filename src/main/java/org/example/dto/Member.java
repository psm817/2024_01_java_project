package org.example.dto;

// public을 붙혀야 Main에서 불러왔을 때 오류가 안난다.
public class Member {
    public int id;
    public String regDate;
    public String loginId;
    public String loginPw;
    public String name;

    // 테스트 데이터는 조회수를 강제로 포함하고싶으면 Article 생성자를 오버로딩한다.
    public Member(int id, String regDate, String loginId, String loginPw, String name) {
        this.id = id;
        this.regDate = regDate;
        this.loginId = loginId;
        this.loginPw = loginPw;
        this.name = name;
    }
}
