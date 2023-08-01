package com.example.socialmedia;


public  class Post {
    private int id;
    private String platform;
    private String content;
    private String scheduledTime;
    // OOP Concept: Encapsulation (Private attributes are accessed through getter and setter methods)

    public int getId() {
        return id;
    }
    // OOP Concept: Encapsulation (Private attributes are modified through setter methods)

    public void setId(int id) {
        this.id = id;
    }
    // OOP Concept: Encapsulation (Private attributes are accessed through getter and setter methods)

    public String getPlatform() {
        return platform;
    }
    // OOP Concept: Encapsulation (Private attributes are modified through setter methods)

    public void setPlatform(String platform) {
        this.platform = platform;
    }
    // OOP Concept: Encapsulation (Private attributes are accessed through getter and setter methods)

    public String getContent() {
        return content;
    }
    // OOP Concept: Encapsulation (Private attributes are modified through setter methods)

    public void setContent(String content) {
        this.content = content;
    }
    // OOP Concept: Encapsulation (Private attributes are accessed through getter and setter methods)

    public String getScheduledTime() {
        return scheduledTime;
    }
    // OOP Concept: Encapsulation (Private attributes are modified through setter methods)

    public void setScheduledTime(String scheduledTime) {
        this.scheduledTime = scheduledTime;
    }
    // OOP Concept: Polymorphism (Override toString() method to provide a custom string representation)

    @Override
    public String toString() {
        return "ID: " + id +
                ", Platform: " + platform +
                ", Content: " + content +
                ", Scheduled Time: " + scheduledTime;
    }
    public static class ScheduledPost extends Post {
        private String recurrence;

        // Constructor for ScheduledPost class
        public ScheduledPost() {
            // Call the superclass (Post) constructor using 'super()' to initialize the inherited attributes
            super();
        }

        // OOP Concept: Encapsulation (Private attribute specific to ScheduledPost is accessed through getter and setter methods)
        public String getRecurrence() {
            return recurrence;
        }

        // OOP Concept: Encapsulation (Private attribute specific to ScheduledPost is modified through setter methods)
        public void setRecurrence(String recurrence) {
            this.recurrence = recurrence;
        }

        // OOP Concept: Polymorphism (Override toString() method to provide a custom string representation)
        @Override
        public String toString() {
            // Use the superclass (Post) toString() method to display common attributes
            String postString = super.toString();
            // Append the recurrence attribute specific to ScheduledPost
            return postString + ", Recurrence: " + recurrence;
        }
    }

}
