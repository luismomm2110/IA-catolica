package teamsandman;
import robocode.*;


import robocode.Droid;
import robocode.MessageEvent;
import robocode.TeamRobot;
import static robocode.util.Utils.normalRelativeAngleDegrees;


// API help : https://robocode.sourceforge.io/docs/robocode/robocode/Robot.html

/**
 * Matthew - a robot by Luis, Eder e Raniel
 */

// subclasse de droid, não pode se mexer, mas tem mais estamina
// recebe ordem do Leader
public class Matthew extends TeamRobot implements Droid
{
	
	public void run() {
		out.println("Matthews ready.");
	}

	/**
	 * onMessageReceived:  Quando receber mensagem do líder Matthews
	 */
	public void onMessageReceived(MessageEvent e) {
		// atira conforme mensagem
		// droid responsável por tiro porque tem mais estamina
		if (e.getMessage() instanceof Point) {
			Point p = (Point) e.getMessage();
			// Calculate x and y do alvo
			double dx = p.getX() - this.getX();
			double dy = p.getY() - this.getY();
			// Calculate angulo do alvo
			double theta = Math.toDegrees(Math.atan2(dx, dy));

			
			turnGunRight(normalRelativeAngleDegrees(theta - getGunHeading()));
			// Atira com o máximo de potência mesmo gastando estamina
			fire(3);
		} // se for mensagem de cor
		else if (e.getMessage() instanceof RobotColors) {
			RobotColors c = (RobotColors) e.getMessage();

			setBodyColor(c.bodyColor);
			setGunColor(c.gunColor);
			setRadarColor(c.radarColor);
			setScanColor(c.scanColor);
			setBulletColor(c.bulletColor);
		}
	}
}
