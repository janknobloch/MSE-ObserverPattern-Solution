package de.tum.jk.behavioural.observer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.text.DefaultCaret;
import javax.swing.text.JTextComponent;
import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.DeploymentException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

import org.glassfish.tyrus.client.ClientManager;

import de.tum.jk.behavioural.observer.subject.ChatSubject;

@ClientEndpoint
public class ChatEndpoint implements ActionListener {

	private JTextArea messages;
	private LinkedList<String> msgs = new LinkedList<String>();
	private JTextField sendField;
	private JButton sendButton;
	private ChatSubject chatsub;
	private Session session;
	private static final String WS_URL = "ws://onesandheroes.io:8025/websockets/chat";

	public ChatEndpoint(ChatSubject chatsub) {
		this.chatsub = chatsub;
		if (chatsub == null) {
			chatsub = new ChatSubject();
		}
		createUI();

		ClientManager client = ClientManager.createClient();
		try {
			client.connectToServer(this, new URI(WS_URL));

		} catch (DeploymentException | URISyntaxException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@OnOpen
	public void onOpen(Session session) {
		this.session = session;
	}

	@OnMessage
	public void onMessage(String message, Session session) {
		msgs.add(message);
		messages.setText(messages.getText() + message);
		chatsub.notifyObservers(message);
		// same as above
	}

	@OnClose
	public void onClose(Session session, CloseReason closeReason) {

	}

	private void createUI() {
		JFrame frame = new JFrame();
		messages = new JTextArea();
		DefaultCaret caret = (DefaultCaret) messages.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		JScrollPane pane = new JScrollPane(messages);
		pane.setAutoscrolls(true);
		sendButton = new JButton("Send Message");
		sendButton.addActionListener(this);
		sendField = new JTextField("send msg...");
		sendField.setSize(300, 300);
		sendField.setMinimumSize(new Dimension(300, 400));
		pane.setSize(800, 300);
		pane.setMinimumSize(new Dimension(800, 300));
		frame.setVisible(true);
		frame.setSize(600, 200);
		frame.setLayout(new BorderLayout());
		frame.add(pane, BorderLayout.CENTER);
		JPanel panne = new JPanel();
		panne.setLayout(new BorderLayout());
		panne.add(sendField, BorderLayout.CENTER);
		panne.add(sendButton, BorderLayout.EAST);
		frame.add(pane, BorderLayout.CENTER);
		frame.add(panne, BorderLayout.SOUTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			session.getBasicRemote().sendText(sendField.getText() + "\n");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
}