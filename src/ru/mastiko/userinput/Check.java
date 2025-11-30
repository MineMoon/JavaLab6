package ru.mastiko.userinput;

public class Check {

	public boolean isInteger(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public boolean Positive (int num) {
		return num >= 0;
	}

}
