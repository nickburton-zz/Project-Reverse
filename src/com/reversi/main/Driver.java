package com.reversi.main;
import java.util.Scanner;

public class Driver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Scanner stdin = new Scanner(System.in);
		int choice = 1;
		String p1 = null;
		String p2 = null;
		
		while (choice !=0)
		{
			System.out.println("----- Reversi Game Menu -----");
			System.out.println("1. New Game");
			System.out.println("2. Load Saved Game");
			System.out.println("0. Exit ");
			System.out.println("->");
			choice = Integer.valueOf(stdin.nextLine());
			
			switch(choice){
			case 1:
				System.out.println("Enter Player 1 name ");
				System.out.println("->");
				p1 = stdin.nextLine();
				System.out.println("Enter Player 2 name ");
				System.out.println("->");
				p2 = stdin.nextLine();
				Game game1 = new Game(p1,p2);
				int choice1 = 1;
				game1.checkCells();
				game1.display();
				while(choice1!=0)
				{
					int x =0;
					int y =0;
					//check valid cells
					System.out.println("P1->Black|Name->"+ game1.getPlayer1()+"|Score->"+game1.getP1Score());
					System.out.println("P1->White|Name->"+ game1.getPlayer2()+"|Score->"+game1.getP2Score());
					
					if(game1.isBlacksTurn()==true)
						System.out.println("--P1 Move--");
					else
						System.out.println("--P2 Move--");
					
					System.out.println("Enter X co->");
					x = Integer.valueOf(stdin.nextLine());
					System.out.println("Enter Y co->");
					y = Integer.valueOf(stdin.nextLine());
					
					game1.move(x, y);
					game1.display();
					if(game1.gameOver()==true)
					{
						choice1=0;
						choice=0;
						System.out.println("P1="+game1.getP1Score()+" v P2="+game1.getP2Score());
						if(game1.getP1Score()>game1.getP2Score())
							System.out.println(game1.getPlayer1()+" wins");
						else if(game1.getP2Score()>game1.getP1Score())
							System.out.println(game1.getPlayer2()+" wins");
						else
							System.out.println("Draw?");
					}
				}
			case 2:
				
			default:
				
			break;
					
			}
		}
	}
}