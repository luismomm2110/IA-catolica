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

// subclasse de droid, não tem scanner, mas tem mais estamina
// recebe ordem do Leader
public class Matthew extends TeamRobot implements Droid {

	/**
	 * run: baseado no MyFirstDroid and Walls
	 */
	boolean peek; // nao muda se tiver robo
	double moveAmount; // How quantidade de movimento

	public void run() {
		// usa o máximo de movimento possível.
		moveAmount = Math.max(getBattleFieldWidth(), getBattleFieldHeight());
		// inicialmente não tem nenhum robo, peek é falso
		peek = false;

		// vira à esquerda pra parede
		turnLeft(getHeading() % 90);
		ahead(moveAmount);
		// Vira arma pra direita
		peek = true;
		turnGunRight(90);
		turnRight(90);

		while (true) {
			peek = true;
			// se move junto à parede
			ahead(moveAmount);
			peek = false;
			// vira
			turnRight(90);
		}
	}

	/**
	 * onMessageReceived: Quando receber mensagem do líder Morpheus
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

	// muda o sentido se tomar um tiro, pro outro lado da parede
	public void onHitByBullet(HitByBulletEvent e) {
		turnLeft(90 - e.getBearing());
	}
}
