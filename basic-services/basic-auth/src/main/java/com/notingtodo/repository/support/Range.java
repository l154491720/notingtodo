package com.notingtodo.repository.support;

import java.io.Serializable;

/**
 * Created by qilin.liu on 2018/6/20.
 */
public class Range<E> implements Serializable {

    private static final long serialVersionUID = 5169062562508595711L;

    private String field;
    private Comparable from;
    private Comparable to;
    private Boolean includeNull;

    public Range(String field) {
        this.field = field;
    }

    public Range(String field, Comparable from, Comparable to) {
        this.field = field;
        this.from = from;
        this.to = to;
    }

    public Range(String field, Comparable from, Comparable to, Boolean includeNull) {
        this.field = field;
        this.from = from;
        this.to = to;
        this.includeNull = includeNull;
    }

    public Range(Range<E> other) {
        this.field = other.field;
        this.from = other.from;
        this.to = other.to;
        this.includeNull = other.includeNull;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Comparable getFrom() {
        return from;
    }

    public void setFrom(Comparable from) {
        this.from = from;
    }

    public Comparable getTo() {
        return to;
    }

    public void setTo(Comparable to) {
        this.to = to;
    }

    public Boolean getIncludeNull() {
        return includeNull;
    }

    public void setIncludeNull(Boolean includeNull) {
        this.includeNull = includeNull;
    }

    public boolean isValid(){
        if(isBetween()){
            return getFrom().compareTo(getTo()) <= 0;
        }
        return true;
    }

    public boolean isBetween(){
        return isFromSet() && isToSet();
    }

    public boolean isFromSet(){
        return getFrom() != null;
    }

    public boolean isToSet(){
        return getTo() != null;
    }

    public boolean isIncludeNullSet(){
        return getIncludeNull() != null;
    }

    public boolean isSet(){
        return isFromSet() || isToSet() || isIncludeNullSet();
    }
}
