package com.chiapin.calculator;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.support.test.uiautomator.UiAutomatorTestCase;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import java.util.Random;

public class CalculatorTest extends UiAutomatorTestCase{

    public void testBasicFunction() throws UiObjectNotFoundException{
        backtoHome();
        // launch calculator app via all apps
        if (!launchCalculatorApp()) {
            getScreenShot("LaunchCalculatorAppFailed");
        }
    }

    public void testFunctionality() throws UiObjectNotFoundException{
        System.out.println(" *** Functionality Test Cases START ***");

        backtoHome();
        // launch calculator app via all apps
        if (!launchCalculatorApp()) {
            getScreenShot("LaunchCalculatorAppFailed");
        }

        // Check the addition of two integer numbers.
        calculatorTest((int)(Math.random()*9999),(int)(Math.random()*9999),"+");
        //Check the addition of one positive and one negative number.
        calculatorTest(-(int)(Math.random()*9999),(int)(Math.random()*9999),"+");
        // Check the subtraction of two integer numbers.
        calculatorTest((int)(Math.random()*9999),(int)(Math.random()*9999),"-");
        //Check the subtraction of one negative and one positive number.
        calculatorTest(-(int)(Math.random()*9999),(int)(Math.random()*9999),"-");
        //Check the multiplication of two integer numbers.
        calculatorTest((int)(Math.random()*9999),(int)(Math.random()*9999),"*");
        //Check the multiplication of one negative and one positive number.
        calculatorTest(-(int)(Math.random()*9999),(int)(Math.random()*9999),"*");
        //Check the division of two integer numbers.
        calculatorTest((int)(Math.random()*9999),(int)(Math.random()*9999),"/");
        //Check the division of one positive number and one integer number.
        calculatorTest(-(int)(Math.random()*9999),(int)(Math.random()*9999),"/");
        //Check the division of a number by zero.
        calculatorTest((int)(Math.random()*9999),0,"/");
        calculatorTest(-(int)(Math.random()*9999),0,"/");
        //Check the division of zero by any number.
        calculatorTest(0,(int)(Math.random()*9999),"/");
        calculatorTest(0,-(int)(Math.random()*9999),"/");

        backtoHome();
        System.out.println(" *** Functionality Test Cases END ***");
    }

    public void testStress() throws UiObjectNotFoundException{
        System.out.println(" *** Stress Test Cases START ***");
        int testCycle = 9;
        backtoHome();
        // launch calculator app via all apps
        if (!launchCalculatorApp()) {
            getScreenShot("LaunchCalculatorAppFailed");
        }

        for (int index = 0; index < testCycle; index++) {
            calculatorTest();
        }

        backtoHome();
        System.out.println(" *** Stress Test Cases END ***");
    }

    public boolean calculatorTest() throws UiObjectNotFoundException{
        CalculatorButton tmpButton = new CalculatorButton();
        double Num1 = 0, Num2 = 0;
        String sNum1 = "",sNum2 = "";
        clearAllText();
        if (((int)(Math.random()*4)) ==0){
            Num1 =(double)(Math.random()*99999);
            Num2 =(double)(Math.random()*99999);
        }else if (((int)(Math.random()*4)) ==1){
            Num1 =(double)(Math.random()*99999);
            Num2 =(long)(Math.random()*99999);
        }else if (((int)(Math.random()*4)) ==2){
            Num1 =(long)(Math.random()*99999);
            Num2 =(double)(Math.random()*99999);
        }else{
            Num1 =(long)(Math.random()*99999);
            Num2 =(long)(Math.random()*99999);
        }
        if (((int)(Math.random()*6)+1)%2 ==0){
            Num1 = -Num1;
        }
        int ranOperator = (int)(Math.random()*4); // To generate the operator

        if (isIntegral(Num1)){sNum1 = String.valueOf((int)Num1);}else{sNum1 = String.valueOf(Num1);}
        if (isIntegral(Num2)){sNum2 = String.valueOf((int)Num2);}else{sNum2 = String.valueOf(Num2);}

        //System.out.println("Num1 = " + sNum1 + " " + tmpButton.ButtonOP[ranOperator][0] + " Num2 = " + sNum2);
        String calResult = calculateNumbyUI(sNum1, sNum2, tmpButton.ButtonOP[ranOperator][0]);
        double opResult = calculateResult(Num1, Num2, tmpButton.ButtonOP[ranOperator][0]);
        if (checkCalculatedResult(calResult, opResult)) {
            writeTestResult(Num1 + tmpButton.ButtonOP[ranOperator][0] + Num2, true);
            System.out.println("["+Num1+tmpButton.ButtonOP[ranOperator][0]+Num2+"] Test result : PASS ");
            return true;
        }else{
            writeTestResult(Num1+tmpButton.ButtonOP[ranOperator][0] + Num2, false);
            System.out.println("[" + Num1 + tmpButton.ButtonOP[ranOperator][0] + Num2 + "] Test result : FAIL ");
            getScreenShot("TestResultFailed");
            return false;
        }
    }

    public boolean calculatorTest(double b1, double b2) throws UiObjectNotFoundException{
        CalculatorButton tmpButton = new CalculatorButton();
        String sNum1 = "",sNum2 = "";
        boolean testResult = false;
        clearAllText();
        int ranOperator = (int)(Math.random()*4); // To generate the operator
        if (isIntegral(b1)){sNum1 = String.valueOf((int)b1);}else{sNum1 = String.valueOf(b1);}
        if (isIntegral(b2)){sNum2 = String.valueOf((int)b2);}else{sNum2 = String.valueOf(b2);}

        String calResult = calculateNumbyUI(sNum1, sNum2, tmpButton.ButtonOP[ranOperator][0]);

        if (sNum2.equals("0") && ranOperator == 3){
            testResult = (checkCalculatedResult(calResult, "Can't divide by 0"));
        }else {
            double opResult = calculateResult(b1, b2, tmpButton.ButtonOP[ranOperator][0]);
            testResult = (checkCalculatedResult(calResult, opResult));
        }

        //System.out.println("Num1 = " + sNum1 + " " + tmpButton.ButtonOP[ranOperator][0] + " Num2 = " + sNum2);
        writeTestResult(b1 + tmpButton.ButtonOP[ranOperator][0] + b2, testResult);
        System.out.println(b1 + tmpButton.ButtonOP[ranOperator][0] + b2+" | Test result : "+ testResult);
        return testResult;
    }

    public boolean calculatorTest(double b1, double b2, String opMethod) throws UiObjectNotFoundException{
        CalculatorButton tmpButton = new CalculatorButton();
        String sNum1 = "",sNum2 = "";
        boolean testResult = false;
        int opIndex = 0;
        clearAllText();
        if (opMethod.equals(tmpButton.ButtonOP[0][0])){opIndex = 0;}
        else if (opMethod.equals(tmpButton.ButtonOP[1][0])){opIndex = 1;}
        else if (opMethod.equals(tmpButton.ButtonOP[2][0])){opIndex = 2;}
        else if (opMethod.equals(tmpButton.ButtonOP[3][0])){opIndex = 3;}
        else {
            System.out.println("Wrong parameter with : "+opMethod);
            return false;
        }
        if (isIntegral(b1)){sNum1 = String.valueOf((int)b1);}else{sNum1 = String.valueOf(b1);}
        if (isIntegral(b2)){sNum2 = String.valueOf((int)b2);}else{sNum2 = String.valueOf(b2);}
        String calResult = calculateNumbyUI(sNum1, sNum2, tmpButton.ButtonOP[opIndex][0]);
        if (sNum2.equals("0") && opIndex == 3){
            testResult = (checkCalculatedResult(calResult, "Can't divide by 0"));
        }else {
            double opResult = calculateResult(b1, b2, tmpButton.ButtonOP[opIndex][0]);
            testResult = (checkCalculatedResult(calResult, opResult));
        }
        //System.out.println("Num1 = " + sNum1 + " " + tmpButton.ButtonOP[ranOperator][0] + " Num2 = " + sNum2);
        writeTestResult(b1 + tmpButton.ButtonOP[opIndex][0] + b2, testResult);
        System.out.println(b1 + tmpButton.ButtonOP[opIndex][0] + b2+" | Test result : "+ testResult);
        return testResult;
    }

    public String calculateNumbyUI(String Numb1,String Numb2,String Operator)throws UiObjectNotFoundException{
        CalculatorButton tmpButton = new CalculatorButton();
        for (int index=0; index<Numb1.length() ;index++){
            Character tmpch = new Character(Numb1.charAt(index));

            if (tmpch.equals('-')){       clickButton(tmpButton.ButtonSub);}
            else if (tmpch.equals('.')){   clickButton(tmpButton.ButtonDot);}
            else{clickButton(tmpButton.Button[((int)tmpch)-48]); }
        }

        if (Operator.equals("+")){clickButton(tmpButton.ButtonOP[0][1]); }
        else if (Operator.equals("-")){clickButton(tmpButton.ButtonOP[1][1]); }
        else if (Operator.equals("*")){clickButton(tmpButton.ButtonOP[2][1]); }
        else if (Operator.equals("/")){clickButton(tmpButton.ButtonOP[3][1]); }

        for (int index=0; index<Numb2.length() ;index++){
            Character tmpch = new Character(Numb2.charAt(index));
            //System.out.println("Num2[" + index + "]=" + tmpch);
            if (tmpch.equals('-')){       clickButton(tmpButton.ButtonSub);}
            else if (tmpch.equals('.')){   clickButton(tmpButton.ButtonDot);}
            else{clickButton(tmpButton.Button[((int)tmpch)-48]); }
        }

        clickButton(tmpButton.ButtonEq);
        String getResult = getResultFromUI();
        return getResult.replace('−','-');
    }

    public boolean checkCalculatedResult(String getResult, double opResult) throws UiObjectNotFoundException{
        boolean resultIsInt = true,testResult = false;
        if (!isIntegral(opResult)){ resultIsInt = false;}
        if (resultIsInt) {
            // double -> long -> string
            System.out.println("[checkCalculatedResult] ResultFromUI = [" + getResult + "] | opResult = [" + String.valueOf((new Double(opResult)).longValue()) + "]");
            if (0== getResult.compareTo(String.valueOf((new Double(opResult)).longValue()))){
                testResult = true;
            }
        }else {
            String strResult = String.valueOf(opResult);
            int tmpLength = strResult.length();
            if (!strResult.contains("E")) {
                if (tmpLength > 17) {
                    tmpLength = 17;
                }
                strResult = strResult.substring(0, tmpLength);
            }

            System.out.println("[checkCalculatedResult] ResultFromUI = [" + Double.parseDouble(getResult) + "] | Result = [" + Double.parseDouble(strResult) + "]");

            if (Double.parseDouble(getResult) == Double.parseDouble(strResult)){
                testResult = true;
            }
        }
        return testResult;
    }
    public boolean checkCalculatedResult(String getResult, String opResult) throws UiObjectNotFoundException{
        System.out.println("[checkCalculatedResult] ResultFromUI = [" + getResult + "] | Result = [" + opResult + "]");
        return getResult.equals(opResult);
    }

    public double calculateResult(double Numb1, double Numb2, String operatorMethod) throws UiObjectNotFoundException{
        boolean resultIsInt = true;
        double result =0;
        if (operatorMethod.equals("+")){       result =  Arith.add(Numb1,Numb2);}
        else if (operatorMethod.equals("-")){  result =  Arith.sub(Numb1,Numb2);}
        else if (operatorMethod.equals("*")){  result =  Arith.mul(Numb1,Numb2);}
        else if (operatorMethod.equals("/")){  result =  Arith.div(Numb1,Numb2,16);}

        System.out.println("[calculateResult]" +Numb1 + " " + operatorMethod + " " + Numb2 + " = " + result);
        return result;
    }

    // check if the input value is an integer
    // @ return true | false
    public static boolean isIntegral(double value) {
        return value % 1.0 == 0;
    }

    // get test resul from result field
    // @return   resultText
    public String getResultFromUI() throws UiObjectNotFoundException{
        UiObject resultTextView = new UiObject(new UiSelector().resourceId("com.android.calculator2:id/result"));
        String resultText = resultTextView.getText();
        //System.out.println("[getResult] resultText = " + resultText);
        return resultText;
    }

    // clear all text by lone press del button
    public void clearAllText() throws UiObjectNotFoundException{
        CalculatorButton tmpButton = new CalculatorButton();
        UiObject ButtonDel = new UiObject(new UiSelector().resourceId(tmpButton.ButtonDel));
        if (ButtonDel.exists()) {
            ButtonDel.longClick();
        }
        UiObject ButtonClr = new UiObject(new UiSelector().resourceId(tmpButton.ButtonClr));
        if (ButtonClr.exists()) {
            ButtonClr.click();
        }
    }
    // clear button by given resource id
    // @return : true | false
    public boolean clickButton(String targetButton) throws UiObjectNotFoundException{
        UiObject tmpButton = new UiObject(new UiSelector().resourceId(targetButton));
        return tmpButton.click();
    }

    // launch calculator app
    // @return   true | false
    public boolean launchCalculatorApp() throws UiObjectNotFoundException{
        CalculatorButton tmpButton = new CalculatorButton();
        getUiDevice().pressHome();
        UiObject allAppsButton = new UiObject(new UiSelector().description("Apps"));
        allAppsButton.clickAndWaitForNewWindow();

        UiScrollable appViews = new UiScrollable(new UiSelector().scrollable(true));

        UiObject CalculatorApp = appViews
                .getChildByText(new UiSelector()
                                .className(android.widget.TextView.class.getName()),
                        "Calculator");
        CalculatorApp.clickAndWaitForNewWindow();

        UiObject ButtonEq = new UiObject(new UiSelector().resourceId(tmpButton.ButtonEq));
        return ButtonEq.waitForExists(5000);
    }

    // Go back to Home screen
    public void backtoHome() throws UiObjectNotFoundException{
        String HomeActivity = "Home screen";
        String currentActivity = " ";
        int backCnt = 0;
        while (!currentActivity.contains(HomeActivity)){
            getUiDevice().pressBack();
            sleep(1000);

            currentActivity = getUiDevice().getCurrentActivityName();
            while (currentActivity == null){
                sleep(1000);
                currentActivity = getUiDevice().getCurrentActivityName();
            }
            backCnt++;
            if (backCnt == 10){
                getUiDevice().pressHome();
            }
        }
    }

    // write test result into /sdcard/TestCaseResult.txt
    // @param testCaseName    the name of test case
    // @param testCaseResult  the result of test case
    // @return                true | false
    public boolean writeTestResult(String conditionStr, boolean testCaseRsult){
        if(conditionStr.equals("") ||conditionStr.equals("")){
            System.out.println("[writeTestResult] input parameter are nil value");
            return false;
        }
        File file = new File("/sdcard/TestCaseResult.txt");
        try{
            BufferedWriter bufWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,true),"utf8"));
            bufWriter.write(conditionStr+" : "+String.valueOf(testCaseRsult)+"\n");
            bufWriter.close();
        }catch(IOException e){
            e.printStackTrace();
            System.out.println("[writeTestResult] Failed to write test result");
            return false;
        }
        return true;
    }

    // get current screen shot and WindowHierarchy
    // @param tmpStr      the file name of screneimage and WindowHierarchy
    // @return            true | false
    public boolean getScreenShot(String tmpStr){
        String filePath = "/sdcard/screenimage/";
        File fileStorageDir = new File(filePath);
        fileStorageDir.mkdir();

        // get current system time
        SimpleDateFormat systemTime = new SimpleDateFormat("MMddHHmmss");
        String currentTime = systemTime.format(new java.util.Date());
        tmpStr = filePath+tmpStr+"_"+currentTime;

        // take screenshot
        if(!getUiDevice().takeScreenshot(new File(tmpStr+".png"))) {
            System.out.println("[getScreenShot] False: Screenshot not taken!!");
            return false;
        }
        else{
            System.out.println("[getScreenShot]take screenshot path:"+tmpStr+".png");
        }

       // dump WindowHierarchy
        try {
            getUiDevice().dumpWindowHierarchy(new File(tmpStr + ".uix"));
            System.out.println("[getScreenShot]take WindowHierarch view path:" + tmpStr + ".uix");
        }
        catch(IOException ex) {
            System.out.println("[getScreenShot] Failed to dump WindowHierarch : "+ex.getMessage());
            return false;
        }
        return true;
    }
}
/*
public boolean BasicFunction() throws UiObjectNotFoundException{

    CalculatorButton tmpButton = new CalculatorButton();
    boolean resultIsInt = true,testResult = false;

    //clear all text
    clearAllText();

    String tmpStr1="",tmpStr2 = "";
    boolean isTapDot = false;

    int strlen1 = (int)(Math.random()*5)+1;  // To generate the length of number 1
    int strlen2 = (int)(Math.random()*5)+1;  // To generate the length of number 1
    int ranOperator = (int)(Math.random()*4); // To generate the operator

    if (((int)(Math.random()*5)+1)%2 ==0){
        tmpStr1 = "-";
        clickButton(tmpButton.ButtonSub);
    }

    // To generate random number 1
    for (int _index=1; _index<=strlen1; _index++){
        int tmpNum = 0;
        if (_index==1){
            tmpNum = (int)(Math.random()*8)+1;
        }
        else{
            tmpNum = (int)(Math.random()*9);
        }
        tmpStr1 = tmpStr1+Integer.toString(tmpNum);
        clickButton(tmpButton.Button[tmpNum]);

        if (!isTapDot){
            if (((int)(Math.random()*9)+1)%3 ==0 && (_index!=strlen1)){
                tmpStr1 = tmpStr1+".";
                clickButton(tmpButton.ButtonDot);
                isTapDot = true;
            }
        }
    }
    // click the operator button
    clickButton(tmpButton.ButtonOP[ranOperator][1]);

    // To generate random number 1
    isTapDot = false;
    for (int _index=1; _index<=strlen2; _index++){
        int tmpNum = 0;
        if (_index==1){
            tmpNum = (int)(Math.random()*8)+1;
        }
        else{
            tmpNum = (int)(Math.random()*9);
        }
        tmpStr2 = tmpStr2+Integer.toString(tmpNum);
        clickButton(tmpButton.Button[tmpNum]);
        if (!isTapDot){
            if (((int)(Math.random()*9)+1)%3 ==0 && (_index!=strlen2)){
                tmpStr2 = tmpStr2+".";
                clickButton(tmpButton.ButtonDot);
                isTapDot = true;
            }
        }
    }

    // click the equal button
    clickButton(tmpButton.ButtonEq);

    // transfer the string to double
    double tmpNum1 =0,tmpNum2 =0;
    try{
        tmpNum1 = Double.parseDouble(tmpStr1);
        tmpNum2 = Double.parseDouble(tmpStr2);
    }
    catch (NumberFormatException e){
        System.out.println(" parse long error!! " + e);
    }

    double opResult = CalculatorProcress(tmpNum1,tmpNum2,tmpButton.ButtonOP[ranOperator][0]);

    // get the test result from ui
    String getResult = getResultFromUI();
    getResult = getResult.replace('−','-');

    testResult = checkCalculatorResult(getResult,opResult);
    if (testResult) {
        System.out.println("[BasicFunction] Test result : PASS ");
        return true;
    }else{
        getScreenShot("[BasicFunctionTest] Test result : FALSE");
        return false;
    }

}
*/


