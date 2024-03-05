package org.example.dto;

public class Article extends Dto {
    public String title;
    public String body;
    public int hit;
    public int memberId;        // 회원 ID

    // 테스트 데이터는 조회수를 강제로 포함하고싶으면 Article 생성자를 오버로딩한다.
    public Article(int id, String regDate, int memberId, String title, String body, int hit) {
        this.id = id;
        this.regDate = regDate;
        this.memberId = memberId;
        this.title = title;
        this.body = body;
        this.hit = hit;
    }

    // 복잡하게 쓰는거보다 this 메서드를 이용해서 간단하게 넘겨준다.
    public Article(int id, String regDate, int memberId, String title, String body) {
        this(id, regDate, memberId, title, body, 0);
    }

    // 메서드에 privite를 붙히면 외부 클래스에서 사용할 수 없다.
    // 메서드에 public를 붙히면 외내부 전부 사용할 수 있다.
    public void increaseHit() {
        hit++;
    }
}
