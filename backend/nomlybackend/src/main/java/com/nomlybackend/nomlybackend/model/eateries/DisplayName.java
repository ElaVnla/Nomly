package com.nomlybackend.nomlybackend.model.eateries;

public class DisplayName{
    // To make displayName be an object, not a string (Expected by google)
    String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
