package de.julian.eyetimer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

public class EyeTimer {

  private static Timer eyeTimer = new Timer("EyeTimer");
  private static Timer pauseTimer = new Timer("PauseTimer");
  private static JFrame jFrame;

  public static void main(String[] args) {
    openStartGUI();
  }

  private static void openStartGUI() {
    jFrame = new JFrame("EyeTimer");
    final JPanel jPanel = new JPanel();

    JButton jButton = new JButton("Start");
    jButton.setBackground(Color.GREEN);
    jButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jFrame.setVisible(false);
        openStopGUI();
        startEyeTimer();
      }
    });

    jPanel.add(jButton);
    jPanel.setBackground(Color.DARK_GRAY);
    jFrame.add(jPanel);
    jFrame.setSize(300, 100);
    jFrame.setLocationRelativeTo(null);
    jFrame.setVisible(true);
  }

  private static void openStopGUI() {
    jFrame = new JFrame("EyeTimer");
    final JPanel jPanel = new JPanel();

    JButton jButton = new JButton("Stop");
    jButton.setBackground(Color.RED);
    jButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jFrame.setVisible(false);
        stopTimers();
      }
    });

    jPanel.add(jButton);
    jPanel.setBackground(Color.DARK_GRAY);
    jFrame.add(jPanel);
    jFrame.setSize(300, 100);
    jFrame.setLocationRelativeTo(null);
    jFrame.setVisible(true);
  }

  private static void stopTimers() {
    pauseTimer.cancel();
    eyeTimer.cancel();
    System.exit(0);
  }

  private static void startEyeTimer() {
    TimerTask timerTask = new TimerTask() {
      @Override
      public void run() {
        SystemTray tray = SystemTray.getSystemTray();

        Image image = Toolkit.getDefaultToolkit().createImage("61ryXWxyb2L.png");

        TrayIcon trayIcon = new TrayIcon(image, "EyeTimer");
        trayIcon.setImageAutoSize(true);

        try {
          jFrame.toFront();
          tray.add(trayIcon);
          trayIcon.displayMessage("Augen Pause", "Bitte schließe deine Augen für 2 Minuten. Ich stoppe die Zeit", TrayIcon.MessageType.WARNING);
          startPauseTimer();
        } catch (AWTException e) {
          e.printStackTrace();
        }
      }
    };

    eyeTimer.schedule(timerTask, 3600000);
  }

  private static void startPauseTimer() {
    TimerTask timerTask = new TimerTask() {
      @Override
      public void run() {
        SystemTray tray = SystemTray.getSystemTray();

        Image image = Toolkit.getDefaultToolkit().createImage("61ryXWxyb2L.png");

        TrayIcon trayIcon = new TrayIcon(image, "PauseTimer");
        trayIcon.setImageAutoSize(true);

        try {
          tray.add(trayIcon);
          trayIcon.displayMessage("Augen Pause", "Du kannst deine Augen öffnen", TrayIcon.MessageType.INFO);
          startEyeTimer();
        } catch (AWTException e) {
          e.printStackTrace();
        }
      }
    };

    pauseTimer.schedule(timerTask, 600000);
  }

}
