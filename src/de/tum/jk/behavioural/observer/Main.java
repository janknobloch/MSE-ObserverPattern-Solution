package de.tum.jk.behavioural.observer;
import java.awt.BorderLayout;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.websocket.DeploymentException;

import org.glassfish.tyrus.client.ClientManager;

import de.tum.jk.behavioural.observer.concrete.BadwordObserver;
import de.tum.jk.behavioural.observer.concrete.EmojiObserver;
import de.tum.jk.behavioural.observer.subject.ChatSubject;

public class Main {

	public static void main(String[] args) throws InterruptedException {

		// Create a ChatSubject
		ChatSubject chatsub = new ChatSubject();
		// Create ChatObserver Interface

		// Create BadwordObserver
		BadwordObserver bwo = new BadwordObserver();
		// Create EmojiObserver
		EmojiObserver emoji = new EmojiObserver();
		// Add Observer to Chatsubject
		chatsub.registerObserver(bwo);
		chatsub.registerObserver(emoji);

		// set ChatSubject onto ChatEndPoint

		
		ChatEndpoint chatEndpoint = new ChatEndpoint(chatsub);

	}

}
