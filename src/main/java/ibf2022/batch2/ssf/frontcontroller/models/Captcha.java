package ibf2022.batch2.ssf.frontcontroller.models;

import java.util.Random;

public class Captcha {

    private static final int CAPTCHA_SETTING = 50;
	private static final String[] CAPTCHA_OPERATION = {"+", "-", "x", "/"};

    private int randnum1;
    private int randnum2;
    private String operation;
    private int answer;

    public int getRandnum1() {
        return randnum1;
    }
    public void setRandnum1(int randnum1) {
        this.randnum1 = randnum1;
    }
    public int getRandnum2() {
        return randnum2;
    }
    public void setRandnum2(int randnum2) {
        this.randnum2 = randnum2;
    }
    public String getOperation() {
        return operation;
    }
    public void setOperation(String operation) {
        this.operation = operation;
    }
    public int getAnswer() {
        return answer;
    }
    public void setAnswer(int answer) {
        this.answer = answer;
    }

	public int generateRandomNum() {
		Random random = new Random();
		return random.nextInt(CAPTCHA_SETTING) + 1;
	}

	public String generateRandomOperation() {
		Random random = new Random();
		return CAPTCHA_OPERATION[random.nextInt(CAPTCHA_OPERATION.length)];
	}

    public int calculateAnswer() {
        switch(this.getOperation()) {
            case "+":
                return this.randnum1 + this.randnum2;
                
            case "-":
                return this.randnum1 - this.randnum2;
                
            case "x":
                return this.randnum1 * this.randnum2;
                
            case "/":
                return this.randnum1 / this.randnum2;
                
            default:
                return (CAPTCHA_SETTING*CAPTCHA_SETTING + 1);
        }
    }

    public void initialize() {
        this.randnum1 = generateRandomNum();
        this.randnum2 = generateRandomNum();
        this.operation = generateRandomOperation();
        this.answer = calculateAnswer();
    }
}