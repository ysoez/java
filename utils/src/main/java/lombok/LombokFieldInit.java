package lombok;

class LombokFieldInit {

    public static void main(String[] args) {
        System.err.println(new ExplicitConstructor());
        System.out.println(ExplicitConstructor.builder().build());
    }

    @ToString
    @Builder
    @AllArgsConstructor
    private static class ExplicitConstructor {
        @Builder.Default
        private String field = "initial";

        public ExplicitConstructor() {
            // ~ no op
        }
    }

}
