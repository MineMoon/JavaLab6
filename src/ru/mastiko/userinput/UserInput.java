package ru.mastiko.userinput;

import java.util.Scanner;

public class UserInput {


	private final Scanner scanner = new Scanner(System.in);
	private final Check check = new Check();
	private String input;

	public int inputInt(int number){
		if(number == 0){
			System.out.println("Введите число: ");
		}
		else if(number>0){
			System.out.println("Введите " + number + "-ое число: ");
		}

		input = scanner.nextLine();
		while (!check.isInteger(input)){
			System.out.println("Некорректный ввод!");
			if(number == 0){
				System.out.println("Введите число: ");
			}
			else if(number>0){
				System.out.println("Введите " + number + "-ое число: ");
			}
			input = scanner.nextLine();
		}
		return Integer.parseInt(input);
	}

	public int inputPositiveInt(int number){
		if(number == 0){
			System.out.println("Введите целое число: ");
		}
		else if(number>0){
			System.out.println("Введите " + number + "-ое целое число: ");
		}

		input = scanner.nextLine();

		while (true) {
			try {
				int value = Integer.parseInt(input);
				if (value < 0) {
					System.out.println("Число должно быть положительным!");
					if(number == 0){
						System.out.println("Введите целое число: ");
					}
					else if(number>0){
						System.out.println("Введите " + number + "-ое целое число: ");
					}
				} else {
					return value;
				}
			} catch (NumberFormatException e) {
				System.out.println("Некорректный ввод!");
				if(number == 0){
					System.out.println("Введите целое число: ");
				}
				else if(number>0){
					System.out.println("Введите " + number + "-ое целое число: ");
				}
			}
			input = scanner.nextLine();
		}
	}

	public String inputString(String what){
		System.out.println("Введите "+ what + ": ");
		input = scanner.nextLine();
		return input;
	}

	public int inputDiaposonInt(int start, int end, String what){
		System.out.println("Введите "+ what+ " от " + start + " до " + end + ": ");

		input = scanner.nextLine();
		while (!check.isInteger(input)) {
			System.out.println("Некорректный ввод!");
				System.out.println("Введите число от" + start + " до " + end + ": ");
			input = scanner.nextLine();
		}
		while (!(start<=Integer.parseInt(input)&&(end>=Integer.parseInt(input)))) {
			System.out.println("Диапозон от " + start + " до " + end + "!");

			System.out.println("Введите "+ what+ " от " + start + " до " + end + ": ");
			input = scanner.nextLine();
			while (!check.isInteger(input)) {
				System.out.println("Некорректный ввод!");
				System.out.println("Введите число от" + start + " до " + end + ": ");
				input = scanner.nextLine();
			}
		}
		return Integer.parseInt(input);
	}

	public int inputChoiceInt(int start, int end, String what){
		System.out.println("Выберете "+ what);

		input = scanner.nextLine();
		while (!check.isInteger(input)) {
			System.out.println("Некорректный ввод!");
			System.out.println("Введите число от " + start + " до " + end + "!");
			System.out.println("Выберете "+ what);
			input = scanner.nextLine();
		}
		while (!(start <= Integer.parseInt(input) && (end >= Integer.parseInt(input)))) {
			System.out.println("Диапозон от " + start + " до " + end + "!");
			System.out.println("Выберете "+ what);
			input = scanner.nextLine();
			while (!check.isInteger(input)) {
				System.out.println("Введите число от " + start + " до " + end + "!");
				System.out.println("Выберете "+ what);
				input = scanner.nextLine();
			}
		}
		return Integer.parseInt(input);
	}

	public int[] inputIntArray(int size){
		int[] array = new int[size];
		for(int i = 1; i <= size; i++){
			array[i-1] = inputInt(i);
		}
		return array;
	}


}
