package view;

import java.util.concurrent.Semaphore;

import controller.Aeroporto;

public class Main {

	public static void main(String[] args)
	{
		int numAvioes = 6;
		Semaphore semaforo = new Semaphore(2);
		Semaphore pistaNorte = new Semaphore(1);
		Semaphore pistaSul = new Semaphore(1);
		
		for (int a = 0; a < numAvioes; a++)
		{
			Aeroporto aviao = new Aeroporto(semaforo, pistaNorte, pistaSul, a);
			aviao.start();
		}
	}

}
