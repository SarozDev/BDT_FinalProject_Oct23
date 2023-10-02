package Twitterkafkaproducersvc;

public class TwitterMessage {
    
    private TwitterData data;

    public TwitterMessage(TwitterData data) {
        this.data = data;
    }

    public TwitterData getData() {
        return data;
    }

    public void setData(TwitterData data) {
        this.data = data;
    }

    public class TwitterData {
        private String id;
        private String text;

        public TwitterData(String id, String text) {
            this.id = id;
            this.text = text;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    public String getMessageId() {
        return null;
    }

    public String getMessageText() {
        return null;
    }
}




