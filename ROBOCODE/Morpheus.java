package teamsandman;
import robocode.*;


import robocode.HitByBulletEvent;
import robocode.ScannedRobotEvent;
import robocode.TeamRobot;

import java.awt.*;
import java.io.IOException;


// API help : https://robocode.sourceforge.io/docs/robocode/robocode/Robot.html

/**
 * Morpheus - a robot by Luis, Eder and Raniel
 */
public class Morpheus extends TeamRobot
{
	/**
	 * run: baseado no Robo Leader and Walls
	 */
	boolean peek; // nao muda se tiver robo
	double moveAmount; // How quantidade de movimento
	

	public void run() {
		// API de Cor
		RobotColors c = new RobotColors();

		c.bodyColor = Color.black;
		c.gunColor = Color.black;
		c.radarColor = Color.black;
		c.scanColor = Color.yellow;
		c.bulletColor = Color.white;

		// Seta cor
		setBodyColor(c.bodyColor);
		setGunColor(c.gunColor);
		setRadarColor(c.radarColor);
		setScanColor(c.scanColor);
		setBulletColor(c.bulletColor);
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
			//vira
			turnRight(90);
		}
	}

	/**
	 * onScannedRobot:  What to do when you see another robot
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		// não atira nos colegas, retorna imediatamente!
		if (isTeammate(e.getName())) {
			return;
		}			
		
		// calcula distância
		double enemyBearing = this.getHeading() + e.getBearing();
		// calcula distância
		double enemyX = getX() + e.getDistance() * Math.sin(Math.toRadians(enemyBearing));
		double enemyY = getY() + e.getDistance() * Math.cos(Math.toRadians(enemyBearing));

		try {
			// manda posição para os droids
			// não atira para não gastar stamina, cabe aos droids que tem mais energia
			broadcastMessage(new Point(enemyX, enemyY));
		} catch (IOException ex) {
			out.println("Unable to send order: ");
			ex.printStackTrace(out);
		}
		
		if (peek) {
			scan();
		}
	}

	/**
	 * onHitByBullet:  Turn perpendicular to bullet path
	 */
	
	// muda o sentido se tomar um tiro, pro outro lado da parede
	public void onHitByBullet(HitByBulletEvent e) {
		turnLeft(90 - e.getBearing());
	}
}
