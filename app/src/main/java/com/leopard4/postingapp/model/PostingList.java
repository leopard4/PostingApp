package com.leopard4.postingapp.model;

import java.util.List;

public class PostingList {

    private List<Posting> items;
    private int count;

    public List<Posting> getItems() {
        return items;
    }

    public void setItems(List<Posting> items) {
        this.items = items;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
