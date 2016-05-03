package calculator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

/**
 * Created by space on 16/5/3.
 */
class ExpressionParser {
    private final String OPS = "+-*/() ";
    private interface op {
        public void calc(Stack<Double> stack);
    }
    private HashMap<String, op> regOp;
    private String exp;
    private ArrayList<String> expSeq;
    private Stack<String> rpnSeq;

    String getResult() {
        Stack<Double> numberStack = new Stack<>();
        while (rpnSeq.size() > 0) {
            String item = rpnSeq.pop();
            if (!OPS.contains(item)) { //number
                numberStack.push(Double.parseDouble(item));
            } else {
                regOp.get(item).calc(numberStack);
            }
        }
        if (numberStack.size() == 1)
            return numberStack.pop().toString();
        else
            return "输入错误";
    }

    private int op2int(String op) {
        final String[] opSeq = {"#", "+-", "*/"};
        for (int i=0; i<opSeq.length; i++) {
            if (opSeq[i].contains(op))
                return i;
        }
        return -1;
    }

    private void RPN() {
        Stack<String> s1 = new Stack<>();
        Stack<String> s2 = new Stack<>();
        s1.push("#");
        for (String item : expSeq) {
            if (!OPS.contains(item)) { //number
                s2.push(item);
            } else {
                if ("*/+-".contains(item)) {
                    while (op2int(item) <= op2int(s1.peek()) && !s1.peek().equals("(")) {
                        s2.push(s1.pop());
                    }
                    s1.push(item);
                } else if ("(".equals(item)) {
                    s1.push(item);
                } else if (")".equals(item)) {
                    while (!s1.peek().equals("(")) {
                        s2.push(s1.pop());
                    }
                    s1.pop();
                }
            }
        }
        while (!s1.peek().equals("#")) {
            s2.push(s1.pop());
        }
        while (s2.size() > 0) {
            rpnSeq.push(s2.pop());
        }
    }

    private void mergeNumber() {
        String strBuf = "";
        ArrayList<String> merged = new ArrayList<>();
        for (int i=0; i<exp.length(); i++) {
            String c = exp.substring(i, i+1);
            if (c.equals(" ")) continue;
            if (OPS.contains(c)) {
                if (strBuf.length() > 0) {
                    merged.add(strBuf);
                    strBuf = "";
                }
                merged.add(c);
            } else {
                strBuf += c;
            }
        }
        if (strBuf.length() > 0) {
            merged.add(strBuf);
        }
        expSeq = merged;
    }

    ExpressionParser(String exp) {
        this.exp = exp;
        rpnSeq = new Stack<>();
        regOp = new HashMap<>();
        regOp.put("+", stack -> {
            double p2 = stack.pop();
            double p1 = stack.pop();
            stack.push(p1 + p2);
        });
        regOp.put("-", stack -> {
            double p2 = stack.pop();
            double p1 = stack.pop();
            stack.push(p1 - p2);
        });
        regOp.put("*", stack -> {
            double p2 = stack.pop();
            double p1 = stack.pop();
            stack.push(p1 * p2);
        });
        regOp.put("/", stack -> {
            double p2 = stack.pop();
            double p1 = stack.pop();
            stack.push(p1 / p2);
        });
        mergeNumber();
        RPN();
    }
}
