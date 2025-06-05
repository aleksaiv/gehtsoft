package org.gehtsoft.myapp.task;


import org.gehtsoft.myapp.libs.Console;

import java.util.*;

public class Calculator extends Task{
    Console console;
    public Calculator(){
        super();
        console = Console.getInstance();
    }
    public String run() {
        String inputString = console.readString("Enter expression");
        return Double.toString(evaluate(inputString));
    }

    public static double evaluate(String expression) {
        List<String> tokens = tokenize(expression);
        return parseExpression(new LinkedList<>(tokens));
    }

    private static List<String> tokenize(String expr) {
        List<String> tokens = new ArrayList<>();
        int i = 0;
        while (i < expr.length()) {
            char c = expr.charAt(i);
            if (Character.isWhitespace(c)) {
                i++;
            } else if (c == '(' || c == ')' || c == '+' || c == '-' || c == '*' || c == '/') {
                tokens.add(Character.toString(c));
                i++;
            } else if (Character.isDigit(c) || c == '.') {
                StringBuilder sb = new StringBuilder();
                while (i < expr.length() &&
                        (Character.isDigit(expr.charAt(i)) || expr.charAt(i) == '.')) {
                    sb.append(expr.charAt(i++));
                }
                tokens.add(sb.toString());
            } else {
                throw new IllegalArgumentException("Invalid character in expression: " + c);
            }
        }

        // Handle unary minus
        for (int j = 0; j < tokens.size(); j++) {
            if (tokens.get(j).equals("-")) {
                if (j == 0 || tokens.get(j - 1).equals("(") || "+-*/".contains(tokens.get(j - 1))) {
                    tokens.set(j, "u-"); // unary minus
                }
            }
        }

        return tokens;
    }

    private static double parseExpression(Queue<String> tokens) {
        Stack<Double> values = new Stack<>();
        Stack<String> ops = new Stack<>();

        while (!tokens.isEmpty()) {
            String token = tokens.poll();

            if (token.equals("(")) {
                values.push(parseExpression(tokens));
            } else if (token.equals(")")) {
                break;
            } else if (token.equals("u-")) {
                double val = getValue(tokens);
                values.push(-val);
            } else if (isNumber(token)) {
                values.push(Double.parseDouble(token));
            } else if (isOperator(token)) {
                while (!ops.isEmpty() && precedence(ops.peek()) >= precedence(token)) {
                    applyOp(values, ops.pop());
                }
                ops.push(token);
            }
        }

        while (!ops.isEmpty()) {
            applyOp(values, ops.pop());
        }

        return values.pop();
    }

    private static double getValue(Queue<String> tokens) {
        String token = tokens.poll();
        if (token.equals("(")) {
            return parseExpression(tokens);
        } else if (isNumber(token)) {
            return Double.parseDouble(token);
        } else {
            throw new IllegalArgumentException("Expected number but got: " + token);
        }
    }

    private static boolean isNumber(String token) {
        return token.matches("-?\\d+(\\.\\d+)?");
    }

    private static boolean isOperator(String token) {
        return "+-*/".contains(token);
    }

    private static int precedence(String op) {
        if (op.equals("+") || op.equals("-")) return 1;
        if (op.equals("*") || op.equals("/")) return 2;
        return 0;
    }

    private static void applyOp(Stack<Double> values, String op) {
        if (values.size() < 2)
            throw new IllegalArgumentException("Insufficient operands for " + op);
        double b = values.pop();
        double a = values.pop();
        switch (op) {
            case "+": values.push(a + b); break;
            case "-": values.push(a - b); break;
            case "*": values.push(a * b); break;
            case "/":
                if (b == 0)
                    throw new ArithmeticException("Division by zero");
                values.push(a / b);
                break;
        }
    }
}