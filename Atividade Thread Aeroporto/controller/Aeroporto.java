package controller;

import java.util.concurrent.Semaphore;

public class Aeroporto extends Thread 
{
	private Semaphore pistas;
	private Semaphore escolherPista;
//	private Semaphore pistaNorte;
//	private Semaphore pistaSul;
	private int idAviao;
	private String pistaOcupada;
	private static boolean pistaSulOcupada;
	private static boolean pistaNorteOcupada;
	
	public Aeroporto(Semaphore pistas, Semaphore escolherPista, Semaphore pistaSul, int idAviao) 
	{
		this.pistas = pistas;
		this.escolherPista = escolherPista;
//		this.pistaNorte = pistaNorte;
		//this.idAviao = idAviao;
		this.idAviao = ((int)getId());
	}
	
	@Override
	public void run() 
	{
		try
		{
			pistas.acquire(); // entram 2 avioes por vez
			
			try
			{
				escolherPista.acquire(); // entra um aviao por vez
				if (pistaSulOcupada)
				{
					System.out.printf("Aviao #%d entrou na pista Norte\n", idAviao);
					pistaNorteOcupada = true;
					pistaOcupada = "Norte";
				}
				else
				{
					System.out.printf("Aviao #%d entrou na pista Sul\n", idAviao);
					pistaSulOcupada = true;
					pistaOcupada = "Sul";
				}
			}
			catch  (InterruptedException e1)
			{
				e1.printStackTrace();
			}
			finally
			{
				escolherPista.release();
			}

			manobrar();
			taxiar();
			decolar();
			afastar();
			System.out.printf("Aviao #%d partiu com sucesso!\n", idAviao);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (pistaOcupada.contains("Norte"))
				pistaNorteOcupada = false;
			if (pistaOcupada.contains("Sul"))
				pistaSulOcupada = false;
			
			pistas.release();
		}
	}
	
	private void manobrar()
	{
		int tempo = mathRandom(3, 7) * 1000;
		metodoSleep(tempo);
	}
	private void taxiar()
	{
		int tempo = mathRandom(5, 10) * 1000;
		metodoSleep(tempo);
	}
	private void decolar()
	{
		int tempo = mathRandom(1, 4) * 1000;
		metodoSleep(tempo);
	}
	private void afastar()
	{
		int tempo = mathRandom(3, 8) * 1000;
		metodoSleep(tempo);
	}
	
	private int mathRandom(int min, int max)
	{
		return (int) ((Math.random() * ((max + 1) - min)) + min);
	}
 	

	private void metodoSleep(int tempo) {
		try 
		{
			sleep(tempo);
		} catch (InterruptedException e) 
		{
			e.printStackTrace();
		}		
	}
}
/*
Um aeroporto tem 2 pistas (norte e sul) e, em cada pista, apenas um
avião pode fazer a decolagem.
O procedimento de decolagem tem 4 fases (manobra, taxiar,
decolagem e afastamento da área).
A fase de manobra pode durar de 3 a 7 segundos. A fase de taxiar, de 5
a 10 segundo. A fase de decolagem, de 1 a 4 segundos. A fase de
afastamento, de 3 a 8 segundos.
O aeroporto reúne, por ciclo, 12 aeronaves que podem decolar pela
pista norte ou pela pista sul (decisão aleatória). Mas, apenas 2 aviões
podem circular pela área de decolagem ao mesmo tempo.
Fazer uma aplicação em Java que resolva o problema.
*/