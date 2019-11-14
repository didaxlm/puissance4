import java.util.*;


public class Puissance4 {
	String nomJoueur1, nomJoueur2;
	public int choixColonne;
	public Random random = new Random();
	public int colonne ;
	public final char jeton1 = 'X';
	public final char jeton2 = 'O';
	public int caseJoue;
	public int[][] plateau = new int[6][7];
	
	
    public int Casejoue() 
    {
    	return caseJoue;
    }

	public void choix() 
	{
		System.out.println(" Quelle colonne voulez vous jouer ? (1 a 7) ");
		Scanner lectureClavier = new Scanner(System.in);
		choixColonne = lectureClavier.nextInt();
		if (choixColonne - 1 < 0 || choixColonne - 1 > 7) {
			System.out.println("Choix impossible, la colonne n'existe pas.");
			choix();
		} else {
			remplissage(choixColonne, 0);
			remplissageMachine(colonne, 0);
		}
	}

	// Initialisation du plateau vide
	public void initialisationPlateau() 
	{
		for (int i = 0; i < this.plateau.length; i++) 
		{
			for (int j = 0; j < this.plateau[0].length; j++) 
			{
				plateau[i][j] = 0;
			}
		}

	}

	// remplissage du plateau en fonction des choix du joueur1
	public void remplissage(int choixColonne, int ligne)
    {
		try
		{
    		if (this.plateau[ligne][choixColonne-1] != 0 && this.plateau[ligne-1][choixColonne - 1] == 0)
    		{
    			this.plateau[ligne-1][choixColonne-1] = 1;
    		}
    		else {
    			if (this.plateau[5][choixColonne-1] == 0) 
    			{
    				this.plateau[5][choixColonne-1] = 1;
    			} 
    			else {
    				remplissage(choixColonne, ligne+1);
    			} 
    		}
		} catch (ArrayIndexOutOfBoundsException e) 
		{
			System.out.println(" Choix impossible, la colonne est pleine ");
			choix();
		}
    }

	// remplissage du plateau par la machine
	public void remplissageMachine(int colonne, int ligne) 
	{
		 try {
	            if(this.plateau[0][colonne] != 0) {
	                throw new ArrayIndexOutOfBoundsException();
	            }
	        
	            if (this.plateau[ligne][colonne] != 0) {
	                this.plateau[ligne-1][colonne] = 2;
	            } else {        
	                if (this.plateau[5][colonne] == 0) {
	                    this.plateau[5][colonne] = 2;
	                } else {
	                    remplissageMachine(colonne, ligne + 1); 
	                }
	            }
	        } catch (ArrayIndexOutOfBoundsException e){	            
	            remplissageMachine(random.nextInt(7), 0);
	        }
	}

	// Affichage du tableau
	public void affichage() 
	{
		for (int i = 0; i < this.plateau.length; i++) 
		{
			for (int j = 0; j < this.plateau[0].length; j++) 
			{
				switch (this.plateau[i][j]) 
				{
				case 0:
					System.out.print(" [ ] ");
					break;
				case 1:
					System.out.print(" [" + jeton1 + "] ");
					break;
				case 2:
					System.out.print(" [" + jeton2 + "] ");
					break;
				}
			}
			System.out.println();
		}
	}
	// Le tableau est rempli
	public boolean jeuPlein()
	{
		for (int i = 0; i < 6; i++) 
		{
			for (int j = 0; j < 7; j++) 
			{
				if (this.plateau[i][j] == 0)
				{
				return false;
				}
			}
		}
		return true;
	}

	// condition de victoire
	  public boolean cherche4() {
		    // Vérifie les horizontales ( - )
		    for (int ligne = 0; ligne < 6; ligne++) 
		    {
		      if (cherche4alignes(0, ligne, 1, 0)) 
		      {
		        return true;
		      }
		    }

		    // Vérifie les verticales ( ¦ )
		    for (int col = 0; col < 7; col++) 
		    {
		      if (cherche4alignes(col, 0, 0, 1)) 
		      {
		        return true;
		      }
		    }

		    // Diagonales (cherche depuis la ligne du bas)
		    for (int col = 0; col < 7; col++) 
		    {
		      // Première diagonale ( / )
		      if (cherche4alignes(col, 0, 1, 1)) 
		      {
		        return true;
		      }
		      // Deuxième diagonale ( \ )
		      if (cherche4alignes(col, 0, -1, 1)) 
		      {
		        return true;
		      }
		    }

		    // Diagonales (cherche depuis les colonnes gauches et droites)
		    for (int ligne = 0; ligne < 6; ligne++) 
		    {
		      // Première diagonale ( / )
		      if (cherche4alignes(0, ligne, 1, 1)) 
		      {
		        return true;
		      }
		      // Deuxième diagonale ( \ )
		      if (cherche4alignes(6 - 1, ligne, -1, 1)) 
		      {
		        return true;
		      }
		    }

		    // On n'a rien trouvé
		    return false;
		  }

		  /**
		   * Cherche 4 pions alignés sur une ligne. Cette ligne est définie par
		   * l'origine des coordonnées (oCol,oLigne), et par le déplacement
		   * (dCol,dLigne). En utilisant des valeurs appropriées pour dCol et dLigne
		   * on peut vérifier toutes les directions:
		   * - horizontale:    dCol = 0, dLigne = 1
		   * - vérticale:      dCol = 1, dLigne = 0
		   * - 1ère diagonale: dCol = 1, dLigne = 1
		   * - 2ème diagonale: dCol = 1, dLigne = -1
		   *
		   * @param oCol   Colonne d'origine de la recherche
		   * @param oLigne Ligne d'origine de la recherche
		   * @param dCol   déplacement sur une colonne
		   * @param dLigne déplacement sur une ligne
		   * @return true si on trouve un alignement
		   */
		  private boolean cherche4alignes(int oCol, int oLigne, int dCol, int dLigne) {
		    int caseJoue = 0;
		    int compteur = 0;

		    int curCol = oCol;
		    int curRow = oLigne;

		    while ((curCol >= 0) && (curCol < 7) && (curRow >= 0) && (curRow < 6)) 
		    {
		      if (plateau[curRow][curCol] != caseJoue) 
		      {
		        // Si la couleur change, on réinitialise le compteur
		    	  caseJoue = plateau[curRow][curCol];
		        compteur = 1;
		      } else {
		        // Sinon on l'incrémente
		        compteur++;
		      }

		      // On sort lorsque le compteur atteint 4
		      if ((caseJoue != 0) && (compteur == 4)) 
		      {
		        return true;
		      }

		      // On passe à l'itération suivante
		      curCol += dCol;
		      curRow += dLigne;
		    }

		    // Aucun alignement n'a été trouvé
		    return false;
		  }

	public static void main(String[] args) 
	{
		Puissance4 jeu = new Puissance4();
		jeu.initialisationPlateau();
		jeu.affichage();
		Scanner lectureClavier = new Scanner(System.in);
		System.out.println("Joueur 1 : Entrez votre nom : ");
		jeu.nomJoueur1 = lectureClavier.nextLine();
		System.out.println(" Bienvenue " + jeu.nomJoueur1 + " Que le meilleur gagne !");
		while (!jeu.cherche4())
		{
			jeu.choix();
			jeu.affichage();
		}
		if (jeu.jeuPlein()) 
		{
			System.out.println(" Match Nul ");
		}
		System.out.println(" Il y a un vainqueur !");
		lectureClavier.close();
	}
}