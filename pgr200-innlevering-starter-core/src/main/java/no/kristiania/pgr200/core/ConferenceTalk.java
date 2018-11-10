package no.kristiania.pgr200.core;

public class ConferenceTalk {
    
    private String title, description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    @Override
    public String toString() {
        return "ConferenceTalk [title=" + title + ", description="+ description + "]";
    }
}
