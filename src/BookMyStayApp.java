public class BookMyStayApp {
    public static void main(String[] args) {

        checkPalindrome();
    }

    // UC2 - Hardcoded Palindrome
    static void checkPalindrome() {

        String str = "madam"; // hardcoded input
        String reversed = new StringBuilder(str).reverse().toString();

        System.out.println("Checking Palindrome for: " + str);

        if (str.equals(reversed)) {
            System.out.println("Result: Palindrome");
        } else {
            System.out.println("Result: Not Palindrome");
        }
    }

}

