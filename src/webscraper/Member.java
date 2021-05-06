package webscraper;

public class Member {
    private String name;
    private String url;
    private String startEmpire;
    private String endEmpire;

    public Member(String name, String url){
           this.name = name;
           this.url = url;
    }

    public Member(String name, String url, String startEmpire, String endEmpire){
        this.name = name;
        this.url = url;
        this.startEmpire = startEmpire;
        this.endEmpire = endEmpire;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getEndEmpire() {
        return endEmpire;
    }

    public String getStartEmpire() {
        return startEmpire;
    }
}
