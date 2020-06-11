import java.io.CharArrayReader;
import java.io.IOException;
import java.util.ArrayList;

public class Lexer {


    //外界传入的代码字符串
    private String code;
    //生成的token流
    private ArrayList<Token> tokenList;


    public Lexer(String code) {
        this.code = code;
        this.tokenList=new ArrayList<Token>();
    }


    //State 枚举类
    public enum  State{
        INIT,
        INT_1,INT_2,INT,
        ID
    }


    //内部类，用来表示Token结构体
    public class Token{
        public Token(State token_Type, String value) {
            this.token_Type = token_Type;
            this.value = value;
        }

        //token 类型
        State token_Type;
        //字面量
        String value;
    }

    //判断是不是字母
    private boolean isAlpha(char ch) {
        return ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z';
    }

    //回到初始状态值 根据目前状态，传入因子来确定新状态
    public State changeState(char ch,State state){


        switch (state){

            case INIT:
                if(isAlpha(ch)){

                    if(ch=='i'){
                        return State.INT_1;
                    }else
                        return State.ID;
                }
            case INT_1:
                if(isAlpha(ch)){

                    if(ch=='n'){
                        return State.INT_2;
                    }else
                        return State.ID;
                }
            case INT_2:
                if(isAlpha(ch)){

                    if(ch=='t'){
                        tokenList.add(new Token(State.INT, "int"));
                        return State.INT;
                    }else
                        return State.ID;
                }
        }


        return State.INIT;
    }




    public ArrayList<Token> tokenize() throws IOException {

        char[] codeArray=code.toCharArray();
        CharArrayReader reader=new CharArrayReader(codeArray);

        int chr;
        char ch;
        State state=State.INIT;

        while((chr=reader.read())!=-1){

            ch=(char)chr;

            switch(state){

                case INIT:
                    state=changeState(ch,state);
                    break;

                case INT_1:
                    state=changeState(ch,state);
                    break;

                case INT_2:
                    state=changeState(ch,state);
                    break;
            }







        }
        return tokenList;

    }


}
