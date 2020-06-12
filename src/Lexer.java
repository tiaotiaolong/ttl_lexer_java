import java.io.CharArrayReader;
import java.io.IOException;
import java.util.ArrayList;

public class Lexer {


    //外界传入的代码字符串
    private String code;
    //生成的token流
    private ArrayList<Token> tokenList;
    //辅助变量 用来存储ID的缓存
    private StringBuilder idSb;


    public Lexer(String code) {
        this.code = code;
        this.tokenList=new ArrayList<Token>();
        this.idSb=new StringBuilder("");
    }


    //State 枚举类
    public enum  State{
        INIT,
        INT_1,INT_2,INT,
        ID_1,ID,
        Assignment,//=
        SemiColon,//;
        IntLiteral,IntLiteral_1,


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

    //是否是数字
    private boolean isDigit(char ch) {
        return ch >= '0' && ch <= '9';
    }

    //是否是空白字符
    private boolean isBlank(int ch) {
        return ch == ' ' || ch == '\t' || ch == '\n';
    }

    //回到初始状态值 根据目前状态，传入因子来确定新状态
    public State changeState(char ch,State state){

        switch (state){

            case INIT:
                if(isAlpha(ch)){

                    if(ch=='i'){
                        return State.INT_1;
                    }
                    else if(ch==';'){
                        tokenList.add(new Token(State.SemiColon, ";"));
                        return State.INIT;
                    }
                    else
                        idSb.append(ch);
                        return State.ID_1;
                        //ch是数字的情况
                }else if(isDigit(ch)){
                    idSb.append(ch);
                    return State.IntLiteral_1;
                }

            case INT_1:
                if(isAlpha(ch)){

                    if(ch=='n'){
                        return State.INT_2;
                    }else if(ch=='='){
                        tokenList.add(new Token(State.Assignment, "="));
                        return State.INIT;
                    }
                    else
                        idSb.append(ch);
                        return State.ID_1;
                }
            case INT_2:
                if(isAlpha(ch)){

                    if(ch=='t'){
                        tokenList.add(new Token(State.INT, "int"));
                        return State.INIT;
                    }else if(ch=='='){
                        tokenList.add(new Token(State.Assignment, "="));
                        return State.INIT;
                    }
                    else
                        idSb.append(ch);
                        return State.ID_1;
                }
            case ID_1:
                //数字或者是字符的情况，说明依旧是ID状态
                if(isAlpha(ch) || isDigit(ch)){
                    idSb.append(ch);
                    return State.ID_1;
                    //如果遇到等号 说明是赋值语句
                }else if(ch == '=') {
                    tokenList.add(new Token(State.ID, idSb.toString()));
                    tokenList.add(new Token(State.Assignment, "="));
                    idSb.setLength(0);
                    return State.INIT;
                }
            case IntLiteral_1:
                if(isDigit(ch)){
                    idSb.append(ch);
                    return State.IntLiteral_1;
                } else if (ch == ';'){
                    tokenList.add(new Token(State.IntLiteral, idSb.toString()));
                    tokenList.add(new Token(State.SemiColon, ";"));

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

            if(isBlank(ch))
                continue;

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

                case ID_1:
                    state=changeState(ch,state);
                    break;
                case IntLiteral_1:
                    state=changeState(ch,state);
                    break;
            }

        }
        return tokenList;

    }


}
