public class UserDetails {
    private String userId;
    private String bookname;
    private String issueddate;

    public UserDetails(String userId, String bookname, String issueddate) {
        this.userId = userId;
        this.bookname = bookname;
        this.issueddate = issueddate;
    }

    // Getters
    public String getUserId() {
        return userId;
    }

    public String getName() {
        return bookname;
    }

    public String getEmail() {
        return issueddate;
    }

   
}