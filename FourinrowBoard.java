import jason.NoValueForVarException;
import jason.asSyntax.NumberTerm;
import jason.asSyntax.Structure;
import jason.environment.Environment;
import jason.environment.grid.GridWorldModel;
import jason.environment.grid.GridWorldView;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FourinrowBoard extends Environment {
    /* Tamaño del tablero */
    private static final int GSize = 8;
    /* Fichas del juego */
    private enum Ficha { RED, BLUE };
    
    /* Nombres de los agentes */
    private final String JugadorPrimario = "JugadorPrimario";
    private final String JugadorSecundario = "JugadorSecundario";

    private static final Logger logger = Logger.getLogger("conecta4.mas2j." + FourinrowBoard.class.getName());
	
    private FourinrowModel model;
    private FourinrowView  view;
    
    @Override
    public void init(String[] args) {
        /* Crear un modelo */
        model = new FourinrowModel();
        
        /* Crear la vista con ese modelo */
        view  = new FourinrowView(model);
        
        /* Asociar la vista con el modelo */
        model.setView(view);
    }
	
    @Override
    public boolean executeAction(String ag, Structure action) {
        logger.log(Level.INFO, "El agente <{0}> ejecuta la acción: {1}", new Object[]{ag, action});
        
        try {
            if (action.getFunctor().equals("put")) {
                int x = (int)((NumberTerm)action.getTerm(0)).solve();
                
                switch (ag) {
                    case JugadorPrimario:
                        model.put(Ficha.BLUE, x);
                        break;
                    case JugadorSecundario:
                        model.put(Ficha.RED, x);
                        break;
                    default:
                        logger.log(Level.SEVERE, "No se reconoce el nombre del agente: {0}", ag);
                        break;
                }
            }
            else {
                return false;
            }
        }
        catch (NoValueForVarException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        
        /* Se espera un tiempo para evitar errores inesperados */
        try {
            Thread.sleep(200);
        }
        catch (InterruptedException e) {}
        
        /* TODO: Comprobar si el juego ha finalizado */
        
        informAgsEnvironmentChanged();
        
        return true;
    }

    class FourinrowModel extends GridWorldModel {
        private final Logger logger = Logger.getLogger("conecta4.mas2j." + FourinrowModel.class.getName());
        
        private FourinrowModel() {
            super(GSize, GSize, 2);
        }
        
        public void put(Ficha f, int x) {
            int p = getHeight();

            while (p >= 0 && !isFree(x, p)) {
                p--;
            }

            if (p < 0){
                logger.log(Level.SEVERE, "Se está intentando insertar una ficha en una columna completa");
            }
            else {
                this.add(f.ordinal(), x, p);
                
                try {
                    Thread.sleep(500);
                }
                catch (InterruptedException e) { }
            }
        }
    }
	
    class FourinrowView extends GridWorldView {
        public FourinrowView (FourinrowModel model) {
            super(model, "Conecta4", 600);
            
            defaultFont = new Font("Arial", Font.BOLD, 18);
            
            setVisible(true);
            
            repaint();
        }
	
        @Override
        public void draw(Graphics g, int x, int y, int object) {
            if (object == Ficha.BLUE.ordinal()) {
                drawFicha(g, x, y, Color.BLUE);
            }
            else if (object == Ficha.RED.ordinal()) {
                drawFicha(g, x, y, Color.RED);
            }
        }
        
        public void drawFicha(Graphics g, int x, int y, Color col) {
            g.setColor(col);
            g.fillOval(x,y,70,70);
        }
    }
}

