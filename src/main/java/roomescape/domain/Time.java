package roomescape.domain;

public class Time {
    private String time;
    private Long id;

    // 새로운 생성자 추가
    public Time(Long id) {
        this.id = id;
    }

    public Time(Long generatedId, String time) {
        this.id = generatedId;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
