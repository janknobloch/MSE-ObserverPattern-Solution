package de.tum.jk.behavioural.observer.concrete;

import de.tum.jk.behavioural.observer.abstractobserver.ChatObserver;

public class BadwordObserver implements ChatObserver {

	@Override
	public void update(String message) {
		if (message.contains("fuck")) {
			System.out.println("badword found");
		}

	}

}
