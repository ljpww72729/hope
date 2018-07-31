package cc.lkme.hope.main.news.data;

public class NewsTabs {
    private int id;
    private String tag;
    private String label;

    public NewsTabs(int id, String label, String tag) {
        this.id = id;
        this.label = label;
        this.tag = tag;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
