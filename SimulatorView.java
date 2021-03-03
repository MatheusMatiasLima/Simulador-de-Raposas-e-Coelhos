import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.HashMap;

/**
 * Uma interface grafica do grid de simulacao
 * A tela mostra um retangulo colorido de cada localizacao
 * representando seus conteudos. Usa uma cor de fundo padrao.
 * Cores para cada tipo de espécies podem ser definidas usando o
 * metodod setColor.
 * 
 * @author David J. Barnes and Michael Kolling
 * @version 2002-04-23
 */
public class SimulatorView extends JFrame
{
    // Cor usada para localizacoes vazias.
    private static final Color EMPTY_COLOR = Color.white;

    // Cor usada para objetos que nao possuem cor definida.
    private static final Color UNKNOWN_COLOR = Color.gray;

    private final String STEP_PREFIX = "Step: ";
    private final String POPULATION_PREFIX = "Population: ";
    private JLabel stepLabel, population;
    private JButton pauseButton;
    private JPanel southPanel;
    private FieldView fieldView;

    private boolean pause = false;
    
    // Um mapa para guardar as cores dos participantes da simulacao
    private HashMap colors;
    // Um objeto de estatisticas que computa e guarda as informacoes da simulacao
    private FieldStats stats;
    private Simulator simulator;

    /**
     * Cria uma tela com os dados de largura e altura.
     */
    public SimulatorView(int height, int width, Simulator simulator)
    {
        this.simulator = simulator;
        stats = new FieldStats();
        colors = new HashMap();

        setTitle("Simulacao de Raposas, Coelhos e Jacares");
        stepLabel = new JLabel(STEP_PREFIX, JLabel.CENTER);
        population = new JLabel(POPULATION_PREFIX, JLabel.CENTER);

        pauseButton = new JButton("PAUSE");
        configureButtonEvent();
        
        setLocation(100, 50);
        
        fieldView = new FieldView(height, width);



        southPanel = new JPanel(new GridLayout(2, 1));
        southPanel.add(population);
        southPanel.add(pauseButton);

        Container contents = getContentPane();
        contents.add(stepLabel, BorderLayout.NORTH);
        contents.add(fieldView, BorderLayout.CENTER);
        contents.add(southPanel, BorderLayout.SOUTH);
        pack();
        setVisible(true);
    }

    private void configureButtonEvent()
    {
    	pauseButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
                if(pause)
                    simulator.resume();
                else
                    simulator.suspend();
                pause = !pause;
            }
        });
    }

    /**
     * Defini a cor para ser usada em cada classe de animal.
     */
    public void setColor(Class animalClass, Color color)
    {
        colors.put(animalClass, color);
    }

    /**
    * Defini a cor para ser usada em cada classe de animal.
    */
    private Color getColor(Class animalClass)
    {
        Color col = (Color)colors.get(animalClass);
        if(col == null) {
            // cor nao definida para a classe
            return UNKNOWN_COLOR;
        }
        else {
            return col;
        }
    }

    /**
     * Mostra o atual estado do campo.
     * @param step Qual interacao esta.
     * @param stats Estado do campo para ser representando.
     */
    public void showStatus(int step, Field field)
    {
        if(!isVisible())
            setVisible(true);

        stepLabel.setText(STEP_PREFIX + step);

        stats.reset();
        fieldView.preparePaint();
            
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                Object animal = field.getObjectAt(row, col);
                if(animal != null) {
                    stats.incrementCount(animal.getClass());
                    fieldView.drawMark(col, row, getColor(animal.getClass()));
                }
                else {
                    fieldView.drawMark(col, row, EMPTY_COLOR);
                }
            }
        }
        stats.countFinished();

        population.setText(POPULATION_PREFIX + stats.getPopulationDetails(field));
        fieldView.repaint();
    }

    /**
     * Determina se a simulacao deve continuar executando.
     * @return true se possui mais de uma especie viva.
     */
    public boolean isViable(Field field)
    {
        return stats.isViable(field);
    }
    
    /**
     * Proporciona uma interface grafica de um campo retangular. Isto e
     * uma classe dentro de uma classse que define componentes customizados para a
     * interface de usuario. Este componente mostra o campo.
     * Isto sao coisas avancadas da GUI - voce pode ignorar isto
     * para seu projeto se voce quiser.
     */
    private class FieldView extends JPanel
    {
        private final int GRID_VIEW_SCALING_FACTOR = 6;

        private int gridWidth, gridHeight;
        private int xScale, yScale;
        Dimension size;
        private Graphics g;
        private Image fieldImage;

        /**
         * Cria um novo componente de campo de visao.
         */
        public FieldView(int height, int width)
        {
            gridHeight = height;
            gridWidth = width;
            size = new Dimension(0, 0);
        }

        /**
         * Diz o gerenciador da GUI o qual grande deve ser.
         */
        public Dimension getPreferredSize()
        {
            return new Dimension(gridWidth * GRID_VIEW_SCALING_FACTOR,
                                 gridHeight * GRID_VIEW_SCALING_FACTOR);
        }
        
        /**
         * Prepara para uma nova rodada de desenho. Para que o componente
         * posso ser redimenzionada, calcula o fator de escala novamente.
         */
        public void preparePaint()
        {
            if(! size.equals(getSize())) {  // se o tamanho tiver mudado...
                size = getSize();
                fieldImage = fieldView.createImage(size.width, size.height);
                g = fieldImage.getGraphics();

                xScale = size.width / gridWidth;
                if(xScale < 1) {
                    xScale = GRID_VIEW_SCALING_FACTOR;
                }
                yScale = size.height / gridHeight;
                if(yScale < 1) {
                    yScale = GRID_VIEW_SCALING_FACTOR;
                }
            }
        }
        
        /**
         * Desenha no grid de localizacao neste campo em uma cor dada.
         */
        public void drawMark(int x, int y, Color color)
        {
            g.setColor(color);
            g.fillRect(x * xScale, y * yScale, xScale-1, yScale-1);
        }

        /**
         * O componente da tele do campo precisa ser mostrado novamente. Copia
         * imagem interna da tela.
         */
        public void paintComponent(Graphics g)
        {
            if(fieldImage != null) {
                g.drawImage(fieldImage, 0, 0, null);
            }
        }
    }
}
