package de.tum.jk.behavioural.observer.subject;

import java.util.LinkedList;

import de.tum.jk.behavioural.observer.abstractobserver.ChatObserver;

public class ChatSubject {

	private LinkedList<ChatObserver> observerCollection = new LinkedList<ChatObserver>();

	public void registerObserver(ChatObserver co) {
		observerCollection.add(co);
	}

	public void unregisterObserver(ChatObserver co) {
		observerCollection.remove(co);
	}

	public void notifyObservers(String message) {

		for (ChatObserver co : observerCollection) {
			co.update(message);
		}
	}
}
