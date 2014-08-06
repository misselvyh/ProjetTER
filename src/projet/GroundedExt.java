package projet;

import java.util.ArrayList;

import projet.Matrice;

public class GroundedExt extends Matrice {
	// Variables
	private ArrayList<Integer> in = new ArrayList<Integer>();
	private ArrayList<Integer> out = new ArrayList<Integer>();
	private ArrayList<Integer> und = new ArrayList<Integer>();
	private ArrayList<Integer> oldUnd = new ArrayList<Integer>();
	private boolean active = false;
	private String nom;

	// Constructeurs
	public GroundedExt() {
		for (int i = 1; i <= getNbArg(); i++) {
			und.add(i);
		}
	}

	public GroundedExt(String a, int b) {
		for (int i = 1; i <= getNbArg(); i++) {
			und.add(i);
		}
		this.nom = a;
		nbArg = b;
		System.out.println("*****Construction de " + a + "*****");
	}

	// Modificateurs
	public void setOutList(ArrayList<Integer> outList) {
		out = outList;
	}

	public void setInList(ArrayList<Integer> inList) {
		in = inList;
	}

	public void setUndList(ArrayList<Integer> undList) {
		und = undList;
	}

	public void setOldUndList(ArrayList<Integer> oldUndList) {
		oldUnd = oldUndList;
	}

	public void setActive() {
		active = true;
	}
	
	// Accesseurs
	public ArrayList<Integer> getOutList() {
		return out;
	}

	public ArrayList<Integer> getInList() {
		return in;
	}

	public ArrayList<Integer> getUndList() {
		return und;
	}

	public ArrayList<Integer> getOldUndList() {
		return oldUnd;
	}

	// Méthodes

	// afficheNom() affiche le nom de l'Agent
	public void afficheNom() {
		System.out.print("" + nom + "");
	}

	// displayOut() affiche la liste des OUT
	public void displayOut() {
		System.out.print("liste des OUT : ");
		for (int i = 0; i < getOutList().size(); i++) {
			System.out.print(getOutList().get(i) + " ");
		}
		System.out.println();

	}

	// displayIn() affiche la liste des IN
	public void displayIn() {
		System.out.print("liste des IN : ");
		for (int i = 0; i < getInList().size(); i++) {
			System.out.print(getInList().get(i) + " ");
		}
		System.out.println();
	}

	// displayUnd() affiche la liste des UND
	public void displayUnd() {
		System.out.print("liste des UND : ");
		for (int i = 0; i < getUndList().size(); i++) {
			System.out.print(getUndList().get(i) + " ");
		}
		System.out.println();
	}

	// displayOldUnd() affiche la liste des OLDUND
	public void displayOldUnd() {
		System.out.print("Voici notre liste des OLDUND : ");
		for (int i = 0; i < getOldUndList().size(); i++) {
			System.out.print(getOldUndList().get(i));
		}
	}

	// whoIsIn() recherche les IN (colonnes de 0), les ajoute a une liste IN,
	// met le reste dans une liste UND
	public void whoIsIn() {
		boolean dejasupprime = false;

		// Si activé = faux
		if (active == false) {
			// Pour y = 0 au nombre d'arguments
			for (int y = 0; y < getNbArg(); y++) {
				// on initialise nbDefendu à 0 (correspond au nombre de 0, càd
				// lorsque Z = 0)
				int nbDefendu = 0;

				// Pour les i = 0 à la taille de la matrice
				for (int i = 0; i < matrice.size(); i++) {

					// n = i modulo nbArg
					int n = i % getNbArg();

					// Si n = argument y et Z = 0
					if ((n == y) && (matrice.get(i).getZ() == 0)) {
						// on incrémente le nbDefendu
						nbDefendu++;
					}

				}

				// Si le nombre de 0 trouvés pour l'argument est égal au nombre
				// total d'arguments
				if (nbDefendu == getNbArg()) {
					// On ajoute l'argument dans la liste des IN
					int z = y + 1;
					in.add(z);
					// Sinon
				} else {
					// On ajoute l'argument dans la liste des UND
					int z = y + 1;
					und.add(z);
				}

				// On met à jour in et und
				setInList(in);
				setUndList(und);
			}
		}
		// Sinon
		else {
			// Pour y = 0 au nombre d'arguments
			for (int y = 0; y < getNbArg(); y++) {
				// On initialise nbDefendu à 0
				int nbDefendu = 0;

				// Pour i = 0 à la taille de notre matrice
				for (int i = 0; i < matrice.size(); i++) {
					// n i modulo nbArg
					int n = i % getNbArg();

					// Si n = y et Z = 0
					if ((n == y) && (matrice.get(i).getZ() == 0)) {
						// On incrémente le nbDefendu
						nbDefendu++;
					}
				}

				// Si nbDefendu est égal au nombre d'arguments
				if (nbDefendu == getNbArg()) {
					int x;

					// Si la taille de la matrice = 1
					// Lorsque la matrice a un seul élément, matrice.get(1)
					// n'existe pas, dans ce cas-là on ajoute celui à la 1ere
					// case
					if (matrice.size() == 1) {
						x = matrice.get(0).getX();
					}
					// Sinon
					else {
						x = matrice.get(y + 1).getX();
					}

					// Si in ne contient pas contient x
					if (in.contains(x) == false) {
						// On ajoute x dans in
						in.add(x);
					}
					// Si und contient x
					if (und.contains(x)) {
						// Si dejasupprime renvoie faux
						// Lorsque l'on supprime une valaur, la valeur suivante
						// passe à l'indice précédent (décalage de toutes les
						// valeurs), il faut donc supprimer celle à une indice
						// en-dessous
						if (dejasupprime == false) {
							// on supprime y de la liste des und et on met
							// déjasupprime à vrai
							und.remove(y);
							dejasupprime = true;
						}
						// Sinon
						else {
							und.remove(y - 1);
						}

					}
					// Sinon si und ne contient pas x
					else {
						System.out.println(x + " nest pas dans und");
					}
				}
				// Sinon si nbDefendu n'est pas au nombre d'arguments
				else {
					int x = matrice.get(y + 1).getX();

					// Si und ne contient pas x
					if (und.contains(x) == false) {
						und.add(x);
					}
				}

				// Mise à jour de in et und
				setInList(in);
				setUndList(und);
			}
		}
	}

	// La méthode whoIsOut() recherche les arguments placés dans la liste OUT et
	// les retire donc de la liste UND
	public void whoIsOut() {

		for (int i = 0; i < in.size(); i++) {

			for (int j = 0; j < matrice.size(); j++) {

				if (matrice.get(j).getX() == in.get(i)) {
					if (matrice.get(j).getZ() == 1) {
						if (out.contains(matrice.get(j).getY()) == false) {
							out.add(matrice.get(j).getY());
						}
						for (int k = 0; k < und.size(); k++) {
							if (und.get(k) == matrice.get(j).getY())
								und.remove(k);
						}

					}

				}

			}

			setOutList(out);

		}
	}

	// whoIsUnd() recherche les attaques dont X et Y appartiennent encore à la
	// liste des UND (donc ni IN ni OUT), puis crée une nouvelle matrice de ces
	// attaques
	// on met à jour la variable matrice ensuite
	public void whoIsUnd() {
		// Copie de la liste UND dans une liste appelée OLDUND
		setOldUndList(und);

		// Création d'une liste d'attaque AttackList
		ArrayList<Attack> attackList = new ArrayList<Attack>();

		// Pour tous les éléments de la liste UND
		for (int i = 0; i < und.size(); i++) {
			// De j = 0 à la taille de la matrice
			for (int j = 0; j < matrice.size(); j++) {

				int k = 0;

				// Pour chaque attaque de la matrice, tant que le X appartient à
				// la liste UND et que k < und.size()
				while ((matrice.get(j).getX() == und.get(i))
						&& (k < und.size())) {
					// Si le Y associé appartient également à la liste des UND
					if (matrice.get(j).getY() == und.get(k)) {
						// On ajoute l'attaque (X,Y) dans la liste attackList
						Attack att = new Attack();
						att.setX(matrice.get(j).getX());
						att.setY(matrice.get(j).getY());
						att.setZ(matrice.get(j).getZ());

						attackList.add(att);
					}
					k++;
				}
			}
		}

		// On met à jour la matrice en lui renvoyant attackList, le booléen
		// active passe à true pour annoncer que whoIsUnd a déjà été appelée une
		// fois
		matrice = attackList;
		setNbArg((int) Math.sqrt(matrice.size()));
		setActive();
	}

	// isUndNew() renvoie faux si la liste Und est la meme que oldUnd, vrai
	// sinon
	public boolean isUndNew() {
		boolean newList = false;
		int i = 0;
		while ((newList) && (und.size() == oldUnd.size()) && (i < und.size())) {
			if (und.get(i) != oldUnd.get(i)) {
				newList = true;
			}
			i++;
		}
		return newList;
	}

	// La méthode inOutUnd est l'algorithme dans lesquels on va utiliser
	// whoIsIn, whoIsOut et whoIsUnd, puis boucler afin de retrouver les 3
	// listes finales in, out et und
 	public void inOutUnd() {
		whoIsIn();

		whoIsOut();

		whoIsUnd();

		while ((und.size() != 0) || (isUndNew() == true)) {

			whoIsIn();

			whoIsOut();

			whoIsUnd();

			for (int i = 0; i < und.size(); i++) {

				out.add(und.get(i));

			}

			und.clear();

		}

		System.out.println("*****3 LISTES FINALES*****");
		displayIn();
		displayOut();
		displayUnd();
	}

	// La méthode inInIn sert simplement à vérifier si une issue passée en paramètre appartient à la liste in.
	public boolean isInIn(int issu) {
		return in.contains(issu);
	}

}