package cod.model;

public class

questao {

        private String id;
        private String type;
        private String rating;
        private String question;

        // Construtor
        public questao(String id, String type, String rating, String question) {
            this.id = id;
            this.type = type;




            this.rating = rating;
            this.question = question;
        }

        // Getters e Setters
        public String getId() { return id; }
        public String getType() { return type; }
        public String getRating() { return rating; }
        public String getQuestion() { return question; }


}
