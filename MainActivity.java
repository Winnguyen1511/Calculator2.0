package com.example.calculator2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TabHost;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Stack;

import java.util.Vector;

public class MainActivity extends AppCompatActivity {
    //Controls of tab1
    String resultBuffer;
    TextView txtResult;
    Button btnC, btnDel;
    Button btn0,btn1,btn2,btn3, btn4,btn5,btn6,btn7,btn8,btn9, btnDot;
    Button btnMod, btnDiv, btnMulti, btnMinus, btnPlus, btnOpen, btnClose;

    //Controls of tab2
    TextView txtDate1, txtDate2, txtDiff;
    ImageButton btnCalendar1, btnCalendar2;

    Calendar calendar1 = Calendar.getInstance();
    Calendar calendar2 = Calendar.getInstance();
    SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();
        resultBuffer = txtResult.getText().toString();
        txtDiff.setText("Same dates");
    }

    private void addEvents() {

    }

    //
    //! Create a tabHost and tabs for the layout
    //
    //! Add all button, text (control) to MainActivity.
    //! \param void
    //! \Add result text->control buttons->input buttons->operator buttons
    //
    //**********************************************************************************************
    private void addControls()
    {
        //!Create tabHost and tabs
        TabHost tabHost = findViewById(R.id.tabHost);
        tabHost.setup();
        TabHost.TabSpec tab1 = tabHost.newTabSpec("t1");
//        tab1.setIndicator("1. Standard");
        tab1.setIndicator("",getResources().getDrawable(R.drawable.standard));
        tab1.setContent(R.id.tab1);
        tabHost.addTab(tab1);

        TabHost.TabSpec tab2 = tabHost.newTabSpec("t2");
//        tab2.setIndicator("2 .Date Calculator");
        tab2.setIndicator("",getResources().getDrawable(R.drawable.datecalculation));
        tab2.setContent(R.id.tab2);
        tabHost.addTab(tab2);

        //!Add all controls in tab1
        txtResult = findViewById(R.id.txtResult);
        btnC = findViewById(R.id.btnC);
        btnDel = findViewById(R.id.btnDel);
        btn0 = findViewById(R.id.btn0);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);
        btnDot = findViewById(R.id.btnDot);

        btnMod = findViewById(R.id.btnMod);
        btnDiv = findViewById(R.id.btnDiv);
        btnMulti= findViewById(R.id.btnMulti);
        btnMinus = findViewById(R.id.btnMinus);
        btnPlus = findViewById(R.id.btnPlus);
        btnOpen = findViewById(R.id.btnOpen);
        btnClose = findViewById(R.id.btnClose);

        //!Add all controls in tab2
        txtDate1 = findViewById(R.id.txtDate1);
        txtDate2 = findViewById(R.id.txtDate2);

        btnCalendar1 = findViewById(R.id.btnCalendar1);
        btnCalendar2 = findViewById(R.id.btnCalendar2);

        txtDate1.setText(sdf1.format(calendar1.getTime()));
        txtDate2.setText(sdf2.format(calendar2.getTime()));
        txtDiff = findViewById(R.id.txtDiff);


    }
    //*************************************************************************************************
    //
    //! Calculate the days between 2 date
    //! \param Calendar
    //! \return number of days
    //
    //*************************************************************************************************

    private long calDiff(Calendar fromDate, Calendar toDate)
    {


        long diff = toDate.getTimeInMillis() - fromDate.getTimeInMillis();
        diff = diff / (24 * 60 * 60 * 1000);
        return diff;
    }
    private String diffToString(long diff)
    {
        if(diff == 0)
        {
            return  "Same dates";
        }
        else if(diff > 0)
        {
            return Long.toString(diff)+" days";
        }
        else
        {
            return "back "+Long.toString(diff*(-1))+" days";
        }
    }
    //**************************************************************************************************
    //
    //! Handling when user click on calendar button
    //! \param View v from tab2
    //!
    //
    //**************************************************************************************************
    public void calendarEvent(View v)
    {
        if(v.getId() == btnCalendar1.getId()) {
            DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    calendar1.set(Calendar.YEAR, i);
                    calendar1.set(Calendar.MONTH, i1);
                    calendar1.set(Calendar.DAY_OF_MONTH, i2);
                    txtDate1.setText(sdf1.format(calendar1.getTime()));
                    txtDiff.setText(diffToString(calDiff(calendar1,calendar2)));
                }
            };
            DatePickerDialog datePickerDialog1 = new DatePickerDialog(
                    MainActivity.this,
                    callback,
                    calendar1.get(Calendar.YEAR),
                    calendar1.get(Calendar.MONTH),
                    calendar1.get(Calendar.DAY_OF_MONTH));
            datePickerDialog1.show();
        }
        else{
            DatePickerDialog.OnDateSetListener callback = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    calendar2.set(Calendar.YEAR, i);
                    calendar2.set(Calendar.MONTH, i1);
                    calendar2.set(Calendar.DAY_OF_MONTH, i2);
                    txtDate2.setText(sdf2.format(calendar2.getTime()));
                    txtDiff.setText(diffToString(calDiff(calendar1,calendar2)));
                }
            };
            DatePickerDialog datePickerDialog2 = new DatePickerDialog(MainActivity.this,
                    callback,
                    calendar2.get(Calendar.YEAR),
                    calendar2.get(Calendar.MONTH),
                    calendar2.get(Calendar.DAY_OF_MONTH));
            datePickerDialog2.show();
        }

    }


    //**************************************************************************************************
    //
    //! Handling input events
    //! \param View v from tab1
    //! Simply concatenate input text (0,1,2,3,...) to resultBuffer
    //! After concatenation, setText for resultTxt.
    //
    //**********************************************************************************************
    public void inputEvent(View v)
    {
        String tmp = "";
        if(v.getId() == btn0.getId())
        {
            tmp = "0";
        }
        else if(v.getId() == btn1.getId())
        {
            tmp = "1";
        }
        else if(v.getId() == btn2.getId())
        {
            tmp = new String("2");
        }
        else if(v.getId() == btn3.getId())
        {
            tmp = new String("3");
        }
        else if(v.getId() == btn4.getId())
        {
            tmp = new String("4");
        }
        else if(v.getId() == btn5.getId())
        {
            tmp = new String("5");
        }
        else if(v.getId() == btn6.getId())
        {
            tmp = new String("6");
        }
        else if(v.getId() == btn7.getId())
        {
            tmp = new String("7");
        }
        else if(v.getId() == btn8.getId())
        {
            tmp = new String("8");
        }
        else if(v.getId() == btn9.getId())
        {
            tmp = new String("9");
        }
        else if(v.getId() == btnDot.getId())
        {
            tmp = new String(".");
        }
        else
        {

        }
        if(resultBuffer.equals("0"))
            resultBuffer = tmp;
        else {
            resultBuffer = resultBuffer + tmp;
        }
        txtResult.setText(resultBuffer);

    }


    //**************************************************************************************************
    //
    //! Handling the control events
    //! \param View v from activity_main.xml
    //! if C button is pressed, set the resultBuffer to "0", this will clear the
    //  result screen
    //! If Del button is pressed, delete the last char in resultBuffer.
    // this will delete the result screen 1 character, until it become '0' character
    //
    //**********************************************************************************************
    public void controlEvent(View v)
    {
        if(v.getId() == btnC.getId())
        {
            resultBuffer = "0";

        }
        else if(v.getId() == btnDel.getId())
        {
            if(!resultBuffer.equals("0"))
            {
                if(resultBuffer.length() != 1)
                {
                    resultBuffer = resultBuffer.substring(0, resultBuffer.length()-1);
                }
                else
                {
                    resultBuffer = "0";
                }
            }
        }
        else
        {

        }
        txtResult.setText(resultBuffer);
    }

    //**********************************************************************************************
    //
    //! Handling operator events
    //! \param View v from activity_main.xml
    // Simply concatenate operation text to resultBuffer
    // this will concatenate to the screen an operator +, -, /, ....
    //
    //**********************************************************************************************
    public void operatorEvent(View v)
    {
        if (resultBuffer.equals("0")&& v.getId() != btnMinus.getId() && v.getId() != btnOpen.getId()) return;
//        if (v.getId() != btnMinus.getId() && isOperator( c: resultBuffer.charAt(resultBuffer.length())))
//        {
//            resultBuffer = resultBuffer.substring(0,resultBuffer.length() -1 );
////            resultBuffer +=
//        }
        if(v.getId() == btnPlus.getId())
        {
            resultBuffer += "+";
        }
        else if(v.getId() == btnMinus.getId())
        {
            if(resultBuffer.equals("0"))
                resultBuffer = "-";
            else
                resultBuffer += "-";
        }
        else if(v.getId() == btnMulti.getId())
        {
            resultBuffer += "*";
        }
        else if(v.getId() == btnDiv.getId())
        {
            resultBuffer += "/";
        }
        else if(v.getId() == btnMod.getId())
        {
            resultBuffer += "%";
        }
        else if(v.getId() == btnOpen.getId())
        {
            if(resultBuffer.equals("0"))
                resultBuffer = "(";
            else
                resultBuffer += "(";
        }
        else if(v.getId() == btnClose.getId())
        {
            resultBuffer += ")";
        }
        else
        {

        }
        txtResult.setText(resultBuffer);
    }
//**************************************************************************************************
//
    //! element sub class, for handling input data
    //! all input text will be convert to this element
    //! the element can be either operator or double number abstractly
    // via the type
    //! the value is saved as double_val or char_val
    //! Example: '+' and '(' or '-' are operators,
    // these can be change to <element type=OPERATOR char_val='+' double_val=0>
    //! Example: '0.5' and '2' and 4.9' are double value
    // these can be change to <element type=NUMBER char_val='\0' double_val=0.5>

    //! \field: type, char_val, double_val
    //! \method: getType, getChar_val, getDouble_val
    //
    //**********************************************************************************************
    private class element{
        static final boolean OPERATOR = false;
        static final boolean NUMBER = true;
        private boolean type;
        private char    char_val;
        private double  double_val;
        public element(boolean type, char c , double d)
        {
            this.type = type;
            this.char_val = c;
            this.double_val = d;
        }
        public boolean getType()
        {
            return this.type;
        }
        public char getChar_val()
        {
            return this.char_val;
        }
        public double getDouble_val()
        {
            return this.double_val;
        }
    }

    //**************************************************************************************************
//
    //! To determine the precedence of operator
    //! \param char ch
    //! \return 1 for plus and minus because this have lowest priority
    //! \return 2 for multiplication and division and mod
    //! \return 3 for power
    //! \return -1 if ch is not an operator
    //
    //**********************************************************************************************
    static int Prec(char ch)
    {
        switch (ch)
        {
            case '+':
            case '-':
                return 1;

            case '*':
            case '/':
            case '%':
                return 2;

            case '^':
                return 3;
        }
        return -1;
    }
    //**************************************************************************************************
    //
    //! The main method that converts given infix expression
    // to postfix expression.
    //
    //**********************************************************************************************
    static Vector<element> infixToPostfix(Vector<element> v)
    {
        Vector<element> result = new Vector<>();
        Stack<element> stack = new Stack<>();
        for(int i = 0; i < v.size(); ++i)
        {
            element tmp = v.elementAt(i);
            if(tmp.getType() == element.NUMBER)
            {
                result.add(tmp);
            }
            else
            {
                if(tmp.getChar_val() == '(')
                {
                    stack.push(tmp);
                }
                else if(tmp.getChar_val() == ')')
                {
                    while (!stack.isEmpty() && stack.peek().getChar_val() != '(')
                        result.add( stack.pop());

                    if (!stack.isEmpty() && stack.peek().getChar_val() != '(')
                        return null; // invalid expression
                    else
                        stack.pop();
                }
                else
                {
                    while (!stack.isEmpty() && Prec(tmp.getChar_val()) <= Prec(stack.peek().getChar_val())){
                        if(stack.peek().getChar_val() == '(')
                            return null;
                        result.add(stack.pop());
                    }
                    stack.push(tmp);
                }

            }
        }
        while(!stack.isEmpty())
        {
            if(stack.peek().type == element.OPERATOR)
            {
                if(stack.peek().char_val == '(') return null;
            }
            result.add(stack.pop());
        }
        return result;
    }
    //**************************************************************************************************
//
    //! evaluate the postfix expression
    //! \param postfix expression in Vector data structure
    //! \return double value of result
    //
    //**********************************************************************************************
    static double evaluatePostfix(Vector<element> v)
    {
        Stack<Double> stack = new Stack<>();
        for(int i = 0; i < v.size(); i++)
        {
            element tmp = v.elementAt(i);
            if(tmp.getType() == element.NUMBER)
            {
                stack.push(tmp.getDouble_val());
            }
            else
            {
                double val1,val2;
                val1 = stack.pop();
                val2 = stack.pop();

                switch (tmp.getChar_val())
                {
                    case '+':
                        stack.push(val2+val1);
                        break;

                    case '-':
                        stack.push(val2- val1);
                        break;

                    case '/':
                        stack.push(val2/val1);
                        break;
                    case '%':
                        stack.push(val2%val1);
                        break;
                    case '*':
                        stack.push(val2*val1);
                        break;
                }
            }
        }
        return stack.pop();
    }
    //**************************************************************************************************
//
    //! Check if a char is an operator or not
    //! \param char c
    //! \return true if c is an operator
    //! \return false if c is not
    boolean isOperator(char c)
    {
        if( c == '+' || c == '-'
                ||c == '*' || c == '/' || c == '%'
                || c == '(' || c == ')')
        {
            return true;
        }
        return false;
    }

    //**************************************************************************************************
//
    //! find the index of the last operation in the input string
    //! This is a subfunction of the convertion from resultBuffer(string) to
    // infixVec(Vector of element).
    //
    //**********************************************************************************************
    public int lastIndexOfOperator(String v)
    {
        int res = 0;
        int buff[] = new int[7];
        buff[0] = v.lastIndexOf("+");
        buff[1] = v.lastIndexOf("-");
        buff[2] = v.lastIndexOf("*");
        buff[3] = v.lastIndexOf("/");
        buff[4] = v.lastIndexOf("%");
        buff[5] = v.lastIndexOf("(");
        buff[6] = v.lastIndexOf(")");

        res = buff[0];
        for(int i = 1; i < 7; i++)
        {
            if(res < buff[i])
                res = buff[i];
        }

        return res;
    }

    //**************************************************************************************************
//
    //! Handling when user press the '=' button
    //! \param View v from the activity_main.xml
    //! First this change the resultBuffer(string) to infixVec(Vector of element)
    //! Second this change infixVec to postfixVec, which contains the postfix expression of the
    // input string from the screen
    //! If the string is not good (have '(' at the end
    // output "Syntax Error"
    //! Third calculate the postfix expression
    //! Final print back to screen
    //
    //**********************************************************************************************
    public void resultEvent(View v)
    {
        char[] buffer = resultBuffer.toCharArray();
        Vector<element> infixVec = new Vector<>();
        Vector<element> postfixVec;
        boolean addZero = false;
        int lastIndexOfOp = lastIndexOfOperator(resultBuffer);
        for(int i = 0; i <= lastIndexOfOp ; i++)
        {
            if(isOperator(buffer[i]))
            {
                if(i >0)
                {
                    if (infixVec.lastElement().type != element.NUMBER && buffer[i] == '-')
                    {
                        infixVec.add((new element(element.OPERATOR, '(', 0)));
                        infixVec.add((new element(element.NUMBER, '\0', 0)));

                        addZero = true;
//                        infixVec.add((new element(element.OPERATOR, ')', 0)));
                    }
                }
                else {
                    infixVec.add((new element(element.NUMBER, '\0', 0)));
//                    infixVec.add( new element(element.OPERATOR, buffer[i], 0));
                }
                infixVec.add( new element(element.OPERATOR, buffer[i], 0));
            }
            else
            {
                int j;
                for(j = i; (!isOperator(buffer[j]))  ; j++);
                infixVec.add(new element(element.NUMBER, '\0', Double.valueOf(resultBuffer.substring(i,j))));
                if(addZero == true) {
                    infixVec.add((new element(element.OPERATOR, ')', 0)));
                    addZero = false;
                }
                i = j - 1;
            }
        }
        if(lastIndexOfOp < resultBuffer.length()-1)
        {
            infixVec.add(new element(element.NUMBER, '\0', Double.valueOf(resultBuffer.substring(lastIndexOfOp+1,resultBuffer.length()))));
        }
        if(addZero == true) {
            infixVec.add((new element(element.OPERATOR, ')', 0)));
            addZero = false;
        }
        postfixVec = infixToPostfix(infixVec);
        if(postfixVec == null)
        {
            txtResult.setText("Syntax Error");
            return;
        }

        double res = evaluatePostfix(postfixVec);
        resultBuffer = Double.toString(res);
        txtResult.setText(resultBuffer);
    }
}
