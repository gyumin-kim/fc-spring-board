package example.springboard.dto;

import lombok.Getter;
import lombok.Setter;

// 게시판 페이징 전용 클래스
// Criteria : 검색 기준, 분류 기준
// 최소한의 필요 데이터 : 한 페이지 당 보여줄 게시글의 수, 어떤 페이지를 보여줄 것인가?
public class Criteria {
    private int page;       // 현 페이지 번호
    private int perPageNum; // 페이지 당 보여줄 게시글의 개수

    // search
    @Setter @Getter
    private String searchType;
    @Setter @Getter
    private String keyword;

    public Criteria(){
        // 최초 게시판에 진입할 때를 위해서 기본 값을 설정
        this.page = 1;
        this.perPageNum = 5;
    }

    public int getPage(){
        return page;
    }

    // Url로 접근할 때 입력받은 값이 0보다 작을 경우 page의 기본값으로 변경해주어야 한다. 1보다 큰 값이 들어오면 해당 페이지를 보여주어야 한다.
    public void setPage(int page){
        if(page <= 0){
            this.page = 1;
            return;
        }
        this.page = page;
    }

    public int getPerPageNum(){
        return perPageNum;
    }

    // 만약 보여줄 게시글의 수를 조작하는 것이 있다면 사용할 메소드(시간이 된다면...)
    public void setPerPageNum(int perPageNum){
        if(perPageNum <= 0 || perPageNum > 100){
            this.perPageNum = 5;
            return;
        }
        this.perPageNum = perPageNum;
    }

    // limit 구문에서 시작 위치를 지정할 때 사용
    // this.page가 1이면 0이되어야 한다.(mysql limit 0,10 으로 해야 10개씩 나옴) / 10. 10
    public int getPageStart(){
        return (this.page - 1) * perPageNum;
    }
}
