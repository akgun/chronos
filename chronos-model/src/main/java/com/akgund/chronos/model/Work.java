package com.akgund.chronos.model;

import hirondelle.date4j.DateTime;

public class Work {
    private Long id;
    private DateTime start;
    private DateTime end;
    private String comment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DateTime getStart() {
        return start;
    }

    public void setStart(DateTime start) {
        this.start = start;
    }

    public DateTime getEnd() {
        return end;
    }

    public void setEnd(DateTime end) {
        this.end = end;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
