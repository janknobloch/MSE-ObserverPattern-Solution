package de.tum.jk.behavioural.observer.concrete;

import de.tum.jk.behavioural.observer.abstractobserver.ChatObserver;

public class EmojiObserver implements ChatObserver {

	@Override
	public void update(String message) {
		if (message.contains(":)")) {
			System.out.println("emoji found");
		}

	}

}
