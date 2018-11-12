package no.kristiania.pgr200.core;

public class ConferenceTalk {
    
	private String title, description, topic, day, starts, id;
	

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
    
    public String getTopic() {
        return topic;
    }
    
    public void setTopic(String topic) {
        this.topic = topic;
    }
    
    public String getDay() {
        return day;
    }
    
    public void setDay(String day) {
        this.day = day;
    }
    
    public String getStarts() {
        return starts;
    }
    
    public void setStarts(String starts) {
        this.starts = starts;
    }
    
    public void setID(String id) {
    	this.id = id;
    }
    public String getID() {
    	return id;
    }
    
    @Override
    public String toString() {
        return "ConferenceTalk [title=" + title + ", description="+ description + ", topic=" + topic + ",day=" + day + ", starts=" + starts + ", id="+ id + "]";
    }
}


