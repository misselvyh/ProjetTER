package projet;

import java.util.Scanner;

public class TestProjet {

	/**
	 * @param args
	 */

	public static void main(String[] args) {

		ConPro cp = new ConPro();

		cp.remplirProCon();
		System.out.println();
		System.out.println("***Affichage des listes CON et PRO***");
		System.out.println();
		cp.displayCon();
		cp.displayPro();
		System.out.println();

		cp.acg();

		Scanner read = new Scanner(System.in);
		boolean continuer = true;
		while (continuer) {
			cp.CheminPossible();
			cp.LookingForSwitch();

			System.out.println("Pour essayer un autre chemin tapez 1 ");
			int c = read.nextInt();

			if (c == 1)
				continuer = true;
			else
				continuer = false;

		}

	}

}
