import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("Hello World!");

        Lexer lexer=new Lexer("int ab1 = 1;");
        System.out.println(lexer.tokenize());
    }
}
