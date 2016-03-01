package de.unisaarland.edutech.conceptmapping;

import java.util.ArrayList;
import java.util.List;

public class CollaborativeString implements Cloneable {

	private class Position {

		public Position(int length, User u) {
			this.length = length;
			this.u = u;
		}

		int length;
		User u;

		@Override
		public String toString() {
			return "Position Length: " + length;
		}
	}

	private List<Position> positions = new ArrayList<Position>();
	private StringBuilder builder = new StringBuilder();

	private User owner;

	public CollaborativeString(User owner) {
		this(owner, "");
	}

	public CollaborativeString(User owner, String content) {
		this.owner = owner;
		if (content.length() > 0)
			append(owner, content);

	}

	public User getOwner() {
		return owner;
	}

	public int length() {
		return builder.length();
	}

	public CollaborativeString append(User u, String content) {
		return insert(u, builder.length(), content);
	}

	public CollaborativeString insert(User u, int index, String content) {
		Position positionToInsert = new Position(content.length(), u);
		builder.insert(index, content);
		return insertPosition(index, positionToInsert);
	}

	private CollaborativeString insertPosition(int index, Position positionToInsert) {

		int currentEndingIndex = 0;

		for (int i = 0; i < positions.size(); i++) {
			Position p = positions.get(i);

			currentEndingIndex += p.length;

			if (currentEndingIndex > index) {
				return splitAndInsert(index, positionToInsert, currentEndingIndex, i, p);
			} else if (index == currentEndingIndex) {
				return insertBetweenPositions(index, positionToInsert, i);
			}

		}
		positions.add(positionToInsert);

		return this;
	}

	private CollaborativeString insertBetweenPositions(int index, Position positionToInsert, int i) {
		positions.add(i + 1, positionToInsert);
		return this;
	}

	private CollaborativeString splitAndInsert(int insertionIndex, Position pNew, int contentLength, int i,
			Position positionToSplit) {
		Position pLeft = null;
		int charsLeftOfInsertionPoint = insertionIndex - (contentLength - positionToSplit.length);
		if (charsLeftOfInsertionPoint > 0) {
			pLeft = new Position(charsLeftOfInsertionPoint, positionToSplit.u);
		}

		Position pRight = positionToSplit;
		pRight.length = pRight.length - charsLeftOfInsertionPoint;

		positions.add(i, pNew);
		if (pLeft != null)
			positions.add(i, pLeft);
		return this;
	}

	public CollaborativeString remove(int startIndex, int count) {
		builder.delete(startIndex, startIndex + count);

		int intervalStart = 0;
		boolean isSameP = true;
		boolean doDelete = false;

		for (int i = 0; i < positions.size(); i++) {
			Position p = positions.get(i);

			int intervalEnd = intervalStart + p.length;

			// left interval start found
			if (!doDelete && startIndex < intervalEnd) {
				int remainingLeft = startIndex - intervalStart;
				p.length = remainingLeft;
				doDelete = true;
			}
			// right interval end found
			if (startIndex + count <= intervalEnd) {
				int remainingRight = intervalEnd - (startIndex + count);
				// in case we remove a subinterval of a larger interval
				p.length = (isSameP) ? p.length + remainingRight : remainingRight;

				if (p.length == 0)
					positions.remove(i);

				return this;
			}
			if (doDelete) {
				positions.remove(i);
				i--;
			}
			intervalStart = intervalEnd;
			isSameP = false;
		}
		return this;
	}

	public CollaborativeString removeLast(int count) {
		return remove(length() - count, count);
	}

	public String getContent() {
		return builder.toString();
	}

	public String toDebugString() {
		StringBuilder debugBuilder = new StringBuilder();
		int startInverval = 0;
		int endInterval = 0;

		for (Position p : positions) {

			endInterval += p.length;
			String openTag = "<" + p.u.getName() + ">";
			String closeTag = "</" + p.u.getName() + ">";

			CharSequence part = builder.subSequence(startInverval, endInterval);

			debugBuilder.append(openTag).append(part).append(closeTag);

			startInverval = endInterval;
		}

		return debugBuilder.toString();
	}

	@Override
	public CollaborativeString clone() {
		CollaborativeString s;
		try {
			s = (CollaborativeString) super.clone();

			s.builder = new StringBuilder(this.builder.toString());
			s.positions = new ArrayList<>();

			for (Position p : this.positions)
				s.positions.add(new Position(p.length, p.u));

			return s;
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}
}
