import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("Hello World!");

        Lexer lexer=new Lexer("int a=1;");
        lexer.tokenize();
    }
}
