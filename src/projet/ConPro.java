package projet;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * La classe ConPro permet l'affichage des agents CON et PRO en fonction de leur
 * systeme d'argumentation et de l'issu, et permet de renvoyer l'ACG.
 * 
 * Elle comprends les accesseurs, les mutateurs, une méthode pour ajouter un
 * agent chez les CON ou les PRO, des méthodes d'affichage de données, une
 * méthode pour renvoyer le ACG.
 * 
 * Con est la liste contenant les agents CON, Pro est la liste contenants les
 * PRO, cp est la liste contenant tous les agents.
 * 
 **/

public class ConPro {

	// Variables
	protected ArrayList<GroundedExt> Con = new ArrayList<GroundedExt>();
	protected ArrayList<GroundedExt> Pro = new ArrayList<GroundedExt>();
	protected ArrayList<GroundedExt> cp = new ArrayList<GroundedExt>();

	// acg : matrice des conflits
	Matrice acg = new Matrice();

	// acg : matrice des pros
	ArrayList<Integer> acgPro = new ArrayList<Integer>();

	// acg : matrice des con
	ArrayList<Integer> acgCon = new ArrayList<Integer>();

	private ArrayList<ArrayList> CheminsPossibles = new ArrayList<ArrayList>();

	int nbAgents;
	int issu;
	private int nbArg;

	// Accesseurs
	public ArrayList<ArrayList> getCheminsPossibles() {
		return CheminsPossibles;
	}

	public int getNbArg() {
		return nbArg;
	}

	// Modificateurs
	public void setCheminsPossibles(ArrayList<ArrayList> a) {
		CheminsPossibles = a;
	}

	public void setNbArg(int n) {
		nbArg = n;
	}

	// Méthodes

	// addCon(e) ajoute l'élément e dans la liste Con
	public void addCon(GroundedExt e) {
		Con.add(e);
	}

	// addPro(e) ajoute l'élément e dans la liste Pro
	public void addPro(GroundedExt e) {
		Pro.add(e);
	}

	// displayCon() affiche tous les agents de la liste Con
	public void displayCon() {
		System.out.print("CON = {");
		for (int i = 0; i < Con.size(); i++) {
			int z = Con.size();
			if (i < (z - 1)) {
				Con.get(i).afficheNom();
				System.out.print(",");
			} else
				Con.get(i).afficheNom();

		}
		System.out.print("}");
		System.out.println();
	}

	// displayPro() affiche tous les agents de la liste Pro
	public void displayPro() {
		System.out.print("PRO = {");
		for (int i = 0; i < Pro.size(); i++) {
			int z = Pro.size();
			if (i < (z - 1)) {
				Pro.get(i).afficheNom();
				System.out.print(",");
			} else
				Pro.get(i).afficheNom();

		}
		System.out.print("}");
		System.out.println();
	}

	/*
	 * remplirProCon demande à l'utilisateur les informations necessaires pour
	 * l'execution de l'application, c'est à dire le nombre d'agents, le nombre
	 * d'arguments et l'issue, ensuite nous déclarons que le nom de l'agents est
	 * a1 si c'est le premier agent, a2 si c'est le deuxieme etc..., on le garde
	 * en parametre pour pouvoir afficher toutes les informations sur un certain
	 * agent en l'appelant par son nom, et compare si l'agent fait partie des
	 * PRO ou des CON.
	 */
	public void remplirProCon() {

		Scanner read = new Scanner(System.in);

		System.out.println("tapez le nombre de agents ");
		nbAgents = read.nextInt();
		System.out.println("Entrez le nombre d'arguments : ");
		nbArg = read.nextInt();
		System.out.println("quel est l'issue? (1=a;2=b...) ");
		issu = read.nextInt();

		for (int i = 0; i < nbAgents; i++) {
			int j = i + 1;
			String nomAgent = "a" + j;

			GroundedExt g = new GroundedExt(nomAgent, nbArg);
			g.remplirMatrice();
			g.inOutUnd();

			// on ajoute a1 a indice 0, a2 a indice 1 etc...
			cp.add(g);

			// pour chaque agents si l'issu fais partie du grounded par
			// définition il fait partie des PRO (il est en faveur de la
			// déclaration), sinon il fait parti des CON
			if (g.isInIn(issu))
				this.addPro(g);
			else
				this.addCon(g);

		}

	}

	// AfficheNomAgent(i) affiche le nom de l'Agent numéro i
	public void AfficheNomAgent(int num) {
		cp.get(num - 1).afficheNom();
	}

	// AfficheMatriceAgent(i) affiche la matrice associé à l'Agent numéro i
	public void AfficheMatriceAgent(int num) {
		cp.get(num - 1).displayMatrice();
	}

	// AfficheUndAgent(i) affiche la liste des Und pour l'Agent numéro i
	public void AfficheUndAgent(int num) {
		cp.get(num - 1).displayUnd();
	}

	// AfficheInAgent(i) affiche la liste des In pour l'agent numéro i
	public void AfficheInAgent(int num) {
		cp.get(num - 1).displayIn();
	}

	// AfficheoutAgent(i) affiche la liste des Out pour l'Agent numéro i
	public void AfficheOutAgent(int num) {
		cp.get(num - 1).displayOut();
	}

	// AfficheAgent(i) affiche les informations relatives à l'agent numéro i
	// (In,Out,Und)...
	public void AfficheAgent(int num) {
		System.out.println();
		System.out.print("*****Affichage de ");
		cp.get(num - 1).afficheNom();
		System.out.print("*****");
		System.out.println();

		System.out.println();

		cp.get(num - 1).displayMatrice2();

		System.out.println();

		cp.get(num - 1).displayIn();

		cp.get(num - 1).displayOut();

		cp.get(num - 1).displayUnd();

	}

	/*
	 * Acg est la matrice resultant de l'union des matrices de tous les agents
	 * 
	 * Nous avons défini une méthode basé sur la numération binaire pour définir
	 * si pour chaque attaques de l'ACG les PRO et les CON ont le controle
	 * positif,negatif ou playable.
	 * 
	 * 001 : controle positif 010 : controle negatif 100 : playale 101 :
	 * playable + controle positif 110 : playable + controle negatif 111 :
	 * playable + controle positif + controle negatif
	 */
	public void acg() {

		int nba = 0;
		int somme;

		// Si l'attaque existe, somme=1, et on l'ajoute a la liste ACG, et on
		// sort de la condition pour ne pas l'inclure plusieurs fois

		for (int j = 0; j < cp.get(0).matrice2.size(); j++) {

			somme = 0;

			for (int i = 0; i < cp.size(); i++)

			{
				somme += (cp.get(i).matrice2.get(j).getZ());

				if (somme == 1) {
					acg.matAcg.add(cp.get(i).matrice2.get(j));
					nba++;
					somme++;
				}

			}

		}

		// Pour chaque attaque dans la liste ACG, on va regarder si pour chaque
		// agent elle apparait en fonction des pro et des con pour obtenir l'acg

		for (int k = 0; k < acg.matAcg.size(); k++)

		{

			int CompteurProAtt = 0;
			int CompteurProDef = 0;

			int CompteurConAtt = 0;
			int CompteurConDef = 0;

			// calcul des attaques chez les con

			for (int i = 0; i < Con.size(); i++)

			{

				for (int m = 0; m < cp.get(0).matrice2.size(); m++)

				{
					// on recherche l'attaque correspondante
					if ((Con.get(i).matrice2.get(m).getX() == acg.matAcg.get(k)
							.getX())
							&& (Con.get(i).matrice2.get(m).getY() == acg.matAcg
									.get(k).getY())) {

						// on incremente le compteur d'attaque ou de defense
						// selon si elle existe ou pas.
						if (Con.get(i).matrice2.get(m).getZ() == 1)
							CompteurConAtt++;
						else
							CompteurConDef++;
					}

				}

			}

			// calcul des attaques chez les pros

			for (int j = 0; j < Pro.size(); j++)

			{

				for (int m = 0; m < cp.get(0).matrice2.size(); m++)

				{
					// on recherche l'attaque correspondante
					if ((Pro.get(j).matrice2.get(m).getX() == acg.matAcg.get(k)
							.getX())
							&& (Pro.get(j).matrice2.get(m).getY() == acg.matAcg
									.get(k).getY())) {

						// on incremente le compteur d'attaque ou de defense
						// selon si elle existe ou pas.s
						if (Pro.get(j).matrice2.get(m).getZ() == 1)
							CompteurProAtt++;
						else
							CompteurProDef++;
					}

				}

			}

			int ConNumero = 0;
			int ProNumero = 0;

			if (CompteurConAtt > CompteurProDef)
				// Con+ : Il y a plus d'agents chez les CON qui peuvent ajouter
				// l'attaque que d'agent chez les PRO qui peuvent la retirer.
				ConNumero += 1;

			if (CompteurConDef >= CompteurProAtt)
				// Con- : Il y a plus d'agents chez les CON qui peuvent retirer
				// l'attaque que d'agent chez les PRO qui peuvent l'ajouter .
				ConNumero += 10;

			if (CompteurConAtt > 0)
				// Con Playable : l'attaque existe au moins une fois chez les
				// CON
				ConNumero += 100;

			if (CompteurProAtt > CompteurConDef)
				// Pro+ : Il y a plus d'agents chez les PRO qui peuvent ajouter
				// l'attaque que d'agent chez les CON qui peuvent la retirer.
				ProNumero += 1;

			if (CompteurProDef >= CompteurConAtt)
				// Pro- : Il y a plus d'agents chez les PRO qui peuvent retirer
				// l'attaque que d'agent chez les PRO qui peuvent l'ajouter .
				ProNumero += 10;

			if (CompteurProAtt > 0)
				// Pro Playable : l'attaque existe au moins une fois chez les
				// PRO
				ProNumero += 100;

			acgPro.add(ProNumero);
			acgCon.add(ConNumero);

		}

		// on affiche le resultat sous forme de "tableau" pour une meilleur
		// lisibilité

		System.out.println("***Affichage graphe ACG***");
		System.out.println();
		System.out.print("Attack  Pro ");
		System.out.println(" Con ");
		System.out.println();

		for (int i = 0; i < acg.matAcg.size(); i++) {
			acg.matAcg.get(i).afficheAttackSansZ();
			System.out.print(acgPro.get(i) + "  ");
			System.out.println(acgCon.get(i));
			System.out.println();
		}

	}

	// Fonction factorielle()
	public static int factorielle(int n) {
		int fact = 1;

		for (int i = 1; i <= n; i++) {
			fact *= i;
		}

		return fact;
	}

	// CheminPossible() recherche tous les chemins possibles pour une arrivée
	// saisie par l'utilisateur et l'enregistre dans la liste CheminsPossibles
	public void CheminPossible()

	{

		System.out.println("***Recherche de chemin possible***");

		Scanner read = new Scanner(System.in);

		System.out.println("Tapez l'arrivee :  ");
		int arrivee = read.nextInt();

		while (arrivee > nbArg) {
			System.out.println("Impossible de dépasser le nombre d'arguments.");
			System.out.println("Tapez l'arrivee :  ");
			arrivee = read.nextInt();
		}

		ArrayList<ArrayList> ListeDesChemins = new ArrayList<ArrayList>();

		int longueurMax = factorielle(acg.matAcg.size());

		/*
		 * Les cas d'arrêts sont : * Lorsque la taille du dernier chemin ajouté
		 * * est égale au nombre max de chemins possibles * Lorsque
		 * NewCheminsPossibles est vide
		 */

		// Recherche des chemins de longueur 2

		int longueur = 0;

		ArrayList<Attack> CheminsPossibles = new ArrayList<Attack>();
		for (int i = 0; i < acg.matAcg.size(); i++) {

			// Si Y = arrivee
			if (acg.matAcg.get(i).getY() == arrivee) {
				// Créer une liste de longueur 1
				ArrayList<Attack> CheminsPossibles2 = new ArrayList<Attack>();
				CheminsPossibles.add(acg.matAcg.get(i));
				CheminsPossibles2.add(acg.matAcg.get(i));
				ListeDesChemins.add(CheminsPossibles2);
			}
		}

		if (ListeDesChemins.size() != 0) {
			longueur++;
		}

		// System.out.println("longueur : " + longueur);

		// Recherche des chemins de longueur 2
		ArrayList<Attack> NewCheminsPossibles = new ArrayList<Attack>();
		ArrayList<ArrayList> ListeBis = new ArrayList<ArrayList>();

		for (int j = 0; j < CheminsPossibles.size(); j++) {
			for (int i = 0; i < acg.matAcg.size(); i++) {
				// Si Y = X.CheminsPossibles
				if (acg.matAcg.get(i).getY() == CheminsPossibles.get(j).getX()) {
					NewCheminsPossibles.add(acg.matAcg.get(i));
					ArrayList<Attack> Chemin = new ArrayList<Attack>();

					Chemin.add(acg.matAcg.get(i));
					Chemin.add(CheminsPossibles.get(j));
					ListeDesChemins.add(Chemin);
					ListeBis.add(Chemin);
				}
			}
		}

		if (NewCheminsPossibles.size() != 0) {
			longueur++;
		}

		// Recherche pour les longueurs supérieures ou égales à 3
		while ((longueur <= longueurMax) && (NewCheminsPossibles.size() != 0)) {

			// Recopie de NewCheminsPossibles dans NewCheminsPossibles2
			ArrayList<Attack> NewCheminsPossibles2 = new ArrayList<Attack>();
			for (int n = 0; n < NewCheminsPossibles.size(); n++) {
				NewCheminsPossibles2.add(NewCheminsPossibles.get(n));
			}

			// Recopie de ListeBis dans ListeBis2
			ArrayList<ArrayList> ListeBis2 = new ArrayList<ArrayList>();
			for (int n = 0; n < ListeBis.size(); n++) {
				ListeBis2.add(ListeBis.get(n));
			}

			// Efface NewCheminsPossibles et ListeBis
			NewCheminsPossibles.clear();
			ListeBis.clear();

			for (int j = 0; j < NewCheminsPossibles2.size(); j++) {
				for (int i = 0; i < acg.matAcg.size(); i++) {
					for (int k = 0; k < ListeBis2.size(); k++) {
						ArrayList<Attack> tmpChemin = ListeBis2.get(k);

						// Si Y de ACG = X.NewCheminsPossibles2
						if (acg.matAcg.get(i).getY() == NewCheminsPossibles2
								.get(j).getX()) {

							ArrayList<Attack> Chemin = new ArrayList<Attack>();

							// Si le 1er élément de ListeBis2 (tmpChemin) =
							// élément j de NewCheminsPossibles2
							if (tmpChemin.get(0).equals(
									NewCheminsPossibles2.get(j))) {

								// Ajouter l'attaque d'ACG dans
								// NewCheminsPossibles
								NewCheminsPossibles.add(acg.matAcg.get(i));

								// Ajouter arête au début
								Chemin.add(acg.matAcg.get(i));

								// On ajoute le reste pour créer notre
								// nouveau chemin dans Chemin
								for (int l = 0; l < tmpChemin.size(); l++) {
									Chemin.add(tmpChemin.get(l));
								}

								int ok = 0;

								// Vérifier que le 1er élément de Chemin
								// n'est pas le même que autres
								for (int m = 1; m < Chemin.size(); m++) {
									if (Chemin.get(0).equals(Chemin.get(m)) == false) {
										ok++;
									}
								}

								if (ok == Chemin.size()-1) {
									// Ajouter le chemin dans ListeBis et
									// ListeDesChemins si c'est le cas
									ListeBis.add(Chemin);
									ListeDesChemins.add(Chemin);
								}
							}

						}

						if (NewCheminsPossibles.size() != 0) {
							longueur++;
						}

					}
				}
			}

		}
		// System.out.println("On sort du while");
		System.out.println();

		// Affichage des chemins possibles
		System.out.println("On affiche les chemins possibles");
		for (int k = 0; k < ListeDesChemins.size(); k++) {
			// On déclare la liste d'attack dans une liste temporaire tmp
			ArrayList<Attack> tmp = ListeDesChemins.get(k);

			for (int l = 0; l < tmp.size(); l++) {
				tmp.get(l).afficheAttackSansZ2();
			}
			System.out.println();
		}

		System.out.println();
		setCheminsPossibles(ListeDesChemins);
	}

	public boolean IsSwitch(Attack a) {
		boolean attaquant = false;
		boolean defenseur = false;

		ArrayList<ArrayList> ListeDesChemins = getCheminsPossibles();

		for (int k = 0; k < ListeDesChemins.size(); k++) {
			// On déclare la liste d'attack dans une liste temporaire tmp
			ArrayList<Attack> tmp = ListeDesChemins.get(k);
			int tailleChemin = tmp.size();

			for (int l = 0; l < tmp.size(); l++) {
				if (tmp.get(l).equals(a)) {
					int position = l + 1;

					// Si tailleChemin PAIRE
					if (tailleChemin % 2 == 0) {
						// Si position PAIRE
						if (position % 2 == 0) {
							attaquant = true;
						}
						// Sinon
						else {
							defenseur = true;
						}
					}
					// Sinon... Si tailleChemin IMPAIRE
					else {
						// Si position PAIRE
						if (position % 2 == 0) {
							defenseur = true;
						}
						// Sinon
						else {
							attaquant = true;
						}
					}
				}
			}
		}
		return ((attaquant) && (defenseur));
	}

	public ArrayList<Attack> LookingForSwitch() {
		System.out.println("***Recherche des switchs***");
		ArrayList<Attack> ListeDesSwitchs = new ArrayList<Attack>();

		// Pour les i = 0 au nombres d'attaques d'ACG
		for (int i = 0; i < acg.matAcg.size(); i++) {
			// Si l'attaque i est un switch
			if (IsSwitch(acg.matAcg.get(i)) == true) {
				acg.matAcg.get(i).afficheAttackSansZ2();
				System.out.println(" est un switch.");
				ListeDesSwitchs.add(acg.matAcg.get(i));
			}
			// Sinon
			else {
				acg.matAcg.get(i).afficheAttackSansZ2();
				System.out.println(" n'est pas un switch.");
			}
		}
		System.out.println();
		return ListeDesSwitchs;
	}
}
