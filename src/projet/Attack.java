package projet;

/**
 * La classe Attaque permet de définir une attaque.
 * 
 * Une attaque est défini tel que X est l'argument attaquant, Y est l'argument attaqué, et (X,Y)=1 si l'attaque existe, 0 sinon.
 * 
 * La classe est composé des get, set et differents affichages des variables.
 * 
 **/

public class Attack {
	// Variables
	private int arg1;
	private int arg2;
	private int val;

	// Constructeurs
	public Attack() {
		arg1 = 0;
		arg2 = 0;
		val = 0;
	}

	public Attack(int x, int y, int z) {
		arg1 = x;
		arg2 = y;
		val = z;
	}

	// Accesseurs
	public int getX() {
		return arg1;
	}

	public int getY() {
		return arg2;
	}

	public int getZ() {
		return val;
	}

	// Modificateurs
	public void setX(int x) {
		arg1 = x;
	}

	public void setY(int y) {
		arg2 = y;
	}

	public void setZ(int z) {
		val = z;
	}

	// Méthodes

	// afficheAttack() affiche une attaque dans la ligne de commande
	// (X,Y) = Z
	// X,Y appartenant à l'ensemble des arguments
	// Z = 1 s'il existe un arc d'attaque entre X et Y, 0 sinon
	public void afficheAttack() {
		System.out.println("(" + getX() + "," + getY() + ")" + "=" + getZ());
	}

	// afficheAttaqueSansZ() affiche une attaque sans Z
	public void afficheAttackSansZ() {
		System.out.print("(" + getX() + "," + getY() + ") : ");
	}

	// afficheAttaqueSansZ2() affiche une attaque sans Z
	public void afficheAttackSansZ2() {
		System.out.print("(" + getX() + "," + getY() + ")");
	}

}