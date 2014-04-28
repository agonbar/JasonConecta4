// Environment code for project conecta4.mas2j

import jason.asSyntax.*;
import jason.environment.Environment;
import jason.environment.grid.GridWorldModel;
import jason.environment.grid.GridWorldView;
import jason.environment.grid.Location;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;
import java.util.logging.Logger;

public class blackboard extends Environment {
	
	public static final int GSize = 8; // tamaño tablero
	public static final int BLUE  = 16; // ficha azul
    public static final int RED  = 32; // ficha roja
	
	public static final Term    put = Literal.parseLiteral("put(X)");

    private Logger logger = Logger.getLogger("conecta4.mas2j."+blackboard.class.getName());
	
	private MarsModel model;
    private MarsView  view;
    
    @Override
    public void init(String[] args) {
        model = new MarsModel(); //crear un modelo
        view  = new MarsView(model); //crear la vista con ese modelo
        model.setView(view); //asociar 
    }
	
	@Override
    public boolean executeAction(String ag, Structure action) {
        logger.info(ag+" doing: "+ action);
        try {
            if (action.equals(put)) {
                model.put();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        updatePercepts();

        try {
            Thread.sleep(200);
        } catch (Exception e) {}
        informAgsEnvironmentChanged();
        return true;
    }
    

    void updatePercepts() {
        clearPercepts();
        
        Location r1Loc = model.getAgPos(0);
		Location r2Loc = model.getAgPos(1);
        
        Literal pos1 = Literal.parseLiteral("pos(r1," + r1Loc.x + "," + r1Loc.y + ")");

        addPercept(pos1);
        
    }

    class MarsModel extends GridWorldModel {
        

        private MarsModel() {
            super(GSize, GSize, 1);
			add(BLUE, 3, 0);
		}
		public void put() {
			//add(BLUE, 3, 0);
		}	
	}
	
	class MarsView extends GridWorldView {

        public MarsView(MarsModel model) {
            super(model, "Conecta4", 600);
            defaultFont = new Font("Arial", Font.BOLD, 18); // change default font
            setVisible(true);
            repaint();
        }
		
		public void draw(Graphics g, int x, int y, int object) {
            switch (object) {
                case blackboard.BLUE: drawFicha(g, x, y);  break;
            }
        }
		
        public void drawFicha(Graphics g, int x, int y) {
			g.setColor(Color.blue);
            g.fillOval(75*x,75*y,70,70);
        }
	}
}
