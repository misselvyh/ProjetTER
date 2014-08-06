package projet;

import java.util.Scanner;
import java.util.ArrayList;

public class Matrice {
	/**
	 * La classe matrice permet la saisie et l'affichage du systeme
	 * d'argumentation saisie par l'utilisateurs pour un agent.
	 * 
	 * Elle comprends les accesseurs, les mutateurs, une méthode pour saisir les
	 * valeur, une méthode pour remplir la matrice et deux methodes pour
	 * afficher les données differement.
	 * 
	 **/

	private static final long serialVersionUID = -8672579277129244766L;
	// Variables
	protected int nbArg;
	protected ArrayList<Attack> matrice = new ArrayList<Attack>();
	protected ArrayList<Attack> matrice2 = new ArrayList<Attack>();
	protected ArrayList<Attack> matAcg = new ArrayList<Attack>();

	// Constructeur
	public Matrice() {

	}

	public Matrice(ArrayList<Attack> m) {
		matrice = m;
		nbArg = m.size();
	}

	// Modificateurs
	public void setNbArg(int n) {
		nbArg = n;
	}

	// Accesseur
	public int getNbArg() {
		return nbArg;
	}

	// Fonction askNbArg() qui retourne le nombre d'arguments du systeme
	public int askNbArg() {
		Scanner read = new Scanner(System.in);

		setNbArg(0);

		while (getNbArg() < 2) {
			System.out.println("Entrez le nombre d'arguments : ");
			int nbArg = read.nextInt();

			if (getNbArg() >= 2)
				setNbArg(nbArg);
			else
				System.out
						.println("Attention le nombre d'arguments doit Ãªtre supÃ©rieur Ã  2 !");
		}

		return getNbArg();
	}

	/*
	 * 
	 * La méthode remplirMatrice consiste à insérer dans la liste Matrice
	 * l'existance toutes les attaques possible selon le nombre d'argument avec
	 * Z = 0. (Tel que (X,Y)=Z). Ensuite selon la valeur rentré par
	 * l'utilisateur pour l'attaquant et le defenseur, on recherche dans la
	 * liste l'attaque contenant les valeurs X et Y correspondant et modifions Z
	 * à Z=1.
	 */

	public void remplirMatrice() {

		Scanner read = new Scanner(System.in);

		int att = 10;
		int def;

		// on initialise la liste matrice avec toutes les attaques existantes
		// avec Z = 0

		for (int noLin = 1; noLin <= nbArg; noLin++) {

			for (int noCol = 1; noCol <= nbArg; noCol++) {

				Attack arrete = new Attack();

				arrete.setX(noLin);
				arrete.setY(noCol);
				arrete.setZ(0);

				matrice.add(arrete);
			}
		}

		int a = 0;

		while (att != 0) {

			System.out
					.println("tapez l'attaquant puis le defenseur  (1=a;2=b...) ; tapez 0 quand vous avez fini ");

			att = read.nextInt();

			while (att > getNbArg()) {
				System.out
						.println("L'attaquant doit etre inferieur au nombre d'arguments");
				System.out
						.println("tapez l'attaquant puis le defenseur  (1=a;2=b...) ; tapez 0 quand vous avez fini ");
				att = read.nextInt();
			}

			if (att != 0) {
				def = read.nextInt();

				while ((def > getNbArg()) || (att == def)) {
					if (att == def) {
						System.out
								.println("Le defenseur ne peut pas etre le meme que l'attaquant");
						System.out.println("Choisissez un nouveau defenseur");

					} else if (def > getNbArg()) {
						System.out
								.println("Le defenseur doit etre inferieur au nombre d'arguments");
						System.out.println("Rentrez le defenseur de nouveau ");
					}
					def = read.nextInt();
				}

				// lorsque l'utilisateur déclare l'attaquant et le defenseur on
				// le recherche dans la liste matrice et on modifie Z à 1.

				for (int i = 0; i < matrice.size(); i++) {
					if ((matrice.get(i).getX() == att)
							&& (matrice.get(i).getY() == def)) {
						matrice.get(i).setZ(1);
						matrice.get(i).afficheAttack();

					}
				}
				a++;
			}

		}

		// il faut une copie de la matrice original qu'on apellera matrice 2 car
		// matrice sera modifié par la suite dans WhoIsUnd
		matrice2 = matrice;

	}

	// Afficher les valeurs de la matrice mais Matrice sera vide apres und
	public void displayMatrice() {
		System.out.println("***Affichage Matrice1***");
		for (int i = 0; i < matrice.size(); i++) {
			matrice.get(i).afficheAttack();
		}
		System.out.println();
	}

	// En revanche Matrice2 aura toujours les valeurs initiales.
	public void displayMatrice2() {
		System.out.println("***Affichage Matrice***");
		for (int i = 0; i < matrice2.size(); i++) {
			matrice2.get(i).afficheAttack();
		}
		System.out.println();
	}

}
