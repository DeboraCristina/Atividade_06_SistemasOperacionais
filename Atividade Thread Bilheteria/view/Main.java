package view;

import java.util.concurrent.Semaphore;

import controller.Bilheteria;

public class Main {

	public static void main(String[] args) 
	{
		int totalIngressos = 100;
		Semaphore semaforo = new Semaphore(1);
		int totalClientes = 300;
		
		for (int i = 0; i < totalClientes; i++)
		{
			Bilheteria venda = new Bilheteria(semaforo, totalIngressos);
			venda.start();
		}
	}

}
