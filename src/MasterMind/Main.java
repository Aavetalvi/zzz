package MasterMind;

import java.util.Arrays;
import java.util.Scanner;

public class Main
{
	//on fixe le nombre de coup possible à 10
	//on va considerer notre couleur comme une chaine de caractère "rouge" "bleu" "jaune" "vert"
	static String caseRouge = "|rouge|";
	static String caseBleu = "|bleu |";
	static String caseVert = "|vert |";
	static String caseJaune = "|jaune|";
	static String caseVide = "|     |";
	static String caseIndice[] = { "\t|", "\t|", "\t|", "\t|", "\t|", "\t|", "\t|", "\t|", "\t|", "\t|" };

	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args)
	{

		Integer coupsRestant = 10;
		boolean gagne = false;

		//création de la combinaison
		String[] combinaisonSecrete = { caseBleu, caseRouge, caseVert, caseRouge };
		String[][] tableau = new String[10][5];
		construireTableau(tableau);
		System.out.println("Bienvenu dans le MasterMind !\n");
		afficheTableau(tableau);
		do
		{
			String[] combiUser = demanderCombinaison();
			coupsRestant--;
			actuCaseIndice(tableau, combinaisonSecrete, combiUser, coupsRestant);
			actualiserTableau(tableau, combiUser, coupsRestant);
			afficheTableau(tableau);

			//on vérifie si on a gagné
			gagne = gagne(combinaisonSecrete, combiUser);

		} while ((coupsRestant > 0) && (!gagne));

		if (coupsRestant == 0)
		{
			System.out.println("\nDommage, vous n'avez pas réussi à trouver la combinaison secrète.");
		}

	}

	public static boolean gagne(String[] combinaisonSecrete, String[] combiUser)
	{
		for (int i = 0; i < 4; ++i)
		{
			if (!combinaisonSecrete[i].equals(combiUser[i]))
			{
				//si la combinaison saisie n'est pas égale à la combinaison secrete, pas gagné
				return false;
			}
		}
		System.out.println("\nBravo! Vous avez deviné la combinaison secrète !");
		return true;
	}

	public static void actuCaseIndice(String[][] tableau, String[] combinaisonSecrete, String[] combiUser,
			int coupsRestant)
	{
		String indice = "\t|";
		String[] combiTemp = new String[4];

		//on créer notre combinaison tampon égale à la combinaison secrete
		for (int i = 0; i < 4; ++i)
		{
			combiTemp[i] = combinaisonSecrete[i];
		}

		//on vérifie si on a des couleur bien placées uniquement
		for (int i = 0; i < 4; ++i)
		{
			if (combiTemp[i] == combiUser[i])
			{
				//si couleur bien placé, on l'indique, puis on la supprime de la variable tampon 
				//(pour ne pas recompter cette position plus tard)
				indice = indice + "o";
				combiTemp[i] = "";
			}
		}

		//on vérifie ensuite si la combinaison contient la couleur
		//(si on est la c'est qu'on a déjà vérifié si la couleur était bien placé
		//et la couleur bien placé ne sera pas recompté vu qu'elle n'existe plus)
		for (int i = 0; i < 4; ++i)
		{
			if ((Arrays.asList(combiTemp).contains(combiUser[i])))
			{
				//si couleur dans la combinaison, on l'indique, puis on la supprime de la variable tampon 
				//(pour ne pas recompter cette position plus tard)
				indice = indice + "x";
				combiTemp[i] = "";
			}
		}

		indice = indice + "\t(o : couleur bien placée ; x = couleur présente mais mal placée)";
		//on actualise notre case indice de la ligne correspondante
		caseIndice[10 - coupsRestant - 1] = indice;
		//on insère cette case indice dans le tableau
		tableau[10 - coupsRestant - 1][4] = caseIndice[10 - coupsRestant - 1];
	}

	public static void actualiserTableau(String[][] tableau, String[] combiUser, int coupsRestant)
	{
		for (int i = 0; i < 4; ++i)
		{
			//on remplace la ligne du tableau par la ligne correspondant au coup de l'utilisateur
			tableau[10 - coupsRestant - 1][i] = combiUser[i];
		}

	}

	public static String[] demanderCombinaison()
	{
		String[] combiUser = new String[4];
		System.out.println("\nEntrez une combinaison de 4 couleurs : ");
		boolean saisiCorrecte = true;

		do
		{
			saisiCorrecte = true;
			combiUser = sc.nextLine().split(" ");
			//si on a bien 4 élements
			if (combiUser.length == 4)
			{
				for (int i = 0; i < 4; ++i)
				{
					//on remplace le texte par notre "design" de la case voulue
					switch (combiUser[i])
					{
					case "rouge":
						combiUser[i] = caseRouge;
						break;
					case "vert":
						combiUser[i] = caseVert;
						break;
					case "bleu":
						combiUser[i] = caseBleu;
						break;
					case "jaune":
						combiUser[i] = caseJaune;
						break;
					default:
						//si on est la c'est qu'on n'a pas rentrée une chaine de caractère attendue
						System.out.println(
								"Mauvaise saisie. Veuillez écrire une suite de 4 couleurs (parmi rouge, bleu, jaune et vert séparées par UN seul espace");
						System.out.println("Exemple : rouge bleu vert jaune");
						saisiCorrecte = false;
						break;
					}

					if (saisiCorrecte == false) //si la saisie n'est pas bonne on sort de la boucle
						break;
				}
			} else
			{
				System.out.println(
						"Mauvaise saisie. Veuillez écrire une suite de 4 couleurs (parmi rouge, bleu, jaune et vert séparées par UN seul espace");
				System.out.println("Exemple : rouge bleu vert jaune");
				saisiCorrecte = false;
			}
		} while (!saisiCorrecte);

		return combiUser;
	}

	public static void construireTableau(String[][] tableau)
	{
		//double boucle pour construire notre tableau
		for (int i = 0; i < 10; ++i)
		{
			for (int j = 0; j < 5; ++j)
			{
				if (j == 4)
				{
					//si on est la c'est qu'on est à droite, donc il doit y avoir une case de style indice
					tableau[i][j] = caseIndice[j];
				} else
				{
					tableau[i][j] = caseVide;
				}

			}
		}
	}

	public static void afficheTableau(String[][] tableau)
	{
		int i = 1;
		for (String[] ligne : tableau)
		{
			System.out.print("Combinaison " + i + "\t");
			for (String cellule : ligne)
			{

				if (cellule == caseRouge)
				{
					System.out.print(caseRouge);
				} else if (cellule == caseVert)
				{
					System.out.print(caseVert);
				} else if (cellule == caseJaune)
				{
					System.out.print(caseJaune);
				} else if (cellule == caseBleu)
				{
					System.out.print(caseBleu);
				} else if (cellule == caseIndice[i - 1])
				{
					System.out.print(caseIndice[i - 1]);
				} else
				{
					System.out.print(caseVide);
				}

			}
			i++;
			System.out.println();
		}
	}

}
