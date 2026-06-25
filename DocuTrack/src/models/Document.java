package models;

public class Document {

    public int documentId;
    public String title;
    public String author;

    public Document(int documentId, String title, String author) {
        this.documentId = documentId;
        this.title = title;
        this.author = author;
    }

    @Override
    public String toString() {
        return "ID: " + documentId +
                ", Title: " + title +
                ", Author: " + author;
    }
}