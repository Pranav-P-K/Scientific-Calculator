import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculator implements ActionListener {

    static JFrame calcFrame = new JFrame("Calculator");
    static JPanel calcPanel = new JPanel();
    static JPanel historyPanel = new JPanel();
    static JTextField textField = new JTextField();
    static JButton[] numberButtons = new JButton[10];
    
    static JButton memoryButton,piButton,epowerxButton,clrButton,delButton,sinButton,cosButton,asinButton,acosButton,
    absButton,onebyxButton,brac1Button,brac2Button,factorialButton,divideButton,powerButton,rootButton,multiplyButton,
    minusButton,logButton,plusButton,lnButton,negativeButton,dotButton,equalsButton;

    static double num1, num2;
    static String operation;
    static String second;

    public static void main(String[] args) {

        calcFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        calcFrame.setSize(515, 700);
        calcFrame.getContentPane().setBackground(Color.BLACK);

        textField.setBounds(50 ,50 , 400, 100);
        textField.setEditable(false);

        for (int i=0;i<10;i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].addActionListener(new Calculator());
        }

        memoryButton = new JButton("Mem");
        memoryButton.addActionListener(new Calculator());

        piButton = new JButton("π");
        piButton.addActionListener(new Calculator());

        epowerxButton = new JButton("e^x");
        epowerxButton.addActionListener(new Calculator());

        clrButton = new JButton("CE");
        clrButton.addActionListener(new Calculator());

        delButton = new JButton("⌫");
        delButton.addActionListener(new Calculator());
        
        sinButton = new JButton("sin");
        sinButton.addActionListener(new Calculator());

        cosButton = new JButton("cos");
        cosButton.addActionListener(new Calculator());
        
        asinButton = new JButton("asin");
        asinButton.addActionListener(new Calculator());

        acosButton = new JButton("acos");
        acosButton.addActionListener(new Calculator());

        absButton = new JButton("|x|");
        absButton.addActionListener(new Calculator());  

        onebyxButton = new JButton("1/x");
        onebyxButton.addActionListener(new Calculator());

        brac1Button = new JButton("(");
        brac1Button.addActionListener(new Calculator());

        brac2Button = new JButton(")");
        brac2Button.addActionListener(new Calculator());

        factorialButton = new JButton("!");
        factorialButton.addActionListener(new Calculator());

        divideButton = new JButton("÷");
        divideButton.addActionListener(new Calculator());

        powerButton = new JButton("x^y");
        powerButton.addActionListener(new Calculator());

        multiplyButton = new JButton("x");
        multiplyButton.addActionListener(new Calculator());

        rootButton = new JButton("y√x");
        rootButton.addActionListener(new Calculator());

        minusButton = new JButton("-");
        minusButton.addActionListener(new Calculator());

        logButton = new JButton("alogb");
        logButton.addActionListener(new Calculator());

        plusButton = new JButton("+");
        plusButton.addActionListener(new Calculator());

        lnButton = new JButton("ln");
        lnButton.addActionListener(new Calculator());

        negativeButton = new JButton("+/-");
        negativeButton.addActionListener(new Calculator());

        dotButton = new JButton(".");
        dotButton.addActionListener(new Calculator());

        equalsButton = new JButton("=");
        equalsButton.addActionListener(new Calculator());

        calcPanel.setBounds(50, 200, 400, 400);
        calcPanel.setLayout(new GridLayout(7, 5, 2, 2));
        calcPanel.setBackground(Color.BLACK);

        historyPanel.setBounds(500, 50, 300, 550);
        historyPanel.setBackground(Color.ORANGE);

        calcPanel.add(memoryButton);
        calcPanel.add(piButton);
        calcPanel.add(epowerxButton);
        calcPanel.add(clrButton);
        calcPanel.add(delButton);

        calcPanel.add(sinButton);
        calcPanel.add(cosButton);
        calcPanel.add(asinButton);
        calcPanel.add(acosButton);
        calcPanel.add(absButton);

        calcPanel.add(onebyxButton);
        calcPanel.add(brac1Button);
        calcPanel.add(brac2Button);
        calcPanel.add(factorialButton);
        calcPanel.add(divideButton);

        calcPanel.add(powerButton);
        calcPanel.add(numberButtons[7]);
        calcPanel.add(numberButtons[8]);
        calcPanel.add(numberButtons[9]);
        calcPanel.add(multiplyButton);

        calcPanel.add(rootButton);
        calcPanel.add(numberButtons[4]);
        calcPanel.add(numberButtons[5]);
        calcPanel.add(numberButtons[6]);
        calcPanel.add(minusButton);

        calcPanel.add(logButton);
        calcPanel.add(numberButtons[1]);
        calcPanel.add(numberButtons[2]);
        calcPanel.add(numberButtons[3]);
        calcPanel.add(plusButton);

        calcPanel.add(lnButton);
        calcPanel.add(negativeButton);
        calcPanel.add(numberButtons[0]);
        calcPanel.add(dotButton);
        calcPanel.add(equalsButton);

        calcFrame.add(textField);
        calcFrame.add(calcPanel);
        calcFrame.add(historyPanel);
        historyPanel.setVisible(false);
        calcFrame.setLayout(null);
        calcFrame.setVisible(true);

    }

    private void evaluateExpression() {
        String expression = textField.getText();
        try {
            double result = evaluate(expression);
            textField.setText(Double.toString(result));
        } catch (ArithmeticException ex) {
            textField.setText("Error");
        }
    }

    public double evaluate(String expression) {
        return (double) new Object() {
            int pos = -1, ch;
    
            void nextChar() {
                ch = (++pos < expression.length()) ? expression.charAt(pos) : -1;
            }
    
            boolean isDigit(char c) {
                return Character.isDigit(c);
            }
    
            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < expression.length()) throw new RuntimeException("Unexpected: " + (char) ch);
                return x;
            }
    
            double parseExpression() {
                double x = parseTerm();
                for (; ; ) {
                    if (eat('+')) x += parseTerm();
                    else if (eat('-')) x -= parseTerm();
                    else return x;
                }
            }
    
            double parseTerm() {
                double x = parseFactor();
                for (; ; ) {
                    if (eat('*')) x *= parseFactor();
                    else if (eat('/')) x /= parseFactor();
                    else return x;
                }
            }
    
            double parseFactor() {
                if (eat('+')) return parseFactor();
                if (eat('-')) return -parseFactor();
    
                double x;
                int startPos = this.pos;
                if (eat('(')) {
                    x = parseExpression();
                    eat(')');
                } else if (isDigit((char) ch) || ch == '.') {
                    while (isDigit((char) ch)) nextChar();
                    x = Double.parseDouble(expression.substring(startPos, this.pos));
                } else {
                    throw new RuntimeException("Unexpected: " + (char) ch);
                }
    
                if (eat('^')) x = Math.pow(x, parseFactor());
    
                return x;
            }
    
            boolean eat(int charToEat) {
                while (Character.isWhitespace(ch)) nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }
        }.parse();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == memoryButton) {
            historyPanel.setVisible(true);
            calcFrame.setSize(850,700);
        } else if (e.getSource() == piButton) {
            textField.setText(Double.toString(Math.PI));
        } else if (e.getSource() == epowerxButton) {
            num1 = Double.parseDouble(textField.getText());
            textField.setText(Double.toString(Math.exp(num1)));
        } else if (e.getSource() == clrButton) {
            textField.setText("");
        } else if (e.getSource() == delButton) {
            String currentText = textField.getText();
            String delText = currentText.substring(0,currentText.length()-1);
            textField.setText(delText);
        } else if (e.getSource() == sinButton) {
            num1 = Double.parseDouble(textField.getText());
            textField.setText(Double.toString((double) (Math.sin(Math.toRadians(num1)))));
        } else if (e.getSource() == cosButton) {
            num1 = Double.parseDouble(textField.getText());
            textField.setText(Double.toString((double) (Math.cos(Math.toRadians(num1)))));
        } else if (e.getSource() == asinButton) {
            num1 = Double.parseDouble(textField.getText());
            textField.setText(Double.toString((double) (Math.asin(Math.toRadians(num1)))));
        } else if (e.getSource() == acosButton) {
            num1 = Double.parseDouble(textField.getText());
            textField.setText(Double.toString((double) (Math.acos(Math.toRadians(num1)))));
        } else if (e.getSource() == absButton) {
            num1 = Double.parseDouble(textField.getText());
            textField.setText(Double.toString(Math.abs(num1)));
        } else if (e.getSource() == onebyxButton) {
            num1 = Double.parseDouble(textField.getText());
            textField.setText(Double.toString(1/num1));
        } else if (e.getSource() == brac1Button) {
            String currentText = textField.getText();
            String brac1Text = "(" + currentText.substring(0,currentText.length());
            textField.setText(brac1Text);
        } else if (e.getSource() == brac2Button){
            String currentText = textField.getText();
            String brac2Text = currentText.substring(0,currentText.length()) + ")";
            textField.setText(brac2Text);
        } else if (e.getSource() == factorialButton) {
            ;
        } else if (e.getSource() == divideButton) {
            String currentText = textField.getText();
            String divText = currentText.substring(0,currentText.length()) + "/";
            textField.setText(divText);
        } else if (e.getSource() == powerButton) {
            ;
        } else if (e.getSource() == multiplyButton) {
            String currentText = textField.getText();
            String prodText = currentText.substring(0,currentText.length()) + "*";
            textField.setText(prodText);
        } else if (e.getSource() == rootButton) {
            ;
        } else if (e.getSource() == minusButton) {
            String currentText = textField.getText();
            String diffText = currentText.substring(0,currentText.length()) + "-";
            textField.setText(diffText);
        } else if (e.getSource() == logButton) {
            ;
        } else if (e.getSource() == plusButton) {
            String currentText = textField.getText();
            String sumText = currentText.substring(0,currentText.length()) + "+";
            textField.setText(sumText);
        } else if (e.getSource() == lnButton) {
            ;
        } else if (e.getSource() == equalsButton) {
            evaluateExpression();
        } else {
            for (int i = 0; i < 10; i++) {
                if (e.getSource() == numberButtons[i]) {
                    String currentText = textField.getText();
                    textField.setText(currentText + i);
                    num1 = Double.parseDouble(textField.getText());
                }
            }
        }
     
    }
}