package Pendu;

import java.util.Arrays;
import java.util.Scanner;

public class Main
{
	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args)
	{
		String motMystere = "bonjour"; // mot de départ à deviner
		String[] motMyst = motMystere.split(""); // transformation de la chaine de carac en tableau
		String lettre = "";
		Integer essai = 7; // nombre d'essais possibles
		boolean lettrePresente = false;
		int gagne = 0;

		System.out.println("Devinez le mot mystère !");

		// on construit un tableau de "_" pour masquer le mot a deviner
		String motMasque[] = constructionMotMasque(motMyst);

		do
		{
			System.out.println("Essais restants : " + essai + " /7");

			// affichage du mot masqué à deviner
			System.out.println(Arrays.toString(motMasque));

			// saisi de la lettre entrée par l'utilisateur
			lettre = saisi();

			//on vérifie si la lettre est présente et modifie le mot masqué si elle l'est
			lettrePresente = verifLettrePresente(motMyst, motMasque, lettre);

			//si la lettre n'est pas présente, on perd un essai
			if (!lettrePresente)
			{
				System.out.println("Lettre non présente !");
				essai--;
			}

			//on remet de base la valeur de lettrePresente à faux
			lettrePresente = false;

			// on vérifie si on a perdu
			if (essai == 0)
			{
				System.out.println("Vous avez perdu !");
			}

			//on vérifie si on a gagné
			//dans mon test je considère qu'on a gagné à partir du moment ou il n'y a plus le
			// caractère "_" dans mon tableau (indexOf renvoit -1 dans ce cas)
			gagne = Arrays.asList(motMasque).indexOf("_");
			if (gagne == -1)
			{
				System.out.println("Vous avez gagné !");
			}

		} while ((essai != 0) && (gagne != -1)); //si on a gagné ou perdu on quitte le programme
	}

	public static boolean verifLettrePresente(String[] motMyst, String[] motMasque, String lettre)
	{
		boolean lettrePresente = false;
		for (Integer i = 0; i < motMyst.length; ++i)
		{
			//on parcourt notre tableau du mot à deviner en vérifiant si la lettre saisie y est
			if (motMyst[i].equals(lettre))
			{
				//si elle y est, on ajoute dans le mot masqué la lettre trouvée
				motMasque[i] = lettre;
				lettrePresente = true;
			}
		}
		return lettrePresente; //on renvoie si oui ou non la lettre est présente
	}

	public static String[] constructionMotMasque(String[] motMystere)
	{
		String[] motMasque = new String[motMystere.length];

		for (Integer i = 0; i < motMasque.length; ++i)
		{
			//on ne masque pas les tirets
			if (motMystere[i].equals("-"))
			{
				motMasque[i] = "-";
			} else if (motMystere[i].equals(" ")) //on ne masque pas les espaces
			{
				motMasque[i] = " ";
			} else //tout les autres caractères sont masqués
			{
				motMasque[i] = "_";
			}

		}
		return motMasque;
	}

	public static String saisi()
	{
		String saisi = "";
		do
		{
			System.out.println("Saisir une lettre : ");
			saisi = sc.nextLine();
			//on vérifie que l'utilisateur a rentré qu'une seule lettre
			if (saisi.length() != 1)
				System.out.println("Mauvaise saisie");
		} while (saisi.length() != 1);
		return saisi;
	}

}
