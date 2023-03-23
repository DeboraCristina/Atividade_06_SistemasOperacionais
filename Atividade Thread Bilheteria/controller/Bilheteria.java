package controller;

import java.util.concurrent.Semaphore;

public class Bilheteria extends Thread
{
	private Semaphore semaforo;
	private static int total_ingressos;
	private int pedido;
	
	public Bilheteria(Semaphore semaforo, int total_ingressos) 
	{
		this.semaforo = semaforo;
		this.total_ingressos = total_ingressos;
	}
	
	@Override
	public void run() 
	{
		if (!login())
			return;
		if (!fazerPedido())
			return;
		try 
		{
			semaforo.acquire();
			validarCompra();
		}
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		finally 
		{
			semaforo.release();
		}
	}
	
	private boolean login()
	{
		int tempo = mathRandom(50, 2000);
		metodoSleep(tempo);
		if (tempo > 1000) 
		{
			System.out.println("TIMEOUT!! NÃO FOI POSSIVEL INICIAR SESSÃO!");
			return false;
		}
		else
		{
			System.out.printf("SESSÃO #%d INICIADA\n", (int)getId());
			return true;
		}
	}

	private boolean fazerPedido()
	{
		int tempo = mathRandom(1000, 3000);
		metodoSleep(tempo);
		if (tempo > 2500) 
		{
			//time out
			System.out.println("TIMEOUT!! SESSÃO ENCERRADA!");
			return false;
		}
		else
		{
			pedido = mathRandom(1, 4);
			System.out.printf("#%d - PEDIDO DE %d INGRESSOS FEITO COM SUCESSO!\n", (int)getId(), pedido);
			return true;
		}
	}
	
	private void validarCompra ()
	{
		System.out.printf("#%d - ", (int)getId());
		if (total_ingressos >= pedido)
		{
			total_ingressos -= pedido;
			System.out.printf("VENDA CONCLUÍDA!\n VOCÊ COMPROU %d INGRESSOS", pedido);
			if (total_ingressos == 0)
				System.out.printf(" E RESTAM %d DISPONIVEIS!\n", total_ingressos);
			else
				System.out.println(" E NÃO HÁ MAIS INGRESSOS DIPONIVEIS!");
		}
		else
			System.out.println("NÃO HÁ MAIS INGRESSOS DIPONIVEIS!");
		System.out.println("SESSÃO ENCERRADA!");
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
