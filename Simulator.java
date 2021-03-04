import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collections;
import java.awt.Color;

/**
 * Um simples simulador de predador-presa, baseado em um campo contendo
 * coelhos, raposas e jacares.
 * 
 * @author David J. Barnes and Michael Kolling
 * @version 2002-04-09
 */
public class Simulator extends Thread {
    // As variaveis finais estaticas privadas representam informacoes de configuracao para a simulacao.

    // A largura padrao da grade.
    private static final int DEFAULT_WIDTH = 150;
    // A profundidade padrao da grade.
    private static final int DEFAULT_DEPTH = 100;
    // A probabilidade de que uma raposa seja criada em qualquer posicao da grade.
    //private static final double FOX_CREATION_PROBABILITY = 0.02;
    // A probabilidade de um coelho ser criado em qualquer posição da grade.
    //private static final double RABBIT_CREATION_PROBABILITY = 0.08;    

    // A etapa atual da simulacao.
    private int step;

    //Quantidade de delay ao executar cada passo da simulaçao
    private long delay = 0;

    private PopulationGenerator populacao;
    
   
    // Constroi um campo de simulacao com tamanho padrao.
    public Simulator() {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH);
    }
    
    /**
     * Crie um campo de simulacao com o tamanho fornecido.
     * @param depth Profundidade do campo. Deve ser maior que zero. 
     * @param width Largura do campo. Deve ser maior que zero.
     */
    public Simulator(int depth, int width) {
        if(width <= 0 || depth <= 0) {
            System.out.println("As dimensoes devem ser maiores do que zero.");
            System.out.println("Usando valores padroes.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }
        populacao = new PopulationGenerator(depth, width, this);
        // Configure um ponto de partida valido.
        reset();
    }

    @Override
    public void run()
    {
        runLongSimulation();
        super.run();
    }

    //Execute a simulacao de seu estado atual por um periodo razoavelmente longo, por exemplo. 500 passos.
    public void runLongSimulation() {
        simulate(50000);
    }
    
     
    // Execute a simulacao de seu estado atual para um determinado numero de etapas.
    // Pare antes de um determinado numero de etapas se deixar de ser viavel.
    public void simulate(int numSteps) {
        for(int step = 1; step <= numSteps && populacao.getView().isViable(populacao.getField()); step++) {
            try
            {
                Thread.sleep(delay);
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
            simulateOneStep();
        }
    }
    

    // Execute a simulacao de seu estado atual para uma unica etapa.
    // Repita em todo o campo atualizando o estado de cada raposa e coelho.


    public void simulateOneStep() {
        step++;
        populacao.getNewActorsList().clear();
        
        // deixe todos os animais agirem
        for(Iterator<Actor> iter = populacao.getActorsList().iterator(); iter.hasNext(); ) {
            Actor actor = iter.next();
            if (actor.isAtive()) {
                actor.act(populacao.getField(), populacao.getUpdatedField(), populacao.getNewActorsList());
            }
            else {
                iter.remove();
            }
        }
        // adiciona animais recem-nascidos a lista de animais
        populacao.getActorsList().addAll(populacao.getNewActorsList());
        
        // Troque o campo e updatedField no final da etapa.
        Field temp = populacao.getField();
        populacao.setField(populacao.getUpdatedField());
        populacao.setUpdatedField(temp);
        populacao.getUpdatedField().clear();

        // exibir o novo campo na tela
        populacao.getView().showStatus(step, populacao.getField());
    }
        
    //Reseta a simulacao para uma posicao inicial.
    public void reset()
    {
        step = 0;
        populacao.getActorsList().clear();
        populacao.getField().clear();
        populacao.getUpdatedField().clear();
        populacao.populate(populacao.getField());
        
        // Mostra a etapa inicial na tela.
        populacao.getView().showStatus(step, populacao.getField());
    }

    public long getDelay()
    {
        return delay;
    }

    public void setDelay(long delay)
    {
        this.delay = delay;
    }
    
/*
    // Povoe o campo com raposas e coelhos.
    private void populate(Field field) {
        Random rand = new Random();
        field.clear();
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                if(rand.nextDouble() <= FOX_CREATION_PROBABILITY) {
                    Fox fox = new Fox(true);
                    actors.add(fox);
                    fox.setLocation(row, col);
                    field.place(fox, row, col);
                }
                else if(rand.nextDouble() <= RABBIT_CREATION_PROBABILITY) {
                    Rabbit rabbit = new Rabbit(true);
                    actors.add(rabbit);
                    rabbit.setLocation(row, col);
                    field.place(rabbit, row, col);
                }
                // caso contrário, deixe o local vazio.
            }
        }
        Collections.shuffle(actors);
    }
*/
}
