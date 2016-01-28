package br.edu.unisinos.pgr.lcenteleghe.utils;

import javax.swing.JOptionPane;

import br.edu.unisinos.pgr.lcenteleghe.position.Point;


public class BallisticUtils {

	
	// Observações:
	// velocidadeInicial, forcaGravitacional -> metros por segundo
	// tempoInicial, instanteDesejado -> segundos
	// angle -> ângulo em graus
	 
	//Cálculo da posição do objeto
	public static Point calculatePosition(float xo, float yo,  float angle,  float velocidadeInicial, 
			double tempoInicial, double instanteDesejado, float forcaGravitacional, float pesoObjeto){
		if(angle>360 || angle<0 || tempoInicial < 0 || velocidadeInicial < 0 || instanteDesejado < 0
				|| pesoObjeto < 0)
			throw new IllegalArgumentException();
		
		
		float x = (float)pontoX(velocidadeInicial, tempoInicial, angle, instanteDesejado, pesoObjeto);
		float y = (float)pontoY(velocidadeInicial, tempoInicial, angle, instanteDesejado, forcaGravitacional, pesoObjeto);
		Point pontoNoInstante = new Point(x+xo, y+yo);
		//JOptionPane.showMessageDialog(null, pontoNoInstante.getY());
		//System.out.println(pontoNoInstante.getY());
		return pontoNoInstante;
	}
	
	//Cálculo da posição do objeto no eixo x
	public static double pontoX(double velocidadeInicial, double tempoInicial, float angle, double instanteDesejado,
			float pesoObjeto){
		try{
			double pontoX = 0;
			float angulo = (float)Math.toRadians(65);
			//float angulo = angle;
			double cosseno = Math.cos(angulo);
			//double cosseno = Math.cos(65);
			double tempo = instanteDesejado-tempoInicial;
			//pontoX = velocidadeInicial*tempo*cosseno*Math.cbrt(pesoObjeto);
			pontoX = velocidadeInicial*tempo*cosseno;
			//System.out.println("pontoX: "+pontoX);
			return pontoX;
		}
		catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "erro no x");
			return 0;
		}
	}
	
	//Cálculo da posição do objeto no eixo y
	public static double pontoY(double velocidadeInicial, double tempoInicial, float angle, double instanteDesejado, 
			float forcaGravitacional, float pesoObjeto){
		try{
			double pontoY = 0;
			//JOptionPane.showMessageDialog(null, angle);
			//System.out.println(angle);
			double angulo = Math.toRadians(angle);
			//double angulo = Math.toRadians(65);
			//float angulo = angle;
			double seno = Math.sin(angulo);
			//double cosseno = Math.cos(angulo);
			double tempo = (double)instanteDesejado-(double)tempoInicial;
			//pontoY = velocidadeInicial*tempo*seno
				//	-0.5*forcaGravitacional*(tempo*tempo)*Math.cbrt(pesoObjeto);
			pontoY = (velocidadeInicial*tempo*seno)-(0.5*forcaGravitacional*(tempo*tempo));
			//pontoY = velocidadeInicial*seno*(tempo/velocidadeInicial*cosseno) - 
				//	((forcaGravitacional*tempo*tempo)/2*(velocidadeInicial*velocidadeInicial*cosseno*cosseno));
			
			return pontoY;
			
		}
		catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}
	
	
}
