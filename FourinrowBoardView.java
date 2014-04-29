
import jason.environment.grid.GridWorldView;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FourinrowBoardView extends GridWorldView {
        private static final Logger logger = Logger.getLogger("conecta4.mas2j." + GridWorldView.class.getName());
        
        private static final String window_title = "Conecta 4";
        private static final int window_width = 800;
        
        public FourinrowBoardView (FourinrowBoardModel model) {
            super(model, window_title, window_width);
            
            this.defaultFont = new Font("Arial", Font.BOLD, 18);
            
            setVisible(true);
            
            repaint();
        }
	
        @Override
        public void draw(Graphics g, int x, int y, int id) {
            logger.log(Level.INFO, "Pintando ficha " + id);
            
            if (id == FourinrowChip.BLUE.ordinal()) {
                drawFicha(g, x, y, Color.BLUE);
            }
            else if (id == FourinrowChip.RED.ordinal()) {
                drawFicha(g, x, y, Color.RED);
            }
        }
        
        public void drawFicha(Graphics g, int x, int y, Color col) {
            g.setColor(col);
            g.fillOval(x,y,70,70);
        }
    }
