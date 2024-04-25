import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculator implements ActionListener {

    static JFrame calcFrame = new JFrame("Calculator");
    static JPanel calcPanel = new JPanel();
    static JPanel historyPanel = new JPanel();
    static JTextField textField = new JTextField();
    static JTextField ansField = new JTextField();
    static JButton[] numberButtons = new JButton[10];
    
    static JButton memoryButton,piButton,expButton,clrButton,delButton,sinButton,cosButton,asinButton,acosButton,
    absButton,onebyxButton,brac1Button,brac2Button,factorialButton,divideButton,powerButton,rootButton,multiplyButton,
    minusButton,logButton,plusButton,lnButton,negativeButton,dotButton,equalsButton;

    static double num1, num2;
    

    public static void main(String[] args) {

        calcFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        calcFrame.setSize(415, 540);
        calcFrame.getContentPane().setBackground(Color.BLACK);

        ansField.setBounds(0,50,400,50);
        ansField.setEditable(false);
        textField.setBounds(0 ,0 , 400, 50);
        textField.setEditable(false);

        for (int i=0;i<10;i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].addActionListener(new Calculator());
        }

        memoryButton = new JButton("Mem");
        memoryButton.addActionListener(new Calculator());

        piButton = new JButton("π");
        piButton.addActionListener(new Calculator());

        expButton = new JButton("e");
        expButton.addActionListener(new Calculator());

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

        powerButton = new JButton("x^2");
        powerButton.addActionListener(new Calculator());

        multiplyButton = new JButton("x");
        multiplyButton.addActionListener(new Calculator());

        rootButton = new JButton("2√x");
        rootButton.addActionListener(new Calculator());

        minusButton = new JButton("-");
        minusButton.addActionListener(new Calculator());

        logButton = new JButton("log10");
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

        calcPanel.setBounds(0, 100, 400, 400);
        calcPanel.setLayout(new GridLayout(7, 5, 2, 2));
        calcPanel.setBackground(Color.BLACK);

        historyPanel.setBounds(400, 0, 300, 500);
        historyPanel.setBackground(Color.ORANGE);

        calcPanel.add(memoryButton);
        calcPanel.add(piButton);
        calcPanel.add(expButton);
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

        calcFrame.add(ansField);
        calcFrame.add(textField);
        calcFrame.add(calcPanel);
        calcFrame.add(historyPanel);
        //historyPanel.setVisible(false);
        calcFrame.setLayout(null);
        calcFrame.setVisible(true);

    }

    public void evaluateExpression() {
        String expression = textField.getText();
        try {
            double result = evaluate(expression);
            ansField.setText(Double.toString(result));
        } catch (ArithmeticException ex) {
            ansField.setText("Error");
        }
    }

    public double evaluate(String expression) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < expression.length()) ? expression.charAt(pos) : -1;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < expression.length()) {
                    throw new RuntimeException("Unexpected: " + (char) ch);
                }
                return x;
            }

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if (eat('+')) x += parseTerm();
                    else if (eat('-')) x -= parseTerm();
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
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
                } else if (Character.isDigit((char) ch)) {
                    while (Character.isDigit((char) ch)) {
                        nextChar();
                    }
                    if (ch == '.') {
                        nextChar();
                        while (Character.isDigit((char) ch)) {
                            nextChar();
                        }
                    }
                    x = Double.parseDouble(expression.substring(startPos, this.pos));
                } else {
                    throw new RuntimeException("Unexpected: " + (char) ch);
                }
            
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
    
    public String setScreen() {
        num1 = Double.parseDouble(ansField.getText());
        String currentText = textField.getText();
        String ansText = ansField.getText();
        currentText = currentText.substring(0,currentText.length()-ansText.length());
        return currentText;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == memoryButton) {
            ;
            
        } 
        
        else if (e.getSource() == piButton) {
            textField.setText(textField.getText() + Double.toString(3.14));
            ansField.setText(Double.toString(3.14));
            
        } 
        
        else if (e.getSource() == expButton) {
            textField.setText(textField.getText() + Double.toString(2.72));
            ansField.setText(Double.toString(2.72));
        } 
        
        else if (e.getSource() == clrButton) {
            textField.setText("");
            ansField.setText("");
        } 
        
        else if (e.getSource() == delButton) {
            String currentText = textField.getText();
            if (currentText.length() > 0) {
                String delText = currentText.substring(0,currentText.length()-1);
                textField.setText(delText);
                ansField.setText(delText);
            }
        } 
        
        else if (e.getSource() == sinButton) {
            String cText = setScreen();
            textField.setText(cText + Double.toString((double) (Math.sin(Math.toRadians(num1)))));
        } 
        
        else if (e.getSource() == cosButton) {
            String cText = setScreen();
            textField.setText(cText + Double.toString((double) (Math.cos(Math.toRadians(num1)))));
        } 
        
        else if (e.getSource() == asinButton) {
            String cText = setScreen();
            textField.setText(cText + Double.toString((double) (Math.asin(Math.toRadians(num1)))));
        } 
        
        else if (e.getSource() == acosButton) {
            String cText = setScreen();
            textField.setText(cText + Double.toString((double) (Math.acos(Math.toRadians(num1)))));
        } 
        
        else if (e.getSource() == absButton) {
            String cText = setScreen();
            textField.setText(cText + Double.toString((double) (Math.abs(num1))));
        } 
        
        else if (e.getSource() == onebyxButton) {
            String cText = setScreen();
            textField.setText(cText + Double.toString((double) (1/num1)));
        } 
        
        else if (e.getSource() == brac1Button) {
            String currentText = textField.getText();
            String brac1Text =  currentText.substring(0,currentText.length()) + "(";
            textField.setText(brac1Text);
        } 
        
        else if (e.getSource() == brac2Button){
            String currentText = textField.getText();
            String brac2Text = currentText.substring(0,currentText.length()) + ")";
            textField.setText(brac2Text);
        } 
        
        else if (e.getSource() == factorialButton) {
            //String cText = setScreen();
            //textField.setText(cText + Double.toString((double) (Math.abs(num1))));
        } 
        
        else if (e.getSource() == divideButton) {
            String currentText = textField.getText();
            textField.setText(currentText+"/");
            ansField.setText("");
        } 
        
        else if (e.getSource() == powerButton) {
            String cText = setScreen();
            textField.setText(cText + Double.toString((double) (num1*num1)));
        } 
        
        else if (e.getSource() == multiplyButton) {
            String currentText = textField.getText();
            textField.setText(currentText+"*");
            ansField.setText("");
        } 
        
        else if (e.getSource() == rootButton) {
            String cText = setScreen();
            textField.setText(cText + Double.toString((double) (Math.sqrt(num1))));
        } 
        
        else if (e.getSource() == minusButton) {
            String currentText = textField.getText();
            textField.setText(currentText+"-");
            ansField.setText("");
        } 
        
        else if (e.getSource() == logButton) {
            String cText = setScreen();
            textField.setText(cText + Double.toString((double) (Math.log10(num1))));
        } 
        
        else if (e.getSource() == plusButton) {
            String currentText = textField.getText();
            textField.setText(currentText+"+");
            ansField.setText("");
        } 
        
        else if (e.getSource() == lnButton) {
            String cText = setScreen();
            textField.setText(cText + Double.toString((double) (Math.log(num1))));
        }

        else if (e.getSource() == negativeButton) {
            ;
        }

        else if (e.getSource() == dotButton) {
            String currentText = textField.getText();
            textField.setText(currentText+".");
            ansField.setText(currentText+".");
        }
        
        else if (e.getSource() == equalsButton) {
            evaluateExpression();
        } 
        
        else {
            for (int i = 0; i < 10; i++) {
                if (e.getSource() == numberButtons[i]) {
                    String currentText = textField.getText();
                    String ansText = ansField.getText();
                    textField.setText(currentText + i);
                    ansField.setText(ansText + i);
                    
                    
                }
            }
        }
    }
}
