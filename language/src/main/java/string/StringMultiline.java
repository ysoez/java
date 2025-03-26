package string;

class StringMultiline {

    public static void main(String[] args) {
        System.out.println(htmlReport());
    }

    private static String htmlReport() {
        String title = "Monthly Report";
        int itemsSold = 150;
        return """
                <!DOCTYPE html>
                <html>
                <head>
                    <title>%s</title>
                </head>
                <body>
                    <h1>%s</h1>
                    <p>Items sold: %d</p>
                </body>
                </html>
                """.formatted(title, title, itemsSold);
    }

}
